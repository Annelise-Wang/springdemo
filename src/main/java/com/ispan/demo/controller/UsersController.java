package com.ispan.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ispan.demo.model.Users;
import com.ispan.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	
	@Autowired
	private UserService uService;
	
	@GetMapping("/users/add")
	public String goRegistePage() {
		return "users/register";
	}
	
	@PostMapping("/users/post")
	public String addNewUser(
			@RequestParam("uname") String username ,
			@RequestParam("psw") String password,
			Model model) {
		boolean isExist = uService.checkIfUsernameExist(username);
		
		if(isExist) {
			model.addAttribute("errorMsg", "已經有此帳號");
		}else {
			Users u1 = new Users();
			u1.setUsername(username);
			u1.setPassword(password);
			
			uService.addUser(u1);
			model.addAttribute("okMsg", "註冊成功");
		}
		
		return "users/register";
	}
	
	@GetMapping("/users/login")
	public String loginPage() {
		return "users/loginPage";
	}
	
	@PostMapping("/users/login")
	public String login(@RequestParam("uname") String username ,
			@RequestParam("psw") String password,HttpSession httpSession,Model model) {
		Users result = uService.checkLogin(username, password);
		
		if(result != null) {
			System.out.println("登入成功");
			httpSession.setAttribute("loginUser", result);
		}else {
			System.out.println("登入失敗");
			model.addAttribute("loginFail", "帳號不存在或帳號密碼錯誤");
		}
		
		return "users/loginPage";
	}
	@GetMapping("/users/logout")
    public String logoutPage() {
        return "users/logoutPage";
    }

    @PostMapping("/users/logout")
    public String logout(HttpSession session) {
        // 清除 session 中的登录用户信息
    	session.invalidate();
        // 重定向到首页
        return "redirect:/";
    }   

}
