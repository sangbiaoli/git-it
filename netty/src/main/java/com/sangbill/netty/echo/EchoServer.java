package com.sangbill.netty.echo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup(); //创建EventLoopGroup
        try {
            ServerBootstrap b = new ServerBootstrap(); //创建ServerBootstrap
            b.group(group)
            .channel(NioServerSocketChannel.class) //指定NIO传输的使用Channel
            .localAddress(new InetSocketAddress(port)) //使用指定的端口设置套接字地址
            .childHandler(new ChannelInitializer<SocketChannel>() { //增加了一个EchoServerHandler到通道的ChannelPipeline
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(serverHandler);
                }
            });
            ChannelFuture f = b.bind().sync(); //异步绑定服务器;sync()等待绑定完成。
            f.channel().closeFuture().sync(); //获取通道的closeFuture并阻塞当前线程，直到它完成为止
        } finally {
            group.shutdownGracefully().sync(); //关闭EventLoopGroup，释放所有资源
        }
    }
}