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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.exception.AliasNotFound;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.vo.URLVO;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

public class PSQLDAO implements IJDBCDAO {
	private static final Logger logger = LogManager.getLogger(PSQLDAO.class);
	final static String SQL_GET_DESTINATION_FROM_ALIAS = "select * from minifier.minimized_urls where minified_alias = ?";
	final static String SQL_GET_ALIAS_FROM_DESTINATION = "select * from minifier.minimized_urls where destination_url = ?";

	final static String SQL_INSERT_NEW_ALIAS = "insert into minifier.minimized_urls (minified_alias,creation_api_key,destination_url,source_ip,user_agent,created_ts,referrer) values (?,?,?,?,?,?,?) ";
	final static String SQL_INSERT_NEW_ALIAS_STATS = "insert into minifier.stats_clicks (minified_alias,click_cnt,last_clicked_ts) values (?,?,?) ";
	final static String SQL_INSERT_NEW_STATS_LOG = "insert into minifier.stats_click_log (minified_alias,source_ip,user_agent,click_ts, referrer) values (?,?,?,?,?) ";
	final static String SQL_UPDATE_CLICK_STATS = "UPDATE minifier.stats_clicks set click_cnt = (click_cnt + 1),last_clicked_ts  = ? where minified_alias = ?";
	final static String SQL_GET_NEXT_ID = "select nextval('minifier.alias_seq')";
	static DataSource ds = null;

	public PSQLDAO(DataSource ds) {
		this.ds = ds;
	}

	public PSQLDAO(String url, String user, String pass) {
		if(ds == null)
		{
			Jdbc3PoolingDataSource ds = null;
			ds = new Jdbc3PoolingDataSource();
			ds.setUrl(url);
			ds.setDataSourceName("MinimizerPool");
			ds.setUser(user);
			ds.setPassword(pass);
			ds.setMaxConnections(10);
			this.ds = ds;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.da4.urlminimizer.plugins.IJDBCDAO#getDestinationUrlFromAlias(java.
	 * lang.String)
	 */
	@Override
	public URLVO getDestinationUrlFromAlias(String alias) {
		Connection conn = null;
		URLVO vo = new URLVO();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_GET_DESTINATION_FROM_ALIAS);
			logger.debug("SQL to be run: "+" " +SQL_GET_DESTINATION_FROM_ALIAS+" " +" Param: "+" " +alias);
			stmt.setString(1, alias);

			rs = stmt.executeQuery();
			if (rs.next() == false)
				return null;

			vo.setAlias(rs.getString("minified_alias"));
			vo.setCreatorApiKey(rs.getString("creation_api_key"));
			vo.setDestination(rs.getString("destination_url"));
			vo.setIp(rs.getString("source_ip"));
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
		return vo;
	}

	@Override
	public URLVO getAliasFromDestination(String destination) throws AliasNotFound {
		Connection conn = null;
		URLVO vo = new URLVO();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_GET_ALIAS_FROM_DESTINATION);
			logger.debug("SQL to be run: "+" " +SQL_GET_ALIAS_FROM_DESTINATION+" " +" Param: "+" " +destination);
			stmt.setString(1, destination);

			rs = stmt.executeQuery();
			if (rs.next() == false)
				throw new AliasNotFound("Alias not found");

			vo.setAlias(rs.getString("minified_alias"));
			vo.setCreatorApiKey(rs.getString("creation_api_key"));
			vo.setDestination(rs.getString("destination_url"));
			vo.setIp(rs.getString("source_ip"));
			vo.setTimeCreated(rs.getTimestamp("created_ts"));
			vo.setUserAgent(rs.getString("user_agent"));
			vo.setReferer(rs.getString("referrer"));
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
		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.da4.urlminimizer.plugins.IJDBCDAO#getNextId()
	 */
	@Override
	public long getNextId() {
		long id = -1;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_GET_NEXT_ID);
			logger.debug("SQL to be run: "+" " +SQL_GET_NEXT_ID);
			rs = stmt.executeQuery();
			if (rs.next() == false)
				return -1;
			id = rs.getLong(1);
		} catch (SQLException e) {
			logger.error("SQL Error", e);
			throw new RuntimeUrlException("SQL Err", e);
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.da4.urlminimizer.plugins.IJDBCDAO#persistUrl(org.da4.urlminimizer.vo.
	 * URLVO)
	 */
	@Override
	public void persistUrl(URLVO dataObj) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT_NEW_ALIAS);
			logger.debug("SQL to be run: "+SQL_GET_ALIAS_FROM_DESTINATION+" Param: " + dataObj.getAlias()+" " + dataObj.getCreatorApiKey()+" " +dataObj.getDestination()+" " +dataObj.getIp());
			stmt.setString(1, dataObj.getAlias());
			stmt.setString(2, dataObj.getCreatorApiKey());
			stmt.setString(3, dataObj.getDestination());
			stmt.setString(4, dataObj.getIp());
			stmt.setString(5, dataObj.getUserAgent());
			stmt.setTimestamp(6, new Timestamp(dataObj.getTimeCreated().getTime()));
			stmt.setString(7, dataObj.getReferer());
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.da4.urlminimizer.plugins.IJDBCDAO#insertNewClicksCount(org.da4.urlminimizer.vo.
	 * URLVO)
	 */
	@Override
	public void insertNewClicksCount(String alias, Date date)
	{
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT_NEW_ALIAS_STATS);
			logger.debug("SQL to be run: "+SQL_INSERT_NEW_ALIAS_STATS+" Param: " + alias + ", " + 0 + ", " + date);
			stmt.setString(1, alias);
			stmt.setLong(2, 0);
			stmt.setTimestamp(3, new Timestamp(date.getTime()));
			stmt.execute();
		}catch (SQLException e) {
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.da4.urlminimizer.plugins.IJDBCDAO#insertStatsLog(org.da4.urlminimizer.vo.
	 * URLVO)
	 */
	@Override
	public void insertStatsLog(String alias, String ip, String useragent, String referrer, Date date)
	{
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			//minified_alias,source_ip,user_agent,click_ts, referrer
			stmt = conn.prepareStatement(SQL_INSERT_NEW_STATS_LOG);
			logger.debug("SQL to be run: "+SQL_INSERT_NEW_STATS_LOG+" Param: " + alias + ", " + ip + ", " + useragent + ", " + date + ", " + referrer);
			stmt.setString(1, alias);
			stmt.setString(2, ip);
			stmt.setString(3, useragent);
			stmt.setTimestamp(4, new Timestamp(date.getTime()));
			stmt.setString(5, referrer);
			stmt.execute();
		}catch (SQLException e) {
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.da4.urlminimizer.plugins.IJDBCDAO#incrementClickCount(org.da4.urlminimizer.vo.
	 * URLVO)
	 */
	@Override
	public void incrementClickCount(String alias, Date date)
	{
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_UPDATE_CLICK_STATS);
			logger.debug("SQL to be run: "+SQL_UPDATE_CLICK_STATS+" Param: " + date + ", " + 0 + ", " + alias);
			stmt.setTimestamp(1, new Timestamp(date.getTime()));
			stmt.setString(2, alias);
			stmt.execute();
		}catch (SQLException e) {
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
