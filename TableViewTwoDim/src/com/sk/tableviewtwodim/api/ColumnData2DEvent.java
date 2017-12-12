package com.sk.tableviewtwodim.api;

import java.util.List;

import javafx.event.Event;
import javafx.event.EventType;

public class ColumnData2DEvent<T> extends Event {

	private static final long serialVersionUID = 1128480883871052778L;

	private String colName;
	private List<String> data;

	public ColumnData2DEvent(String colName, List<String> data) {
		super(EventType.ROOT);
		this.colName = colName;
		this.data = data;
	}

	public List<String> getColData() {
		return data;
	}

	public String getName() {
		return colName;
	}
}