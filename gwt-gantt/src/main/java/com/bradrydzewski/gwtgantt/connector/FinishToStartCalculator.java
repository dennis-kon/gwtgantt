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

package com.bradrydzewski.gwtgantt.connector;

import com.bradrydzewski.gwtgantt.geometry.Point;
import com.bradrydzewski.gwtgantt.geometry.Rectangle;

/**
 * Calculate the path to connect the Right edges of two
 * rectangles with a polygonal line.
 * 
 * @author Brad Rydzewski
 *
 */
public class FinishToStartCalculator implements Calculator {

	public Point[] calculate(Rectangle r1, Rectangle r2) {

		int top = r1.getTop() + r1.getHeight()/2;
		
		Point[] points = new Point[3];
		points[0] = new Point(r1.getRight(), top);
		points[1] = new Point(r2.getLeft()+5, top);
		points[2] = new Point(r2.getLeft()+5, r2.getTop()-10);
		
		return points;
	}
	
}
