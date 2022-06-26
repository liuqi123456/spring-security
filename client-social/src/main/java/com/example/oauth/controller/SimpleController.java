package com.example.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SimpleController {

    /**
     * principal对象由spring框架自动注入，表示当前登录的用户
     * @param principal
     * @return
     */
    @GetMapping("/hello")
    public String hello(Principal principal){
        return "hello, " + principal.getName();
    }

}
