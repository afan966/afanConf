package com.afan.conf.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * 从websocket中获取用户session session建立连接的HTTP握手连接的属性值。
 * 
 * @author afan
 * 
 */
public class HttpSessionConfigurator extends Configurator {
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		//这里没有jsessionId，所以没有session
		if (httpSession != null) {
			sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
		}
	}
}
