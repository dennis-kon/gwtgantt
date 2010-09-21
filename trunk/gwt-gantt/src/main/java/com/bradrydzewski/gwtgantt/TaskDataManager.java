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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bradrydzewski.gwtgantt.model.Task;

/**
 * Manages the set of {@link  Task}s displayed through a
 * {@link TaskViewer}.
 *
 * @author Brad Rydzewski
 * @see com.bradrydzewski.gwtgantt.TaskViewer
 */
public class TaskDataManager {

    /**
     * A reference to the &quot;currently selected task&quot;. Will be
     * <code>null</code> when no currently selected task has been set.
     */
    private Task selectedTask = null;

    /**
     * The collection of tasks to be maintained by this
     * <code>TaskManager</code>.
     */
    private ArrayList<Task> tasks = new ArrayList<Task>();

//    /**
//     * Map that indexes Tasks by UID for quick retrieval.
//     */
//    private HashMap<Integer, Task> taskIndex = new HashMap<Integer, Task>();

    /**
     * Internal state flag indicating whether the collection of tasks
     * needs to be sorted.
     */
    private boolean sortPending = true;

    /**
     * Returns the collection of tasks that this <code>TaskManager</code>
     * maintains. <strong>Warning</strong>: this method returns a modifiable
     * reference to the internally managed list of tasks; client code
     * might break the invariants that this <code>TaskManager</code>
     * enforces if it performs any operations that modify the returned
     * collection.
     *
     * @return The tasks managed by this <code>TaskManager</code>.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds an task to the collection of tasks maintained by this
     * <code>ApplicationManager</code>.
     *
     * @param task The task to be made part of this manager's managed
     *             collection
     */
    public void addTask(Task task) {
        if (task != null) {
            tasks.add(task);
//            taskIndex.put(task.getUID(),task);
            sortPending = true;
        }
    }

    /**
     * Adds multiple tasks to the collection maintained by this
     * <code>ApplicationManager</code>.
     *
     * @param tasks The tasks that will be made part of this
     *                     manager's managed collection
     */
    public void addTasks(ArrayList<Task> tasks) {
        if (tasks != null) {
            for (Task task : tasks) {
                addTask(task);
            }
        }
    }

    /**
     * Removes the <code>task</code> from this manager's managed
     * collection.
     *
     * @param task The task to remove
     */
    public void removeTask(Task task) {
        if (task != null) {
            boolean wasRemoved = tasks.remove(task);
//            taskIndex.remove(task.getUID());

            if (wasRemoved) {
                sortPending = true;
            }
            //I'd rather have the client keep the reference to the selected one if they need it than having here
            //a reference to a thing I no longer should know about...
            if (hasTaskSelected() &&
                    getSelectedTask().equals(task)) {
                selectedTask = null;
            }
        }
    }

    /**
     * Removes the &quot;currently selected&quot; task from this
     * manager's collection.
     */
    public void removeCurrentlySelectedTask() {
        if (hasTaskSelected()) {
            removeTask(getSelectedTask());
            selectedTask = null;
        }
    }

//    /**
//     * Performs a lookup and retrieves the task based in UID.
//     * @param UID Unique Identifier
//     * @return Task with the requested Unique Identifier
//     */
//    public Task getTaskByUID(int UID) {
//        return taskIndex.get(UID);
//    }

    /**
     * Empties the collection of managed tasks.
     */
    public void clearTasks() {
        tasks.clear();
//        taskIndex.clear();
    }

    /**
     * Sets the task that should be considered the &quot;currently
     * selected&quot; task.
     *
     * @param selectedTask The task to consider &quot;currently
     *                            selected&quot;
     */
    public void setSelectedTask(Task selectedTask) {
        if (selectedTask != null &&
                tasks.contains(selectedTask)) {
            this.selectedTask = selectedTask;
        }
    }

    /**
     * Indicates whether there is a &quot;currently selected&quot; task
     * at the moment.
     *
     * @return <code>true</code> if there is a currently selected task
     *         for the collection managed by this component, <code>false</code>
     *         otherwise
     */
    public boolean hasTaskSelected() {
        return selectedTask != null;
    }

    /**
     * Returns the task in this manager's collection that is
     * &quot;currently selected&quot;.
     *
     * @return The currently selected task
     */
    public Task getSelectedTask() {
        return selectedTask;
    }

//    /**
//     * Sorts the collection of tasks using the provided Comparator.
//     */
//    public void sortTasks(Comparator<? super Task> comparator) {
//        if (sortPending) {
//            Collections.sort(tasks, comparator);
//            sortPending = false;
//        }
//    }

    /**
     * Moves the &quot;currently selected&quot; to the previous task in
     * the managed collection of this <code>TaskManager</code>. <br/> The
     * &quot;previous&quot; task will be the task before the
     * currently selected task in the set of tasks (whether
     * ordered or not).
     * <p/>
     * <br/> Because this operation depends on a &quot;currently selected
     * task&quot;, no previous task is considered to exist if
     * there is no &quot;currently selected task.&quot; or it is the
     * first in the set.
     *
     * @return <code>true</code> if selecting the previous task was
     *         successful, <code>false</code> no currently selected task
     *         is set or the currently selected task is the first in the
     *         collection.
     */
    public boolean selectPreviousTask() {
        boolean moveSucceeded = false;
        if (getSelectedTask() != null) {
            int selectedtaskIndex =
                    getTasks().indexOf(getSelectedTask());
            Task prevtask;
            if (selectedtaskIndex > 0 && (prevtask =
                    getTasks().get(selectedtaskIndex - 1)) != null) {
                selectedTask = prevtask;
                moveSucceeded = true;
            }
        }
        return moveSucceeded;
    }

    /**
     * Moves the &quot;currently selected&quot; to the next task in the
     * managed collection of this <code>TaskManager</code>. <br/> The
     * &quot;next&quot; task will be the task after the currently
     * selected task in the set of tasks (whether ordered or
     * not).
     * <p/>
     * <br/> Because this operation depends on a &quot;currently selected
     * task&quot;, no next task is considered to exist if there is
     * no &quot;currently selected task or it is the last in the
     * set.&quot;
     *
     * @return <code>true</code> if selecting the previous task was
     *         successful, <code>false</code> no currently selected task
     *         is set or the currently selected task is the last in the
     *         collection.
     */
    public boolean selectNextTask() {
        boolean moveSucceeded = false;

        if (getSelectedTask() != null) {
            int selectedtaskIndex =
                    getTasks().indexOf(getSelectedTask());
            int lastIndex = getTasks().size() - 1;

            Task nexttask;
            if (selectedtaskIndex < lastIndex &&
                    (nexttask = getTasks().get(selectedtaskIndex + 1)) !=
                            null) {
                selectedTask = nexttask;
                moveSucceeded = true;
            }
        }

        return moveSucceeded;
    }

    /**
     * Resets the &quot;currently selected&quot; task of this manager.
     * <p></p>If this manager has a currently selected task, the
     * task <code>selected</code> property will be set to
     * <code>false</code> and this manager's <code>selectedTask</code>
     * property will be set to <code>null</code>.
     */
    public void resetSelectedTask() {
        if (hasTaskSelected()) {
            //selectedTask.setSelected(false);
            selectedTask = null;
        }
    }

    /**
     * Tells whether the passed <code>task</code> is the same one as the
     * one that is currently selected in this manager.
     *
     * @param task The task to test to be the same as the
     *                    currently selected
     * @return <code>true</code> if there is a currently selected task
     *         and it is equal to the passed <code>task</code>
     */
    public boolean isTheSelectedTask(Task task) {
        return hasTaskSelected() && selectedTask.equals(
                task);
    }

    
	public static final Comparator<Task> TASK_ORDER_COMPARATOR = new Comparator<Task>() {

		@Override
		public int compare(Task arg0, Task arg1) {
			return Integer.valueOf(arg0.getOrder()).compareTo(arg1.getOrder());
		}
		
	};
}
