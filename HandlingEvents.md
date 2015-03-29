**WARNING:** This page is **out of date**. The event model will continue to change as we iterate. Once we finalize our event model we will update the page.



# Task Click #

The `ItemClickEvent` is fired when a `Task` in the `GanttChart` or `TaskGrid` receives a single click from the user. You may want to handle this event in order to select a `Task`.

```
   gantt.addItemClickHandler(new ItemClickHandler<Task>(){
      @Override
      public void onItemClick(ItemClickEvent<Task> event) {
         gantt.setSelectedItem(event.getItem());
      }
   });
```

# Task Double Click #

The `ItemDoubleClickEvent` is fired when a `Task` in the `GanttChart` or `TaskGrid` receives a double click from the user. You may want to handle this event in order to select a `Task`, open a `Popup` panel, etc.

```
   gantt.addItemDoubleClickHandler(new ItemDoubleClickHandler<Task>(){
      @Override
      public void onItemDoubleClick(ItemDoubleClickEvent<Task> event) {
         ...open a popup...
      }
   });
```

# Task Mouse Over #

The `MouseEnterEvent` is fired when a mouse is hovered over a `Task` in the `GanttChart`. You may want to handle this event in order to display a tooltip, for example.

```
   gantt.addMouseEnterHandler(new MouseEnterHandler<Task>(){
      @Override
      public void onMouseEnter(MouseEnterEvent<Task> event) {
         ...show a tooltip...
      }
   });
```

# Task Mouse Out #

The `MouseExitEvent` is fired when a mouse leaves a `Task` in the `GanttChart`. You may want to handle this event in order to hide a `Task`'s tooltip, for example.

```
   gantt.addMouseExitHandler(new MouseExitHandler<Task>(){
      @Override
      public void onMouseExit(MouseExitEvent<Task> event) {
         ...hide previously displayed tooltip...
      }
   });
```

# Gantt Chart Scroll #

The `ScrollEvent` is fired when the `GanttChart` or `TaskGrid` are scrolled. This event is useful when you display both the `GanttChart` and `TaskGrid` at the same time, and need to synchronize the vertical scrollbar.

See our [demo application](http://code.google.com/p/gwtgantt/source/browse/trunk/gwt-gantt-demo/gwt-gantt-demo/src/com/bradrydzewski/gwtgantt/demo/client/DemoPanel.java) for an example.

```
   gantt.addScrollHandler(new ScrollHandler(){
      @Override
      public void onScroll(ScrollEvent event) {
         grid.setScrollPosition(event.getX(), event.getY());
      }
   });
```