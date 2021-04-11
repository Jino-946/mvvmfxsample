package org.m946.mvvmfxsample.db;


import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CountryVM implements ViewModel {
	StringProperty country = new SimpleStringProperty();
	StringProperty currency = new SimpleStringProperty();

	
	
	
	public CountryVM(String country, String currency) {
		this.country.set(country);
		this.currency.set(currency);
	}
	
	public StringProperty country() {
		return country;
	}
	
	public StringProperty currency() {
		return currency;
	}
	
	public void setCountry(String country) {
		this.country.set(country);
	}
	public String getCountry() {
		return this.country.get();
	}

	public void setCurrency(String currency) {
		this.currency.set(currency);
	}
	public String getCurrency() {
		return this.currency.get();
	}
	
}
