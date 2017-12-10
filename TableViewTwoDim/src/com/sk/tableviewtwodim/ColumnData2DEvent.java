package com.sk.tableviewtwodim;

import java.util.List;

import javafx.event.Event;
import javafx.event.EventType;

public class ColumnData2DEvent<T> extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1128480883871052778L;
	/**
	 * 
	 */

	private String rowName;
	private List<String> data;

	public ColumnData2DEvent(String colName, List<String> data) {
		super(EventType.ROOT);
		this.rowName = rowName;
		this.data = data;
	}
}