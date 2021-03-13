package org.m946.mvvmfxsample.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;

public class YaFbManager {
	private String URL = null;
	private final String USER = "SYSDBA";
	private final String PASSWORD = "bakerata";
	private boolean inTransaction = false;
	private Connection txConnection;
	
	
	public YaFbManager(String host, String dbPath) {
		URL = "jdbc:firebirdsql:%s/3050:%s?encoding=UTF8".formatted(host, dbPath);
	}
	
	public Connection getConnection() {
		
		return txConnection;
	}
	
	public Connection open() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void beginTransation() {
		if (!inTransaction) {
			txConnection = open();
			inTransaction = true;
			try {
				txConnection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void commit() {
		if (txConnection != null && inTransaction) {
			inTransaction = false;
			try {
				txConnection.commit();
				txConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void rollback() {
		if (txConnection != null && inTransaction) {
			inTransaction = false;
			try {
				txConnection.rollback();
				txConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	public boolean checkConnection() {
		final String SQL = "select 1 from rdb$database";
		int n = getSingleResult(SQL, Integer.class);
		return n == 1 ? true : false;
	}

	public <E> E getSingleResult(String sql, Class<E> type, Object arg) {
		String query = sql.formatted(arg);
		return getSingleResult(query, type);
	}
	
	
	public <E> E getSingleResult(String sql, Class<E> type) {
		E result = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection conn = inTransaction ? getConnection() : open();
		try {
			statement = conn.prepareStatement(sql);
			resultSet = statement.executeQuery();
			JdbcMapper<E> mapper = JdbcMapperFactory.newInstance().newMapper(type);
			resultSet.next();
			result = mapper.map(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					resultSet.close();
					statement.close();
					if (!inTransaction) {
						conn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
