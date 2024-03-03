package com.ispan.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ispan.demo.model.Users;
import com.ispan.demo.model.UsersRepository;

@Service
public class UserService {
	
	@Autowired
	private UsersRepository userRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	public Users addUser(Users users) {
		users.setPassword(pwdEncoder.encode(users.getPassword()));
		return userRepo.save(users);
	}
	
	public boolean checkIfUsernameExist(String username) {
		Users dbUser = userRepo.findByUsername(username);
		
		if(dbUser != null) {
			return true;
		}else {
			return false;
		}
	}
	
	public Users checkLogin(String username, String pwd)  {
		Users dbUser = userRepo.findByUsername(username);
		
		if(dbUser != null) {
			            // 要驗證的字串, 資料庫內的字串(加密過的)         
			if(pwdEncoder.matches(pwd, dbUser.getPassword())) {
			   return dbUser;
			}
		}
		
		return null;
	}

}