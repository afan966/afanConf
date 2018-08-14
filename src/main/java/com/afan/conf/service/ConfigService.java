package com.afan.conf.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afan.conf.config.AfanConfig;
import com.afan.conf.dao.IConfigDao;
import com.afan.conf.enity.App;
import com.afan.conf.enity.AppConfig;
import com.afan.conf.enity.AppConfigItem;
import com.afan.conf.enity.AppInstance;
import com.afan.tool.encrypt.DESUtils;

@Service
public class ConfigService {
	
	@Autowired
	IConfigDao configDao;
	
	private static DESUtils desUtil = null;
	static{
		try {
			desUtil = new DESUtils("afan-config");
		} catch (Exception e) {
		}
	}
	
	private Map<String, App> appMap = new HashMap<String, App>();
	private Map<String, Map<String, AppConfig>> configDataMap = null;
	
	private synchronized void init(){
		try {
			configDataMap = new HashMap<String, Map<String,AppConfig>>();
			List<App> appList = configDao.getAppList();
			for (App app : appList) {
				appMap.put(app.getAppId(), app);
				
				Map<String, AppConfig> configMap = new HashMap<String, AppConfig>();
				List<AppConfig> configList = configDao.getAppConfigList(app.getAppId(), null);
				for (AppConfig config : configList) {
					if(config.getType()==1){
						config.setContent(configDao.getAppConfigContent(app.getAppId(), config.getConfigKey()));
					}else if(config.getType()==2){
						List<AppConfigItem> itemList = configDao.getAppConfigItemList(app.getAppId(), config.getConfigKey());
						Map<String, AppConfigItem> itemMap = new HashMap<String, AppConfigItem>();
						for (AppConfigItem item : itemList) {
							itemMap.put(item.getConfigItemKey(), item);
						}
						config.setItemMap(itemMap);
					}else if(config.getType()==3){
						config.setItemList(configDao.getAppConfigItemList(app.getAppId(), config.getConfigKey()));
					}
					configMap.put(config.getConfigKey(), config);
				}
				configDataMap.put(app.getAppId(), configMap);
			}
		} catch (Exception e) {
		}
	}
	
	private Map<String, AppConfig> getAppConfigMap(String appId){
		if(configDataMap == null){
			init();
		}
		return configDataMap.get(appId);
	}
	
	public Map<String, Map<String, AppConfig>> getAllConfigData(){
		if(configDataMap == null){
			init();
		}
		return configDataMap;
	}
	
	/**
	 * 客户端获取配置列表
	 * @param appId
	 * @return
	 */
	private Map<String, AfanConfig> getClientConfigData(String appId) {
		Map<String, AppConfig> dataMap = getAppConfigMap(appId);
		Map<String, AfanConfig> configMap = new HashMap<String, AfanConfig>();
		for (String configKey : dataMap.keySet()) {
			configMap.put(configKey, new AfanConfig(dataMap.get(configKey)));
		}
		return configMap;
	}
	
	public App getApp(String appId) throws Exception {
		return configDao.getApp(appId);
	}
	
	//客户端链接配置中心，获取全量配置数据
	public Map<String, AfanConfig> connectConfig(AppInstance instance) throws Exception {
		App app = configDao.getApp(instance.getAppId());
		configDao.saveAppInstance(instance);
		Map<String, AfanConfig> configMap = getClientConfigData(instance.getAppId());
		String token = desUtil.encrypt(instance.toString(app.getVersion()));
		AfanConfig tconfig = new AfanConfig(token);
		AfanConfig vconfig = new AfanConfig(app.getVersion()+"");
		configMap.put("__version",vconfig);
		configMap.put("__token", tconfig);
		return configMap;
	}
	
	//主动刷新客户端刷新配置
	public Map<String, AfanConfig> refreshConfig(String appId, long version) throws Exception {
		App app = configDao.getApp(appId);
		if(app.getVersion()>version){
			Map<String, AppConfig> dataMap = getAppConfigMap(appId);
			Map<String, AfanConfig> configMap = new HashMap<String, AfanConfig>();
			for (String configKey : dataMap.keySet()) {
				AppConfig config = dataMap.get(configKey);
				if(config.getVersion()>version){
					configMap.put(configKey, new AfanConfig(config));
				}
			}
			return configMap;
		}
		return null;
	}
	
}
