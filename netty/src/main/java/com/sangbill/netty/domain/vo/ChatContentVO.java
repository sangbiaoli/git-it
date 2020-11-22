package com.sangbill.netty.domain.vo;

import com.sangbill.netty.domain.entity.User;
import lombok.Data;

@Data
public class ChatContentVO {
    private String chatId;
    private String content;
    private User user;
}
