package com.sangbill.netty.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.logging.Logger;

public class ChatWebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger logger = Logger.getLogger(WebSocketServerHandshaker.class.getName());
	private WebSocketServerHandshaker handshaker;


	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

	}
}