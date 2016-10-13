package org.da4.urlminimizer.plugins.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.da4.urlminimizer.exception.AliasNotFound;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.vo.URLVO;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

public class PSQLDAO implements IJDBCDAO {
	final static String SQL_GET_DESTINATION_FROM_ALIAS = "select * from minifier.minimized_urls where minified_alias = ?";
	final static String SQL_GET_ALIAS_FROM_DESTINATION = "select * from minifier.minimized_urls where destination_url = ?";

	final static String SQL_INSERT_NEW_ALIAS = "insert into minifier.minimized_urls (minified_alias,creation_api_key,destination_url,source_ip) values (?,?,?,?) ";

	final static String SQL_GET_NEXT_ID = "select nextval('minifier.alias_seq')";
	DataSource ds = null;

	public PSQLDAO(DataSource ds) {
		this.ds = ds;
	}

	public PSQLDAO(String url, String user, String pass) {
		Jdbc3PoolingDataSource ds = null;
		ds = new Jdbc3PoolingDataSource();
		ds.setUrl(url);
		ds.setDataSourceName("MinimizerPool");
		ds.setUser(user);
		ds.setPassword(pass);
		ds.setMaxConnections(10);
		this.ds = ds;
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
			stmt.setString(1, alias);

			rs = stmt.executeQuery();
			if (rs.next() == false)
				return null;

			vo.setAlias(rs.getString("minified_alias"));
			vo.setCreatorApiKey(rs.getString("creation_api_key"));
			vo.setDestination(rs.getString("destination_url"));
			vo.setIp(rs.getString("source_ip"));
		} catch (SQLException e) {
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
			stmt.setString(1, destination);

			rs = stmt.executeQuery();
			if (rs.next() == false)
				throw new AliasNotFound("Alias not found");

			vo.setAlias(rs.getString("minified_alias"));
			vo.setCreatorApiKey(rs.getString("creation_api_key"));
			vo.setDestination("destination_url");
			vo.setIp(rs.getString("source_ip"));
		} catch (SQLException e) {
			e.printStackTrace();
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

			rs = stmt.executeQuery();
			if (rs.next() == false)
				return -1;
			id = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
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
			stmt.setString(1, dataObj.getAlias());
			stmt.setString(2, dataObj.getCreatorApiKey());
			stmt.setString(3, dataObj.getDestination());
			stmt.setString(4, dataObj.getIp());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
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
