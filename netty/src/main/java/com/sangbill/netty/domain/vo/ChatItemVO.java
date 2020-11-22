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
