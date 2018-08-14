package com.afan.conf.config;

import java.io.Serializable;
import java.util.Date;

/**
 * 链接sessionkey
 * @author afan
 *
 */
public class AppInstKey implements Serializable{
	private static final long serialVersionUID = 1L;

	private String appInstance;//实例
	private String appId;
	private String clientIp;
	private long version;
	
	public AppInstKey(String appId, String appInstance) {
		this.appId = appId;
		this.appInstance = appInstance;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(version).append(",");//版本
		sb.append(appId).append(",");
		sb.append(appInstance).append(",");
		sb.append(appInstance).append(",");
		sb.append(new Date());
		return sb.toString();
	}

	public String getAppInstance() {
		return appInstance;
	}
	public void setAppInstance(String appInstance) {
		this.appInstance = appInstance;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
}