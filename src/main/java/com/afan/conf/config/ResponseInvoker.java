package com.afan.conf.config;

import java.util.Map;

/**
 * 统一包装请求响应格式
 * @author afan
 *
 */
public abstract class ResponseInvoker {
	
	public ConfigResponse exec(Object... obj){
		ConfigResponse response = new ConfigResponse();
		try {
			//监控请求时间
			response.setConfigMap(invok(obj));
		} catch (Exception e) {
			response.setStatus(1);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	public abstract Map<String, AfanConfig> invok(Object... obj) throws Exception;
}
