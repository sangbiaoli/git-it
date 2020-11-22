package com.sangbill.netty.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatItemVO {
    private String name;
    private String chatId;

    /**
     * 保证id小的在前面
     * @param friend
     * @param userId
     */
    public ChatItemVO(UserVO friend,Integer userId){
        this.name = friend.getUsername();
        if(friend.getId() > userId){
            this.chatId = "u"+userId+"_u"+friend.getId();
        }else{
            this.chatId = "u"+friend.getId()+"_u"+userId;
        }
    }

    public ChatItemVO(GroupVO groupVO){
        this.name = groupVO.getGroupName();
        this.chatId = "g"+groupVO.getId();
    }

    public Integer parseGroupId() {
        if(this.chatId.startsWith("g")){
            return Integer.parseInt(this.chatId.split("g")[1]);
        }else{
            return null;
        }
    }

    public Integer[] parseUserIds() {
        if(this.chatId.startsWith("u")){
            String[] s = this.chatId.split("_");
            return new Integer[]{
                Integer.parseInt(s[0].split("u")[1]),
                Integer.parseInt(s[1].split("u")[1])
            };
        }else{
            return null;
        }
    }
}
