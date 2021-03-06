package com.sangbill.netty.base.conf;

import com.sangbill.netty.base.interceptor.DomainInterceptor;
import com.sangbill.netty.base.interceptor.VisitorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
public class MyWebConfig extends WebMvcConfigurationSupport {

	@Resource
	private VisitorInterceptor visitorInterceptor;
	@Resource
	private DomainInterceptor domainInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(domainInterceptor).addPathPatterns("/**");
		registry.addInterceptor(visitorInterceptor).addPathPatterns("/admin/**").excludePathPatterns(
			Arrays.asList("/admin/page/login","/admin/user/doLogin")
		);
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
