package com.sangbill.dubbo.service;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sangbill.dubbo.DubboService;



/**
 * @author LQB
 * 2018年8月4日
 * 
 */
@Component
public class DubboConsumerService {

    @Reference
    DubboService dubboService;

    public void printMsg() {
        System.out.println(dubboService.sayHello("abd"));
    }
}