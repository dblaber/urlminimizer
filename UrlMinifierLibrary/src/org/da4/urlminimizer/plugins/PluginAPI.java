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