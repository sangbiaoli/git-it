package com.sangbill.netty.base.interceptor;

import com.sangbill.netty.domain.entity.User;
import com.sangbill.netty.util.DateUtil;
import com.sangbill.netty.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 管理台请求拦截器：
 * 1.验证会话是否失效
 * @author ouanui
 *
 */
@Slf4j
@Component
public class VisitorInterceptor implements HandlerInterceptor {
	
	public static final String SESSION_USER = "SESSION_USER";
	private long beginTime;

	private String url;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {		
		String ip = IpUtil.getIpAddress(request);
		beginTime = System.currentTimeMillis();
		url = request.getRequestURI();
		log.info("开始处理请求:{},IP:{},时间:{},参数:{}", url, ip, DateUtil.date2String(new Date(),DateUtil.yyyy_MM_dd_HH_mm_ss), request.getQueryString());

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			response.sendRedirect("/admin/page/login");
			return false;
		}
		return true;
	}

	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		url = request.getRequestURI();
		log.info("完成处理请求:{},使用时间:{} ms.", url, (System.currentTimeMillis() - beginTime));
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}


}
