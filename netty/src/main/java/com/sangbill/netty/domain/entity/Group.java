package com.sangbill.netty.domain.entity;

import com.sangbill.netty.domain.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Group {
    private Integer id;
    private String groupName;

    private List<UserVO> userList;

    public Group(int id, String groupName) {
        this.id = id;
        this.groupName = groupName;
        userList = new ArrayList<>();
    }
}
