package com.example.app.service;

import java.util.List;

import com.example.app.domain.Event;
import com.example.app.domain.Level;
import com.example.app.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService extends UserDetailsService {

	List<User> getUserList() throws Exception;

	User findUserByLoginId(String loginId);

	void addUser(User user) throws Exception;

	void editUser(User user) throws Exception;

	void deleteUser(Integer userId) throws Exception;

	List<Level> getLevelList() throws Exception;



}
