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

import com.bradrydzewski.gwtgantt.presenter.GanttWeekPresenter;
import com.google.gwt.core.client.GWT;

/**
 * A GanttChart widget that extends the {@link TaskViewer}
 * component. This is basically a Facade that prevents the
 * developer from having to initialize the {@link TaskViewer}
 * component and manually set its {@link TaskPresenter}.
 * 
 * @author Brad Rydzewski
 */
public class GanttChart extends TaskViewer implements HasZoom {

	/**
	 * The current ZoomLevel for this control.
	 */
	private int zoomLevel = 0;
	
	/**
	 * Initializes the TaskViewer with the {@link GanttWeekPresenter}
	 * as the default Presenter / View combination.
	 */
	public GanttChart() {
		super((TaskPresenter)GWT.create(GanttWeekPresenter.class), null, null);
	}

    /**
     * {@inheritDoc}
     */
    public int getZoomLevel() {
        return zoomLevel;
    }

    /**
     * {@inheritDoc}
     */
    public void setZoomLevel(int level) {
        this.zoomLevel = level;
        refresh(true);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean zoomIn() {
    	//TODO: Implement GanttChart.zoomIn()
    	return false;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean zoomOut() {
    	//TODO: Implement GanttChart.zoomOut()
    	return false;
    }
}
