package com.xzm.springbootshiro.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String exception(Throwable ex) {
        ex.printStackTrace();
        System.out.println(ex.getMessage());
        return ex.getMessage();
    }
}
