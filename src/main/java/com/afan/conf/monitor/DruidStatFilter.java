package com.afan.conf.monitor;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * druid监控拦截器 
 * @author afan
 *
 */
@WebFilter(filterName="druidWebStatFilter",  
urlPatterns="/*",  
initParams={  
    @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),// 忽略资源  
}) 
public class DruidStatFilter extends WebStatFilter {
 
}
