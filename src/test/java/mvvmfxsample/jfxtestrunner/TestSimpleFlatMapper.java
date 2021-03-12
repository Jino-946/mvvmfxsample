package mvvmfxsample.jfxtestrunner;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.m946.mvvmfxsample.JfxTestRunner;
import org.m946.mvvmfxsample.db.CountryVM;
import org.m946.mvvmfxsample.db.DbService;

import javafx.beans.binding.Bindings;
import javafx.scene.control.TextField;


@RunWith(JfxTestRunner.class)
public class TestSimpleFlatMapper {
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
		CountryVM result = dbService.getUsaVM();
		TextField country = new TextField();
		TextField currency = new TextField();

		Bindings.bindBidirectional(country.textProperty(), result.country());
		Bindings.bindBidirectional(currency.textProperty(), result.currency());
		
		assertEquals("USA", country.textProperty().getValue());
		assertEquals("Dollar", currency.textProperty().getValue());
		
		country.textProperty().setValue("Japan");
		currency.textProperty().setValue("Yen");
		assertEquals("Japan", result.getCountry());
		assertEquals("Yen", result.getCurrency());
	}

}
