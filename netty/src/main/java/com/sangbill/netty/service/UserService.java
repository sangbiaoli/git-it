package com.sangbill.netty.service;

import com.sangbill.netty.base.Result;
import com.sangbill.netty.domain.dto.LoginDTO;
import com.sangbill.netty.domain.entity.User;

public interface UserService {
    Result login(LoginDTO loginDTO);

    Result loadChatItem(User user);
}
