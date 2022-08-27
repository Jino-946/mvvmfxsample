package org.m946.mvvmfxsample.db;

public class Country {
	private String country;
	private String currency;
	

	public Country(String country, String currency) {
		this.country = country;
		this.currency = currency;
	}

	public void copyTo(CountryDTO dto) {
		dto.setCountry(country);
		dto.setCurrency(currency);
	}
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
