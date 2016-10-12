package org.da4.urlminimizer;

import java.util.Set;

import org.da4.urlminimizer.exception.ConfigException;
import org.da4.urlminimizer.plugins.IPlugin;
import org.da4.urlminimizer.vo.ConfigVO;

public class UrlMinimizer {

	private Set<IPlugin> preplugins;
	private Set<IPlugin> postplugins;

	public UrlMinimizer(String configFile) {
		IConfig configParser = new XmlConfiguration();
		try {
			ConfigVO config = configParser.getConfig(configFile);
			System.out.println(config);
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String minimize(String plugin)
	{
	return null;	
	}
	
	public String maximize(String plugin)
	{
		return null;
	}

}
