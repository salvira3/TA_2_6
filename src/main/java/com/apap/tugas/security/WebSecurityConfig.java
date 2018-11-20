package com.apap.tugas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/tambah").permitAll()
			.antMatchers("/user/addUser").permitAll()
			.antMatchers("/api/daftar-ranap").permitAll()
			.antMatchers("/daftar-request/**").hasAnyAuthority("ADMIN")
			.antMatchers("/daftar-ranap").hasAnyAuthority("ADMIN")
			.antMatchers("/penanganan/**").hasAnyAuthority("ADMIN", "DOKTER")
			.antMatchers("/obat/**").hasAnyAuthority("ADMIN")
			.antMatchers("/jadwal-jaga").hasAnyAuthority("ADMIN")
			.antMatchers("/kamar/**").hasAnyAuthority("ADMIN")
			
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
			.permitAll();
	}
	
	/*
	 *
	 * 
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.passwordEncoder(encoder())
			.withUser("hehe").password(encoder().encode("hehe"))
			.roles("USER");
	}
	*/
	
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configAuthetication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}
	
	

}