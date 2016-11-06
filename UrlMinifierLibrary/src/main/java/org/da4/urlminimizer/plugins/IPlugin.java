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

import org.da4.urlminimizer.Hook;
import org.da4.urlminimizer.Operation;
import org.da4.urlminimizer.exception.APIKeyNotFound;
import org.da4.urlminimizer.vo.URLVO;
/**
 * Plugin api. Method plugin must implement to hook into the url minimizer
 * @author dmb
 *
 */
public interface IPlugin {
	/** Call shortly after plugin is instantiated or after reload.  Startup routines should go here
	 * 
	 * @param params Parameters passed during initialization, elements from config file
	 * can be passed here
	 */
	void init(Map<String, String> params);
/**
 * Main execution of plugin when hook is matched
 * @param hook Hook into an area of minimizer, currently Preprocessing, Postprocessing or processing
 * @param operation Operation in which the plugin was called. Could be minimize or maximize
 * @param input Input parameter to plugin
 * @param output Output object optionally can be supplied
 * @param params Additional parameters that can be passed to the plugin at runtime
 * @return An object can be returned from plugin that can be used in workflow of minimization
 * @throws APIKeyNotFound 
 */
	URLVO execute(Hook hook, Operation operation, Object input, Object output, Map<String, Object> params) throws APIKeyNotFound;
	/**
	 * Called during termination of the plugin
	 */
	void finished();

}