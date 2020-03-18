package com.example.offer.controller.skip;

import com.example.offer.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname NameController
 * @Description TODO
 * @Date 2020/1/6 13:05
 * @Created by MaHC
 */
@Controller
public class NameController extends BaseController {

    @RequestMapping("/get")
    public String get(){
        System.out.println("11111111111111111111111");
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
        return "/login.html";
    }

    @RequestMapping("/main")
    public String main(){
        return "/main.html";
    }

    @RequestMapping("/default500")
    public String default500(){
        return "/default/500.html";
    }

    @RequestMapping("/customer")
    public String customer(){
        return "/default/welcome.html";
    }


}
