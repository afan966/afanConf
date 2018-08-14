package com.afan.conf.dao;

import java.util.List;
import com.afan.conf.enity.App;
import com.afan.conf.enity.AppConfig;
import com.afan.conf.enity.AppConfigContent;
import com.afan.conf.enity.AppConfigItem;
import com.afan.conf.enity.AppInstance;
import com.afan.conf.enity.AppNamespace;
import com.afan.conf.enity.AppUser;
import com.afan.conf.enity.AppUserPermission;
import com.afan.dbmgr.DBException;

public interface IConfigDao {

	App getApp(String appId) throws DBException;
	
	List<App> getAppList() throws DBException;
	
	List<AppInstance> getAppInstanceList() throws DBException;
	
	List<AppNamespace> getAppNamespaceList() throws DBException;
	
	
	List<AppConfig> getAppConfigList(String appId, String namespace) throws DBException;
	
	AppConfigContent getAppConfigContent(String appId, String configKey) throws DBException;
	
	List<AppConfigItem> getAppConfigItemList(String appId, String configKey) throws DBException;
	
	
	List<AppUser> getAppUserList() throws DBException;
	
	AppUser getAppUser(String userName, String userPazzword) throws DBException;
	
	List<AppUserPermission> getAppUserPermissionList(String userName, String appId) throws DBException;
	
	
	
	void saveApp(App app) throws DBException;
	
	void saveAppInstance(AppInstance instance) throws DBException;
	
	void connectAppInstance(AppInstance instance) throws DBException;
	
	void disConnectAppInstance(String appId, String appInstance) throws DBException;
	
	void saveAppNamespace(AppNamespace namespace) throws DBException;
	
	void saveAppConfig(AppConfig config) throws DBException;
	
	void saveAppConfigContent(AppConfigContent content) throws DBException;
	
}
