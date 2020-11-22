package com.sangbill.netty.netty;

import org.apache.commons.collections.bidimap.DualHashBidiMap;

public class NettyGlobal {
    
    public  enum NettyMessageType{
        OPEN_CHANNEL(1,"打开通道"),
        CHAT(2,"发送聊天消息");
        
        public Integer key;
        public String desc;
        private NettyMessageType(Integer key,String desc){
            this.key = key;
            this.desc = desc;
        }
    }
    
	//userId，Channel
	public static DualHashBidiMap channelMap = new DualHashBidiMap();
	
 }