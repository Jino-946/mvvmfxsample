package org.m946.mvvmfxsample;

import java.sql.SQLException;
import java.util.List;

import org.m946.hanakolib.db.JaybirdManager;
import org.m946.hanakolib.db.SfmDao;
import org.m946.mvvmfxsample.db.Country;
import org.m946.mvvmfxsample.db.CountryDTO;

import de.saxsys.mvvmfx.ViewModel;

public class CountryVM implements ViewModel {
	public List<Country> countryList;
	public CountryDTO countryDTO;
	
	private JaybirdManager fbManager = new JaybirdManager("localhost", "employee");
	private SfmDao<CountryDTO, String> countryDAO = null;
	
	public CountryVM() {
		countryList = getCountries();
		countryDTO = new CountryDTO(countryList.get(0));
		
	}

	
	public List<Country> getCountries(){
		String sql = 
		"""
		select country, currency 
		from COUNTRY
		order by country
		""";
		countryList =  fbManager.getResultList(sql, Country.class);
		return countryList;
	}
	
	public void updateCountry() throws SQLException {
		if (countryDAO == null)
			countryDAO = fbManager.newInstanceOfSfmDao("COUNTRY", CountryDTO.class, String.class);
		
		countryDAO.update(countryDTO);
	}
}
