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
import org.da4.urlminimizer.exception.APIKeyNotFound;
import org.da4.urlminimizer.vo.URLVO;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class HashCacheStoragePlugin extends PluginAPI {
	private int maxCacheCount = 0;
	private static final Logger logger = LogManager.getLogger(HashCacheStoragePlugin.class);
	static private Cache<String, URLVO> aliasCache = null;
	static private Cache<String, URLVO> bigUrlCache = null;
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
	aliasCache = CacheBuilder.newBuilder().maximumSize(maxCacheCount).build();
	bigUrlCache = CacheBuilder.newBuilder().maximumSize(maxCacheCount).build();
	logger.info("Created alias cache and bigurl cache, " + maxCacheCount + " max size each!");
	
}
@Override
	public URLVO execute(Hook hook, Operation operation, Object input, Object output, Map<String, Object> params) throws APIKeyNotFound {
		if(operation.equals(Operation.MAXIMIZE)){
			if(hook.equals(Hook.POSTPROCESSOR))
			{
				URLVO url = (URLVO)params.get("REAL_URL");
				aliasCache.put(url.getAlias(), url);
			} else if(hook.equals(Hook.PROCESSOR))
			{
				if(aliasCache.getIfPresent((String)input) != null)
					logger.debug("Cache Hit!");
				return aliasCache.getIfPresent((String)input);
			}
			
		}else if (operation.equals(Operation.MINIMIZE))
		{
			if(hook.equals(Hook.POSTPROCESSOR))
			{
				URLVO url = (URLVO)params.get("ALIAS");
				bigUrlCache.put(url.getDestination(), url);
				
			}else if(hook.equals(Hook.PROCESSOR))
			{
				if(bigUrlCache.getIfPresent((String)input) != null)
					logger.debug("Cache hit!");
				return bigUrlCache.getIfPresent((String)input);
			}
		}
		return super.execute(hook, operation, input, output, params);
	}
}

