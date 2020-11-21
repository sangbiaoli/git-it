package com.sangbill.netty.domain.entity;

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

    private List<User> userList;

    public Group(int id, String groupName) {
        this.id = id;
        this.groupName = groupName;
        userList = new ArrayList<>();
    }
}
