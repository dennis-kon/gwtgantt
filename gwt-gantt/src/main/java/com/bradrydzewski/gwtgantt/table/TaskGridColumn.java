package com.bradrydzewski.gwtgantt.table;

import com.bradrydzewski.gwtgantt.model.Task;
import com.google.gwt.user.cellview.client.Column;

public class TaskGridColumn {
	
	private String name;
	private String style;
	private int width = 100;
	private final Column<Task, ?> column;
	
	public TaskGridColumn(Column<Task, ?> column) {
		this(column,"",100);
	}

	public TaskGridColumn(Column<Task, ?> column, String name, int width) {
		this(column,name,width,null);
	}
	
	public TaskGridColumn(Column<Task, ?> column, String name, int width, String style) {
		this.column = column;
		this.name = name;
		this.width = width;
		this.style = style;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Column<Task, ?> getColumn() {
		return column;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
}
