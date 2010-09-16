package com.bradrydzewski.gwtgantt.presenter;

import java.util.Collections;
import java.util.List;

import com.bradrydzewski.gwtgantt.TaskPresenter;
import com.bradrydzewski.gwtgantt.TaskDisplay;
import com.bradrydzewski.gwtgantt.ItemDataManager;
import com.bradrydzewski.gwtgantt.model.Task;
import com.bradrydzewski.gwtgantt.view.TaskGridView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class TaskGridPresenter implements TaskPresenter {

	public interface Display {
		void bind(TaskPresenter view);
		void renderTask(Task task, int row, int rowOffset);
		void onBeforeRendering();
		void onAfterRendering();
		void doTaskSelected(Task task);
		void doTaskDeselected(Task task);
		void doScroll(int x, int y);
		Widget asWidget();
	}

	private Display display;
	private TaskDisplay project;
	
	public TaskGridPresenter() {
		this((Display) GWT.create(TaskGridView.class));
	}
	
	public TaskGridPresenter(Display display) {
		this.display = display;
		this.display.bind(this);
	}
	
	
	@Override
	public void attach(HasWidgets container, TaskDisplay project) {
		this.project = project;
		container.clear();
		container.add(display.asWidget());
	}

	@Override
	public void refresh() {
		display.onBeforeRendering();
		this.renderTasks();
		display.onAfterRendering();
	}
	

	public void renderTasks() {
		int count = 0;
		boolean collapse = false;
		int collapseLevel = -1;
		
		//some debugging...
		GWT.log("renderTasks method invoked");
		
		for (int i = 0; i < project.getItemCount(); i++) {
			//get the task
			Task task = project.getItem(i);
			
			//check to make sure this is a child item of a collapsed task
			collapse = collapse && task.getLevel()>=collapseLevel;
			
			//if not collapsed, we render the task
			if(!collapse) {
				if(task.isSummary()) {
					collapse = task.isCollapsed();
					collapseLevel = task.getLevel()+1;
				}
				display.renderTask(task, i, count);
				count++;
			}
		}
		
		//color the selected task, if it is selected
		if(project.getSelectedItem()!=null)
			display.doTaskSelected(project.getSelectedItem());
	}

	@Override
	public void sortItems(List<Task> taskList) {
		Collections.sort(taskList,ItemDataManager.TASK_ORDER_COMPARATOR);
	}

	@Override
	public int getHorizontalScrollPosition() {
		return 0;
	}

	@Override
	public int getVerticalScrollPosition() {
		return 0;
	}

	@Override
	public void onItemClicked(Task task) {
		project.fireItemClickEvent(task);
	}

	@Override
	public void onItemDoubleClicked(Task task) {
		project.fireItemDoubleClickEvent(task);
	}

	@Override
	public void onItemMouseOver(Task task) {
		project.fireItemEnterEvent(task);
	}

	@Override
	public void onItemMouseOut(Task task) {
		project.fireItemExitEvent(task);
	}
	
	public void doItemSelected(Task task) {
		display.doTaskSelected(task);
	}
	
	public void doItemDeselected(Task task) {
		display.doTaskDeselected(task);
	}
	
	public void doItemEnter(Task task) {
		//not implemented
	}
	
	public void doItemExit(Task task) {
		//not implemented
	}

	@Override
	public void onScroll(int x, int y) {
		project.fireScrollEvent(x, y);
	}

	@Override
	public void onItemExpand(Task task) {
		refresh();
		project.fireItemCollapseEvent(task);
	}

	@Override
	public void onItemCollapse(Task task) {
		refresh();
		project.fireItemExpandEvent(task);
	}
}
