package org.da4.urlminimizer;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.da4.urlminimizer.exception.ConfigException;
import org.da4.urlminimizer.plugins.IPlugin;
import org.da4.urlminimizer.vo.ConfigVO;
import org.da4.urlminimizer.vo.PluginVO;

public class UrlMinimizer {

	private Set<IPlugin> preplugins = new LinkedHashSet<IPlugin>();
	private Set<IPlugin> procplugins = new LinkedHashSet<IPlugin>();
	private Set<IPlugin> postplugins = new LinkedHashSet<IPlugin>();
	ConfigVO config = null;
	public UrlMinimizer(String configFile) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		IConfig configParser = new XmlConfiguration();
		try {
			config = configParser.getConfig(configFile);
			System.out.println(config);
		} catch (ConfigException e) {
			e.printStackTrace();
		}
		for(PluginVO plugin:config.getPluginConfigs())
		{
			try{
				IPlugin pluginImpl = null;
				if(plugin.getHook() == Hook.PREPROCESSOR)
				{
					System.out.println("Loading PREPROCESSOR plugin `"+plugin.getClazz()+"`");
					pluginImpl = (IPlugin)Class.forName(plugin.getClazz()).newInstance();
					preplugins.add(pluginImpl);
				}
				else if(plugin.getHook() == Hook.POSTPROCESSOR)
				{
					System.out.println("Loading POSTPROCESSOR plugin `"+plugin.getClazz()+"`");
					pluginImpl = (IPlugin)Class.forName(plugin.getClazz()).newInstance();
					postplugins.add(pluginImpl);
				}
				else if(plugin.getHook() == Hook.PROCESSOR)
				{
					System.out.println("Loading PROCESSOR plugin `"+plugin.getClazz()+"`");
					pluginImpl = (IPlugin)Class.forName(plugin.getClazz()).newInstance();
					procplugins.add(pluginImpl);
				}
				else
				{
					System.err.println("Invalid Hook. Ignoring plugin");
					continue;
				}
				pluginImpl.init(plugin.getAttributes());
			
			} catch (ClassNotFoundException e)
			{
				System.err.println("ERROR: Can not load plugin ` " + plugin.getClazz()+"`. Check classpath and that plugin exists");
				continue;
			}
		}

		
	}
	public ConfigVO getConfig()
	{
		return config;
	}
	public String minimize(String in)
	{
		String alias = null;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//preprocessing
		for(IPlugin preplugins:preplugins)
		{
			in = (String)preplugins.execute(Hook.PREPROCESSOR,Operation.MINIMIZE, in, null,paramMap);

		}
		
		//processing
		for(IPlugin plugin:procplugins)
		{
			alias = (String)plugin.execute(Hook.PROCESSOR, Operation.MINIMIZE, in,null, paramMap);
			if(alias != null)
				break;
		}
		
		//POSTPROCESSOR
		for(IPlugin preplugins:preplugins)
		{
			alias = (String)preplugins.execute(Hook.POSTPROCESSOR,Operation.MINIMIZE,  in, null,paramMap);
		}
		return config.getRootUrl() + alias;
	}
	
	public String maximize(String in)
	{
		String realUrl = null;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//preprocessing
		for(IPlugin plugin:preplugins)
		{
			in = (String)plugin.execute(Hook.PREPROCESSOR,Operation.MAXIMIZE, in, null,paramMap);

		}
		
		//processing
		for(IPlugin plugin:procplugins)
		{
			realUrl = (String)plugin.execute(Hook.PROCESSOR, Operation.MAXIMIZE, in,null, paramMap);
			if(realUrl != null)
				break;
		}
		
		//POSTPROCESSOR
		for(IPlugin plugin:preplugins)
		{
			realUrl = (String)plugin.execute(Hook.POSTPROCESSOR,Operation.MAXIMIZE,  in, null,paramMap);
		}
		return realUrl;
	}

}
