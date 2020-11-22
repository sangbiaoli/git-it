package com.sangbill.netty.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatItemVO {
    private Integer id;
    private String name;
    private Integer type;
    private String chatId;

    public ChatItemVO(UserVO userVO){
        this.id = userVO.getId();
        this.name = userVO.getUsername();
        this.type = 1;
        this.chatId = this.type+"_"+this.id;
    }

    public ChatItemVO(GroupVO groupVO){
        this.id = groupVO.getId();
        this.name = groupVO.getGroupName();
        this.type = 2;
        this.chatId = this.type+"_"+this.id;
    }
}
