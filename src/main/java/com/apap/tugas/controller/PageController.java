package com.apap.tugas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apap.tugas.model.UserRoleModel;

@Controller
public class PageController {
	@RequestMapping("/")
	public String home(Model model) {
		UserRoleModel user = new UserRoleModel();
		model.addAttribute("user",user);
		return "home";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
	
}
