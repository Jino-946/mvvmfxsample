package org.m946.mvvmfxsample.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.simpleflatmapper.jdbc.Crud;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class YaFbManager {
	private final static Logger log = LoggerFactory.getLogger(YaFbManager.class);
	
	
	private String url = null;
	private final String USER = "SYSDBA";
	private final String PASSWORD = "bakerata";
	private boolean inTransaction = false;
	private Connection txConnection;
	
	
	public YaFbManager(String host, String dbPath) {
		url = "jdbc:firebirdsql:%s/3050:%s?encoding=UTF8".formatted(host, dbPath);
	}
	
	public Connection getConnection() {
		return inTransaction ? txConnection : open();
	}
	
	private Connection open() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, USER, PASSWORD);
		} catch (SQLException e) {
			log.error(e.toString());
		}
		return conn;
	}
	
	public void beginTransation() {
		if (!inTransaction) {
			txConnection = open();
			inTransaction = true;
			try {
				txConnection.setAutoCommit(false);
				log.info("トランザクションを開始しました。");
			} catch (SQLException e) {
				log.error(e.toString());
			}
		}
	}

	public void commit() {
		if (txConnection != null && inTransaction) {
			inTransaction = false;
			try {
				txConnection.commit();
				log.info("トランザクションをコミットしました。");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					txConnection.close();
					txConnection = null;
				} catch (SQLException e) {
					log.error(e.toString());
				}
			}
		}
	}

	public void rollback() {
		if (txConnection != null && inTransaction) {
			inTransaction = false;
			try {
				txConnection.rollback();
				log.info("トランザクションをロールバックしました。");
			} catch (SQLException e) {
				log.error(e.toString());
			}finally {
				try {
					txConnection.close();
					txConnection = null;
				} catch (SQLException e) {
					log.error(e.toString());
				}
			}
		}
	}

	public void close() {
		if (txConnection != null) {
			try {
				txConnection.close();
			} catch (SQLException e) {
				log.error(e.toString());
			}
		}
	}
	
	public boolean checkConnection() {
		final String SQL = "select 1 from rdb$database";
		int n = getSingleResult(SQL, Integer.class);
		return n == 1 ? true : false;
	}

	/** 
	 * DBより条件に一致する単一列を選択しオブジェクトにマッピングして返す。
	 *
	 * @param <E> 戻値の型
	 * @param sql 選択型クエリー
	 * @param type 戻値のクラス
	 * @return Eで指定した型のオブジェクト
	 */
	public <E> E getSingleResult(String sql, Class<E> type) {
		log.debug(sql);
		
		E result = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection conn = getConnection();
		try {
			statement = conn.prepareStatement(sql);
			resultSet = statement.executeQuery();
			JdbcMapper<E> mapper = JdbcMapperFactory.newInstance().newMapper(type);
			resultSet.next();
			result = mapper.map(resultSet);
		} catch (SQLException e) {
			log.error(e.toString());
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
				log.error(e.toString());
			}
		}
		
		return result;
	}	
	
	/** 
	 * DBより条件に一致する行をオブジェクトにマッピングしリストとして返す。
	 *
	 * @param <E> 戻値の型
	 * @param sql 選択型クエリー
	 * @param type 戻値のクラス
	 * @return Eで指定した型のオブジェクトのリスト
	 */
	public <E> List<E> getResultList(String sql, Class<E> type) {
		log.debug(sql);
		
		List<E> result = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection conn = getConnection();
		try {
			statement = conn.prepareStatement(sql);
			resultSet = statement.executeQuery();
			JdbcMapper<E> mapper = JdbcMapperFactory.newInstance().newMapper(type);
			mapper.stream(resultSet).forEach(row -> result.add(row));
		} catch (SQLException e) {
			log.error(e.toString());
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
				log.error(e.toString());
			}
		}
		return result;
	}	
	
	/**
	 * テーブルにCRUD操作を行うオブジェクトを生成する
	 * 
	 * @param <E> DBテーブルをマッピングする型
	 * @param <T> DBテーブルの主キーの型
	 * @param table テーブル名
	 * @param targetType マッピングするクラス
	 * @param pkeyType   主キーのクラス
	 * @return
	 */
	public <E, T> Crud<E, T>createCrud(String table, Class<E> targetType, Class<T>pkeyType){
		Crud<E, T> crud = null;
		try {
			crud = JdbcMapperFactory
						.newInstance()
						.crud(targetType, pkeyType)
						.table(getConnection(), table);
		} catch (SQLException e) {
			log.error(e.toString());
		}
		return crud;
	}
	
	
	
	/* SQLパラメータありのgetSingleResult*/
	public <E> E getSingleResult(String sql, Class<E> type, Object arg) {
		String query = sql.formatted(arg);
		return getSingleResult(query, type);
	}
	
	public <E> E getSingleResult(String sql, Class<E> type, Object arg1, Object arg2) {
		String query = sql.formatted(arg1, arg2);
		return getSingleResult(query, type);
	}

	public <E> E getSingleResult(String sql, Class<E> type, 
									Object arg1, Object arg2, Object arg3) {
		String query = sql.formatted(arg1, arg2, arg3);
		return getSingleResult(query, type);
	}

	public <E> E getSingleResult(String sql, Class<E> type,
									Object arg1, Object arg2, Object arg3, Object arg4) {
		String query = sql.formatted(arg1, arg2, arg3, arg4);
		return getSingleResult(query, type);
	}
	public <E> E getSingleResult(String sql, Class<E> type, 
							Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
		String query = sql.formatted(arg1, arg2, arg3, arg4, arg5);
		return getSingleResult(query, type);
	}
	
	
	/* SQLパラメータありの getResultList */
	public <E> List<E> getResultList(String sql, Class<E> type, Object arg) {
		String query = sql.formatted(arg);
		return getResultList(query, type);
	}
	
	public <E> List<E> getResultList(String sql, Class<E> type, Object arg1, Object arg2) {
		String query = sql.formatted(arg1, arg2);
		return getResultList(query, type);
	}

	public <E> List<E> getResultList(String sql, Class<E> type, 
									Object arg1, Object arg2, Object arg3) {
		String query = sql.formatted(arg1, arg2, arg3);
		return getResultList(query, type);
	}

	public <E> List<E> getResultList(String sql, Class<E> type,
									Object arg1, Object arg2, Object arg3, Object arg4) {
		String query = sql.formatted(arg1, arg2, arg3, arg4);
		return getResultList(query, type);
	}
	public <E> List<E> getResultList(String sql, Class<E> type, 
							Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
		String query = sql.formatted(arg1, arg2, arg3, arg4, arg5);
		return getResultList(query, type);
	}		
}
