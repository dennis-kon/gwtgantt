package com.bradrydzewski.gwtgantt.presenter;

import java.util.Collections;
import java.util.List;

import com.bradrydzewski.gwtgantt.TaskPresenter;
import com.bradrydzewski.gwtgantt.TaskDisplay;
import com.bradrydzewski.gwtgantt.ItemDataManager;
import com.bradrydzewski.gwtgantt.model.Predecessor;
import com.bradrydzewski.gwtgantt.model.Task;
import com.bradrydzewski.gwtgantt.view.TaskGridView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;

public class TaskGridPresenter implements TaskPresenter {

	public interface Display {
		void bind(TaskPresenter view);
		void renderTask(Task task, int row, int rowOffset);
                void renderPredecessor(String predecessors, int row, int rowOffset);
		void onBeforeRendering();
		void onAfterRendering();
		void doTaskSelected(Task task);
		void doTaskDeselected(Task task);
		void doScroll(int x, int y);
		Widget asWidget();
	}

	private Display display;
	private TaskDisplay project;
        private HashMap<Integer,Task> taskIndex = new HashMap<Integer,Task>();
	
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
                taskIndex.clear();

                for (int i = 0; i < project.getItemCount(); i++) {
                    Task task = project.getItem(i);
                    taskIndex.put(task.getUID(),task);
                }
		
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
                                String predecessors = getPredecessorString(task);
				display.renderTask(task, i, count);
				display.renderPredecessor(predecessors, i, count);
				count++;
			}
		}
		
		//color the selected task, if it is selected
		if(project.getSelectedItem()!=null)
			display.doTaskSelected(project.getSelectedItem());

                //release task index from memory
                taskIndex.clear();
	}

        String getPredecessorString(Task task) {
            StringBuilder predecessors = new StringBuilder();

            if(task.getPredecessors()!=null) {
                for(int i=0;i<task.getPredecessors().size();i++) {
                    Predecessor p = task.getPredecessors().get(i);
                    Task pTask = taskIndex.get(p.getUID());
                    predecessors.append(pTask.getOrder());
                    if(i<task.getPredecessors().size()-1) {
                        predecessors.append(", ");
                    }
                }
            }

            return predecessors.toString();
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
