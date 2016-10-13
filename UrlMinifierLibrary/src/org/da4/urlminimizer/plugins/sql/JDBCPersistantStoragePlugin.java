package org.da4.urlminimizer.plugins.sql;

import java.util.Map;
import java.util.Set;

import org.da4.urlminimizer.Hook;
import org.da4.urlminimizer.Operation;
import org.da4.urlminimizer.exception.AliasNotFound;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.plugins.PluginAPI;
import org.da4.urlminimizer.vo.URLVO;

public class JDBCPersistantStoragePlugin extends PluginAPI {
	IJDBCDAO dao = null;
	final static  String RESERVED_ALIASES = "RESERVED_ALIASES";
	@Override
	public void init(Map<String, String> params) {
		super.init(params);
		dao = new PSQLDAO(params.get("url"),params.get("userid"), params.get("password"));
	}
	@Override
	public Object execute(Hook hook, Operation operation, Object input, Object output,Map<String,Object> params) {
		super.execute(hook,operation, input, output,params);
		
		if(Operation.MAXIMIZE.equals(operation))
		{
			return dao.getDestinationUrlFromAlias((String)input);	 
		}
		else if(Operation.MINIMIZE.equals(operation))
		{
			URLVO url;
			try {
				url = dao.getAliasFromDestination((String)input);
			} catch (AliasNotFound e) {
				String alias = null;
				Set<String> reservedSet = (Set<String>) params.get(RESERVED_ALIASES);
				do{
					long id = dao.getNextId();
					alias = Long.toString(id,36);
				}while(reservedSet.contains(alias));
				url = new URLVO();
				url.setAlias(alias);
				url.setCreatorApiKey("WEBSITE");
				url.setDestination((String)input);
				url.setIp("127.0.0.1");
				dao.persistUrl(url);
			}
			output =url.getAlias();
			return url.getAlias();
		}
		else{
			throw new RuntimeUrlException("Invalid operation `"+operation+"`");
		}
	}
}
