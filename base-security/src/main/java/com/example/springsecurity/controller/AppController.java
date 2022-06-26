package com.example.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 面向客户端公开访问的api
 */
@RestController
@RequestMapping("/app/api")
public class AppController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String hello(){
        return "hello, app";
    }

    @GetMapping("/create")
    public String create(String password){
        return passwordEncoder.encode(password);
    }

}
