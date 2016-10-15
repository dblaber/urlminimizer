package org.da4.urlminimizer.plugins;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.Hook;
import org.da4.urlminimizer.Operation;
import org.da4.urlminimizer.UrlMinimizer;

public class PluginAPI implements IPlugin {
	private static final Logger logger = LogManager.getLogger(PluginAPI.class);
	public PluginAPI() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.da4.urlminimizer.plugins.IPlugin#init(java.util.Map)
	 */
	@Override
	public void init(Map<String, String> params) {
		logger.info("Starting plugin `" + this.getClass().getName() + "`...");
		logger.debug("Params: " + params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.da4.urlminimizer.plugins.IPlugin#execute(org.da4.urlminimizer.
	 * Operation, java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object execute(Hook hook, Operation operation, Object input, Object output, Map<String, Object> params) {
		logger.trace("Starting execution of plugin `" + this.getClass().getName() + "` , Operation: " + operation
				+ " hook: " + hook + "...");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.da4.urlminimizer.plugins.IPlugin#finished()
	 */
	@Override
	public void finished() {
		logger.info("Plugin terminated.");
	}

}