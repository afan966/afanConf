package com.afan.conf.enity;

import java.io.Serializable;
import com.afan.dbmgr.DBTable;
import com.afan.dbmgr.DBColumn;
import java.util.Date;
import java.util.List;
import java.util.Map;

@DBTable(db="afan_config",table="app_config", primaryClumns={"appId", "configKey"})
public class AppConfig implements Serializable{
	private static final long serialVersionUID = 1L;

	@DBColumn
	private String appId;
	@DBColumn
	private String configKey;
	@DBColumn
	private String configValue;
	@DBColumn
	private String namespace;//分类
	@DBColumn
	private int type;//0常规,1大字段,2Map,3List
	@DBColumn
	private boolean isDelete;
	@DBColumn(handler="date")
	private Date createTime;
	@DBColumn(handler="date")
	private Date modifiyTime;//修改时间
	@DBColumn
	private String modifiyName;
	@DBColumn
	private long version;

	private AppConfigContent content;
	private List<AppConfigItem> itemList;
	private Map<String, AppConfigItem> itemMap;

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
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public AppConfigContent getContent() {
		return content;
	}
	public void setContent(AppConfigContent content) {
		this.content = content;
	}
	public List<AppConfigItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<AppConfigItem> itemList) {
		this.itemList = itemList;
	}
	public Map<String, AppConfigItem> getItemMap() {
		return itemMap;
	}
	public void setItemMap(Map<String, AppConfigItem> itemMap) {
		this.itemMap = itemMap;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
}