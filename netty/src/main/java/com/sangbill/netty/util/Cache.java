package com.sangbill.netty.util;

import com.sangbill.netty.domain.entity.Group;
import com.sangbill.netty.domain.entity.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

public class Cache {
    private static List<User> userList;
    private static List<Group> groupList;

    private static Map<Integer,User> userMap;
    private static Map<String,User> userNameMap;
    private static Map<Integer,Group> groupMap;


    public static void init(){
        loadUser();
        loadGroup();
        setFriend();
        setGroup();
    }

    private static void setGroup() {
        setGroup(1,Arrays.asList(2,3,4,5,6));
        setGroup(2,Arrays.asList(3,5,6,10));
        setGroup(3,Arrays.asList(6,7,9));
        setGroup(4,Arrays.asList(7,8,9));
    }

    private static void setGroup(int groupId, List<Integer> userIdList) {
        Group group = groupMap.get(groupId);
        for(int i = 0;i < userIdList.size();i++){
            User temp = userMap.get(userIdList.get(i));
            group.getUserList().add(temp);
            temp.getGroupList().add(group);
        }
    }

    private static void setFriend() {
        setFriend(1,Arrays.asList(2,3,4,5,6));
        setFriend(2,Arrays.asList(3,5,6,10));
        setFriend(3,Arrays.asList(6,7,9));
        setFriend(4,Arrays.asList(7,8,9));
    }

    private  static void setFriend(int userId, List<Integer> friendsId) {
        User user = userMap.get(userId);
        for(int i = 0;i < friendsId.size();i++){
            User temp = userMap.get(friendsId.get(i));
            user.getFriend().add(temp);
            temp.getFriend().add(user);
        }
    }

    private static void loadGroup() {
        groupList = new ArrayList<>();
        groupMap = new HashMap<>();
        for (int i = 0;i < 20;i++){
            Group group = new Group(i+1,"group"+(i+1));
            groupList.add(group);
            groupMap.put(group.getId(),group);
        }
    }

    private static void loadUser() {
        userList = new ArrayList<>();
        userMap = new HashMap<>();
        userNameMap = new HashMap<>();
        for (int i = 0;i < 20;i++){
            User user = new User(i+1,"user"+(i+1),"123456");
            userList.add(user);
            userMap.put(user.getId(),user);
            userNameMap.put(user.getUsername(),user);
        }
    }


    public static User getUser(String username) {
        return userNameMap.get(username);
    }
}
