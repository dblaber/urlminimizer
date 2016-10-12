package org.da4.urlminimizer.exception;

public class RuntimeUrlException extends RuntimeException {
	private static final long serialVersionUID = -4583229940988206643L;
	public RuntimeUrlException(String msg, Throwable e) {
		super(msg,e);
	}
	public RuntimeUrlException(String msg) {
		super(msg);
	}
}
