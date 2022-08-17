package mvvmfxsample.db;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.m946.mvvmfxsample.db.Country;
import org.m946.mvvmfxsample.db.CountryDTO;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;

public class TestJdbc {
	private Connection conn;
	private final String URL = "jdbc:firebirdsql:localhost/3050:employee?encoding=UTF8";
	private final String USER = "SYSDBA";
	private final String PASSWORD = "bakerata";
	
	@Before
	public void setUp() {
        try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
    	if (conn != null) {
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
	}
	
	@Test
	public void testConnection() {
    	final String SQL = "select 1 from rdb$database";
        int result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			rs.next();
			result = rs.getInt(1);
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
        assertEquals(1, result);
	}
	
	@Test
	public void testDollar() {
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
        assertEquals("Dollar", result);
	}

	@Test
	public void testMapSingleResult() {
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
        
        Country expected = new Country("USA", "Dollar");
        assertEquals(expected, result);
	}
	
	@Test
	public void testMapResultList() {
    	List<Country> result = new ArrayList<>();
    	final String sql = "select country, currency from country order by country";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			JdbcMapper<Country> mapper = JdbcMapperFactory.newInstance().newMapper(Country.class);
			mapper.stream(rs).forEach(c -> result.add(c));
			
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
        
        assertEquals(14, result.size());
        
		Country australia = new Country("Australia", "ADollar");
		assertEquals(australia, result.get(0));	
	}
	
	@Test
	public void testMap2ViewModel() {
    	final String sql = "select country, currency from country where country = 'USA'";
    	CountryDTO result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			JdbcMapper<CountryDTO> mapper = JdbcMapperFactory.newInstance().newMapper(CountryDTO.class);
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
       assertEquals("USA", result.getCountry());	
       assertEquals("Dollar", result.getCurrency());
	}
}
