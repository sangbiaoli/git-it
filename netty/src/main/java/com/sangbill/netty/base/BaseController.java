package com.sangbill.netty.base;

import com.sangbill.netty.base.interceptor.VisitorInterceptor;
import com.sangbill.netty.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    protected HttpSession getSession() {
        if (request == null) {
            return null;
        }
        return request.getSession();
    }

    protected User getUser() {
        HttpSession session = getSession();
        if (session == null)
            return null;
        return (User) session.getAttribute(VisitorInterceptor.SESSION_USER);
    }

}