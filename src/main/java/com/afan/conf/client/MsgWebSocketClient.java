package com.afan.conf.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
 
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
 
public class MsgWebSocketClient extends WebSocketClient{
 
	public MsgWebSocketClient(String url) throws URISyntaxException {
		super(new URI(url));
	}
 
	@Override
	public void onOpen(ServerHandshake shake) {
		// TODO Auto-generated method stub
		System.out.println("握手...");
		for(Iterator<String> it=shake.iterateHttpFields();it.hasNext();) {
			String key = it.next();
			System.out.println(key+":"+shake.getFieldValue(key));
		}
	}
 
	@Override
	public void onMessage(String paramString) {
		// TODO Auto-generated method stub
		System.out.println("接收到消息："+paramString);
	}
 
	@Override
	public void onClose(int paramInt, String paramString, boolean paramBoolean) {
		// TODO Auto-generated method stub
		System.out.println("关闭...");
	}
 
	@Override
	public void onError(Exception e) {
		// TODO Auto-generated method stub
		System.out.println("异常"+e);
		
	}
	
	public static void main(String[] args) {
		try {
			MsgWebSocketClient client = new MsgWebSocketClient("ws://localhost:8001/afanConf/test/tradeCenter");
			client.connect();
			Thread.sleep(1000);
			client.send("测试websocket。。。");
			boolean flag = true;
			int i=10;
			while(flag) {
				client.send("测试websocket。。。"+(i--));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(i == 0) {
					flag = false;
				}
			}
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

