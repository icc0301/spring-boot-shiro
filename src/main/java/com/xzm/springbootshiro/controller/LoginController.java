package com.xzm.springbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/doLogin")
    public void doLogin(String username,String password){
        System.out.println(username);
        System.out.println(password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        System.out.println(token);
        // 获取当前的 Subject. 调用 SecurityUtils.getSubject();
        Subject currentUser = SecurityUtils.getSubject();
        // 执行登入
        currentUser.login(token);
        System.out.println("登入成功");
    }
}
