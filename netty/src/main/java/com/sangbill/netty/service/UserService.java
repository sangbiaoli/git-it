package com.sangbill.netty.service;

import com.sangbill.netty.base.Result;
import com.sangbill.netty.domain.dto.LoginDTO;

public interface UserService {
    Result login(LoginDTO loginDTO);
}
