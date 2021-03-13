package mvvmfxsample.db;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.m946.mvvmfxsample.db.YaFbManager;

public class TestYaFbManager {
	private YaFbManager fbManager = null;
	@Before
	public void setUp() throws Exception {
		fbManager = new YaFbManager("localhost", "employee");
	}

	@Test
	public void test() {
		assertTrue(fbManager.checkConnection());
	}

}
