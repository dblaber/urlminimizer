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
package org.da4.urlminimizer.plugins.sql;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.Hook;
import org.da4.urlminimizer.Operation;
import org.da4.urlminimizer.exception.APIKeyNotFound;
import org.da4.urlminimizer.exception.AliasNotFound;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.plugins.PluginAPI;
import org.da4.urlminimizer.vo.URLVO;
/**
 * JDBC storage plugin, main plugin to store urls in a relational DB
 * @author dmb
 *
 */
public class JDBCPersistantStoragePlugin extends PluginAPI {
	private static final Logger logger = LogManager.getLogger(JDBCPersistantStoragePlugin.class);
	IJDBCDAO dao = null;
	final static String RESERVED_ALIASES = "RESERVED_ALIASES";

	@Override
	public void init(Map<String, String> params) {
		super.init(params);
		dao = new PSQLDAO(params.get("url"), params.get("userid"), params.get("password"));
	}
/**
 * Check in database to determine whether url exists, if it exists, return it
 * otherwise, pick from db sequences until one isn't in the reserved list
 * Persist once open sequence is found
 * @throws APIKeyNotFound 
 */
	@Override
	public URLVO execute(Hook hook, Operation operation, Object input, Object output, Map<String, Object> params) throws APIKeyNotFound {
		super.execute(hook, operation, input, output, params);
		boolean urlCreated = false;
		Map<String,String> clientMetadata = (Map<String,String>)params.get("CLIENT_METADATA");
		// if null lets just create empty map to avoid null checks later
		if(clientMetadata == null)
			clientMetadata = new HashMap<String,String>();
		if (Operation.MAXIMIZE.equals(operation)) {
			return dao.getDestinationUrlFromAlias((String) input);
		} else if (Operation.MINIMIZE.equals(operation)) {
			if(!dao.isAPIKeyExist((String)clientMetadata.get("CLIENT_KEY")))
			{
				throw new APIKeyNotFound((String)clientMetadata.get("CLIENT_KEY"));
			}
			URLVO url;
			try {
				url = dao.getAliasFromDestination((String) input);
			} catch (AliasNotFound e) {
				String alias = null;
				Set<String> reservedSet = (Set<String>) params.get(RESERVED_ALIASES);
				do {
					long id = dao.getNextId();
					// use base 36 to convert id to alias. 
					alias = Long.toString(id, 36);
				} while (reservedSet.contains(alias));
				url = new URLVO();
				url.setAlias(alias);
				url.setCreatorApiKey((String)clientMetadata.get("CLIENT_KEY"));
				url.setDestination((String) input);
				url.setIp((String)clientMetadata.get("IP"));
				url.setReferer((String)clientMetadata.get("REFERER"));
				url.setTimeCreated(new Date());
				url.setUserAgent(clientMetadata.get("USER_AGENT"));
				dao.persistUrl(url);
				urlCreated = true;
				params.put("URL_CREATED",true);
			}
			output = url.getAlias();
			return url;
		} else {
			throw new RuntimeUrlException("Invalid operation `" + operation + "`");
		}
	}
}
