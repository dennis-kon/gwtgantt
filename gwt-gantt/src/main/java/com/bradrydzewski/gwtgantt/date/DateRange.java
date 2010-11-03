package com.bradrydzewski.gwtgantt.date;

import java.util.Date;

public class DateRange {

	private Date start;
	private Date finish;
	
	public DateRange() {
		
	}
	
	public DateRange(Date start, Date finish) {
		this.start = start;
		this.finish = finish;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}
}
