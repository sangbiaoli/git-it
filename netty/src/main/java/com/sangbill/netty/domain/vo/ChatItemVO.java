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
    private Integer userId1;
    private Integer userId2;
    private Integer groupId;

    /**
     * 保证id小的在前面
     * @param friend
     * @param userId
     */
    public ChatItemVO(UserVO friend,Integer userId){
        this.name = friend.getUsername();
        if(friend.getId() > userId){
            this.userId1 = userId;
            this.userId2 = friend.getId();
        }else{
            this.userId1 = friend.getId();
            this.userId2 = userId;
        }
        this.chatId = String.format("u%s_u%s",userId1,userId2);
    }

    public ChatItemVO(GroupVO groupVO){
        this.name = groupVO.getGroupName();
        this.groupId = groupVO.getId();
        this.chatId = "g"+groupVO.getId();
    }

    public ChatItemVO(String chatId) {
        this.setChatId(chatId);
    }

    public void setChatId(String chatId){
        this.chatId = chatId;
        parse();
    }

    public void parse() {
        if(this.chatId == null){
            return;
        }

        if(this.chatId.startsWith("g")){
            this.groupId = Integer.parseInt(this.chatId.split("g")[1]);
        }else if(this.chatId.startsWith("u")){
            String[] s = this.chatId.split("_");
            this.userId1 = Integer.parseInt(s[0].split("u")[1]);
            this.userId2 = Integer.parseInt(s[1].split("u")[1]);
        }
    }
}
