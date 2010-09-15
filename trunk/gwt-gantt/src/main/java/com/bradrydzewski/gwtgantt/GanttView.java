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

import com.google.gwt.user.client.ui.HasWidgets;

/**
 *
 * @author Brad Rydzewski
 */
public interface GanttView {

    public void attach(HasWidgets container, Project project);
    public void refresh();
	public void sortTasks(List<Task> taskList);
    public int getHorizontalScrollPosition();
    public int getVerticalScrollPosition();
    
    public void onTaskClicked(Task task);
    public void onTaskDoubleClicked(Task task);
    public void onTaskMouseOver(Task task);
    public void onTaskMouseOut(Task task);
    public void onScroll(int x, int y);
    public void onTaskExpand(Task task);
    public void onTaskCollapse(Task task);

	public void doTaskSelected(Task task);
	public void doTaskDeselected(Task task);
	public void doTaskEnter(Task task);
	public void doTaskExit(Task task);
}
