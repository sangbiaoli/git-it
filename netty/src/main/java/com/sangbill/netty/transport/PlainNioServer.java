package com.sangbill.netty.transport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class PlainNioServer {
	public void serve(int port) throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		ServerSocket ssocket = serverChannel.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		ssocket.bind(address); //绑定server到已选择的端口
		Selector selector = Selector.open();//为要处理的通道打开selector
		serverChannel.register(selector, SelectionKey.OP_ACCEPT); //注册ServerSocket到Selectro上以便介入连接
		final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
		for (;;) {
			try {
				selector.select(); //等待新事件
			} catch (IOException ex) {
				ex.printStackTrace();
				// handle exception
				break;
			}
			Set<SelectionKey> readyKeys = selector.selectedKeys(); //获取所有到来的事件的SelectionKey
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				try {
					if (key.isAcceptable()) {  //检查新连接是否准备好接入
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
						System.out.println("Accepted connection from " + client);
					}
					if (key.isWritable()) {  //检查新连接是否准备写
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						while (buffer.hasRemaining()) {
							if (client.write(buffer) == 0) { //往已连接的客户端写入数据
								break;
							}
						}
						client.close(); //关闭连接
					}
				} catch (IOException ex) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException cex) {
						// ignore on close
					}
				}
			}
		}
	}
}