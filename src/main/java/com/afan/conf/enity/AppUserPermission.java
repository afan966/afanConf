package com.afan.conf.enity;

import java.io.Serializable;
import com.afan.dbmgr.DBTable;
import com.afan.dbmgr.DBColumn;

@DBTable(db="afan_config",table="app_user_permission")
public class AppUserPermission implements Serializable{
	private static final long serialVersionUID = 1L;

	@DBColumn
	private String userName;
	@DBColumn
	private String appId;
	@DBColumn
	private String namespace;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}