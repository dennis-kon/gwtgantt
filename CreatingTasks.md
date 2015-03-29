This article gives a fairly in-depth overview of working with `Task`s and adding them to `GanttChart` and `TaskGrid` widgets. If you read one gwt-gantt wiki article, I recommend you read this one ...



# Creating a Task #

The `GanttChart` and `TaskGrid` widgets both take in items of type `Task`, which is a simple interface that defines all the required fields to render a set of `Task`s. You can create your own `Task` implementation, or you can use our default `TaskImpl` that we provide along with the gwt-gantt API.

```
   Task task1 = new TaskImpl();
   task1.setUID(1);
   task1.setName(...);
   task1.setLevel(0);
   task1.setOrder(1);
   task1.setPercentComplete(50);
   task1.setStart(...);
   task1.setFinish(...);

   gantt.addItem(task1);
```

## Task Attributes Explained ##

### Unique Id (UID) ###

The Unique Id, or `UID` field, is probably the most important field for a `Task`. It is used by gwt-gantt to uniquely identify a `Task` and match it to its predecessors (in order to join `Task`s with connectors.

This UID heavily used internally, so make sure to give each `Task` its own unique id.

```
   task1.setUID(1);
```

### Order ###

The `Over` attribute specifies a`Task`s order in the Gantt chart. gwt-gannt does not automatically calculate a `Task`s order. It is up to the developer to explicitly set this property in order for the gantt chart to render correctly.

gwt-gannt will sort all `Task`s based on the `Order` property prior to rendering the Gantt Chart.

### Level ###

The `Level` attribute, along with the `Order` attribute, defines the `Task` hierarchy in the tree. A level of `0` means that the task is a root-level Task. A `Task` with a level of `2` is two levels deep in the tree structure, meaning the `Task` is a child of a child.

gwt-gannt relies on the developer to correctly set the `Level` attribute. It will not automatically calculate the hierarchy of a list of `Task`s.

In the following example `task5` is considered a parent of `task6`, based on the `Order` and `Level` attributes:
```
   task5.setSummary(true);
   task5.setOrder(5);
   task5.setLevel(0);

   task6.setOrder(6)
   task6.setLevel(1);
```

### Is Summary ###

The `Summary` boolean attribute defines if a `Task` has children and is collapsible. Summary tasks are rendered differently than regular tasks.

### Is Milestone ###

The `Milestone` boolean attribute defines a task as a milestone, meaning it will be rendered as lasting only one day (regardless of Start and Finish date), and will be displayed as a Diamond shape.

### Is Collapsed ###

The `Collapsed` boolean attribute defines if a summary `Task` should be collapsed, hiding all of its child tasks. The `TaskGrid` widget will set this property when a user clicks the expand / collapse buttons. This is one of the only times a gwt-gantt widget will update a `Task`'s attribute.

Note: this attribute is only taken into account for Summary tasks (ie `isSummary() == true`).

## Task Predecessors ##

Predecessors can be used to define a relationship between two `Task`s. Visually this is represented by drawing a line connecting two `Task`s.

In the below example, we are adding a `Predecessor` to `task6`. It's `Predecessor` is defined as `task5` (we pass in task5's UID). We also define the `PredecessorType`.

```
   task5.setSummary(true);
   task5.setOrder(5);
   task5.setLevel(0);

   task6.setOrder(6)
   task6.setLevel(1);
   task6.getPredecessors().add(
             new Predecessor(5, PredecessorType.SS));
```

There are four different `PredecessorType`s, each of which is rendered differently on a `GanttChart`:
  * Start to Start (SS)
  * Start to Finish (SF)
  * Finish to Finish (FF)
  * Finish to Start (FS)

# Editing Tasks #

If you make changes to a `Task`, and would like the changes reflected in the `GanttChart` or `TaskGrid`, you will need to manually instruct the widget to refresh its layout. This is done by invoking the `ListDataProvider`'s `refresh()` method:

```
   dataProvider.refresh();
```

# Selecting Tasks #

In order to manually select or deselect a `Task`, you can use the `SelectionModel`:
```
   selectionModel.setSelected(task, true);
```

The selection model will handle updating and refreshing the `GanttChart` to reflect the selection changes.