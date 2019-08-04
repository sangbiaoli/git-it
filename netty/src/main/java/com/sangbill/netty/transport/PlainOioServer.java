package com.sangbill.netty.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class PlainOioServer {
	public void serve(int port) throws IOException {
		final ServerSocket socket = new ServerSocket(port);//server到指定的端口
		try {
			for (;;) {
				final Socket clientSocket = socket.accept();//接入一个连接
				System.out.println("Accepted connection from " + clientSocket);
				new Thread(new Runnable() {  //创建一个新线程处理连接
					@Override
					public void run() {
						OutputStream out;
						try {
							out = clientSocket.getOutputStream();
							out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8"))); //往已连接的客户端写消息
							out.flush();
							clientSocket.close(); //关闭客户端
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								clientSocket.close();
							} catch (IOException ex) {
								// ignore on close
							}
						}
					}
				}).start(); //启动线程
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}