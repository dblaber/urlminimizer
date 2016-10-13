package org.da4.urlminimizer.exception;

public class AliasNotFound extends Exception {
	public AliasNotFound(String msg, Throwable e) {
		super(msg,e);
	}
	public AliasNotFound(String msg) {
		super(msg);
	}
}
