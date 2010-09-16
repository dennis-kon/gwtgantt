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
package com.bradrydzewski.gwtgantt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A base implementation of a {@link ITask}.
 *
 * @author Brad Rydzewski
 *
 */
public class TaskImpl implements Task {

	public static final String STYLE_DEFAULT = "blue";
	public static final String STYLE_BLUE = "blue";
	public static final String STYLE_RED = "red";
	public static final String STYLE_GREEN = "green";
	
	
    private int UID;
    private String name;
    private String notes;
    private int order;
    private int level;
    private Date start;
    private Date finish;
    private int percentComplete;
    private boolean milestone;
    private boolean summary;
    private boolean collapsed;
    private String style = STYLE_DEFAULT;
    private List<Predecessor> predecessors;

    public TaskImpl() {
        predecessors = new ArrayList<Predecessor>();
    }

    @Override
    public int getUID() {
        return UID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public Date getStart() {
        return start;
    }

    @Override
    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public Date getFinish() {
        return finish;
    }

    @Override
    public void setFinish(Date finish) {
        this.finish = finish;
    }

    @Override
    public boolean isMilestone() {
        return milestone;
    }

	@Override
	public boolean isSummary() {
		return summary;
	}

	@Override
	public boolean isCollapsed() {
		return collapsed;
	}

    @Override
    public List<Predecessor> getPredecessors() {
        return predecessors;
    }
    
    public boolean addPredecessor(int UID) {
    	return addPredecessor(UID, PredecessorType.FS);
    }
    
    public boolean addPredecessor(int UID, PredecessorType type) {
    	return predecessors.add(new Predecessor(UID, type));
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public void setMilestone(boolean milestone) {
        this.milestone = milestone;
    }

	public void setSummary(boolean summary) {
		this.summary = summary;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
	
    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPredecessors(List<Predecessor> predecessors) {
        this.predecessors = predecessors;
    }

    public void setStyle(String style) {
    	this.style = style;
    }
    
	@Override
	public String getStyle() {
		return style;
	}

    public Object clone() {
        TaskImpl task = new TaskImpl();
        task.UID = this.UID;
        task.finish = this.finish;
        task.level = this.level;
        task.milestone = this.milestone;
        task.summary = task.summary;
        task.name = this.name;
        task.notes = this.notes;
        task.order = this.order;
        task.predecessors = this.predecessors;
        task.start = this.start;
        task.style = this.style;
        return task;
    }

    @Override
    public int compareTo(Task o) {
        return Integer.valueOf(order).compareTo(o.getOrder());
    }

	public void setPercentComplete(int percentComplete) {
		this.percentComplete = percentComplete;
	}

	@Override
	public int getPercentComplete() {
		return percentComplete;
	}
}
