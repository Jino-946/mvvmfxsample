package org.m946.mvvmfxsample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.m946.hanakolib.jfx.AutoNumberingCellFactory;
import org.m946.mvvmfxsample.db.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

public class CountryView implements FxmlView<CountryVM>, Initializable {
	private final static Logger log = LoggerFactory.getLogger(CountryView.class);

	@FXML private TableView<Country> tableView;
	// 自動採番する行番号
	@FXML private TableColumn<Country, Integer> seqCol;
	@FXML private TableColumn<Country, String> countryCol;
	@FXML private TableColumn<Country, String> currencyCol;
	private ObservableList<Country> tableRows;

	@FXML private TextField countryFld;
	@FXML private TextField currencyFld;
	
	@InjectViewModel private CountryVM vm;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableRows = FXCollections.observableArrayList();
		tableView.itemsProperty().setValue(tableRows);
		tableView.setItems(tableRows);
		tableRows.addAll(vm.countryList);
		
		seqCol.setCellFactory(new AutoNumberingCellFactory<Country>());
		countryCol.setCellValueFactory(new PropertyValueFactory<Country, String>("country"));
		currencyCol.setCellValueFactory(new PropertyValueFactory<Country, String>("currency"));
		
		
		countryFld.textProperty().bindBidirectional(vm.countryDTO.country());
		countryFld.setEditable(false);
		currencyFld.textProperty().bindBidirectional(vm.countryDTO.currency());
		tableView.setOnMouseClicked(e -> {
			boolean doubleClicked = e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2;
			if (doubleClicked) {
				tableView.getSelectionModel().getSelectedItem().copyTo(vm.countryDTO);
				log.debug(vm.countryDTO.getCountry() + ":" + vm.countryDTO.getCurrency());
			}
		});
		
	}

	public void updateCountry() {
		try {
			vm.updateCountry();
			tableRows.clear();
			tableRows.addAll(vm.getCountries());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
