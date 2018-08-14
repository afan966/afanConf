package com.afan.conf.enity;

import java.io.Serializable;
import com.afan.dbmgr.DBTable;
import com.afan.dbmgr.DBColumn;
import java.util.Date;

@DBTable(db="afan_config",table="app_config_content", primaryClumns={"appId", "configKey"})
public class AppConfigContent implements Serializable{
	private static final long serialVersionUID = 1L;

	@DBColumn
	private String appId;
	@DBColumn
	private String configKey;
	@DBColumn
	private String configContent;
	@DBColumn(handler="date")
	private Date createTime;
	@DBColumn(handler="date")
	private Date modifiyTime;//修改时间
	@DBColumn
	private String modifiyName;

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getConfigContent() {
		return configContent;
	}
	public void setConfigContent(String configContent) {
		this.configContent = configContent;
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
	public String getModifiyName() {
		return modifiyName;
	}
	public void setModifiyName(String modifiyName) {
		this.modifiyName = modifiyName;
	}
}