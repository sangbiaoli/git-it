package com.sangbill.netty.service.impl;

import com.sangbill.netty.domain.entity.User;
import com.sangbill.netty.domain.vo.LoginDTO;
import com.sangbill.netty.domain.vo.Result;
import com.sangbill.netty.service.UserService;
import com.sangbill.netty.util.Cache;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Result login(LoginDTO loginDTO) {
        User user = Cache.getUser(loginDTO.getUsername());
        if(user == null || !user.getPasswd().equals(loginDTO.getPasswd())){
            return Result.fail("登录失败");
        }
        return Result.success("登录成功",user);
    }
}
