package org.da4.urlminimizer;

public enum Operation {
	
	PREPROCESSOR("Preprocessor"),POSTPROCESSOR("Postprocessor");
	private String strOperation;
	private Operation(String operation)
	{
		this.strOperation = operation;
	}
	
}
