package com.sangbill.netty.service;

import com.sangbill.netty.base.Result;
import com.sangbill.netty.domain.dto.LoginDTO;
import com.sangbill.netty.domain.entity.User;
import com.sangbill.netty.domain.vo.ChatContentVO;
import com.sangbill.netty.domain.vo.ChatItemVO;

public interface ChatService {

    Result loadChatItem(User user);

    Result loadChatHis(User user, ChatItemVO itemVO);

    Result send(User user, ChatContentVO contentVO);

}
