package org.da4.urlminimizer.vo;

import java.util.HashMap;
import java.util.Map;

public class URLVO {
	private String alias;
	private String destination;
	private String creatorClass;
	private String ip;
	boolean isSpam = false;
	
	
	private Map<String,String> metaData = new HashMap<String,String>();
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Map<String, String> getMetaData() {
		return metaData;
	}
	public void setMetaData(Map<String, String> metaData) {
		this.metaData = metaData;
	}
	public String getCreatorClass() {
		return creatorClass;
	}
	public void setCreatorClass(String creatorClass) {
		this.creatorClass = creatorClass;
	}
	public boolean isSpam() {
		return isSpam;
	}
	public void setSpam(boolean isSpam) {
		this.isSpam = isSpam;
	}
	
	
}
