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

/**
 * Defines an {@link Widget} that knows how to layout its child components.
 * 
 * @author Brad Rydzewski
 * 
 */
public interface HasLayout {

	/**
	 * Temporarily suspends the layout logic for the widget.
	 */
	public void suspendLayout();

	/**
	 * Resumes the layout logic for the widget.
	 */
	public void resumeLayout();

	/**
	 * Determines whether the widget has changed since the last layout was
	 * performed.
	 * 
	 * @return {@code true} if the widget's layout needs to be re-calculated.
	 */
	public boolean isDirty();

	/**
	 * Requests the widget to redraw itself and any child controls.
	 */
	public void refresh();

	/**
	 * Requests the widget to redraw itself and any child controls.
	 * 
	 * @param force Flag to force the widget to redraw, <code>false</code> to
	 *        redraw only if the widget is dirty.
	 */
	public void refresh(boolean force);
}
