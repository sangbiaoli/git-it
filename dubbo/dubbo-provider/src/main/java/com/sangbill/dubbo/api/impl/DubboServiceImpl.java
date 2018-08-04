package com.sangbill.dubbo.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sangbill.dubbo.api.DubboService;


@Service(version = "1.0.0")
public class DubboServiceImpl implements DubboService {

	public String sayHello(String name) {
		return name;
	}
}