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

import java.util.List;

import com.bradrydzewski.gwtgantt.event.ItemExpandEvent;

public interface HasTasks {

    public void addTask(Task task);

    public void addTasks(List<Task> taskList);

    public void removeTask(Task task);

    public void removeAllTasks();
    
    public void setSelectedTask(Task task);
    
    public boolean isSelected(Task task);

    public Task getSelectedTask();

    public int getTaskCount();

    public Task getTask(int index);
    
    public int getTaskIndexOf(Task task);

	public void fireTaskClickEvent(Task task);

	public void fireTaskDoubleClickEvent(Task task);

    public void fireTaskEnterEvent(Task task);
    
    public void fireTaskExitEvent(Task task);

	public void fireTaskExpandEvent(Task task);

	public void fireTaskCollapseEvent(Task task);
}
