package org.da4.urlminimizer;

import java.util.HashMap;
import java.util.Map;

public enum Hook {
	
	PREPROCESSOR("Preprocessor"),
	POSTPROCESSOR("Postprocessor"),
	PROCESSOR("Processor");
	
	private String strOperation;
	private static Map<String,Hook> operationMap = new HashMap<String,Hook>();
	static{
		for(Hook operation:Hook.values())
		{
			operationMap.put(operation.strOperation, operation);
		}
	}
	private Hook(String operation)
	{
		this.strOperation = operation;
	}
	public static Hook  get(String operation)
	{
		return operationMap.get(operation);
	}
}
