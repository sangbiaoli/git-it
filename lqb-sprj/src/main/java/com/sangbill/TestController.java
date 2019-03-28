/* 
 * Copyright (c) 2018, CENTRIN.CIYUN.LTD. All rights reserved.
 */
package com.sangbill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO(描述这个类的作用) 
 * @author liqiangbiao
 * @date 2018年9月29日
 */

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private DomainUrlUtil urlUtil;
    
    @RequestMapping("/index")
    public  String index(){
        return urlUtil.getUrl();
    }
}
