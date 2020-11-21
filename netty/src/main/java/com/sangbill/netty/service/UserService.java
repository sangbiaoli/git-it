package com.sangbill.netty.service;

import com.sangbill.netty.domain.vo.LoginDTO;
import com.sangbill.netty.domain.vo.Result;

public interface UserService {
    Result login(LoginDTO loginDTO);
}
