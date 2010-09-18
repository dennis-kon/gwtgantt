package com.bradrydzewski.gwtgantt.demo.client;

import java.util.List;

import com.bradrydzewski.gwtgantt.GanttChart;
import com.bradrydzewski.gwtgantt.TaskGrid;
import com.bradrydzewski.gwtgantt.event.ItemClickEvent;
import com.bradrydzewski.gwtgantt.event.ItemClickHandler;
import com.bradrydzewski.gwtgantt.event.ItemCollapseEvent;
import com.bradrydzewski.gwtgantt.event.ItemCollapseHandler;
import com.bradrydzewski.gwtgantt.event.ItemDoubleClickEvent;
import com.bradrydzewski.gwtgantt.event.ItemDoubleClickHandler;
import com.bradrydzewski.gwtgantt.event.ItemExpandEvent;
import com.bradrydzewski.gwtgantt.event.ItemExpandHandler;
import com.bradrydzewski.gwtgantt.model.Task;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DemoPanel extends Composite {

	private static DemoPanelUiBinder uiBinder = GWT
			.create(DemoPanelUiBinder.class);

	interface DemoPanelUiBinder extends UiBinder<Widget, DemoPanel> {
	}
	
	@UiField GanttChart gantt;
	@UiField TaskGrid grid;

	public DemoPanel() {

		//initialize the ui binder
		initWidget(uiBinder.createAndBindUi(this));
		
		//add tasks to the gantt and grid
		List<Task> taskList = DataGenerator.generateTasks();
		gantt.addItems(taskList);
		grid.addItems(taskList);
		
		//bind events
		bind();
	}
	
	public void bind() {
		gantt.addItemClickHandler(new ItemClickHandler<Task>(){
			@Override
			public void onItemClick(ItemClickEvent<Task> event) {
				GWT.log("Task clicked in Gantt: "+event.getItem().getName());
				gantt.setSelectedItem(event.getItem());
				grid.setSelectedItem(event.getItem());
			}
		});
		gantt.addItemDoubleClickHandler(new ItemDoubleClickHandler<Task>(){
			@Override
			public void onItemDoubleClick(ItemDoubleClickEvent<Task> event) {
				GWT.log("Task double-clicked in Gantt: "+event.getItem().getName());
				Window.alert("You double-clicked " + event.getItem().getName());
			}
		});
		grid.addItemClickHandler(new ItemClickHandler<Task>(){
			@Override
			public void onItemClick(ItemClickEvent<Task> event) {
				GWT.log("Task clicked in Grid: "+event.getItem().getName());
				gantt.setSelectedItem(event.getItem());
				grid.setSelectedItem(event.getItem());
			}
		});
		grid.addItemDoubleClickHandler(new ItemDoubleClickHandler<Task>(){
			@Override
			public void onItemDoubleClick(ItemDoubleClickEvent<Task> event) {
				GWT.log("Task double-clicked in Grid: "+event.getItem().getName());
				Window.alert("You double-clicked " + event.getItem().getName());
			}
		});
		grid.addItemCollapseHandler(new ItemCollapseHandler<Task>(){
			@Override
			public void onItemCollapse(ItemCollapseEvent<Task> event) {
				GWT.log("Task collapsed in Grid: "+event.getItem().getName());
				gantt.refresh(true);
			}
		});
		grid.addItemExpandHandler(new ItemExpandHandler<Task>(){
			@Override
			public void onItemExpand(ItemExpandEvent<Task> event) {
				GWT.log("Task expended in Grid: "+event.getItem().getName());
				gantt.refresh(true);
			}
		});
	}

}
