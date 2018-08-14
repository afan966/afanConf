package com.afan.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.afan.dbmgr.pool.druid.DruidMgr;
import com.afan.tool.log.LogBackConfigLoader;

@SpringBootApplication
@EnableDiscoveryClient
public class AfanConfigServer {

	public static void main(String[] args) {
		LogBackConfigLoader.init("E:\\Workspaces\\MyEclipse11\\afan.dbmgr\\src\\main\\resources\\logback.xml");
		SpringApplication.run(AfanConfigServer.class, args);
		DruidMgr.getInstance().init();
	}

}
