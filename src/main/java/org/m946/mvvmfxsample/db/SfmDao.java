package org.m946.mvvmfxsample.db;

import java.sql.SQLException;

import org.simpleflatmapper.jdbc.Crud;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * org.simpleflatmapper.jdbc.Crudのラッパークラス<br>
 * SimpleFlatMapperDaoの略 SfmDao<br><br>
 * DBテーブルに対しCRUD操作を行う。<br>
 * 
 * YaFbManagerのインスタンスからインスタンス化されることを想定している。
 * 
 * @author xyro
 *
 * @param <E>DBテーブルをマッピングするPOJOの型
 * @param <T>DBテーブルの主キーの型
 */
public class SfmDao<E, T> {
	private final static Logger log = LoggerFactory.getLogger(SfmDao.class);

	private YaJdbcManager fbManager = null;
	private String table = null;
	private Crud<E, T> crud = null;
	
	/**
	 * @param fbManager FbDaoを生成するYaFbManagerのインスタンス 
	 * @param table DBテーブルの名称
	 * @param targetType DBテーブルをマッピングするPOJOの型
	 * @param pkeyType DBテーブルの主キーの型
	 */
	public SfmDao(YaJdbcManager fbManager, String table, Class<E> targetType, Class<T>pkeyType) {
		this.fbManager = fbManager;
		this.table = table;
		try {
			crud = JdbcMapperFactory
						.newInstance()
						.crud(targetType, pkeyType)
						.table(fbManager.getConnection(), table);
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}
	
	/**
	 * テーブルにbeanをインサートする
	 * @param bean インサートするPOJO
	 */
	public void insert(E bean) {
		try {
			crud.create(fbManager.getConnection(), bean);
			log.debug("Table: %s insert: %s".formatted(table, bean));
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}
	
	/**
	 * テーブルより主キーで行を取得する
	 * @param key 主キーの値
	 * @return 取得した行をマッピングしたPOJO
	 */
	public E get(T key) {
		E result = null;
		try {
			result = crud.read(fbManager.getConnection(), key);
			log.debug("Table: %s get: key = %s".formatted(table, key));
		} catch (SQLException e) {
			log.error(e.toString());
		}
		return result;
	}
	
	/**
	 * テーブルをbeanの内容で更新する
	 * @param bean 更新する行をマッピングしたPOJO
	 */
	public void update(E bean) {
		try {
			crud.update(fbManager.getConnection(), bean);
			log.debug("Table: %s update: %s".formatted(table, bean));
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}
	
	/**
	 * テーブルより主キーで指定した行を削除する
	 * @param key 削除する行の主キーの値
	 */
	public void delete(T key) {
		try {
			crud.delete(fbManager.getConnection(), key);
			log.debug("Table: %s delete: key = ".formatted(table, key));
		} catch (SQLException e) {
			log.error(e.toString());
		}
		
	}

}
