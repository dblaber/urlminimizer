package org.da4.urlminimizer.plugins;

import java.util.Map;

import org.da4.urlminimizer.Operation;

public class PluginAPI implements IPlugin {

	public PluginAPI() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.da4.urlminimizer.plugins.IPlugin#init(java.util.Map)
	 */
	@Override
	public void init(Map<String,String> params) {
		System.out.println("Starting plugin `"+ this.getClass().getName() +"`...");
		System.out.println("Params: " + params);
	}

	/* (non-Javadoc)
	 * @see org.da4.urlminimizer.plugins.IPlugin#execute(org.da4.urlminimizer.Operation, java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object execute(Operation operation, Object input, Object output) {
		System.out.println("Starting execution of plugin `"+ this.getClass().getName() +"`...");
		return null;
	}

	/* (non-Javadoc)
	 * @see org.da4.urlminimizer.plugins.IPlugin#finished()
	 */
	@Override
	public void finished() {
		System.out.println("Plugin terminated.");
	}

}