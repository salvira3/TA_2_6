package com.apap.tugas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tugas.model.UserRoleModel;
import com.apap.tugas.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		String msg = "";
		userService.addUser(user);
		msg = "User berhasil dibuat. Login untuk melanjutkan.";
		model.addAttribute("msg",msg);
		
		return "tambah-user";
	}
}
