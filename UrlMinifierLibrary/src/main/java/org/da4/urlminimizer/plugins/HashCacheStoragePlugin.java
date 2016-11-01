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
import org.da4.urlminimizer.vo.URLVO;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class HashCacheStoragePlugin extends PluginAPI {
	private int maxCacheCount = 0;
	private static final Logger logger = LogManager.getLogger(HashCacheStoragePlugin.class);
	Cache<String, URLVO> cache = null;
/**
 * Expects 'maxcachecount' to be provided as a param
 */
@Override
public void init(Map<String, String> params) {
	super.init(params);
	int maxCacheCount = 100;
	if( params.get("maxcachecount") != null)
		maxCacheCount = Integer.parseInt((String)params.get("maxcachecount"));
	this.maxCacheCount = maxCacheCount;
	logger.debug("Setting up cache");
	cache = CacheBuilder.newBuilder().maximumSize(maxCacheCount).build();
	
}
@Override
	public URLVO execute(Hook hook, Operation operation, Object input, Object output, Map<String, Object> params) {
		if(operation.equals(Operation.MINIMIZE)){
			if(hook.equals(Hook.POSTPROCESSOR))
			{
				//cache.put(key, value);
			}
			
		}else if (operation.equals(Operation.MAXIMIZE))
		{
			
		}
		return super.execute(hook, operation, input, output, params);
	}
}

