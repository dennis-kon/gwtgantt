package com.bradrydzewski.gwtgantt;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwtgantt.connector.CalculatorFactory;
import com.bradrydzewski.gwtgantt.geometry.Point;
import com.bradrydzewski.gwtgantt.geometry.Rectangle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class GanttWeekView implements GanttView {

	public interface Display {
		void bind(GanttView view);
		void renderTask(Task task, Rectangle rectangle);
		void renderTaskSummary(Task task, Rectangle rectangle);
		void renderTaskMilestone(Task task, Rectangle rectangle);
		void renderTaskLabel(Task task, Rectangle rectangle);
		void renderConnector(Point[] path);
		void renderTopTimescaleCell(Rectangle bounds, String text);
		void renderBottomTimescaleCell(Rectangle bounds, String text);
		void renderRow(Rectangle bounds, int rowNumber);
		void renderColumn(Rectangle bounds, int dayOfWeek);
		void onBeforeRendering();
		void onAfterRendering();
		void doTaskSelected(Task task);
		void doTaskDeselected(Task task);
		void doTaskEnter(Task task);
		void doTaskExit(Task task);
		Rectangle getTaskRectangle(int UID);
		int getHorizontalScrollPosition();
		int getVerticalScrollPosition();
		Widget asWidget();
	}
	
	protected int ROW1_WIDTH = 278;
	protected int ROW1_WIDTH_OFFSET = 280;
	protected int ROW1_HEIGHT = 23;
	protected int ROW1_HEIGHT_OFFSET = 25;
	protected int ROW2_WIDTH = 38;
	protected int ROW2_WIDTH_OFFSET = 40;
	protected int ROW2_HEIGHT = 23;
	protected int ROW2_HEIGHT_OFFSET = 25;
	public static final int TASK_ROW_HEIGHT = 24;
	public static final int TASK_HEIGHT = 10;
	public static final int TASK_PADDING_TOP = 6;
	public static final int MILESTONE_WIDTH = 16;
	public static final int MILESTONE_PADDING_TOP = 4;
	public static final int SUMMARY_HEIGHT = 7;
	public static final int SUMMARY_PADDING_TOP = 6;
	
	private Project project;
	private Display display = GWT.create(GanttWeekDisplay.class);
	private Date start;
	private Date finish;

	public GanttWeekView() {
		display.bind(this);
	}

	@Override
	public void attach(HasWidgets container, Project project) {
		this.project = project;
		container.clear();
		container.add(display.asWidget());
	}

	@Override
	public void refresh() {
		this.start = calculateStartDate();
		this.finish = calculateFinishDate();
		display.onBeforeRendering();
		renderBackground();
		renderTasks();
		renderConnectors();
		display.onAfterRendering();
	}

	@SuppressWarnings("deprecation")
	protected void renderBackground() {

		Date date = (Date)start.clone();
		
		int diff = DateUtil.differenceInDays(start, finish);
		int weeks = (int) Math.ceil(diff / 7);

		// FOR EACH WEEK
		for (int i = 0; i < weeks; i++) {

			//TODO: Remove reference to DateTimeFormat, which requires GWT Test Case
			Date firstDayOfWeek =  DateUtil.getFirstDayOfWeek(date);
			String topTimescaleString = DateTimeFormat.getMediumDateFormat().format(firstDayOfWeek);
			
			//ADD WEEK PANEL
			Rectangle weekHeaderBounds = new Rectangle(ROW1_WIDTH_OFFSET * i,0,ROW1_WIDTH,25);
			display.renderTopTimescaleCell(weekHeaderBounds, topTimescaleString);

			// ADD 7 DAYS PER WEEK
			for (int d = 0; d < 7; d++) {
				//ADD DAY PANEL
				Date weekDay = DateUtil.addDays(date, d);
				int width = ROW2_WIDTH;
				int height = 24;
				int left = (ROW1_WIDTH_OFFSET * i) + (ROW2_WIDTH_OFFSET * d);
				int top = 0;
				Rectangle bottomTimescaleBounds = new Rectangle(left, top, width, height);
				String bottomTimescaleString = weekDay.getDate() + "";
				display.renderBottomTimescaleCell(bottomTimescaleBounds, bottomTimescaleString);
			
				if (d == 6 || d==0) {
					//ADD BACKGROUND FOR SAT, SUND
					int colTop = 0;
					int colLeft = (ROW1_WIDTH_OFFSET * i)	+ (ROW2_WIDTH_OFFSET * d);
					int colWidth = 38;
					int colHeight = -1; //not used... 100% defined by style
					Rectangle colBounds = new Rectangle(colLeft, colTop, colWidth, colHeight);
					display.renderColumn(colBounds, d);
				}
			}

			date = DateUtil.addDays(date, DateUtil.DAYS_PER_WEEK);
		}
	}

	protected void renderTasks() {
		//clear task panel
		//clear lookup Map
		
		int count = 0;
		boolean collapse = false;
		int collapseLevel = -1;

		for (int i = 0; i < project.getTaskCount(); i++) {
			//get the task
			Task task = project.getTask(i);
			
			collapse = collapse && task.getLevel()>=collapseLevel;
			
			if(!collapse) {
				if(task.isSummary()) {
					//render the summary widget
					renderTaskSummary(task, count);
					collapse = task.isCollapsed();
					collapseLevel = task.getLevel()+1;
				} else if(task.isMilestone()) {
					//render the milestone widget
					renderTaskMilestone(task, count);
				} else {
					//render the task widget
					renderTask(task, count);
				}
				count++;
			}
		}
	}

	protected void renderTask(Task task, int order) {

		int daysFromStart = DateUtil.differenceInDays(start, task.getStart());//+1;
		int daysInLength = DateUtil.differenceInDays(task.getStart(), task.getFinish())+1;

		daysInLength = Math.max(daysInLength, 1);
		
		int top = TASK_ROW_HEIGHT*order+TASK_PADDING_TOP;//order * TASK_HEIGHT + ((order+1) * TASK_PADDING) + (order * TASK_PADDING);
		int left = daysFromStart * ROW2_WIDTH_OFFSET;
		int width = daysInLength * ROW2_WIDTH_OFFSET-4;
		int height = TASK_HEIGHT;

		//render the task
		Rectangle taskBounds = new Rectangle(left, top, width, height);
		display.renderTask(task, taskBounds);
		
		//render the label
		Rectangle labelBounds = new Rectangle(taskBounds.getRight(), top-2, -1, -1);
		display.renderTaskLabel(task, labelBounds);
		
		//if task is selected, make sure it is rendered as selected
		if(project.isSelected(task)) {
			this.doTaskSelected(task);
		}
	}

	protected void renderTaskSummary(Task task, int order) {

		int daysFromStart = DateUtil.differenceInDays(start, task.getStart());//+1
		int daysInLength = DateUtil.differenceInDays(task.getStart(), task.getFinish())+1;

		daysInLength = Math.max(daysInLength, 1);

		int top = TASK_ROW_HEIGHT*order+SUMMARY_PADDING_TOP;;//order * TASK_HEIGHT + ((order+1) * TASK_PADDING) + (order * TASK_PADDING);
		int left = daysFromStart * ROW2_WIDTH_OFFSET;
		int width = daysInLength * ROW2_WIDTH_OFFSET-4;
		int height = SUMMARY_HEIGHT;

		//render the task
		Rectangle taskBounds = new Rectangle(left, top, width, height);
		display.renderTaskSummary(task, taskBounds);
		
		//render the label
		Rectangle labelBounds = new Rectangle(taskBounds.getRight(), top-2, -1, -1);
		display.renderTaskLabel(task, labelBounds);
		
		//if task is selected, make sure it is rendered as selected
		if(project.isSelected(task)) {
			this.doTaskSelected(task);
		}
	}

	protected void renderTaskMilestone(Task task, int order) {

		int daysFromStart = DateUtil.differenceInDays(start, task.getStart());//+1;

		int top = TASK_ROW_HEIGHT*order+MILESTONE_PADDING_TOP;//order * TASK_HEIGHT + ((order+1) * TASK_PADDING) + (order * TASK_PADDING);
		int left = daysFromStart * ROW2_WIDTH_OFFSET;
		int width = MILESTONE_WIDTH;
		int height = TASK_HEIGHT;

		//render the task
		Rectangle taskBounds = new Rectangle(left, top, width, height);
		display.renderTaskMilestone(task, taskBounds);
		
		//render the label
		Rectangle labelBounds = new Rectangle(taskBounds.getRight(), top-2, -1, -1);
		display.renderTaskLabel(task, labelBounds);
		
		//if task is selected, make sure it is rendered as selected
		if(project.isSelected(task)) {
			this.doTaskSelected(task);
		}
	}
	
	protected void renderConnectors() {
		
		for(int i=0; i<project.getTaskCount(); i++) {
			
			Task task = project.getTask(i);
			for(Predecessor predecessor : task.getPredecessors()) {
				Point[] path = null;
				Rectangle fromRect = display.getTaskRectangle(predecessor.getUID());
				Rectangle toRect = display.getTaskRectangle(task.getUID());
				if(fromRect!=null && toRect!=null) {
					path = CalculatorFactory.get(
							predecessor.getType()).calculate(fromRect, toRect);
					renderConnector(path);
				}
				
			}
			
		}
		
	}

	protected void renderConnector(Point[] path) {
		display.renderConnector(path);
	}

	protected Date calculateStartDate() {
		
		Date adjustedStart = project.getStart();
		
		//if the gantt chart's start date is null, let's set one automatically
		if(project.getStart()==null) {
			adjustedStart = new Date();
		}

		//if the first task in the gantt chart is before the gantt charts
		// project start date ...
		if(project.getTaskCount()>0 &&
				project.getTask(0).getStart().before(adjustedStart)) {
			
			adjustedStart = project.getTask(0).getStart();	
		}
		
		adjustedStart = DateUtil.addDays(adjustedStart, -7);
		adjustedStart = DateUtil.getFirstDayOfWeek(adjustedStart);
		return adjustedStart;
	}
	
	protected Date calculateFinishDate() {
		
		Date adjustedFinish = project.getFinish();
		
		//if the gantt chart's finish date is null, let's set one automatically
		if(adjustedFinish==null) {
			adjustedFinish = new Date();
			adjustedFinish = DateUtil.addDays(adjustedFinish, 7*48);
		}

		//if the last task in the gantt chart is after the gantt charts
		// project finish date ...
		if(project.getTaskCount()>0 && project.getTask(
				project.getTaskCount()-1).getFinish().after(adjustedFinish)) {
			
			adjustedFinish = project.getTask(project.getTaskCount()-1).getFinish();	
		}
		
		adjustedFinish = DateUtil.addDays(adjustedFinish, 7);
		adjustedFinish = DateUtil.getFirstDayOfWeek(adjustedFinish);
		return adjustedFinish;
	}


	@Override
	public void onTaskClicked(Task task) {
		project.fireTaskClickEvent(task);
	}

	@Override
	public void onTaskDoubleClicked(Task task) {
		project.fireTaskDoubleClickEvent(task);
	}

	@Override
	public void onTaskMouseOver(Task task) {
		project.fireTaskEnterEvent(task);
	}

	@Override
	public void onTaskMouseOut(Task task) {
		project.fireTaskExitEvent(task);
	}
	
	public void doTaskSelected(Task task) {
		display.doTaskSelected(task);
	}
	
	public void doTaskDeselected(Task task) {
		display.doTaskDeselected(task);
	}
	
	public void doTaskEnter(Task task) {
		display.doTaskEnter(task);
	}
	
	public void doTaskExit(Task task) {
		display.doTaskExit(task);
	}

	@Override
	public void onScroll(int x, int y) {
		project.fireScrollEvent(x, y);
	}

	@Override
	public int getHorizontalScrollPosition() {
		return display.getHorizontalScrollPosition();
	}

	@Override
	public int getVerticalScrollPosition() {
		return display.getVerticalScrollPosition();
	}
	

	
	@Override
	public void sortTasks(List<Task> taskList) {
		Collections.sort(taskList,TaskManager.TASK_ORDER_COMPARATOR);
	}
}
