package com.afan.conf.enity;

import java.io.Serializable;
import com.afan.dbmgr.DBTable;
import com.afan.dbmgr.DBColumn;
import java.util.Date;

@DBTable(db="afan_config",table="app_instance", primaryClumns={"appId", "appInstance"})
public class AppInstance implements Serializable{
	private static final long serialVersionUID = 1L;

	@DBColumn
	private String appInstance;//实例
	@DBColumn
	private String appId;
	@DBColumn
	private String instanceIp;//客户端IP
	@DBColumn
	private int status;//0正常，1下线
	@DBColumn(handler="date")
	private Date connectTime;
	@DBColumn(handler="date")
	private Date createTime;
	@DBColumn(handler="date")
	private Date modifiyName;

	public String toString(long version){
		StringBuilder sb = new StringBuilder();
		sb.append(version).append(",");//版本
		sb.append(appId).append(",");
		sb.append(appInstance).append(",");
		sb.append(instanceIp).append(",");
		sb.append(connectTime);
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
	public String getInstanceIp() {
		return instanceIp;
	}
	public void setInstanceIp(String instanceIp) {
		this.instanceIp = instanceIp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifiyName() {
		return modifiyName;
	}
	public void setModifiyName(Date modifiyName) {
		this.modifiyName = modifiyName;
	}
	
}