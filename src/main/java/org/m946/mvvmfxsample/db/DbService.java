package org.m946.mvvmfxsample.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;

public class DbService {
		private Connection conn;
		private final String URL = "jdbc:firebirdsql:localhost/3050:employee?encoding=UTF8";
		private final String USER = "SYSDBA";
		private final String PASSWORD = "bakerata";
		
		
	    public DbService() {
	        try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	   
	    
	    public boolean checkConnection() throws RuntimeException{
	    	final String SQL = "select 1 from rdb$database";
	        boolean result = false;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
				ps = conn.prepareStatement(SQL);
				rs = ps.executeQuery();
				rs.next();
				int n = rs.getInt(1);
				result = n == 1 ? true : false;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						rs.close();
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
	        return result;
	    }
	    
	    public String getDollar() {
	    	final String sql = "select currency from country where country = 'USA'";
	    	String result = "";
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				rs.next();
				result = rs.getString(1);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						rs.close();
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
	        return result;	    	
	    }
	   
	    public Country getUsa() {
	    	final String sql = "select country, currency from country where country = 'USA'";
	    	Country result = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				JdbcMapper<Country> mapper = JdbcMapperFactory.newInstance().newMapper(Country.class);
				rs.next();
				result = mapper.map(rs);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						rs.close();
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
	        return result;
	    }
	    
	    public List<Country> getCountries(){
	    	List<Country> countries = new ArrayList<>();
	    	final String sql = "select country, currency from country order by country";
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				JdbcMapper<Country> mapper = JdbcMapperFactory.newInstance().newMapper(Country.class);
				mapper.stream(rs).forEach(c -> countries.add(c));
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						rs.close();
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}	    	
	    	
	    	return countries;
	    }
	  
	    public CountryVM getUsaVM() {
	    	final String sql = "select country, currency from country where country = 'USA'";
	    	CountryVM result = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				JdbcMapper<CountryVM> mapper = JdbcMapperFactory.newInstance().newMapper(CountryVM.class);
				rs.next();
				result = mapper.map(rs);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						rs.close();
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
	        return result;
	    }
	    
	    public void close() {
	    	if (conn != null) {
	    		try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    	}
	    }
}
