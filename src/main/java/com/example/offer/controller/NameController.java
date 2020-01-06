package com.example.offer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname NameController
 * @Description TODO
 * @Date 2020/1/6 13:05
 * @Created by shang
 */
@RequestMapping("/name")
@Controller
public class NameController {

    @RequestMapping("/get")
    public String get(){
        System.out.println("11111111111111111111111");
        return "/login/login.html";
    }

}
