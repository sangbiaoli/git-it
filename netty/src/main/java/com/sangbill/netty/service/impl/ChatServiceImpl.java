package com.sangbill.netty.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sangbill.netty.base.Result;
import com.sangbill.netty.domain.entity.Group;
import com.sangbill.netty.domain.entity.User;
import com.sangbill.netty.domain.vo.ChatContentVO;
import com.sangbill.netty.domain.vo.ChatItemVO;
import com.sangbill.netty.domain.vo.UserVO;
import com.sangbill.netty.netty.ChatWebSocketParam;
import com.sangbill.netty.netty.NettyGlobal;
import com.sangbill.netty.service.ChatService;
import com.sangbill.netty.util.Cache;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {


    @Override
    public Result loadChatItem(User user) {
        return Result.success("成功",user.getChatItems());
    }

    @Override
    public Result loadChatHis(User user, ChatItemVO itemVO) {
        if(checkChatId(user,itemVO)){
            return Result.success("成功", Cache.getChatList(itemVO.getChatId()));
        }else{
            return Result.fail("失败");
        }
    }

    @Override
    public Result send(User user, ChatContentVO contentVO) {
        ChatItemVO itemVO = new ChatItemVO(contentVO.getChatId());
        if(checkChatId(user,itemVO)){
            contentVO.setUser(user);
            Cache.addChat(contentVO);
            this.chatNotify(contentVO,itemVO);
            return Result.success("成功");
        }else{
            return Result.fail("失败");
        }
    }


    public void chatNotify(ChatContentVO contentVO, ChatItemVO itemVO) {
        Integer userId = contentVO.getUser().getId();
        if(itemVO.getGroupId() != null){
            Group group = Cache.getGroup(itemVO.getGroupId());
            for(int i = 0;i < group.getUserList().size();i++){
                UserVO userVO = group.getUserList().get(i);
                if(userVO.getId() != userId){
                    channelNofity(contentVO,userVO.getId());
                }
            }
        }else if(itemVO.getUserId1() != null && itemVO.getUserId2() != null ){
            Integer friendId = (itemVO.getUserId1() == userId)?itemVO.getUserId2():itemVO.getUserId1();
            channelNofity(contentVO,friendId);
        }
    }

    /***
     * 根据用户id，查找对应channel，然后发送消息
     * @param contentVO
     * @param userId
     */
    public void channelNofity(ChatContentVO contentVO, Integer userId){
        ChatWebSocketParam param = new ChatWebSocketParam();
        param.setType(NettyGlobal.NettyMessageType.CHAT.key);
        param.setMessage("收到新消息");
        param.setChatId(contentVO.getChatId());
        param.setContent(contentVO.getContent());
        param.setUserId(contentVO.getUser().getId());
        String text = JSONObject.toJSONString(param);
        Channel channel = (Channel) NettyGlobal.channelMap.getKey(userId);
        if(channel!=null && channel.isActive()){
            channel.writeAndFlush(new TextWebSocketFrame(text));
        }
    }

    private boolean checkChatId(User user, ChatItemVO itemVO) {
        if(itemVO.getGroupId() != null){
            return user.checkGroup(itemVO.getGroupId());
        }else if(itemVO.getUserId1() != null && itemVO.getUserId2() != null ){
            return user.checkFriend(itemVO.getUserId1(),itemVO.getUserId2());
        }else{
            return false;
        }
    }

}
