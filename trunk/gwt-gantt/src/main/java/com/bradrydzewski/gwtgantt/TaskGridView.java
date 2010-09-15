package com.bradrydzewski.gwtgantt;

import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class TaskGridView implements GanttView {

	public interface Display {
		void bind(GanttView view);
		void renderTask(Task task, int row, int rowOffset);
		void onBeforeRendering();
		void onAfterRendering();
		void doTaskSelected(Task task);
		void doTaskDeselected(Task task);
		void doScroll(int x, int y);
		Widget asWidget();
	}

	private Display display;
	private Project project;
	
	public TaskGridView() {
		this((Display) GWT.create(TaskGridDisplay.class));
	}
	
	public TaskGridView(Display display) {
		this.display = display;
		this.display.bind(this);
	}
	
	
	@Override
	public void attach(HasWidgets container, Project project) {
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
		
		for (int i = 0; i < project.getTaskCount(); i++) {
			//get the task
			Task task = project.getTask(i);
			
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
		if(project.getSelectedTask()!=null)
			display.doTaskSelected(project.getSelectedTask());
	}

	@Override
	public void sortTasks(List<Task> taskList) {
		Collections.sort(taskList,TaskManager.TASK_ORDER_COMPARATOR);
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
	public void onTaskClicked(Task task) {
		project.fireTaskClickEvent(task);
	}

	@Override
	public void onTaskDoubleClicked(Task task) {
		project.fireTaskDoubleClickEvent(task);
	}

	@Override
	public void onTaskMouseOver(Task task) {
		project.fireTaskEnterEvent(task);
	}

	@Override
	public void onTaskMouseOut(Task task) {
		project.fireTaskExitEvent(task);
	}
	
	public void doTaskSelected(Task task) {
		display.doTaskSelected(task);
	}
	
	public void doTaskDeselected(Task task) {
		display.doTaskDeselected(task);
	}
	
	public void doTaskEnter(Task task) {
		//not implemented
	}
	
	public void doTaskExit(Task task) {
		//not implemented
	}

	@Override
	public void onScroll(int x, int y) {
		project.fireScrollEvent(x, y);
	}

	@Override
	public void onTaskExpand(Task task) {
		refresh();
		project.fireTaskCollapseEvent(task);
	}

	@Override
	public void onTaskCollapse(Task task) {
		refresh();
		project.fireTaskExpandEvent(task);
	}
}
