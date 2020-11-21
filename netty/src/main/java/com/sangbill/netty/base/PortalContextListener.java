package com.sangbill.netty.base;

import com.sangbill.netty.util.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


/**
 * 
 * Spring启动完成之后执行 实现其他的一些初始化动作
 * 
 */
public  class PortalContextListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
        Cache.init();
	}
}
