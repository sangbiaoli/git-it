package com.sangbill.netty.web;

import com.sangbill.netty.base.BaseController;
import com.sangbill.netty.base.Result;
import com.sangbill.netty.domain.dto.LoginDTO;
import com.sangbill.netty.domain.vo.ChatItemVO;
import com.sangbill.netty.service.ChatService;
import com.sangbill.netty.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chat")
public class ChatController extends BaseController {

    @Resource
    private ChatService chatService;

    @PostMapping("/loadChatItem")
    public Result loadChatItem(){
        Result rs = chatService.loadChatItem(getUser());
        return rs;
    }

    @PostMapping("/loadChatHis")
    public Result loadChatHis(@RequestBody ChatItemVO itemVO){
        Result rs = chatService.loadChatHis(getUser(), itemVO);
        return rs;
    }

}

