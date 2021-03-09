package org.m946.mvvmfxsample.db;

import org.jdbi.v3.core.Jdbi;

public class DbService {
	private Jdbi jdbi = Jdbi.create("jdbc:firebirdsql:localhost/3050:employee?encoding=UTF8", "SYSDBA", "bakerata");
	
	public boolean checkConnection() {
		Integer n = jdbi.withHandle(handle ->
			handle.createQuery("select 1 from rdb$database")
			.mapTo(Integer.class)
			.one()
		);
		return n == 1 ? true : false;
	}

}
