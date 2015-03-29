# Setup #

  1. Add the `gwt-gantt` jar file to your project's build path. Right-click on the project node in the Package Explorer and select 'Build Path > Add External Archives...'. Specify the downloaded `gwt-gantt-<version>.jar`
  1. Modify `<Your Application>.gwt.xml` to inherit the `gwt-gantt` module:
```
  <inherits name='com.bradrydzewski.Gantt' />
```


# Creating a Gantt Chart #

First we need to create a `ListDataProvider` and `SelectionModel`, which are part of GWT 2.1's [data presentation model](http://code.google.com/webtoolkit/doc/latest/DevGuideUiCellWidgets.html).
```
  ListDataProvider<Task> dataProvider = new ListDataProvider<Task>();
  SelectionModel<Task> selectionModel = new SingleSelectionModel<Task>();
```

Second, we need to create the Data Provider. The data provider is used to map your own classes to the `GanttChart`. For now, however, we will use gwt-gantt's default Data Provider.
```
  ProvidesTask<Task> taskProvider = new ProvidesTaskImpl();
```

Third, we can now create the `GanttChart`, passing the Data Provider into the constructor.
```
  GanttChart<Task> gantt = new GanttChart<Task>(taskProvider);
  gantt.setWidth("500px");
  gantt.setHeight("400px");

```

Fourth, we add the `SelectionModel` to the `GanttChart`, and add the `GanttChart` to the `ListDataProvider`. The `SelectionModel` is used to select and deselect tasks. It will also alert you when the selection changes.
```
  gantt.setSelectionModel(selectionModel);
  dataProvider.addDataDisplay(gantt);
```

Finally we add the `GanttChart` to the `RootPanel`, just like any standard GWT widget:
```
   RootPanel.get().add(gantt);
```

# Adding Tasks #

The following code snippet demonstrates how to create and add a List of `Task`s to the `GanttChart` widget:

```
   List<Task> taskList = new ArrayList<Task>();
 
   Task task = new TaskImpl();
   task.setUID(1);
   task.setSummary(true);
   task.setName("Test Task 1");
   task.setLevel(0);
   task.setOrder(1);
   task.setPercentComplete(50);
   task.setStart(new Date());
   task.setFinish(new Date());

   //add the task to the list
   taskList.add(task);

   //add the list to the data provider
   dataProvider.setList(taskList);

```