package com.sangbill.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sangbill.dubbo.DubboService;


@Service
public class DubboServiceImpl implements DubboService {

	public String sayHello(String name) {
		return name;
	}
}