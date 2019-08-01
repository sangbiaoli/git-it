package com.sangbill.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Sharable // 将该类标记为实例可在通道之间共享的类
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	@Override
	public void channelActive(ChannelHandlerContext ctx) { // 当通知通道处于活动状态时，发送一条消息
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) { // 记录接收到的消息的转储
		System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // 在异常情况下，记录错误并关闭通道
		cause.printStackTrace();
		ctx.close();
	}
}