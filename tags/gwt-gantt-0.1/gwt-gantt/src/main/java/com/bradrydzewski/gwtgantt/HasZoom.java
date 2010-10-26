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
 * Describes an object that can be zoomed in and out.
 * 
 * @author Brad Rydzewski
 */
public interface HasZoom {

	/**
	 * Requests the object to zoom in.
	 * @return {@code true} if the object was able to zoom in.
	 */
	public boolean zoomIn();
	
	/**
	 * Requests the object to zoom out.
	 * @return {@code true} if the object was able to zoom out.
	 */
	public boolean zoomOut();
	
	/**
	 * Sets the zoom level for this object. 
	 * @param level the zoom level.
	 */
	public void setZoomLevel(int level);
	
	/**
	 * Returns the zoom level for this object.
	 * @return the zoom level.
	 */
	public int getZoomLevel();
}
