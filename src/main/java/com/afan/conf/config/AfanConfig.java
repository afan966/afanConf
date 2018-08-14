package com.afan.conf.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.afan.conf.enity.AppConfig;
import com.afan.conf.enity.AppConfigItem;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 客户端配置对象
 * @author afan
 *
 */
public class AfanConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String _v;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> _l;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Map<String, String> _m;
	
	public AfanConfig(String value) {
		this._v = value;
	}
	
	public AfanConfig(AppConfig config) {
		this._v = config.getConfigValue();
		if(config.getContent()!=null){
			this._v = config.getContent().getConfigContent();
		}
		if(config.getItemList()!=null && config.getItemList().size()>0){
			this._l = new ArrayList<String>();
			for (AppConfigItem item : config.getItemList()) {
				this._l.add(item.getConfigItemValue());
			}
		}
		if(config.getItemMap()!=null && config.getItemMap().size()>0){
			this._m = new HashMap<String, String>();
			for (String itemKey : config.getItemMap().keySet()) {
				this._m.put(itemKey, config.getItemMap().get(itemKey).getConfigItemValue());
			}
		}
	}

	public String value() {
		return _v;
	}
	
	public String get_v() {
		return _v;
	}
	public void set_v(String _v) {
		this._v = _v;
	}
	public List<String> get_l() {
		return _l;
	}
	public void set_l(List<String> _l) {
		this._l = _l;
	}
	public Map<String, String> get_m() {
		return _m;
	}
	public void set_m(Map<String, String> _m) {
		this._m = _m;
	}
	
}