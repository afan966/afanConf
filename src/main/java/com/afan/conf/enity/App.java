package com.afan.conf.enity;

import java.io.Serializable;
import com.afan.dbmgr.DBTable;
import com.afan.dbmgr.DBColumn;
import java.util.Date;

@DBTable(db="afan_config",table="app", primaryClumns={"appId"})
public class App implements Serializable{
	private static final long serialVersionUID = 1L;

	@DBColumn
	private String appId;//APP
	@DBColumn
	private String appName;//AppName
	@DBColumn
	private String ownerName;//所有人
	@DBColumn
	private String ownerMobile;//手机
	@DBColumn
	private String ownerEmail;//邮箱
	@DBColumn(handler="date")
	private Date createTime;//创建时间
	@DBColumn(handler="date")
	private Date modifiyTime;//修改时间
	@DBColumn
	private long version;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerMobile() {
		return ownerMobile;
	}
	public void setOwnerMobile(String ownerMobile) {
		this.ownerMobile = ownerMobile;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifiyTime() {
		return modifiyTime;
	}
	public void setModifiyTime(Date modifiyTime) {
		this.modifiyTime = modifiyTime;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
}