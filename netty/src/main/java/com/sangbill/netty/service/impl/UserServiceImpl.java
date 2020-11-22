package com.sangbill.netty.service.impl;

import com.sangbill.netty.domain.dto.LoginDTO;
import com.sangbill.netty.domain.entity.User;
import com.sangbill.netty.base.Result;
import com.sangbill.netty.domain.vo.ChatItemVO;
import com.sangbill.netty.service.UserService;
import com.sangbill.netty.util.Cache;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Result loadChatItem(User user) {
        List<ChatItemVO> list = new ArrayList();
        for(int i = 0;i < user.getFriend().size();i++){
            list.add(new ChatItemVO(user.getFriend().get(i)));
        }
        for(int i = 0;i < user.getGroupList().size();i++){
            list.add(new ChatItemVO(user.getGroupList().get(i)));
        }
        return Result.success("成功",list);
    }
}
