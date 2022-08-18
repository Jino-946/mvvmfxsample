package org.m946.hanakolib.jfx;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;


/**
 * TableViewに行番号を自動採番する列を追加する。
 * 
 * <pre>
 * fxmlファイルでTableViewを設計する。 
 * {@code
 *  <TableView fx:id="tableView" BorderPane.alignment="CENTER">
 *      <columns>
 *          <TableColumn fx:id="seq"  sortable="false" style="-fx-alignment: CENTER_RIGHT;" text="No." />
 *          <TableColumn fx:id="col1"  sortable="false" style="-fx-alignment: CENTER_RIGHT;" text="列1" />
 *          <TableColumn fx:id="col2"  text="列2" />
 *  ....
 * }
 * </pre>
 * 
 * <pre>
 * ViewクラスでTableColumnの参照をインジェクトする。
 * {@code
 *  @FXML
 *  private TableView &lt;SomeDto&gt; tableView;
 *  {@literal}@FXML
 *  private TableColumn&lt;SomeDto, Integer&gt; seq;
 *  {@literal}@FXML 
 *  private TableColumn&lt;SomeDto, Integer&gt;integerCol;
 *  {@literal}@FXML
 *  private TableColumn&lt;SomeDto, java.sql.Date&gt; dateCol;
 *  ...
 * }
 * </pre>
 * 
 * <pre>
 * ViewクラスのinitializeメソッドでTableColumnにCellFactoryをセットしデータをバインドする。
 * {@code
 * seq.setCellFactory(new AutoNumberingCellFactory<SomeDto>());
 * integerCol.setCellValueFactory(new PropertyValueFactory<SomeDto, Integer>("intField"));
 * dateCol.setCellValueFactory(new PropertyValueFactory<SomeDto, java.sql.Date>("dateField"));
 * }
 * </pre>
 * @author xyro
 *
 * @param <T>
 */
public class AutoNumberingCellFactory<T> implements Callback<TableColumn<T,Integer>, TableCell<T, Integer>> {
	public AutoNumberingCellFactory(){
	}

	@Override
	public TableCell<T, Integer> call(TableColumn<T, Integer> param) {
		return new TableCell<T, Integer>(){
			@Override
			protected void updateItem(Integer item, boolean empty){
				super.updateItem(item, empty);
				
				if (!empty){
					setText(this.getTableRow().getIndex() + 1 + "");
				}else {
					setText("");
				}
			}
		};
	}
}
