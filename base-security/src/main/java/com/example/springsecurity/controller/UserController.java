package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user api 必须登录以后，才能进行操作
 */
@RestController
@RequestMapping("/user/api")
public class UserController {

    @GetMapping("/hello")
    public String hello(){
        return "hello, user";
    }

}
