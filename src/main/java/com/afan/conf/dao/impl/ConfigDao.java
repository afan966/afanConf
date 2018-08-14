package com.afan.conf.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.afan.conf.dao.IConfigDao;
import com.afan.conf.enity.App;
import com.afan.conf.enity.AppConfig;
import com.afan.conf.enity.AppConfigContent;
import com.afan.conf.enity.AppConfigItem;
import com.afan.conf.enity.AppInstance;
import com.afan.conf.enity.AppNamespace;
import com.afan.conf.enity.AppUser;
import com.afan.conf.enity.AppUserPermission;
import com.afan.dbmgr.DBException;
import com.afan.dbmgr.pool.AfanConnect;
import com.afan.dbmgr.pool.DBConnect;
import com.afan.dbmgr.pool.DefaultConnect;
import com.afan.dbmgr.pool.wrap.ResultSetWrapper;
import com.afan.dbmgr.util.SQLUtil;
import com.afan.dbmgr.util.SqlObject;

@Repository
public class ConfigDao implements IConfigDao {

	
	@Override
	public App getApp(String appId) throws DBException {
		try (AfanConnect conn = new AfanConnect();){
			conn.prepareStatement("select * from app where appId = ?", appId);
			ResultSetWrapper<App> rs = new ResultSetWrapper<>(conn, App.class);
			return rs.query();
		}
	}
	
	@Override
	public List<App> getAppList() throws DBException {
		try (AfanConnect conn = new AfanConnect();){
			conn.prepareStatement("select * from app");
			ResultSetWrapper<App> rs = new ResultSetWrapper<>(conn, App.class);
			return rs.queryList();
		}
	}

	@Override
	public List<AppInstance> getAppInstanceList() throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppNamespace> getAppNamespaceList() throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppConfig> getAppConfigList(String appId, String namespace) throws DBException {
		try (AfanConnect conn = new AfanConnect();){
			conn.prepareStatement("select * from app_config where appId = ?",appId);
			ResultSetWrapper<AppConfig> rs = new ResultSetWrapper<>(conn, AppConfig.class);
			return rs.queryList();
		}
	}

	@Override
	public AppConfigContent getAppConfigContent(String appId, String configKey) throws DBException {
		try (DBConnect conn = new DefaultConnect();){
			conn.prepareStatement("select * from app_config_content where appId = ? and configKey = ?", appId, configKey);
			ResultSetWrapper<AppConfigContent> rs = new ResultSetWrapper<>(conn, AppConfigContent.class);
			return rs.query();
		}
	}

	@Override
	public List<AppConfigItem> getAppConfigItemList(String appId, String configKey) throws DBException {
		try (DBConnect conn = new DefaultConnect();){
			conn.prepareStatement("select * from app_config_item where appId = ? and configKey = ?",appId, configKey);
			ResultSetWrapper<AppConfigItem> rs = new ResultSetWrapper<>(conn, AppConfigItem.class);
			return rs.queryList();
		}
	}

	@Override
	public List<AppUser> getAppUserList() throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser getAppUser(String userName, String userPazzword) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppUserPermission> getAppUserPermissionList(String userName, String appId) throws DBException {
		return null;
	}

	@Override
	public void saveApp(App app) throws DBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAppInstance(AppInstance instance) throws DBException {
		try (DBConnect conn = new DefaultConnect();){
			SqlObject sqlObject = SQLUtil.insertOrUpdate(instance, "app_instance", "instanceIp,status,connectTime");
			conn.prepareStatement(sqlObject);
			conn.executeUpdate();
		}
	}
	
	@Override
	public void connectAppInstance(AppInstance instance) throws DBException {
		try (DBConnect conn = new DefaultConnect();){
			conn.prepareStatement("update `app_instance` set `status` = 0 where `appInstance` = ? and `appId` = ?", instance.getAppInstance(), instance.getAppId());
			conn.executeUpdate();
		}
	}
	
	@Override
	public void disConnectAppInstance(String appId, String appInstance) throws DBException {
		try (DBConnect conn = new DefaultConnect();){
			conn.prepareStatement("update `app_instance` set `status` = 1 where `appInstance` = ? and `appId` = ?", appInstance, appId);
			conn.executeUpdate();
		}
	}

	@Override
	public void saveAppNamespace(AppNamespace namespace) throws DBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAppConfig(AppConfig config) throws DBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAppConfigContent(AppConfigContent content) throws DBException {
		// TODO Auto-generated method stub

	}

}
