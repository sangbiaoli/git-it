package com.sangbill.netty.web;

import com.sangbill.netty.base.BaseController;
import com.sangbill.netty.base.interceptor.VisitorInterceptor;
import com.sangbill.netty.base.Result;
import com.sangbill.netty.domain.dto.LoginDTO;
import com.sangbill.netty.domain.entity.User;
import com.sangbill.netty.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @PostMapping("/doLogin")
    public Result doLogin(@RequestBody LoginDTO loginDTO, HttpServletRequest req, HttpServletResponse resp){
        Result rs = userService.login(loginDTO);
        if(rs.isOK()){
            afterSuccessLogin(req,resp,(User)rs.getData());
        }
        return rs;
    }

    private void afterSuccessLogin(HttpServletRequest req, HttpServletResponse resp,User user) {
        req.getSession().setAttribute(VisitorInterceptor.SESSION_USER,user);
        resp.addCookie(createCookie("userId",user.getId()+""));
        resp.addCookie(createCookie("username",user.getUsername()));
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key,value);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        return cookie;
    }

}
