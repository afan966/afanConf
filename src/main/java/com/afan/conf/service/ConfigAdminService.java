package com.afan.conf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afan.conf.dao.IConfigDao;

@Service
public class ConfigAdminService {

	@Autowired
	IConfigDao configDao;
	
	@Autowired
	ConfigService configService;
	
	
}
