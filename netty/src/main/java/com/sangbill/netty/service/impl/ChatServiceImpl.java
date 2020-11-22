package com.sangbill.netty.service.impl;

import com.sangbill.netty.base.Result;
import com.sangbill.netty.domain.dto.LoginDTO;
import com.sangbill.netty.domain.entity.Group;
import com.sangbill.netty.domain.entity.User;
import com.sangbill.netty.domain.vo.ChatContentVO;
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
        return Result.success("成功",user.getChatItems());
    }

    @Override
    public Result loadChatHis(User user, ChatItemVO itemVO) {
        if(checkChatId(user,itemVO)){
            return Result.success("成功", Cache.getChatList(itemVO.getChatId()));
        }else{
            return Result.fail("失败");
        }
    }

    @Override
    public Result send(User user, ChatContentVO contentVO) {
        ChatItemVO itemVO = new ChatItemVO();
        itemVO.setChatId(contentVO.getChatId());
        if(checkChatId(user,itemVO)){
            contentVO.setUser(user);
            Cache.addChat(contentVO);
            return Result.success("成功");
        }else{
            return Result.fail("失败");
        }
    }
    
    private boolean checkChatId(User user, ChatItemVO itemVO) {
        Integer groupId = itemVO.parseGroupId();
        Integer[] userIds = itemVO.parseUserIds();
        if(groupId != null){
            return user.checkGroup(groupId);
        }else if(userIds != null){
            return user.checkFriend(userIds);
        }else{
            return false;
        }
    }


}
