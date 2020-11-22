package com.sangbill.netty.netty;

import lombok.Data;

@Data
public class ChatWebSocketParam {
    private Integer type;
    private Integer userId;
    private String chatId;
    private String message;
    private String content;
}
