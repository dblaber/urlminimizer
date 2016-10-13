package org.da4.urlminimizer.vo;

import java.util.List;

public class ConfigVO {
	private String productName;
	private String rootUrl;
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

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	@Override
	public String toString() {
		return "ConfigVO [productName=" + productName + ", rootUrl=" + rootUrl + ", pluginConfigs=" + pluginConfigs
				+ "]";
	}

}
