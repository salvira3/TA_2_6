package com.apap.tugas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tugas.model.UserRoleModel;
import com.apap.tugas.repository.UserRoleDb;


@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleDb userDb; 
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}

	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	@Override
	public UserRoleModel findUserByUsername(String username) {
		
		return userDb.findByUsername(username);
	}

}
