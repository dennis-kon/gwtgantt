package com.bradrydzewski.gwtgantt.date;

import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwtgantt.DateUtil;

public class DateRangeCalculator {
	
	public DateRange calculateDateRange(DateRange project,
			DateRange task) {
		
		DateRange range = new DateRange();
		return range;
	}
	

	
    protected Date calculateStartDate(DateRange project,
			DateRange task) {

        Date adjustedStart = project.getStart();

        //if the gantt chart's start date is null, let's set one automatically
        if (project.getStart() == null) {
            adjustedStart = new Date();
        }

        //if the first task in the gantt chart is before the gantt charts
        // project start date ...
        if (task.getStart().before(adjustedStart)) {
            adjustedStart = task.getStart();
        }

        adjustedStart = DateUtil.addDays(adjustedStart, -7);
        adjustedStart = DateUtil.getFirstDayOfWeek(adjustedStart);
        return adjustedStart;
    }

    protected Date calculateFinishDate(DateRange project,
			DateRange task) {

        Date adjustedFinish = project.getFinish();

        //if the gantt chart's finish date is null, let's set one automatically
        if (adjustedFinish == null) {
            adjustedFinish = new Date();
            adjustedFinish = DateUtil.addDays(adjustedFinish, 7 * 48);
        }

        //if the last task in the gantt chart is after the gantt charts
        // project finish date ...
        if (task.getFinish().after(adjustedFinish)) {

            adjustedFinish = task.getFinish();
        }

        adjustedFinish = DateUtil.addDays(adjustedFinish, 7);
        adjustedFinish = DateUtil.getFirstDayOfWeek(adjustedFinish);
        return adjustedFinish;
    }
}
