package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 面向客户端公开访问的api
 */
@RestController
@RequestMapping("/app/api")
public class AppController {

    @GetMapping("/hello")
    public String hello(){
        return "hello, app";
    }

}
