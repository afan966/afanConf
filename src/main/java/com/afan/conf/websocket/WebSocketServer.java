package com.afan.conf.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afan.conf.config.AfanConfig;
import com.afan.conf.config.AppInstKey;
import com.afan.conf.config.ConfigRequest;
import com.afan.conf.config.ConfigResponse;
import com.afan.conf.config.ResponseInvoker;
import com.afan.conf.enity.App;
import com.afan.conf.enity.AppInstance;
import com.afan.conf.service.ConfigService;
import com.afan.tool.json.JsonUtil;

/**
 * websocketServer
 * 
 * @author afan
 * 
 */
@ServerEndpoint(value = "/afanConf/{appId}/{appInstance}", configurator = HttpSessionConfigurator.class)
@Component
public class WebSocketServer {

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static final AtomicInteger onlineInstanceCount = new AtomicInteger(0);
	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static final ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketServer>> webSocketMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketServer>>();
	private static final ConcurrentHashMap<String, AppInstKey> tokenMap = new ConcurrentHashMap<String, AppInstKey>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	// 接收sid
	private String appId;
	private String appInstance;
	private String token;

	@Autowired
	ConfigService configService;
	
	public static WebSocketServer wsServer;
	//解决@Autowired注入为NULL的问题
	@PostConstruct
	public void init() {
		wsServer = this;
	}

	private WebSocketServer getWebSocketServer(String appId, String appInstance) {
		if (webSocketMap.get(appId) != null) {
			return webSocketMap.get(appId).get(appInstance);
		}
		return null;
	}
	
	private void removeWebSocketServer(String appId, String appInstance) {
		WebSocketServer server = getWebSocketServer(appId, appInstance);
		System.out.println(appId+","+appInstance+"..."+server.session.isOpen());
		webSocketMap.get(appId).remove(appInstance);
	}

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	// public void onOpen(Session session, @PathParam("appId") String appId, @PathParam("appInstance") String appInstance, EndpointConfig config) {
	public void onOpen(Session session, @PathParam("appId") String appId, @PathParam("appInstance") String appInstance) {
		ConfigResponse response = new ConfigResponse("login");
		this.session = session;
		this.appId = appId;
		this.appInstance = appInstance;

		WebSocketServer server = getWebSocketServer(appId, appInstance);
		if (server != null) {
			reciveMessage(response, 1, "实例已在线");
			return;
		}

		// token
		AppInstKey appInstKey = new AppInstKey(appId, appInstance);
		this.token = appInstKey.toString();
		tokenMap.put(token, appInstKey);

		// socket
		if (webSocketMap.get(appId) == null) {
			webSocketMap.put(appId, new ConcurrentHashMap<String, WebSocketServer>());
		}
		webSocketMap.get(appId).put(appInstance, this);
		System.out.println("有新窗口开始监听:" + appId + "," + appInstance + " 当前在线人数为" + onlineInstanceCount.incrementAndGet());
		response.setMessage(token);
		reciveMessage(response);
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		removeWebSocketServer(appId, appInstance);
		tokenMap.remove(token);
		System.out.println("有一连接 " + appId + "," + appInstance + " 关闭！当前在线人数为" + onlineInstanceCount.decrementAndGet());
	}

	/**
	 * 收到客户端消息回调方法
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		ConfigResponse response = new ConfigResponse("recive");
		ConfigRequest request = JsonUtil.toObject(message, ConfigRequest.class);
		if (request == null || request.getToken() == null || request.getAction() == null) {
			reciveMessage(response, 2, "非法请求");
			return;
		}

		AppInstKey key = tokenMap.get(request.getToken());
		if (key == null) {
			reciveMessage(response, 101, "token失效，请重新链接");
			return;
		}
		App app = null;
		try {
			app = wsServer.configService.getApp(key.getAppId());
			if (app == null) {
				reciveMessage(response, 102, "app[" + key.getAppId() + "]不存在");
				return;
			}
			app.setAppId(appId);
		} catch (Exception e) {
			reciveMessage(response, 102, "app[" + key.getAppId() + "]不存在");
			return;
		}

		if ("AllConfig".equals(request.getAction())) {
			// 全量
			AppInstance instance = new AppInstance();
			instance.setAppId(appId);
			instance.setAppInstance(key.getAppId());
			instance.setInstanceIp(request.getParams().get("ip"));
			instance.setStatus(0);
			instance.setConnectTime(new Date());
			instance.setCreateTime(new Date());

			response = new ResponseInvoker() {
				@Override
				public Map<String, AfanConfig> invok(Object... obj) throws Exception {
					AppInstance instance = (AppInstance) obj[0];
					return wsServer.configService.connectConfig(instance);
				}
			}.exec(instance);
			response.setAction(request.getAction());

			Map<String, AfanConfig> configMap = response.getConfigMap();
			tokenMap.remove(request.getToken());
			key.setVersion(app.getVersion());
			this.token = configMap.get("__token").value();
			tokenMap.put(this.token, key);
		} else if ("IncrConfig".equals(request.getAction())) {
			// 增量
			response = new ResponseInvoker() {
				@Override
				public Map<String, AfanConfig> invok(Object... obj) throws Exception {
					AppInstKey key = (AppInstKey) obj[0];
					return configService.refreshConfig(key.getAppId(), key.getVersion());
				}
			}.exec(key);
		}

		System.out.println("收到来自窗口" + appId + "," + appInstance + "的信息:" + message);
		reciveMessage(response);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 服务器主动推送
	 * 
	 * @param message
	 * @throws IOException
	 */
	private void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	private void sendMessage(ConfigResponse response) throws IOException {
		this.sendMessage(JsonUtil.toJson(response));
	}
	
	//通知客户端删除
	public void sendDelete(String key){
		try {
			ConfigResponse response = new ConfigResponse("DeleteConfig");
			response.setMessage(key);
			sendMessage(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 服务器响应客户端
	 * @param response
	 */
	private void reciveMessage(ConfigResponse response) {
		reciveMessage(response, 0, null);
	}

	private void reciveMessage(ConfigResponse response, int errCode, String errMessage) {
		try {
			if (errCode > 0) {
				response.setStatus(errCode);
				response.setMessage(errMessage);
			}
			sendMessage(response);
		} catch (IOException e) {
			System.out.println("websocket IO异常");
			// this.session.close();这里断开客户端就连不上了，
		}
	}

	/**
	 * 群发自定义消息
	 * 
	 * @param message
	 * @param appId
	 * @param appInstance
	 * @throws IOException
	 */
	public static void sendInfo(String message, @PathParam("appId") String appId, @PathParam("appInstance") String appInstance) throws IOException {
		System.out.println("推送消息到窗口" + appId + "," + appInstance + "推送内容:" + message);
		if (appId != null) {
			if (webSocketMap.get(appId) != null) {
				if (appInstance != null) {
					if (webSocketMap.get(appId).get(appInstance) != null) {
						webSocketMap.get(appId).get(appInstance).sendMessage(message);
					}
				} else {
					for (String instance : webSocketMap.get(appId).keySet()) {
						webSocketMap.get(appId).get(instance).sendMessage(message);
					}
				}
			}
		} else {
			for (String app : webSocketMap.keySet()) {
				for (String instance : webSocketMap.get(app).keySet()) {
					try {
						webSocketMap.get(app).get(instance).sendMessage(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 获取在线客户端数
	 * 
	 * @return
	 */
	public static synchronized int getOnlineCount() {
		return onlineInstanceCount.get();
	}

}