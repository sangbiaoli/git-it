package com.sangbill.netty.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

@Slf4j
public class ChatWebSocketInboundHandler extends SimpleChannelInboundHandler<Object> {

	private WebSocketServerHandshaker handshaker;

	/**
	 * 唯一要求要重写的方法
	 * @param ctx
	 * @param msg
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, ((FullHttpRequest) msg));
		} else if (msg instanceof WebSocketFrame) {
			WebSocketFrame webMsg = (WebSocketFrame) msg;
			// 判断是否关闭链路的指令
			if (msg instanceof CloseWebSocketFrame) {
				handshaker.close(ctx.channel(), (CloseWebSocketFrame) webMsg.retain());
				return;
			}
			// 判断是否ping消息
			if (msg instanceof PingWebSocketFrame) {
				ctx.channel().write(new PongWebSocketFrame(webMsg.content().retain()));
				return;
			}
			handlerWebSocketFrame(ctx, webMsg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		log.info("websocket客户端与服务端连接开启");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		NettyGlobal.channelMap.remove(ctx.channel().id());
		log.info("websocket客户端与服务端连接关闭");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
	}

	private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		log.info("execute handleHttpRequest(ChannelHandlerContext ctx, WebSocketFrame frame)");
		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			System.out.println("本例程仅支持文本消息，不支持二进制消息");
//			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
			return;
		}
		// 請求消息
		String request = ((TextWebSocketFrame) frame).text();
		log.info("服务端收到：" + request);
		if(StringUtils.equals(request, "ping")){//心跳处理
			return;
		}
		ChatWebSocketParam nettyData = JSONObject.parseObject(request, ChatWebSocketParam.class);
		if(nettyData != null){
			if(nettyData.getType() == NettyGlobal.NettyMessageType.OPEN_CHANNEL.key) {
				NettyGlobal.channelMap.put(ctx.channel(),nettyData.getUserId());
				nettyData.setMessage("成功绑定 channel");
				ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(nettyData)));
			}
		}

	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		log.info("execute handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req)");
		if (!req.getDecoderResult().isSuccess()
				|| (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://localhost:8081/websocket", null, false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory
					.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx,
										 FullHttpRequest req, DefaultFullHttpResponse res) {
		// 返回应答给客户端
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
					CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
		}
		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private static boolean isKeepAlive(FullHttpRequest req) {
		return false;
	}
}