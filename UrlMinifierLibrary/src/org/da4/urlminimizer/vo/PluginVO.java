package org.da4.urlminimizer.vo;

import java.util.Map;

import org.da4.urlminimizer.Operation;

public class PluginVO {
	String clazz;
	public PluginVO(String clazz, Operation hook) {
		super();
		this.clazz = clazz;
		this.hook = hook;
	}
	Operation hook;
	Map<String,String> attributes;
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public Operation getHook() {
		return hook;
	}
	public void setHook(Operation hook) {
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
