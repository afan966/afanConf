package com.afan.conf.enity;

import java.io.Serializable;
import com.afan.dbmgr.DBTable;
import com.afan.dbmgr.DBColumn;
import java.util.Date;

@DBTable(db="afan_config",table="app_namespace", primaryClumns={"appId", "namespace"})
public class AppNamespace implements Serializable{
	private static final long serialVersionUID = 1L;

	@DBColumn
	private String appId;
	@DBColumn
	private String namespace;//分类
	@DBColumn
	private boolean isDelete;
	@DBColumn(handler="date")
	private Date createTime;

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
	public boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}