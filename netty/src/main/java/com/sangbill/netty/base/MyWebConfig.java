package com.sangbill.netty.base;

import com.sangbill.netty.base.interceptor.DomainInterceptor;
import com.sangbill.netty.base.interceptor.VisitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
public class MyWebConfig extends WebMvcConfigurationSupport {

	@Resource
	private VisitorInterceptor visitorInterceptor;
	@Resource
	private DomainInterceptor domainInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(domainInterceptor).addPathPatterns("/**");
		registry.addInterceptor(visitorInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/page/login");
	}

	/**
	 * 这里有个坑，SpringBoot2 必须重写该方法，否则静态资源无法访问
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}

}
