package com.ispan.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping({ "/index", "/" })
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "userdetails";
    }
    
    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "/login";
    }
}