package com.paypal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/display")
    public String display(){
        System.out.println("In display method");
        return "In display method";
    }

    @GetMapping("/show")
    public String show(){
        System.out.println("In show method");
        return "In show method";
    }
}
