package com.sangbill.netty.netty;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NettyServerExecutor {
	public NettyServerExecutor() {
		System.err.println("---------- Spring自动加载netty开始   ---------- ");
		NettyServer server = new NettyServer();
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(server);
		System.err.println("---------- Spring自动加载netty完成  ---------- ");
	}
}
