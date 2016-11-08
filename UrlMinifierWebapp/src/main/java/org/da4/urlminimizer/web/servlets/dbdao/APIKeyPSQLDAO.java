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
package org.da4.urlminimizer.web.servlets.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.vo.URLVO;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

public class APIKeyPSQLDAO  {
	private static final Logger logger = LogManager.getLogger(APIKeyPSQLDAO.class);
	final static String SQL_GET_APIKEY_INFO = "select * from minifier.api_registration where api_key = ? and active = true";
	final static String SQL_INSERT_APIKEY_INFO = "insert into minifier.api_registration (email_address,api_key,active,creation_ts) values (?,?,true,?)";
	final static String SQL_ACTIVATE_APIKEY = "update minifier.api_registration set active = true where email_address = ?";
	static DataSource ds = null;

	public APIKeyPSQLDAO(DataSource ds) {
		this.ds = ds;
	}

	public APIKeyPSQLDAO(String url, String user, String pass) {
		if(ds == null)
		{
			Jdbc3PoolingDataSource ds = null;
			ds = new Jdbc3PoolingDataSource();
			ds.setUrl(url);
			ds.setDataSourceName("APIKeyPool");
			ds.setUser(user);
			ds.setPassword(pass);
			ds.setMaxConnections(3);
			this.ds = ds;
		}
	}
	public boolean isAPIKeyExist(String apiKey) {
		Connection conn = null;
		URLVO vo = new URLVO();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_GET_APIKEY_INFO);
			logger.debug("SQL to be run: "+" " +SQL_GET_APIKEY_INFO+" " +" Param: "+" " +apiKey);
			stmt.setString(1, apiKey);

			rs = stmt.executeQuery();
			if (rs.next() == false)
				return false;
		} catch (SQLException e) {
			logger.error("SQL Error", e);
			throw new RuntimeUrlException("SQL Err", e);
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return true;
	}

	public void persistNewAPIKey(String email, String apiKey, Date creationTime) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT_APIKEY_INFO);
			logger.debug("SQL to be run: "+SQL_INSERT_APIKEY_INFO+" Param: " + email+" " + apiKey+" " +creationTime);
			stmt.setString(1, email);
			stmt.setString(2, apiKey);
			stmt.execute();
		} catch (SQLException e) {
			logger.error("SQL Error", e);
			throw new RuntimeUrlException("SQL Err", e);
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	
}
