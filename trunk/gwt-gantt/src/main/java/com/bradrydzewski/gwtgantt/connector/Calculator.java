package com.bradrydzewski.gwtgantt.connector;

import com.bradrydzewski.gwtgantt.geometry.Point;
import com.bradrydzewski.gwtgantt.geometry.Rectangle;

public interface Calculator {
	public Point[] calculate(Rectangle r1, Rectangle r2);
}
