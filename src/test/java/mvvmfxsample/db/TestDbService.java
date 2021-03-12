package mvvmfxsample.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.m946.mvvmfxsample.db.Country;
import org.m946.mvvmfxsample.db.DbService;

public class TestDbService {
	DbService dbService;
	
	@Before
	public void setUp() {
		dbService = new DbService();
	}
	
	@After
	public void tearDown() {
		dbService.close();
	}

	@Test
	public void test() {
		assertTrue(dbService.checkConnection());

	}
	
	@Test
	public void testDollar() {
		assertEquals("Dollar", dbService.getDollar());
	}
	
	@Test
	public void testUsa() {
		Country usa = new Country("USA", "Dollar");
		assertEquals(usa, dbService.getUsa());
	}
	
	@Test
	public void testCountries() {
		List<Country> countries = dbService.getCountries();
		assertEquals(14, countries.size());
		
		Country australia = new Country("Australia", "ADollar");
		assertEquals(australia, countries.get(0));	
	}
}
