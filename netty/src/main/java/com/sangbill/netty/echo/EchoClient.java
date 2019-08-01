package com.sangbill.netty.echo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	private final String host;
	private final int port;

	public EchoClient(String host, int port) { //设置服务器的InetSocketAddress
		this.host = host;
		this.port = port;
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host> <port>");
			return;
		}
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		new EchoClient(host, port).start();
	}
	
	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();//创建Bootstrap
			b.group(group)	//指定处理客户端事件的EventLoopGroup；需要实现NIO。
			.channel(NioSocketChannel.class) //通道类型是NIO传输的类型
			.remoteAddress(new InetSocketAddress(host, port))
			.handler(new ChannelInitializer<SocketChannel>() {	//在创建通道时向管道添加EchoClientHandler
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new EchoClientHandler());
				}
			});
			ChannelFuture f = b.connect().sync();
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
}
