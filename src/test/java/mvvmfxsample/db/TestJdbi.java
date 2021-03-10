package mvvmfxsample.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.m946.mvvmfxsample.db.DbService;

public class TestJdbi {

	@Test
	public void test() {
		DbService dbService = new DbService();
		assertTrue(dbService.checkConnection());
	}

}
