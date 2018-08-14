package com.afan.conf.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afan.conf.config.AfanConfig;
import com.afan.conf.config.ResponseInvoker;
import com.afan.conf.enity.AppInstance;
import com.afan.conf.service.ConfigService;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	ConfigService configService;

	@RequestMapping("/connect")
	public Object connect(@RequestParam String appId, @RequestParam String instanceName, @RequestParam String ip) {
		AppInstance instance = new AppInstance();
		instance.setAppId(appId);
		instance.setAppInstance(instanceName);
		instance.setInstanceIp(ip);
		instance.setStatus(0);
		instance.setConnectTime(new Date());
		instance.setCreateTime(new Date());

		return new ResponseInvoker() {
			@Override
			public Map<String, AfanConfig> invok(Object... obj) throws Exception {
				AppInstance instance = (AppInstance) obj[0];
				return configService.connectConfig(instance);
			}
		}.exec(instance);
	}

	@RequestMapping("/refreshConfig")
	public Object refreshConfig(@RequestParam String appId, @RequestParam long version) {
		return new ResponseInvoker() {
			@Override
			public Map<String, AfanConfig> invok(Object... obj) throws Exception {
				String appId = (String) obj[0];
				long version = (Long) obj[1];
				return configService.refreshConfig(appId, version);
			}
		}.exec(appId, version);
	}

}
