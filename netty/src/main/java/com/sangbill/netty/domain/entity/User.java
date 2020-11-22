package com.sangbill.netty.domain.entity;

import com.sangbill.netty.domain.vo.ChatItemVO;
import com.sangbill.netty.domain.vo.GroupVO;
import com.sangbill.netty.domain.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User{

    private Integer id;
    private String username;
    private String passwd;

    private List<UserVO> friends;
    private List<GroupVO> groups;
    private List<ChatItemVO> chatItems = new ArrayList();


    public User(int id, String username, String passwd) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
        friends = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public void initChatItem() {
        chatItems = new ArrayList();
        for(int i = 0; i < this.getFriends().size(); i++){
            chatItems.add(new ChatItemVO(this.getFriends().get(i),this.id));
        }
        for(int i = 0; i < this.getGroups().size(); i++){
            chatItems.add(new ChatItemVO(this.getGroups().get(i)));
        }
    }

    public boolean checkGroup(Integer groupId) {
        for (GroupVO g:this.groups) {
            if(g.getId() == groupId){
                return true;
            }
        }
        return false;
    }

    public boolean checkFriend(Integer[] userIds) {
        if(userIds[0] == userIds[1]){
            return false;
        }
        if(userIds[0] != this.id && userIds[1] != this.id){
            return false;
        }
        Integer friendId = (userIds[0] == this.id)?userIds[1]:userIds[0];
        for (UserVO u:this.friends) {
            if(u.getId() == friendId){
                return true;
            }
        }
        return false;
    }
}
