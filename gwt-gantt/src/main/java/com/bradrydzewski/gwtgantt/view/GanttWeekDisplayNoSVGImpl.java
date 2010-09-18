package com.bradrydzewski.gwtgantt.view;

import com.bradrydzewski.gwtgantt.geometry.Point;

/**
 * Extend the GanttWeekDisplay so that it is compatible with
 * browsers that do not yet implement inline SVG elements.
 * @author Brad Rydzewski
 */
public class GanttWeekDisplayNoSVGImpl extends GanttWeekDisplay {
	@Override
	public void initSVG() {
		//do nothing, we can't use SVGs!
	}
	
	@Override
	public void renderConnector(Point point[]) {
		renderConnectorAsDiv(point);
	}
}
