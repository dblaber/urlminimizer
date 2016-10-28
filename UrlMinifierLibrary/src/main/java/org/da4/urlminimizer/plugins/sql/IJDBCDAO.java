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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.da4.urlminimizer.exception.AliasNotFound;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.vo.URLVO;
/**
 * DAO Class for SQL relational solutions
 * @author dmb
 *
 */
public interface IJDBCDAO {
	/**
	 * Get destination url from database
	 * @param alias Alias to lookup
	 * @return Destination that alias resolved to
	 */
	URLVO getDestinationUrlFromAlias(String alias);
	/**
	 * Get next id sequence from db
	 * @return Next ID
	 */
	long getNextId();
/**
 * Persist urlvo object to the tables that are mapped to it
 * @param dataObj
 */
	void persistUrl(URLVO dataObj);
/**
 * Resolve alias from destination if it exists
 * @param destination Destination maximized url
 * @return URLVO if a matching alias exists
 * @throws AliasNotFound
 */
	URLVO getAliasFromDestination(String destination) throws AliasNotFound;
void incrementClickCount(String alias, Date date);
void insertStatsLog(String alias, String ip, String useragent, String referer, Date date);
void insertNewClicksCount(String alias, Date date);

}