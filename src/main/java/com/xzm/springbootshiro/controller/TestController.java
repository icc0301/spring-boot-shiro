package com.xzm.springbootshiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String index(){
        return "欢迎来到小猪迷Spring Boot Shiro";
    }
}
