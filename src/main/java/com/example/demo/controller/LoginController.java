package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }

    @PostMapping("/index")
    public String getIndexView() {
        return "index";
    }
}
