package com.bradrydzewski.gwtgantt.demo.client;

import com.bradrydzewski.gwtgantt.model.DurationFormat;
import com.bradrydzewski.gwtgantt.model.Task;
import com.bradrydzewski.gwtgantt.model.TaskImpl;
import com.bradrydzewski.gwtgantt.view.TaskGridView;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;

public class EditableTaskGrid extends TaskGridView {

	private TextBox nameTextBox = new TextBox();
	private TaskImpl editingTask = null;
	private int editingColumn = -1;
	private int editingRow = -1;
	
	public EditableTaskGrid() {
		
		//set style for nameTextBox
		nameTextBox.setWidth("100%");
		nameTextBox.setHeight("21px");
		nameTextBox.getElement().getStyle().setPadding(0, Unit.PX);
		nameTextBox.getElement().getStyle().setProperty("border", "0px");
		nameTextBox.getElement().getStyle().setProperty("outlineStyle", "none");
	}
	
	public void bind() {
		nameTextBox.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				editingTask.setName(event.getValue());
			}
		});
	}

	/**
	 * Make sure we mark the editable task
	 * null on refresh.
	 */
	@Override
	public void onBeforeRendering() {
		editingTask = null;
		editingColumn = -1;
		editingRow = -1;
		super.onBeforeRendering();
	}

	/**
	 * Here we make the name editable.
	 */
	public void makeNameEditable(Task task, int row) {
		
		if(editingTask!=null) {
			makeCellReadable(editingTask, editingRow);
		}

		this.editingColumn = 1;
		this.editingRow = row;
		this.editingTask = (TaskImpl)task;
		nameTextBox.setValue(task.getName(),false);
		bodyTable.setWidget(editingRow, editingColumn, nameTextBox);
	}
	
	public void reset() {

		if(editingTask!=null) {
			editingTask.setName(nameTextBox.getValue());
			makeCellReadable(editingTask, editingRow);
		}
		
		editingTask = null;
		editingColumn = -1;
		editingRow = -1;
	}
	
	/**
	 * Here we basically just copy the code for rendering
	 * a Task. Efficient? No, but that's okay when we
	 * are just trying to customize a 3rd party library.
	 */
	private void makeCellReadable(Task task, int row) {

		bodyTable.setHTML(row, 0, "<div>"+String.valueOf(row+1)+"</div>");

		int leftPadding = task.getLevel() * 20 + 5;
		FlowPanel treePanel = new FlowPanel();
		
		if(task.isSummary()) {
			treePanel.add(new CollapseButton(task));
		} else {
			leftPadding += 21; //11 width + 10pxpadding of toggle button + 1 (why +1? I don't know)
		}
		
		treePanel.getElement().getStyle().setPaddingLeft(leftPadding, Unit.PX);
		treePanel.add(new InlineLabel(task.getName()));
		bodyTable.setWidget(row, 1, treePanel);
		
		
		String duration = DurationFormat.format(task.getDurationFormat(), task.getDuration());
		bodyTable.setHTML(row, 2, "<div>"+duration+"</div>");
		bodyTable.setHTML(row, 3, "<div>"+((task.getStart()==null)?"--":DATE_FORMAT.format(task.getStart()))+"</div>");
		bodyTable.setHTML(row, 4, "<div>"+((task.getStart()==null)?"--":DATE_FORMAT.format(task.getFinish()))+"</div>");
		
		bodyTable.getCellFormatter().setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		bodyTable.getCellFormatter().setHorizontalAlignment(row, 3, HasHorizontalAlignment.ALIGN_RIGHT);
		bodyTable.getCellFormatter().setHorizontalAlignment(row, 4, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		//make summary rows bold
		if(task.isSummary())
			bodyTable.getRowFormatter().addStyleName(row, "summaryRow");
	}
	
	public boolean isEditing(Task task, int col) {
		return editingTask!=null && editingTask.equals(task) && this.editingColumn == col;
	}
		
}
