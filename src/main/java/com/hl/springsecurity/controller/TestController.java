package com.hl.springsecurity.controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @PostMapping("/toMain")
    public  String toMain(){
        return "redirect:success.html";
    }

    @PostMapping("/toError")
    public  String toError(){
        return "redirect:error.html";
    }

    @RequestMapping("/toLogin")
    public  String toLogin(){
        return "login";
    }

    @RequestMapping("/toDemo")
    public  String toDemo(){
        return "demo";
    }
}
