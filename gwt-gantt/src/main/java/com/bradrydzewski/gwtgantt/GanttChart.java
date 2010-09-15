/*
 * This file is part of gwt-gantt
 * Copyright (C) 2010  Scottsdale Software LLC
 *
 * gwt-gantt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/
 */
package com.bradrydzewski.gwtgantt;

import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwtgantt.event.HasItemClickHandlers;
import com.bradrydzewski.gwtgantt.event.HasItemCollapseHandlers;
import com.bradrydzewski.gwtgantt.event.HasItemDoubleClickHandlers;
import com.bradrydzewski.gwtgantt.event.HasItemExpandHandlers;
import com.bradrydzewski.gwtgantt.event.HasMouseEnterHandlers;
import com.bradrydzewski.gwtgantt.event.HasMouseExitHandlers;
import com.bradrydzewski.gwtgantt.event.HasScrollHandlers;
import com.bradrydzewski.gwtgantt.event.ItemClickEvent;
import com.bradrydzewski.gwtgantt.event.ItemClickHandler;
import com.bradrydzewski.gwtgantt.event.ItemCollapseEvent;
import com.bradrydzewski.gwtgantt.event.ItemCollapseHandler;
import com.bradrydzewski.gwtgantt.event.ItemDoubleClickEvent;
import com.bradrydzewski.gwtgantt.event.ItemDoubleClickHandler;
import com.bradrydzewski.gwtgantt.event.ItemExpandEvent;
import com.bradrydzewski.gwtgantt.event.ItemExpandHandler;
import com.bradrydzewski.gwtgantt.event.MouseEnterEvent;
import com.bradrydzewski.gwtgantt.event.MouseEnterHandler;
import com.bradrydzewski.gwtgantt.event.MouseExitEvent;
import com.bradrydzewski.gwtgantt.event.MouseExitHandler;
import com.bradrydzewski.gwtgantt.event.ScrollEvent;
import com.bradrydzewski.gwtgantt.event.ScrollHandler;
import com.bradrydzewski.gwtgantt.resources.GanttResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 *
 * @author Brad Rydzewski
 */
public class GanttChart extends Composite implements Project, HasLayout, HasZoom,
	HasItemClickHandlers<Task>, HasItemDoubleClickHandlers<Task>,
	HasMouseEnterHandlers<Task>, HasMouseExitHandlers<Task>, HasScrollHandlers,
	HasItemCollapseHandlers<Task>, HasItemExpandHandlers<Task> {

    private FlowPanel root = new FlowPanel();
    private Date start;
    private Date finish;
    private ZoomLevel zoom;
    private TaskManager tasks = GWT.create(TaskManager.class);
    private GanttView view = null;//GWT.create(TaskGridView.class);//GWT.create(GanttWeekView.class);
    private boolean suspended = false;
    private boolean dirty = false;

    public GanttChart() {
        this(null, null);
    }

    public GanttChart(Date start, Date finish) {
        this((GanttView) GWT.create(GanttWeekView.class), start, finish);
    }
    
    public GanttChart(GanttView view) {
    	this(view, null, null);
    }

    public GanttChart(GanttView view, Date start, Date finish) {

        //initialize the root widget
        initWidget(root);

        //make sure we aren't given a null view
        assert(view!=null);

        this.view = view;
        this.start = start;
        this.finish = finish;

        //load style sheet
        GanttResources.INSTANCE.taskStyle().ensureInjected();
        
        //load the view
        view.attach(root, this);
    }


    @Override
    public void onLoad() {
        refresh(true);
    }

    public void suspendLayout() {
        suspended = true;
    }

    public void resumeLayout() {
        suspended = false;
        refresh();
    }

    public boolean isDirty() {
        return dirty;
    }

    public void refresh() {
        refresh(false);
    }

    public void refresh(boolean force) {

    	//we automatically refresh once the widget is attached,
    	//  so if the widget is not yet attached, exit
    	if(!isAttached())
    		return;
    	
        if (suspended && force != true) {
            return;
        }

        if (dirty == false && force != true) {
            return;
        }
        
        GWT.log("Refershing the GanttChart's view: "+view.getClass().getName());
        view.sortTasks(tasks.getTasks());
        view.refresh();

        dirty = false;
    }

    public void addTask(Task task) {
        tasks.addTask(task);
        dirty = true;
        refresh();
    }

    public void addTasks(List<Task> taskList) {
        for (Task task : taskList) {
            tasks.addTask(task);
        }

        dirty = true;
        refresh();
    }

    public void removeTask(Task task) {
        tasks.removeTask(task);
        dirty = true;
        refresh();
    }

    public void removeAllTasks() {
        tasks.clearTasks();
        dirty = true;
        refresh();
    }

    public void setSelectedTask(Task task) {
    	if(!tasks.isTheSelectedTask(task)) {
    		if(tasks.getSelectedTask()!=null) {
    			view.doTaskDeselected(tasks.getSelectedTask());
    		}
    		tasks.setSelectedTask(task);
    		view.doTaskSelected(task);
    	}
    }
    
    public boolean isSelected(Task task) {
    	return tasks.isTheSelectedTask(task);
    }

    public Task getSelectedTask() {
        return tasks.getSelectedTask();
    }

    public int getTaskCount() {
        return tasks.getTasks().size();
    }

    public Task getTask(int index) {
        return tasks.getTasks().get(index);
    }
    
    public int getTaskIndexOf(Task task) {
    	return tasks.getTasks().indexOf(task);
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
        dirty = true;
        refresh();
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
        dirty = true;
        refresh();
    }
    
    public int getHorizontalScrollPosition() {
    	return view.getHorizontalScrollPosition();
    }
    
    public int getVerticalScrollPosition() {
    	return view.getVerticalScrollPosition();
    }

    public ZoomLevel getZoom() {
        return zoom;
    }

    public void setZoom(ZoomLevel zoom) {
        this.zoom = zoom;
        dirty = true;
        refresh();
    }


	@Override
	public void fireTaskClickEvent(Task task) {
		ItemClickEvent.fire(this,task);
	}

	@Override
	public void fireTaskDoubleClickEvent(Task task) {
		ItemDoubleClickEvent.fire(this,task);
	}

	@Override
	public void fireTaskEnterEvent(Task task) {
		view.doTaskEnter(task);
		MouseEnterEvent.fire(this, task);
	}

	@Override
	public void fireTaskExitEvent(Task task) {
		view.doTaskExit(task);
		MouseExitEvent.fire(this, task);
	}

	@Override
	public void fireTaskExpandEvent(Task task) {
		view.doTaskExit(task);
		ItemExpandEvent.fire(this, task);
	}

	@Override
	public void fireTaskCollapseEvent(Task task) {
		view.doTaskExit(task);
		ItemExpandEvent.fire(this, task);
	}

	@Override
	public void fireScrollEvent(int x, int y) {
		this.fireEvent(new ScrollEvent(x, y));
	}

	@Override
	public HandlerRegistration addItemClickHandler(ItemClickHandler<Task> handler) {
		return addHandler(handler, ItemClickEvent.getType());
	}

	@Override
	public HandlerRegistration addItemDoubleClickHandler(
			ItemDoubleClickHandler<Task> handler) {
		return addHandler(handler, ItemDoubleClickEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseExitHandler(
			MouseExitHandler<Task> handler) {
		return addHandler(handler, MouseExitEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseEnterHandler(
			MouseEnterHandler<Task> handler) {
		return addHandler(handler, MouseEnterEvent.getType());
	}

	@Override
	public HandlerRegistration addScrollHandler(ScrollHandler handler) {
		return addHandler(handler, ScrollEvent.getType());
	}

	@Override
	public HandlerRegistration addItemExpandHandler(
			ItemExpandHandler<Task> handler) {
		return addHandler(handler, ItemExpandEvent.getType());
	}

	@Override
	public HandlerRegistration addItemCollapseHandler(
			ItemCollapseHandler<Task> handler) {
		return addHandler(handler, ItemCollapseEvent.getType());
	}

}
