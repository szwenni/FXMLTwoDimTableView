package com.sk.tableviewtwodim;

import java.util.List;

import javafx.event.Event;
import javafx.event.EventType;

public class RowData2DEvent<T> extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8940103662388869262L;
	private String rowName;
	private List<String> data;

	public RowData2DEvent(String rowName, List<String> data) {
		super(EventType.ROOT);
		this.rowName = rowName;
		this.data = data;
	}
}
