package org.da4.urlminimizer.plugins.sql;

import java.util.Map;

import org.da4.urlminimizer.Hook;
import org.da4.urlminimizer.Operation;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.plugins.PluginAPI;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class JDBCPersistantStoragePlugin extends PluginAPI {
	BiMap<String,String> aliasToUrlMap = HashBiMap.create();
	@Override
	public void init(Map<String, String> params) {
		super.init(params);
		aliasToUrlMap.putAll(params);
	}
	@Override
	public Object execute(Hook hook, Operation operation, Object input, Object output) {
		super.execute(hook,operation, input, output);
		
		if(Operation.MAXIMIZE.equals(operation))
		{
			output = aliasToUrlMap.get(input);
			return aliasToUrlMap.get(input);	 
		}
		else if(Operation.MINIMIZE.equals(operation))
		{
			output = aliasToUrlMap.inverse().get(input);
			return aliasToUrlMap.inverse().get(input);
		}
		else{
			throw new RuntimeUrlException("Invalid operation `"+operation+"`");
		}
	}
}
