package org.da4.urlminimizer.plugins;

import java.util.Map;

import org.da4.urlminimizer.Hook;
import org.da4.urlminimizer.Operation;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.vo.URLVO;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class UrlReservationPlugin extends PluginAPI {
	BiMap<String,String> aliasToUrlMap = HashBiMap.create();
	final static  String RESERVED_ALIASES = "RESERVED_ALIASES";
	@Override
	public void init(Map<String, String> params) {
		super.init(params);
		aliasToUrlMap.putAll(params);
	}
	@Override
	public Object execute(Hook hook, Operation operation, Object input, Object output,Map<String,Object> params) {
		super.execute(hook,operation, input, output,params);
		params.put("RESERVED_ALIASES", aliasToUrlMap.keySet());
		if(Operation.MAXIMIZE.equals(operation))
		{
			output = aliasToUrlMap.get(input);
			if(output ==  null)
				return null;
			URLVO vo = new URLVO();
			vo.setAlias((String)input);
			vo.setDestination((String)output);
			
			return vo;	 
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
