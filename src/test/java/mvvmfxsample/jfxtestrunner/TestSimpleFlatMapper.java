package mvvmfxsample.jfxtestrunner;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.m946.mvvmfxsample.JfxTestRunner;
import org.m946.mvvmfxsample.db.CountryDTO;
import org.m946.mvvmfxsample.db.DbService;
import org.simpleflatmapper.jdbc.Crud;

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
		CountryDTO result = dbService.getCountryDTO("USA");
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
	
	@Test
	public void testCreation() {
		dbService.beginTransation();
		
		CountryDTO result = dbService.getCountryDTO("USA");
		TextField country = new TextField();
		TextField currency = new TextField();

		Bindings.bindBidirectional(country.textProperty(), result.country());
		Bindings.bindBidirectional(currency.textProperty(), result.currency());
		
		
		country.textProperty().setValue("China");
		currency.textProperty().setValue("Yuan");
		assertEquals("China", result.getCountry());
		assertEquals("Yuan", result.getCurrency());
	
		Crud<CountryDTO, String> crud = dbService.countryVMCrud();
		try {
			crud.create(dbService.getConnection(), result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		CountryDTO c = dbService.getCountryDTO("China");
		assertEquals("China", c.getCountry());
		assertEquals("Yuan", c.getCurrency());
		dbService.rollback();
				
	}
}
