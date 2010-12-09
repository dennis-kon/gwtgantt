package com.bradrydzewski.gwtgantt.demo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwtgantt.gantt.GanttChart;
import com.bradrydzewski.gwtgantt.gantt.GanttChartPresenterQuarterImpl;
import com.bradrydzewski.gwtgantt.gantt.GanttChartView;
import com.bradrydzewski.gwtgantt.gantt.ProvidesTask;
import com.bradrydzewski.gwtgantt.gantt.ProvidesTaskImpl;
import com.bradrydzewski.gwtgantt.gantt.TaskDisplayPresenter;
import com.bradrydzewski.gwtgantt.gantt.TaskDisplayView;
import com.bradrydzewski.gwtgantt.model.Task;
import com.bradrydzewski.gwtgantt.table.TaskGridColumn;
import com.bradrydzewski.gwtgantt.table.override.CellTextImpl;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class DemoPanel extends Composite {

	private SelectionModel<Task> selectionModel = new SingleSelectionModel<Task>();
	private Button refreshButton = new Button("Refresh");
	private Button zoomOutButton = new Button("Zoom Out");
	private Button zoomInButton = new Button("Zoom In");
	private FlowPanel panel = new FlowPanel();
	private ListDataProvider<Task> taskDataProvider = new ListDataProvider<Task>();
	private GanttChart<Task> taskTable = null;
//	private GridView<Task> taskTable = new GridView<Task>();

	public DemoPanel() {
		ProvidesTask<Task> provider = new ProvidesTaskImpl();
		TaskDisplayView<Task> view = new GanttChartView<Task>();
		TaskDisplayPresenter<Task> presenter = new GanttChartPresenterQuarterImpl<Task>();
		taskTable = new GanttChart<Task>(provider);

		initWidget(panel);
		panel.add(taskTable);
		panel.add(refreshButton);
		panel.add(zoomOutButton);
		panel.add(zoomInButton);
		
//		List<TaskGridColumn> columns = getColumns();
//		taskTable.addColumns(columns);

		refreshButton.getElement().getStyle().setPosition(Position.ABSOLUTE);
		refreshButton.getElement().getStyle().setTop(0, Unit.PX);
		refreshButton.getElement().getStyle().setRight(0, Unit.PX);
		refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				taskDataProvider.refresh();
			}
		});
		
		zoomOutButton.getElement().getStyle().setPosition(Position.ABSOLUTE);
		zoomOutButton.getElement().getStyle().setTop(0, Unit.PX);
		zoomOutButton.getElement().getStyle().setRight(57, Unit.PX);
		zoomOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				taskTable.zoomOut();
			}
		});
		
		zoomInButton.getElement().getStyle().setPosition(Position.ABSOLUTE);
		zoomInButton.getElement().getStyle().setTop(0, Unit.PX);
		zoomInButton.getElement().getStyle().setRight(150, Unit.PX);
		zoomInButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				taskTable.zoomIn();
			}
		});

		taskTable.setSelectionModel(selectionModel);
		taskDataProvider.addDataDisplay(taskTable);
		List<? extends Task> taskList = DataGenerator.generateTasks();
		taskDataProvider.setList((List<Task>) taskList);
//		taskDataProvider.refresh();
	}

	public List<TaskGridColumn> getColumns() {

		String shortFormatPattern = "MM/dd/yy";
		String dayFormatPattern = "EEE";
		DateTimeFormat format = DateTimeFormat.getFormat(
				dayFormatPattern + " " + shortFormatPattern);
		
		List<TaskGridColumn> colums = new ArrayList<TaskGridColumn>();

		// ORDER COLUMN
		Column<Task, String> orderColumn = new Column<Task, String>(
				new TextCell()) {

			@Override
			public String getValue(Task object) {
				return String.valueOf(object.getOrder());
			}
		};

		// NAME COLUMN
//		Column<Task, Task> nameColumn = new Column<Task, Task>(
//				new TaskGridNameCell(){
//					@Override
//					public void onExpandCollapse(Task task) {
//						taskDataProvider.refresh();
//					}
//				}) {
//
//			@Override
//			public Task getValue(Task object) {
//				return object;
//			}
//		};
//		nameColumn.setFieldUpdater(new FieldUpdater<Task, Task>() {
//			public void update(int index, Task object, Task value) {
//				// Called when the user changes the value.
//				// object.setName(value);
//			}
//		});

		// DURATION COLUMN
		Column<Task, String> durationColumn = new Column<Task, String>(
				new CellTextImpl(CellTextImpl.ALIGN_RIGHT){
					  public String validate(String newValue, String oldValue) {
						  return oldValue;
					  }
				}) {

			@Override
			public String getValue(Task object) {
				return String.valueOf(object.getDuration());
			}
		};
		durationColumn.setFieldUpdater(new FieldUpdater<Task, String>() {
			public void update(int index, Task object, String value) {
				// Called when the user changes the value.
				// object.setper
				GWT.log("setting duration");
				object.setDuration(0);
				Timer t = new Timer() {

					@Override
					public void run() {
						GWT.log("running timer");
						taskDataProvider.refresh();
					}
					
				};
				GWT.log("scheduling task");
				t.schedule(500);
			}
		});

		// START COLUMN
		Column<Task, Date> startColumn = new Column<Task, Date>(new DateCell(
				format)) {
			@Override
			public Date getValue(Task object) {
				return object.getStart();
			}
		};
		startColumn.setFieldUpdater(new FieldUpdater<Task, Date>() {
			public void update(int index, Task object, Date value) {
				object.setStart(value);
			}
		});

		// FINISH COLUMN
		Column<Task, Date> finishColumn = new Column<Task, Date>(new DateCell(
				format)) {
			@Override
			public Date getValue(Task object) {
				return object.getFinish();
			}
		};
		finishColumn.setFieldUpdater(new FieldUpdater<Task, Date>() {
			public void update(int index, Task object, Date value) {
				object.setFinish(value);
			}
		});

		// PREDECESSOR COLUMN
		Column<Task, String> predecessorColumn = new Column<Task, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Task object) {
				return "";
			}
		};
		predecessorColumn.setFieldUpdater(new FieldUpdater<Task, String>() {
			public void update(int index, Task object, String value) {

			}
		});

		// RESOURCE COLUMN
		Column<Task, String> resourceColumn = new Column<Task, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Task object) {
				return "";
			}
		};
		resourceColumn.setFieldUpdater(new FieldUpdater<Task, String>() {
			public void update(int index, Task object, String value) {

			}
		});

		colums.add(new TaskGridColumn<Task>(orderColumn,"&nbsp;", 50));
//		colums.add(new TaskGridColumn(nameColumn,"Task Name", 350));
		colums.add(new TaskGridColumn<Task>(durationColumn,"Duration", 80));
		colums.add(new TaskGridColumn<Task>(startColumn,"Start", 110));
		colums.add(new TaskGridColumn<Task>(finishColumn,"Finish", 110));
		colums.add(new TaskGridColumn<Task>(predecessorColumn,"Predecessors", 100));
		colums.add(new TaskGridColumn<Task>(resourceColumn,"Resources", 100));
		return colums;
	}
}
