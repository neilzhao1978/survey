package com.neil.survey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neil.survey.module.User;
import com.neil.survey.repository.UserRepository;
import com.neil.survey.service.ILoginService;
@Service
@Transactional
public class LoginService implements ILoginService {

	@Autowired
	private UserRepository userRepo;

	public boolean verifyLogin(User user) {
		List<User> userList = userRepo.findByEmailAndPsw(user.getEmail(), user.getPsw());
		return userList.size() > 0;
	}
}
