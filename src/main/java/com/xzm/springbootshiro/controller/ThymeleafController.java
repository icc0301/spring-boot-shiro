package com.xzm.springbootshiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ThymeleafController {

    @GetMapping("/thymeleaf")
    public String getThymeleaf(){
        return "index";
    }
}