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
 * Describes an object that displays a range of data
 * with a start and a finish.
 * 
 * @author Brad Rydzewski
 */
public interface HasRange<T> {

	/**
	 * Get the start of the range.
	 * 
	 * @return the start of the range.
	 */
	public T getStart();

	/**
	 * Set the start of the range.
	 * 
	 * @param start
	 *            the start of the range.
	 */
	public void setStart(T start);

	/**
	 * Get the end of the range.
	 * 
	 * @return the end of the range.
	 */
	public T getFinish();

	/**
	 * Set the end of the range.
	 * 
	 * @param finish
	 *            the end of the range.
	 */
	public void setFinish(T finish);
}
