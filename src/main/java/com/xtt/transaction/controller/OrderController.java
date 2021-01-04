package com.xtt.transaction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/order")
public class OrderController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello world";
    }
}
