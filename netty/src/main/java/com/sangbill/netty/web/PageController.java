package com.sangbill.netty.web;

import com.sangbill.netty.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/page")
public class PageController extends BaseController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
