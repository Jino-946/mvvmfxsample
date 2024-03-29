package mvvmfxsample.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.m946.hanakolib.db.SfmDao;
import org.m946.hanakolib.db.JaybirdManager;
import org.m946.mvvmfxsample.db.Country;
import org.m946.mvvmfxsample.db.CountryDTO;
import org.simpleflatmapper.jdbc.Crud;

public class TestJaybirdManager {
	private JaybirdManager fbManager = null;
	
	@Before
	public void setUp() throws Exception {
		fbManager = new JaybirdManager("localhost", "employee");
	}

	@Test
	public void test() {
		assertTrue(fbManager.checkConnection());
	}

	@Test
	public void testDollar() {
		String sql = "select currency from country where country = 'USA'";
		assertEquals("Dollar", fbManager.getSingleResult(sql, String.class));
	}
	
	@Test
	public void testMapSingleResult() {
		String sql = "select country, currency from country where country = '%s'";
		Country expected = new Country("USA", "Dollar");
		assertEquals(expected, fbManager.getSingleResult(sql, Country.class, "USA"));
	}
	
	@Test
	public void testMapResultList() {
    	String sql = "select country, currency from country order by country";
    	List<Country> result = fbManager.getResultList(sql, Country.class);
   
    	assertEquals(14, result.size());       
		Country australia = new Country("Australia", "ADollar");
		assertEquals(australia, result.get(0));	
	}
	
	@Test
	public void testMap2ViewModel() {
    	String sql = "select country, currency from country where country = '%s'";
    	CountryDTO result = fbManager.getSingleResult(sql, CountryDTO.class, "Japan");
    	assertEquals("Japan", result.getCountry());
    	assertEquals("Yen", result.getCurrency());
	}

	
	
	@Test
	public void testCRUD() {
		String sql1 = "select country, currency from country order by country";
      	String sql2 = "select currency from country where country='%s'";
    	List<Country> result = fbManager.getResultList(sql1, Country.class);
      	assertEquals(14, result.size());   
      	
		fbManager.beginTransation();
		@SuppressWarnings("deprecation")
      	Crud<Country, String> crud = fbManager.createCrud("country", Country.class, String.class);
    
      	
      	//Create
      	try {
			crud.create(fbManager.getConnection(), new Country("China", "Yuan"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
      	result = fbManager.getResultList(sql1, Country.class);
      	assertEquals(15, result.size());   
      	assertEquals("Yuan", fbManager.getSingleResult(sql2, String.class, "China"));
      	
      	//Read
      	Country expected = new Country("Fiji", "FDollar");
      	Country fiji = null;
		try {
			fiji = crud.read(fbManager.getConnection(), "Fiji");
		} catch (SQLException e) {
			e.printStackTrace();
		}
      	assertEquals(expected, fiji);
      
      	//Update
      	Country japan = new Country("Japan", "円");
      	try {
			crud.update(fbManager.getConnection(), japan);
		} catch (SQLException e) {
			e.printStackTrace();
		}
      	assertEquals("円", fbManager.getSingleResult(sql2, String.class, "Japan"));
      	
      	//Deleteは外部キー制約によりエラーになるのでテストはできない
      	
      	//ロールバック	
      	fbManager.rollback();
      	result = fbManager.getResultList(sql1, Country.class);
      	assertEquals(14, result.size());   
	}

	@Test
	public void testSfmDao() throws SQLException {
		String sql1 = "select country, currency from country order by country";
      	String sql2 = "select currency from country where country='%s'";

      	List<Country> result = fbManager.getResultList(sql1, Country.class);
      	assertEquals(14, result.size());   
      	
		fbManager.beginTransation();
    	SfmDao<Country, String> countryDao = fbManager.newInstanceOfSfmDao("country", Country.class, String.class);

    	try {
    		countryDao.insert(new Country("China", "Yuan"));
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	result = fbManager.getResultList(sql1, Country.class);
      	assertEquals(15, result.size());   
     	
      	Country expected = new Country("Fiji", "FDollar");
      	Country fiji = null;
      	fiji = countryDao.get("Fiji");
      	assertEquals(expected, fiji);
      
      	//Update
      	Country japan = new Country("Japan", "円");
      	try {
			countryDao.update(japan);
		} catch (SQLException e) {}
      	assertEquals("円", fbManager.getSingleResult(sql2, String.class, "Japan"));
      
      	//ロールバック	
      	fbManager.rollback();
      	result = fbManager.getResultList(sql1, Country.class);
      	assertEquals(14, result.size());   		
	}

}
