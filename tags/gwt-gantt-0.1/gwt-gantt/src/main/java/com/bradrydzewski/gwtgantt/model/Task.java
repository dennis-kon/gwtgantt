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

import java.util.Date;
import java.util.List;


/**
 * Defines the behaviors that a <code>Task</code> to be displayed
 * in the gwt-gantt widget.
 *
 * @author Brad Rydzewski
 * @see com.bradrydzewski.gwtgantt.model.TaskImpl
 */
public interface Task extends Cloneable, Comparable<Task> {

    /**
     * Gets the unique ID of this task. For internal use only to compare and
     * link two tasks.
     *
     * @return A numeric ID identifying each task managed by gwt-gantt
     */
    public int getUID();

    /**
     * Sets the unique ID of this task.
     * @param UID
     */
    public void setUID(int UID);

    /**
     * Gets the Name of the task.
     *
     * @return Task name.
     */
    public String getName();

    /**
     * Sets the Name of the task.
     * 
     * @param name Task name.
     */
    public void setName(String name);

    /**
     * Gets the Text notes associated with the task.
     *
     * @return Task notes.
     */
    public String getNotes();

    /**
     * Gets the Order of the task.
     *
     * @return Task order.
     */
    public int getOrder();

    /**
     * Sets the Order of the task.
     *
     * @param order
     *            Task order.
     */
    public void setOrder(int order);

    /**
     * Gets the indent Level of the task.
     *
     * @return Task indentation level.
     */
    public int getLevel();

    /**
     * Sets the indent Level of the task.
     *
     * @param level
     *            Task indentation level.
     */
    public void setLevel(int level);

    /**
     * Gets the start Date of the task.
     *
     * @return Task start date.
     */
    public Date getStart();

    /**
     * Sets the start Date of the task.
     *
     * @param start
     *            Task start date.
     */
    public void setStart(Date start);

    /**
     * Gets the finish Date of the task.
     *
     * @return Task finish date.
     */
    public Date getFinish();
    
    /**
     * Gets the duration of the Task.
     * @return Task duration.
     */
    public double getDuration();
    
    /**
     * Gets the unit of measure for expressing a duration of time.
     * @return Task duration format.
     */
    public DurationFormat getDurationFormat();

    /**
     * Sets the finish Date of the task.
     *
     * @param finish
     *            Task finish date.
     */
    public void setFinish(Date finish);

    /**
     * The percentage of the task duration completed, as a whole number
     * between 0 and 100.
     * @return Percent complete.
     */
    public int getPercentComplete();
    
    /**
     * Set the percentage of the task duration completed, as a whole number
     * between 0 and 100.
     * @param percentComplete Percent complete.
     */
    public void setPercentComplete(int percentComplete);

    /**
     * Whether the task is a milestone.
     *
     * @return True or False if the task is a milestone.
     */
    public boolean isMilestone();

    /**
     * Whether the task is a summary.
     *
     * @return True or False if the task is a summary.
     */
    public boolean isSummary();

    /**
     * Sets whether the task is a summary (i.e. has child tasks).
     * @param summary True or False if the task is a summary
     */
    public void setSummary(boolean summary);


    /**
     * Whether the task is Hidden and should not be displayed on the Gantt
     * chart. This is used for UI rendering only, and has no other use
     * or meaning.
     * 
     * @return True or False if the task is hidden.
     */
    public boolean isCollapsed();

    /**
     * Sets whether or not the Task should be visually collapsed,
     * hiding all of its child tasks on the Gantt chart.
     * @param collapsed Flag indicating if task should be collapsed
     */
    public void setCollapsed(boolean collapsed);

    /**
     * Gets the List of {@link Predecessor}s for this task.
     *
     * @return
     */
    public List<Predecessor> getPredecessors();
    
    /**
     * Gets the CSS Style used when rendering this Task.
     */
    public String getStyle();

    /**
     * Sets the CSS Style used when rendering this Task.
     */
    public void setStyle(String style);
}
