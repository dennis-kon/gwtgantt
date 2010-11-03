package com.bradrydzewski.gwtgantt.gantt;

import java.util.Date;

import com.bradrydzewski.gwtgantt.DateUtil;
import com.bradrydzewski.gwtgantt.date.DateRange;
import com.bradrydzewski.gwtgantt.geometry.Rectangle;

public class GanttChartBuilder {

    protected static final int ROW1_WIDTH = 278;
    protected static final int ROW1_WIDTH_OFFSET = 280;
    protected static final int ROW1_HEIGHT = 23;
    protected static final int ROW1_HEIGHT_OFFSET = 25;
    protected static final int ROW2_WIDTH = 38;
    protected static final int ROW2_WIDTH_OFFSET = 40;
    protected static final int ROW2_HEIGHT = 23;
    protected static final int ROW2_HEIGHT_OFFSET = 25;
    protected static final int TASK_ROW_HEIGHT = 24;
    protected static final int TASK_HEIGHT = 10;
    protected static final int TASK_PADDING_TOP = 6;
    protected static final int MILESTONE_WIDTH = 16;
    protected static final int MILESTONE_PADDING_TOP = 4;
    protected static final int SUMMARY_HEIGHT = 7;
    protected static final int SUMMARY_PADDING_TOP = 6;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 0;
	
	public Rectangle calculateHeaderCell(int row, int column) {
		return null;
	}
	
	public Rectangle calculateTaskRectangle(
			DateRange project, DateRange task, int index) {
		
        int daysFromStart = DateUtil.differenceInDays(project.getStart(),task.getStart());//+1;
        int daysInLength = DateUtil.differenceInDays(project.getStart(), task.getFinish()) + 1;

        daysInLength = Math.max(daysInLength, 1);

        int top = TASK_ROW_HEIGHT * index + TASK_PADDING_TOP;
        int left = daysFromStart * ROW2_WIDTH_OFFSET;
        int width = daysInLength * ROW2_WIDTH_OFFSET - 4;
        int height = TASK_HEIGHT;

        //render the task
        return new Rectangle(left, top, width, height);
	}
	
	public Rectangle calculateMilestoneRectangle(
			DateRange project, DateRange task, int index) {
		
        int daysFromStart = DateUtil.differenceInDays(
        		project.getStart(), task.getStart());

        int top = TASK_ROW_HEIGHT * index + MILESTONE_PADDING_TOP;
        int left = daysFromStart * ROW2_WIDTH_OFFSET;
        int width = MILESTONE_WIDTH;
        int height = TASK_HEIGHT;

        //render the task
        return new Rectangle(left, top, width, height);
	}
	
	public Rectangle calculateSummaryRectangle(
			DateRange project, DateRange task, int index) {
		
        int daysFromStart = DateUtil.differenceInDays(project.getStart(),task.getStart());//+1;
        int daysInLength = DateUtil.differenceInDays(project.getStart(), task.getFinish()) + 1;

        daysInLength = Math.max(daysInLength, 1);

        int top = TASK_ROW_HEIGHT * index + SUMMARY_PADDING_TOP;
        int left = daysFromStart * ROW2_WIDTH_OFFSET;
        int width = daysInLength * ROW2_WIDTH_OFFSET - 4;
        int height = SUMMARY_HEIGHT;

        //render the task
        return new Rectangle(left, top, width, height);
	}
}
