package org.da4.urlminimizer.vo;

import java.util.List;

public class ConfigVO {
	private String productName;
	private List<PluginVO> pluginConfigs;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<PluginVO> getPluginConfigs() {
		return pluginConfigs;
	}
	public void setPluginConfigs(List<PluginVO> pluginConfigs) {
		this.pluginConfigs = pluginConfigs;
	}
	@Override
	public String toString() {
		return "ConfigVO [productName=" + productName + ", pluginConfigs=" + pluginConfigs + "]";
	}

}
