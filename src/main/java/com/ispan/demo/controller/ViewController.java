package com.ispan.demo.controller;

import java.io.IOException;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ViewController {

    @RequestMapping("/login")
    public String login() {
        return "users/login";
    }

    @RequestMapping({ "/indexPage", "/" })
    public String indexPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "index";
    }
    
//    @PostMapping("/logout")
//    public String logout() {
//        SecurityContextHolder.clearContext();
//        return "users/login";
//    }
    
    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception =
                (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            if (exception != null) {
                response.getWriter().write(exception.toString());
            } else {
                response.getWriter().write("Login failed with unknown error.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class ErrorController {

        @RequestMapping("/error")
        public String handleError() {
            return "error";
        }
    }

}