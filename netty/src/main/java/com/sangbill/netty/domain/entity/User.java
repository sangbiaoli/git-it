package com.sangbill.netty.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private Integer id;
    private String username;
    private String passwd;

    private List<User> friend;
    private List<Group> groupList;

    public User(int id, String username, String passwd) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
        friend = new ArrayList<>();
        groupList = new ArrayList<>();
    }
}
