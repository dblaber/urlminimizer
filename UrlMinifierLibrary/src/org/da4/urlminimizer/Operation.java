package org.da4.urlminimizer;

import java.util.HashMap;
import java.util.Map;

public enum Operation {
	
	PREPROCESSOR("Preprocessor"),POSTPROCESSOR("Postprocessor");
	private String strOperation;
	private static Map<String,Operation> operationMap = new HashMap<String,Operation>();
	static{
		for(Operation operation:Operation.values())
		{
			operationMap.put(operation.strOperation, operation);
		}
	}
	private Operation(String operation)
	{
		this.strOperation = operation;
	}
	public static Operation  get(String operation)
	{
		return operationMap.get(operation);
	}
}
