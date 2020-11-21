package com.sangbill.netty.base.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DomainInterceptor implements HandlerInterceptor {
    

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));  
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
		response.setHeader("Access-Control-Max-Age", "3600");  
		response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, Accept, Authorization");  
		response.setHeader("Access-Control-Allow-Credentials", "true");  
		response.setHeader("XDomainRequestAllowed","1");
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}
	
}
