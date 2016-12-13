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
import java.util.ArrayList;
import java.util.List;
/**
 * Blocked url patterns, as provided from configuration file as attributes
 */
import java.util.Map;
import java.util.regex.Pattern;

import org.da4.urlminimizer.Hook;
import org.da4.urlminimizer.Operation;
import org.da4.urlminimizer.exception.APIKeyNotFound;
import org.da4.urlminimizer.exception.URLBlockedException;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.exception.URLException;
import org.da4.urlminimizer.vo.URLVO;

public class BlockUrlPatternPlugin extends PluginAPI {
	List<Pattern> blackListRegExp = new ArrayList<Pattern>();
	@Override
	public void init(Map<String, String> params) {
		super.init(params);
		 for(String key: params.keySet())
		 {
			 blackListRegExp.add(Pattern.compile(key));
		 }
	}

	@Override
	public URLVO execute(Hook hook, Operation operation, Object input, Object output, Map<String, Object> params) throws APIKeyNotFound, URLException {
		super.execute(hook, operation, input, output, params);
		String inputUrl = (String)input;;
		if (Operation.MAXIMIZE.equals(operation) && Hook.POSTPROCESSOR.equals(hook)) {
			URLVO fullUrl = (URLVO)params.get("REAL_URL");
			for(Pattern regExp : blackListRegExp)
			{
				if(regExp.matcher(fullUrl.getDestination()).matches())
				{
					throw new URLBlockedException("URL blocked");
				}
			}
			return null;
		} else if (Operation.MINIMIZE.equals(operation) && Hook.PROCESSOR.equals(hook)) {

			for(Pattern regExp : blackListRegExp)
			{
				if(regExp.matcher(inputUrl).matches())
				{
					throw new URLBlockedException("URL blocked");
				}
			}
		} else {
		}
		return null;
	}
}
