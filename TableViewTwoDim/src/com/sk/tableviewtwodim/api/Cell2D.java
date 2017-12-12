package com.sk.tableviewtwodim.api;

public class Cell2D<T> {

	private T item;
	private String row;
	private String column;

	public Cell2D(T item, String row, String column) {
		this.item = item;
		this.row = row;
		this.column = column;
	}

	public T getItem() {
		return item;
	}

	public String getRow() {
		return row;
	}

	public String getColumn() {
		return column;
	}

}
