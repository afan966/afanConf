package com.afan.conf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afan.conf.service.ConfigService;

/**
 * 管理端控制台
 * @author afan
 *
 */
@RestController
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	ConfigService configService;
	
	@RequestMapping("/config")
	public Object connect() {
		return configService.getAllConfigData();
	}
}
