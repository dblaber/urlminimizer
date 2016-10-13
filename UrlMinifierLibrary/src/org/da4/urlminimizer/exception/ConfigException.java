package org.da4.urlminimizer.exception;

public class ConfigException extends Exception {
	public ConfigException(String msg, Throwable e) {
		super(msg, e);
	}

	public ConfigException(String msg) {
		super(msg);
	}
}
