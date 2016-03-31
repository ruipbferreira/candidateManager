package com.interviewmanagement.main.dao;

import java.util.List;

import com.interviewmanagement.main.model.User;

public interface UsersDAO {
	User getUserByUsername(User user);
    List<User> listUsers();
	User getUserById(int id);
	Integer createOrUpdateUser(User user);
	void removeUser(int id);
}
