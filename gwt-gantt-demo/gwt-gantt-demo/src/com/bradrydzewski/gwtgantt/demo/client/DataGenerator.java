package com.bradrydzewski.gwtgantt.demo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwtgantt.DateUtil;
import com.bradrydzewski.gwtgantt.model.DurationFormat;
import com.bradrydzewski.gwtgantt.model.Predecessor;
import com.bradrydzewski.gwtgantt.model.PredecessorType;
import com.bradrydzewski.gwtgantt.model.Task;
import com.bradrydzewski.gwtgantt.model.TaskImpl;

public class DataGenerator {

	public static List<Task> generateTasks() {
		List<Task> taskList = new ArrayList<Task>();
		
		TaskImpl task1 = new TaskImpl();
		task1.setUID(1);
		task1.setSummary(true);
		task1.setName("Test Task 1");
		task1.setLevel(0);
		task1.setOrder(1);
		task1.setPercentComplete(50);
		task1.setStart(new Date());
		task1.setFinish(new Date());
		task1.setDuration(DateUtil.differenceInDays(task1.getStart(), task1.getFinish()));
		taskList.add(task1);
		
		TaskImpl task2 = new TaskImpl();
		task2.setUID(2);
		task2.setName("Test Task 2");
		task2.setLevel(1);
		task2.setOrder(2);
		task2.setPercentComplete(75);
		task2.setStart(new Date());
		task2.setFinish(new Date());
		task2.setDuration(DateUtil.differenceInDays(task2.getStart(), task2.getFinish()));
		taskList.add(task2);

		TaskImpl task3 = new TaskImpl();
		task3.setUID(3);
		task3.setName("Test Task 3");
		task3.setLevel(1);
		task3.setOrder(3);
		task3.setPercentComplete(25);
		task3.setStart(new Date());
		task3.setFinish(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+1));
		task3.setDuration(DateUtil.differenceInDays(task3.getStart(), task3.getFinish()));
		task3.getPredecessors().add(new Predecessor(2, PredecessorType.SS));
		taskList.add(task3);

		TaskImpl task4 = new TaskImpl();
		task4.setUID(4);
		task4.setName("Test Task 4");
		task4.setLevel(0);
		task4.setOrder(4);
		task4.setPercentComplete(0);
		task4.setStart(new Date());
		task4.setFinish(new Date());
		task4.setDuration(3);
		task4.setDurationFormat(DurationFormat.HOURS);
		task4.setStyle(TaskImpl.STYLE_RED);
		task4.getPredecessors().add(new Predecessor(3, PredecessorType.FS));
		taskList.add(task4);
		
		TaskImpl task5 = new TaskImpl();
		task5.setUID(5);
		task5.setName("Test Task 5");
		task5.setLevel(0);
		task5.setOrder(5);
		task5.setPercentComplete(25);
		task5.setStart(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+5));
		task5.setFinish(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+7));
		task5.setDuration(DateUtil.differenceInDays(task5.getStart(), task5.getFinish()));
		task5.getPredecessors().add(new Predecessor(4, PredecessorType.FS));
		taskList.add(task5);
		
		TaskImpl task6 = new TaskImpl();
		task6.setUID(6);
		task6.setName("Test Task 6");
		task6.setLevel(0);
		task6.setOrder(6);
		task6.setPercentComplete(0);
		task6.setStart(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+5));
		task6.setFinish(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+7));
		task6.setDuration(DateUtil.differenceInDays(task6.getStart(), task6.getFinish()));
		task6.getPredecessors().add(new Predecessor(5, PredecessorType.FS));
		taskList.add(task6);
		
		TaskImpl task7 = new TaskImpl();
		task7.setUID(7);
		task7.setName("Summary Task (#7)");
		task7.setLevel(0);
		task7.setOrder(7);
		task7.setPercentComplete(0);
		task7.setStart(new Date());
		task7.setFinish(DateUtil.addDays(new Date(), 10));
		task7.setDuration(DateUtil.differenceInDays(task7.getStart(), task7.getFinish()));
		task7.setSummary(true);
		taskList.add(task7);
		
		Date date = new Date();
		for(int i=8;i<18;i++) {
			
			TaskImpl taskN = new TaskImpl();
			taskN.setUID(i);
			taskN.setName("Test Task "+i);
			taskN.setLevel(1);
			taskN.setOrder(i);
			taskN.setPercentComplete(0);
			taskN.setStart(date);
			//increment date
			date = (Date)date.clone();
			date = DateUtil.addDays(date, 1);
			
			taskN.setFinish(date);
			taskN.setDuration(DateUtil.differenceInDays(
					taskN.getStart(), taskN.getFinish()));
			
			
			if(i>8)
				taskN.getPredecessors().add(new Predecessor(i-1, PredecessorType.FS));
			
			//add task to list
			taskList.add(taskN);
		}
		
		return taskList;
	}
}
