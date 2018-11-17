package com.apap.tugas.service;

import com.apap.tugas.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	UserRoleModel findUserByUsername(String username);
}
