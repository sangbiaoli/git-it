package com.sangbill.netty.domain.vo;

import com.sangbill.netty.domain.entity.Group;
import lombok.Data;

@Data
public class ChatItemVO {
    private Integer id;
    private String name;
    private Integer type;

    public ChatItemVO(UserVO userVO){
        this.id = userVO.getId();
        this.name = userVO.getUsername();
        this.type = 1;
    }

    public ChatItemVO(GroupVO groupVO){
        this.id = groupVO.getId();
        this.name = groupVO.getGroupName();
        this.type = 2;
    }
}
