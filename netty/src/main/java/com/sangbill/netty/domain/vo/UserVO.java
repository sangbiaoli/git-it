package com.sangbill.netty.domain.vo;

import com.sangbill.netty.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVO {

    private Integer id;
    private String username;

    public UserVO(User temp) {
        this.id = temp.getId();
        this.username = temp.getUsername();
    }
}
