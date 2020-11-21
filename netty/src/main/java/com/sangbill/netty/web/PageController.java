package com.sangbill.netty.web;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/page")
public class PageController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
