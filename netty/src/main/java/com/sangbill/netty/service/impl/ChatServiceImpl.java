package com.sangbill.netty.service.impl;

import com.sangbill.netty.base.Result;
import com.sangbill.netty.domain.dto.LoginDTO;
import com.sangbill.netty.domain.entity.User;
import com.sangbill.netty.domain.vo.ChatItemVO;
import com.sangbill.netty.service.ChatService;
import com.sangbill.netty.service.UserService;
import com.sangbill.netty.util.Cache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {


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

    @Override
    public Result loadChatHis(User user, ChatItemVO itemVO) {
        return Result.success("成功", Collections.emptyList());
    }
}
