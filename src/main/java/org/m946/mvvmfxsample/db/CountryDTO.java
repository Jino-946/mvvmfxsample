package org.m946.mvvmfxsample.db;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * DTOクラスではjavafx.beansプロパティをフィールドに定義することでViewのJavaFXコントロールとバインディングし、
 * プロパティフィールドに改めてgetter、setterを定義して、SimpleFlatMapperのJdbcMapperFactoryを使ってプロパティを
 * DBのテーブルとマッピングする。<br>
 * つまりDTOクラスを定義することよりJavaFXコントロールとDBテーブルを直接マッピングすることが可能になる。
 * 
 *<pre>{@code
 * 	public CountryDTO getCountryDTO(String countryName) {
 *		final String sql = "select country, currency from country where country = ?" ;
 *		CountryDTO result = null;
 *		PreparedStatement ps = null;
 *		ResultSet rs = null;
 *		Connection conn = inTransaction ? connection : open();
 *		try {
 *			ps = conn.prepareStatement(sql);
 *			ps.setString(1, countryName);
 *			rs = ps.executeQuery();
 *			JdbcMapper<CountryDTO> mapper = JdbcMapperFactory.newInstance().newMapper(CountryDTO.class);
 *			rs.next();
 *			result = mapper.map(rs);
 *		...
 *}</pre>
 * 
 * @author xyro
 *
 */
public class CountryDTO {
	StringProperty country = new SimpleStringProperty();
	StringProperty currency = new SimpleStringProperty();

	
	
	public CountryDTO(String country, String currency) {
		this.country.set(country);
		this.currency.set(currency);
	}

	public CountryDTO(Country pojo) {
		this(pojo.getCountry(), pojo.getCurrency());
	}

	/* Viewコントロールとバインディング用のプロパティの定義*/
	public StringProperty country() {
		return country;
	}
	
	public StringProperty currency() {
		return currency;
	}
	
	/* ORマッピング用のgetter,setterの定義 */
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
