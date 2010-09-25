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
package com.bradrydzewski.gwtgantt.renderer;

import com.bradrydzewski.gwtgantt.geometry.Point;

/**
 * Extend the GanttWeekRenderer so that it is compatible with
 * browsers that do not yet implement inline SVG elements.
 * @author Brad Rydzewski
 */
public class GanttWeekRendererNoSVGImpl extends GanttWeekRenderer {
	@Override
	public void initSVG() {
		//do nothing, we can't use SVGs!
	}
	
	@Override
	public void renderConnector(Point point[]) {
		renderConnectorAsDiv(point);
	}
}
