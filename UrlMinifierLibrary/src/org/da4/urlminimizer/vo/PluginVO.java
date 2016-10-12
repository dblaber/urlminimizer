package org.da4.urlminimizer.vo;

import java.util.Map;

import org.da4.urlminimizer.Hook;

public class PluginVO {
	String clazz;
	public PluginVO(String clazz, Hook hook) {
		super();
		this.clazz = clazz;
		this.hook = hook;
	}
	Hook hook;
	Map<String,String> attributes;
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public Hook getHook() {
		return hook;
	}
	public void setHook(Hook hook) {
		this.hook = hook;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	@Override
	public String toString() {
		return "PluginVO [clazz=" + clazz + ", hook=" + hook + ", attributes=" + attributes + "]";
	}
	
}
