package com.example.app.service;

import com.example.app.domain.Level;
import com.example.app.domain.User;
import com.example.app.mapper.LevelMapper;
import com.example.app.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private LevelMapper levelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> getUserList() throws Exception {
		return userMapper.selectAll();
	}

	@Override
	public User findUserByLoginId(String loginId) {
		return userMapper.selectByLoginId(loginId).orElse(null);
	}

	@Override
	public List<Level> getLevelList() throws Exception {
		return levelMapper.selectAll();
	}

	@Override
	public void addUser(User user) throws Exception {
		val hashedPassword = passwordEncoder.encode(user.getLoginPass());
		user.setLoginPass(hashedPassword);
		System.out.println(hashedPassword);
		System.out.println(user.getLoginPass());
		userMapper.insert(user);
	}

	@Override
	public void editUser(User user) throws Exception {
		userMapper.update(user);
	}

	@Override
	public void deleteUser(Integer userId) throws Exception {
		userMapper.delete(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		return userMapper.selectByLoginId(loginId)
				.orElseThrow(() -> new UsernameNotFoundException(loginId));
	}

}
