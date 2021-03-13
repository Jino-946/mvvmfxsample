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

public class DbService {
	private Connection connection;
	private final String URL = "jdbc:firebirdsql:localhost/3050:employee?encoding=UTF8";
	private final String USER = "SYSDBA";
	private final String PASSWORD = "bakerata";
	private boolean inTransaction = false;

	public DbService() {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}
	
	
	public void beginTransation() {
		if (connection != null) {
			inTransaction = true;
			try {
				connection.setAutoCommit(!inTransaction);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void commit() {
		if (connection != null && inTransaction) {
			inTransaction = false;
			try {
				connection.commit();
				connection.setAutoCommit(inTransaction);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void rollback() {
		if (connection != null && inTransaction) {
			inTransaction = false;
			try {
				connection.rollback();
				connection.setAutoCommit(inTransaction);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean checkConnection() throws RuntimeException {
		final String SQL = "select 1 from rdb$database";
		boolean result = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(SQL);
			rs = ps.executeQuery();
			rs.next();
			int n = rs.getInt(1);
			result = n == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					rs.close();
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public String getCurrency(String country) {
		final String sql = "select currency from country where country = ?";
		String result = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, country);
			rs = ps.executeQuery();
			rs.next();
			result = rs.getString(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					rs.close();
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Country getCountry(String country) {
		final String sql = "select country, currency from country where country = ?";
		Country result = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, country);
			rs = ps.executeQuery();
			JdbcMapper<Country> mapper = JdbcMapperFactory.newInstance().newMapper(Country.class);
			rs.next();
			result = mapper.map(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					rs.close();
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<Country> getCountries() {
		List<Country> countries = new ArrayList<>();
		final String sql = "select country, currency from country order by country";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			JdbcMapper<Country> mapper = JdbcMapperFactory.newInstance().newMapper(Country.class);
			mapper.stream(rs).forEach(c -> countries.add(c));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					rs.close();
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return countries;
	}

	public CountryVM getCountryVM(String countryName) {
		final String sql = "select country, currency from country where country = ?" ;
		CountryVM result = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, countryName);
			rs = ps.executeQuery();
			JdbcMapper<CountryVM> mapper = JdbcMapperFactory.newInstance().newMapper(CountryVM.class);
			rs.next();
			result = mapper.map(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					rs.close();
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;		
	}
	
	
	
	public Crud<CountryVM, String> countryVMCrud(){
		Crud<CountryVM, String> crud = null;
		try {
			crud = JdbcMapperFactory
				.newInstance()
				.crud(CountryVM.class, String.class)
				.table(connection, "country");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return crud;
		
	}
	
	
	public void insert(CountryVM country) {
		final String sql = "insert into country(country, currency) values(?, ?)" ;
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, country.getCountry());
			ps.setString(2, country.getCurrency());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}

	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
