package com.sangbill.netty.util;

import com.sangbill.netty.domain.entity.Group;
import com.sangbill.netty.domain.entity.User;
import com.sangbill.netty.domain.vo.ChatContentVO;
import com.sangbill.netty.domain.vo.GroupVO;
import com.sangbill.netty.domain.vo.UserVO;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class Cache {
    private static List<User> userList = new ArrayList<>();
    private static List<Group> groupList = new ArrayList<>();

    private static Map<Integer,User> userMap = new HashMap<>();
    private static Map<String,User> userNameMap = new HashMap<>();
    private static Map<Integer,Group> groupMap = new HashMap<>();
    private static Map<String, List<ChatContentVO>> chatMap = new HashMap<>();

    public static void init(){
        loadUser();
        loadGroup();
        setFriend();
        setGroup();
        setUserChatItem();
    }

    private static void setUserChatItem() {
        for(int i = 0;i < userList.size();i++){
            userList.get(i).initChatItem();
        }
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
            group.getUserList().add(new UserVO(temp));
            temp.getGroups().add(new GroupVO(group));
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
            user.getFriends().add(new UserVO(temp));
            temp.getFriends().add(new UserVO(user));
        }
    }

    private static void loadGroup() {
        for (int i = 0;i < 20;i++){
            Group group = new Group(i+1,"group"+(i+1));
            groupList.add(group);
            groupMap.put(group.getId(),group);
        }
    }

    private static void loadUser() {
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

    public static List<ChatContentVO> getChatList(String chatId) {
        List<ChatContentVO> list = chatMap.get(chatId);
        return (CollectionUtils.isEmpty(list))?Collections.emptyList():list;
    }

    public static void addChat(ChatContentVO contentVO) {
        List<ChatContentVO> list = chatMap.get(contentVO.getChatId());
        if(list == null){
            list = new ArrayList<>();
        }
        list.add(contentVO);
        chatMap.put(contentVO.getChatId(),list);
    }

    public static Group getGroup(Integer groupId) {
        return groupMap.get(groupId);
    }
}
