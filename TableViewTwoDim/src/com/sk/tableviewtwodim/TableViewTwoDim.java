package com.sk.tableviewtwodim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import com.sk.tableviewtwodim.api.ColumnData2DEvent;
import com.sk.tableviewtwodim.api.RowData2DEvent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TableViewTwoDim<T> extends AnchorPane {

	@FXML
	private TableView<T> table;

	@FXML
	private TableColumn<T, String> rowHeaders;

	private HashMap<String, TableColumn<T, String>> columns = new HashMap<>();

	private Function<String, T> matcher;

	private List<String> columnHeader;
	private List<String> rowHeader;

	private EventHandler<RowData2DEvent<T>> clickRowDataListener;
	private EventHandler<ColumnData2DEvent<T>> clickColDataListener;

	private HashMap<T, String> rowItemMatcher = new HashMap<>();

	public TableViewTwoDim() throws IOException {
		final FXMLLoader loader = new FXMLLoader();
		loader.setRoot(this);
		loader.setController(this);
		loader.setLocation(this.getClass().getResource("TableViewTwoDim_View.fxml"));

		loader.load();
	}

	public void init(String name, List<String> columnHeader, List<String> rowHeader) {
		rowHeaders.setText(name);
		rowHeaders.setSortable(false);
		this.rowHeader = rowHeader;
		this.columnHeader = columnHeader;
		setRowHeaderValueFactory();
	}

	public void setItems() {
		for (String col : columnHeader) {
			TableColumn<T, String> column = new TableColumn<>();
			makeHeader(column, col, 0);
			column.setSortable(false);
			table.getColumns().add(column);
			columns.put(col, column);
		}
		for (String row : rowHeader) {
			T item = matcher.apply(row);
			rowItemMatcher.put(item, row);
			this.table.getItems().add(item);
		}
	}

	public void setRowItemMatcher(Function<String, T> matcher) {
		this.matcher = matcher;
	}

	public void setItemValueFactoryForColumn(String column, Function<T, String> valueSelector) {
		columns.get(column)
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<T, String> param) {
						// TODO Auto-generated method stub
						return new SimpleStringProperty(valueSelector.apply(param.getValue()));
					}
				});
	}

	private void setRowHeaderValueFactory() {
		rowHeaders
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<T, String> param) {
						return new SimpleStringProperty(rowItemMatcher.get(param.getValue()));
					}
				});
		rowHeaders.setCellFactory(new Callback<TableColumn<T, String>, TableCell<T, String>>() {

			@Override
			public TableCell<T, String> call(TableColumn<T, String> param) {
				final TableCell<T, String> cell = new TableCell<T, String>() {

					@Override
					public void updateItem(String item, boolean empty) {
						int index = this.indexProperty().get();
						if (index >= 0 && !empty) {
							VBox vBox = new VBox(new Label(item));
							vBox.setAlignment(Pos.CENTER);
							vBox.getProperties().put("index", index);
							this.setGraphic(vBox);
							this.getGraphic().setOnMouseClicked(new EventHandler<Event>() {

								@Override
								public void handle(Event event) {
									List<String> values = new ArrayList<String>();
									for (TableColumn<T, String> cols : columns.values()) {
										values.add(cols.getCellData(index));
									}
									clickRowDataListener
											.handle(new RowData2DEvent<>(rowHeaders.getCellData(index), values));
								}
							});
						}
					}
				};

				return cell;
			};
		});
	}

	public void setClickListenerForRow(EventHandler<RowData2DEvent<T>> clickRowDataListener) {
		this.clickRowDataListener = clickRowDataListener;
	}

	public void setClickListenerForColumnsHeader(EventHandler<ColumnData2DEvent<T>> clickColDataListener) {
		this.clickColDataListener = clickColDataListener;
	}

	private void makeHeader(TableColumn<T, String> target, String name, int index) {
		VBox vBox = new VBox(new Label(name));
		vBox.setAlignment(Pos.CENTER);
		vBox.getProperties().put("index", index);
		target.setGraphic(vBox);
		target.getGraphic().setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				List<String> values = new ArrayList<String>();
				for (int i = 0; i < table.getItems().size(); i++) {
					values.add(target.getCellData(i));
				}
				clickColDataListener.handle(new ColumnData2DEvent<>(name, values));
			}

		});
		target.setText("");
	}

}
