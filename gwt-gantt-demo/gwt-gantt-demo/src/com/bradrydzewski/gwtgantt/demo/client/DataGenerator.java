package com.bradrydzewski.gwtgantt.demo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		taskList.add(task1);
		
		TaskImpl task2 = new TaskImpl();
		task2.setUID(2);
		task2.setName("Test Task 2");
		task2.setLevel(1);
		task2.setOrder(2);
		task2.setPercentComplete(75);
		task2.setStart(new Date());
		task2.setFinish(new Date());
		taskList.add(task2);

		TaskImpl task3 = new TaskImpl();
		task3.setUID(3);
		task3.setName("Test Task 3");
		task3.setLevel(1);
		task3.setOrder(3);
		task3.setPercentComplete(25);
		task3.setStart(new Date());
		task3.setFinish(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+1));
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
		task6.getPredecessors().add(new Predecessor(5, PredecessorType.FS));
		taskList.add(task6);
		
		return taskList;
	}
}
