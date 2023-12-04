package com.example.app.mapper;

import java.util.List;

import com.example.app.domain.User;
import java.util.Optional;

public interface UserMapper {

	List<User> selectAll() throws Exception;

	User selectByUserId(Integer userId) throws Exception;

	Optional<User> selectByLoginId(String loginId);

	void insert(User user) throws Exception;

	void update(User user) throws Exception;

	void delete(Integer userId) throws Exception;

}
