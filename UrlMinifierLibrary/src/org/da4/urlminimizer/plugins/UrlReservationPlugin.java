package org.da4.urlminimizer.plugins;

import java.util.HashMap;
import java.util.Map;

import org.da4.urlminimizer.Operation;

public class UrlReservationPlugin extends PluginAPI {
	Map<String,String> reservedAliases = new HashMap<String,String>();
	@Override
	public void init(Map<String, String> params) {
		super.init(params);
		reservedAliases = params;
	}
	@Override
	public Object execute(Operation operation, Object input, Object output) {
		super.execute(operation, input, output);
		output = reservedAliases.get(input);
		return reservedAliases.get(input);	
	}
}
