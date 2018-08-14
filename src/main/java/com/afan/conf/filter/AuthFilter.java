package com.afan.conf.filter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
@WebFilter(filterName = "authfilter", urlPatterns = "/*")
public class AuthFilter implements Filter {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(AuthFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("init auth filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HttpServletRequest request_ = (HttpServletRequest) request;
		try {
			String method = StringUtils.trimToEmpty(request_.getMethod()).toUpperCase();
			String strBackUrl = "";
			String common_url = "http://" + request_.getServerName() // 服务器地址
					+ ":" + request_.getServerPort() // 端口号
					+ request_.getContextPath() // 项目名称
					+ request_.getServletPath() // 请求页面或其他地址
					+ "?";
			if (method.equals("POST")) {
				Map<String, String[]> params = request.getParameterMap();
				String queryString = "";
				for (String key : params.keySet()) {
					String[] values = params.get(key);
					for (int i = 0; i < values.length; i++) {
						String value = values[i];
						queryString += key + "=" + value + "&";
					}
				}
				// 去掉最后一个空格
				try {
					if (!"".equals(queryString)) {
						strBackUrl = common_url + queryString.substring(0, queryString.length() - 1);
					} else {
						strBackUrl = common_url.substring(0, common_url.length() - 1);
					}
				} catch (Exception e) {
					logger.debug("", e);
				}
			} else if (method.equals("GET")) {
				strBackUrl = common_url + (request_.getQueryString()); // 参数
			} else {
				logger.debug("此连接不打印");
			}
			logger.info(">>>>>>>>>>>>>>>>>前置请求地址：" + method + "---->" + strBackUrl);
			logger.info(">>>>>>>>>>>>>>>>>前置请求时间：" + df.format(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("destroy auth filter...");
	}

}