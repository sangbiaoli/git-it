package com.sangbill.dubbo.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sangbill.dubbo.api.DubboService;

/**
 * 城市业务 Dubbo 服务层实现层
 *
 * Created by bysocket on 28/02/2017.
 */
// 注册为 Dubbo 服务
@Service(version = "1.0.0")
public class DubboServiceImpl implements DubboService {

	public String sayHello(String name) {
		return name;
	}
}