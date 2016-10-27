/*******************************************************************************
 * Copyright 2016 Darren Blaber
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.da4.urlminimizer;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.exception.ConfigException;
import org.da4.urlminimizer.plugins.IPlugin;
import org.da4.urlminimizer.vo.ConfigVO;
import org.da4.urlminimizer.vo.PluginVO;
import org.da4.urlminimizer.vo.URLVO;
/**
 * Main class that is entrypoint to the url minimizer library. The main methods to minimize and maximize will
 * exist here, along with basic skeleton workflow
 * @author dmb
 *
 */
public class UrlMinimizer {
	private static final Logger logger = LogManager.getLogger(UrlMinimizer.class);
	private Set<IPlugin> preplugins = new LinkedHashSet<IPlugin>();
	private Set<IPlugin> procplugins = new LinkedHashSet<IPlugin>();
	private Set<IPlugin> postplugins = new LinkedHashSet<IPlugin>();
	ConfigVO config = null;
	/**
	 * Constructor to deal with getting config and creating instance of plugins and calling init on plugins
	 * @param configFile Config file that will be parsed to create ConfigVO object
	 * @throws InstantiationException Reflection error
	 * @throws IllegalAccessException Reflection error
	 * @throws ClassNotFoundException Plugin class can not be found
	 */
	public UrlMinimizer(String configFile)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		IConfig configParser = new XmlConfiguration();
		try {
			config = configParser.getConfig(configFile);
			logger.debug(config);
		} catch (ConfigException e) {
			logger.fatal("Config error", e);
		}
		for (PluginVO plugin : config.getPluginConfigs()) {
			try {
				IPlugin pluginImpl = null;
				if (plugin.getHooks().contains(Hook.PREPROCESSOR)) {
					logger.info("Loading PREPROCESSOR plugin `" + plugin.getClazz() + "`");
					pluginImpl = (IPlugin) Class.forName(plugin.getClazz()).newInstance();
					preplugins.add(pluginImpl);
				} else if (plugin.getHooks().contains(Hook.POSTPROCESSOR)) {
					logger.info("Loading POSTPROCESSOR plugin `" + plugin.getClazz() + "`");
					pluginImpl = (IPlugin) Class.forName(plugin.getClazz()).newInstance();
					postplugins.add(pluginImpl);
				} else if (plugin.getHooks().contains(Hook.PROCESSOR)) {
					logger.info("Loading PROCESSOR plugin `" + plugin.getClazz() + "`");
					pluginImpl = (IPlugin) Class.forName(plugin.getClazz()).newInstance();
					procplugins.add(pluginImpl);
				} else {
					logger.error("Invalid Hook. Ignoring plugin. Hooks: " + plugin.getHooks());
					continue;
				}
				pluginImpl.init(plugin.getAttributes());

			} catch (ClassNotFoundException e) {
				logger.error("ERROR: Can not load plugin ` " + plugin.getClazz()
						+ "`. Check classpath and that plugin exists");
				continue;
			}
		}

	}

	public ConfigVO getConfig() {
		return config;
	}
	/**
	 * Method to minimize a url, minify it, create alias and provide aliased url as result
	 * @param in Input url to minimize
	 * @param ip ip address of client requesting we create url
	 * @param clientKey Client key of client that is requesting url be created
	 * @return The minimized url for the destination
	 */
	public String minimize(String in, Map<String,String> clientMetadata) {
		logger.debug("Maximizing " + in);
		String alias = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("CLIENT_METADATA",clientMetadata);
		// preprocessing
		for (IPlugin preplugins : preplugins) {
			in = (String) preplugins.execute(Hook.PREPROCESSOR, Operation.MINIMIZE, in, null, paramMap);

		}

		// processing
		for (IPlugin plugin : procplugins) {
			alias = (String) plugin.execute(Hook.PROCESSOR, Operation.MINIMIZE, in, null, paramMap);
			if (alias != null)
				break;
		}

		// POSTPROCESSOR
		for (IPlugin preplugins : preplugins) {
			alias = (String) preplugins.execute(Hook.POSTPROCESSOR, Operation.MINIMIZE, in, null, paramMap);
		}
		return config.getRootUrl() + alias;
	}
	/**
	 * Get destination url fron input alias
	 * @param in Input as alias 
	 * @return Full destination url 'maximized'
	 */
	public String maximize(String in,Map<String,String> clientMetadata) {
		String realUrl = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("CLIENT_METADATA",clientMetadata);
		// preprocessing
		for (IPlugin plugin : preplugins) {
			in = (String) plugin.execute(Hook.PREPROCESSOR, Operation.MAXIMIZE, in, null, paramMap);

		}

		// processing
		for (IPlugin plugin : procplugins) {
			URLVO out = (URLVO) plugin.execute(Hook.PROCESSOR, Operation.MAXIMIZE, in, null, paramMap);
			if (out != null) {
				realUrl = out.getDestination();
				break;
			}

		}

		// POSTPROCESSOR
		for (IPlugin plugin : preplugins) {
			realUrl = (String) plugin.execute(Hook.POSTPROCESSOR, Operation.MAXIMIZE, in, null, paramMap);
		}
		return realUrl;
	}

}
