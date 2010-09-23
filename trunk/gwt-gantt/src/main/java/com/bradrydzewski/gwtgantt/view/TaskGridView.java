package com.bradrydzewski.gwtgantt.view;

import java.util.ArrayList;

import com.bradrydzewski.gwtgantt.TaskPresenter;
import com.bradrydzewski.gwtgantt.model.DurationFormat;
import com.bradrydzewski.gwtgantt.model.Task;
import com.bradrydzewski.gwtgantt.presenter.TaskGridPresenter.Display;
import com.bradrydzewski.gwtgantt.resources.GridResources;
import com.bradrydzewski.gwtgantt.widget.override.FlexTable;
import com.bradrydzewski.gwtgantt.widget.override.Grid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class TaskGridView extends Composite implements Display {

	public class CollapseButton extends ToggleButton {
		private Task item;
		public CollapseButton(Task item) {
			super(new Image(GridResources.INSTANCE.collapseArrowImage()), new Image(GridResources.INSTANCE.expandArrowImage()));
			this.item = item;
			this.setDown(item.isCollapsed());
			getElement().getStyle().setDisplay(
					com.google.gwt.dom.client.Style.Display.INLINE);
		}
		@Override
		public void onClick() {
		    super.onClick();
		    boolean collapsed = !item.isCollapsed();
		    item.setCollapsed(collapsed);
		    
		    if(collapsed) presenter.onItemCollapse(item);
		    else presenter.onItemExpand(item);
		}
	}
	
	public class HeaderTable extends Grid {
		
		public HeaderTable(int rows, int cols) {
			super(rows, cols);
			this.sinkEvents(Event.ONMOUSEMOVE | Event.ONMOUSEDOWN
			        | Event.ONMOUSEUP | Event.ONCLICK | Event.ONMOUSEOUT);
		}
		
		@Override
		  public void onBrowserEvent(Event event) {
		    super.onBrowserEvent(event);
		    switch (DOM.eventGetType(event)) {
		      case Event.ONMOUSEDOWN:
		        // Start resizing a header column
		        if (DOM.eventGetButton(event) != Event.BUTTON_LEFT) {
		          return;
		        }
		        if (resizeWorker.getCurrentCell() != null) {
		          DOM.eventPreventDefault(event);
		          DOM.eventCancelBubble(event, true);
		          resizeWorker.startResizing(event);
		        }
		        break;
		      case Event.ONMOUSEUP:
		        if (DOM.eventGetButton(event) != Event.BUTTON_LEFT) {
		          return;
		        }
		        // Stop resizing the header column
		        if (resizeWorker.isResizing()) {
		          resizeWorker.stopResizing(event);
		        }
		        break;
		      case Event.ONMOUSEMOVE:
		        // Resize the header column
		        if (resizeWorker.isResizing()) {
		          resizeWorker.resizeColumn(event);
		        } else {
		          resizeWorker.setCurrentCell(event);
		        }
		        break;
		      case Event.ONMOUSEOUT:
		        break;
		    }
		  }
	}
	
	public class ColumnResizeWorker {

	    /**
	     * The current header cell that the mouse is affecting.
	     */
	    private Element curCell = null;

	    /**
	     * The index of the current header cell.
	     */
	    private int curCellIndex = 0;

	    /**
	     * The current x position of the mouse.
	     */
	    private int mouseXCurrent = 0;

	    /**
	     * The last x position of the mouse when we resized.
	     */
	    private int mouseXLast = 0;

	    /**
	     * The starting x position of the mouse when resizing a column.
	     */
	    private int mouseXStart = 0;

	    /**
	     * A timer used to resize the columns. As long as the timer is active, it
	     * will poll for the new row size and resize the columns.
	     */
	    private Timer resizeTimer = new Timer() {
	      @Override
	      public void run() {
	        resizeColumn();
	        schedule(100);
	      }
	    };

	    /**
	     * A boolean indicating that we are resizing the
	     * current header cell.
	     */
	    private boolean resizing = false;

	    /**
	     * Returns the current cell.
	     * 
	     * @return the current cell
	     */
	    public Element getCurrentCell() {
	      return curCell;
	    }

	    /**
	     * Returns true if a header is currently being resized.
	     * 
	     * @return true if resizing
	     */
	    public boolean isResizing() {
	      return resizing;
	    }

	    /**
	     * Resize the column on a mouse event. This method also marks the client as
	     * busy so we do not try to change the size repeatedly.
	     * 
	     * @param event the mouse event
	     */
	    public void resizeColumn(Event event) {
	      mouseXCurrent = DOM.eventGetClientX(event);
	    }

	    /**
	     * Set the current cell that will be resized based on the mouse event.
	     * 
	     * @param event the event that triggered the new cell
	     * @return true if the cell was actually changed
	     */
	    public boolean setCurrentCell(Event event) {
	      // Check the resize policy of the table
	      Element cell = null;
	      cell = headerTable.getEventTargetCell(event);
	      

	      // See if we are near the edge of the cell
	      int clientX = DOM.eventGetClientX(event);
	      if (cell != null) {
	        int absRight = DOM.getAbsoluteLeft(cell)
	            + DOM.getElementPropertyInt(cell, "offsetWidth");
	        if (clientX < absRight - 15 || clientX > absRight) {
	          cell = null;
	        }
	      }

	      // Change out the current cell
	      if (cell != curCell) {
	        // Clear the old cell
	        if (curCell != null) {
	          DOM.setStyleAttribute(curCell, "cursor", "");
	        }

	        // Set the new cell
	        curCell = cell;
	        if (curCell != null) {
	          curCellIndex = getCellIndex(curCell);
	          if (curCellIndex < 1) { //set to 1, since column 0 is not draggable
	            curCell = null;
	            return false;
	          }
	          DOM.setStyleAttribute(curCell, "cursor", "e-resize");
	        }
	        return true;
	      }

	      // The cell did not change
	      return false;
	    }

	    /**
	     * Start resizing the current cell when the user clicks on the right edge of
	     * the cell.
	     * 
	     * @param event the mouse event
	     */
	    public void startResizing(Event event) {
	      if (curCell != null) {
	        resizing = true;
	        mouseXStart = DOM.eventGetClientX(event);
	        mouseXLast = mouseXStart;
	        mouseXCurrent = mouseXStart;
	        // Start the timer and listen for changes
	        DOM.setCapture(headerTable.getElement());//TODO: may want to set to header panel instead of table
	        resizeTimer.schedule(5);//20
	      }
	    }

	    /**
	     * Stop resizing the current cell.
	     * 
	     * @param event the mouse event
	     */
	    public void stopResizing(Event event) {
	      if (curCell != null && resizing) {
	        resizing = false;
	        DOM.releaseCapture(headerTable.getElement()); //TODO: may need to use header panel here
	        resizeTimer.cancel();
	        resizeColumn();
	      }
	    }

	    /**
	     * Get the actual cell index of a cell in the header table.
	     * 
	     * @param cell the cell element
	     * @return the cell index
	     */
	    private int getCellIndex(Element cell) {
	    	return DOM.getChildIndex(DOM.getParent(cell), cell);
	    }

	    /**
	     * Helper method that actually sets the column size. This method is called
	     * periodically by a timer.
	     */
	    private void resizeColumn() {
	    	
	      if (mouseXLast != mouseXCurrent) {
	        
	        int currWidth = curCell.getClientWidth()-1;
	        int newWidth = currWidth + (mouseXCurrent-mouseXLast);
	        TaskGridView.this.resizeColumn(curCellIndex, newWidth);
	        mouseXLast = mouseXCurrent;
	      }
	    }
	}
	
	
	

	private ColumnResizeWorker resizeWorker = new ColumnResizeWorker();
	private FlowPanel root = new FlowPanel();
	private FlowPanel headerPanel = new FlowPanel();
	protected HeaderTable headerTable = null;// new HeaderTable(1,6);
	private ScrollPanel bodyPanel = new ScrollPanel();
	protected FlexTable bodyTable = new FlexTable();
	private TaskPresenter presenter;
	
	private int[] columnWidthArray = new int[]{40, 200,80,110,110,100};
	private String[] columnNameArray = new String[]{"&nbsp;","Task Name","Duration","Start","Finish","Predecessors"};
	
	/**
	 * Cached list of visible Tasks.
	 */
	private ArrayList<Task> visibleItems = new ArrayList<Task>();
	
	public TaskGridView() {
		initWidget(root);
		
		//load table css
		GridResources.INSTANCE.style().ensureInjected();
		
		//set root style
		root.setStyleName("treeTable");

		//create header panel, docked north
		headerPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);
		headerPanel.setStyleName("treeTableHeader");
		root.add(headerPanel);

		//create body panel, docked center
		bodyPanel.setAlwaysShowScrollBars(false);
		bodyPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);
		bodyPanel.setStyleName("treeTableBody");
		bodyPanel.getElement().getStyle().setProperty("overflowX", "scroll");
		bodyPanel.getElement().getStyle().setProperty("overflowY", "hidden");
		bodyTable.setCellPadding(0);
		bodyTable.setCellSpacing(0);
		bodyPanel.add(bodyTable);
		root.add(bodyPanel);

		//add event handlers for scrolling, clicking, etc
		bodyPanel.addScrollHandler(new ScrollHandler(){
			@Override
			public void onScroll(ScrollEvent event) {
				int x=bodyPanel.getHorizontalScrollPosition();
				int y=bodyPanel.getScrollPosition();
				headerTable.getElement().getStyle().setLeft(x*-1, Unit.PX);
				presenter.onScroll(bodyPanel.getHorizontalScrollPosition(),y);
			}
		});
		bodyTable.addDoubleClickHandler(new DoubleClickHandler(){
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				int row = bodyTable.getCellForEvent(event).getRowIndex();
				presenter.onItemDoubleClicked(visibleItems.get(row));
			}
		});
		bodyTable.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				int row = bodyTable.getCellForEvent(event).getRowIndex();
				GWT.log("Row "+row+" clicked in the TaskGridDisplay");
				presenter.onItemClicked(visibleItems.get(row));
			}
		});
	}
	
	@Override
	public void bind(TaskPresenter presenter) {
		this.presenter = presenter;
	}

	public static final DateTimeFormat DATE_FORMAT =
		DateTimeFormat.getFormat("EEE M/d/yy");
//	    DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
	
	//TODO: I'm thinking we can render each column separately
	@Override
	public void renderTask(Task task, int row, int rowOffset) {
		
		bodyTable.setHTML(rowOffset, 0, "<div>"+String.valueOf(row+1)+"</div>");

		int leftPadding = task.getLevel() * 20 + 5;
		FlowPanel treePanel = new FlowPanel();
		
		if(task.isSummary()) {
			treePanel.add(new CollapseButton(task));
		} else {
			leftPadding += 21; //11 width + 10pxpadding of toggle button + 1 (why +1? I don't know)
		}
		
		treePanel.getElement().getStyle().setPaddingLeft(leftPadding, Unit.PX);
		treePanel.add(new InlineLabel(task.getName()));
		bodyTable.setWidget(rowOffset, 1, treePanel);
		
		
		String duration = DurationFormat.format(task.getDurationFormat(), task.getDuration());
		bodyTable.setHTML(rowOffset, 2, "<div>"+duration+"</div>");
		bodyTable.setHTML(rowOffset, 3, "<div>"+((task.getStart()==null)?"--":DATE_FORMAT.format(task.getStart()))+"</div>");
		bodyTable.setHTML(rowOffset, 4, "<div>"+((task.getStart()==null)?"--":DATE_FORMAT.format(task.getFinish()))+"</div>");
		
		bodyTable.getCellFormatter().setHorizontalAlignment(rowOffset, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		bodyTable.getCellFormatter().setHorizontalAlignment(rowOffset, 3, HasHorizontalAlignment.ALIGN_RIGHT);
		bodyTable.getCellFormatter().setHorizontalAlignment(rowOffset, 4, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		//make summary rows bold
		if(task.isSummary())
			bodyTable.getRowFormatter().addStyleName(rowOffset, "summaryRow");
		
		//add task to list of visible tasks
		//TODO: Move visibleItems list to the View??
		visibleItems.add(task);
	}

        @Override
        public void renderPredecessor(String text, int row, int rowOffset) {
            bodyTable.setHTML(rowOffset, 5, "<div>"+text+"</div>");
        }

	@Override
	public void onBeforeRendering() {
		//draw the header if necessary
		if(headerTable==null)
			drawHeader();
		//clear the table
		bodyTable.clear();
		bodyTable.removeAllRows();
		//clear the cache of visible items
		visibleItems.clear();
	}

	@Override
	public void onAfterRendering() {
		//set the default value for each column
		if(visibleItems.size()>0) {
			//set the appropriate size for each row
			for(int i=0;i<columnWidthArray.length;i++) {
				bodyTable.getCellFormatter().setWidth(0, i, columnWidthArray[i]+"px");
			}
		}
		
	}
	
	void drawHeader() {
		if(headerTable!=null && headerTable.getParent()!=null) {
			headerTable.removeFromParent();
		}
		
		headerTable = new HeaderTable(1,columnWidthArray.length);
		headerTable.setCellPadding(0);
		headerTable.setCellSpacing(0);
		headerPanel.add(headerTable);

		//set the appropriate size for each row
		for(int i=0;i<columnWidthArray.length;i++) {
			headerTable.setHTML(0, i, "<div>"+columnNameArray[i]+"</div>");
			headerTable.getCellFormatter().setWidth(0, i, columnWidthArray[i]+"px");
			headerTable.getCellFormatter().setVerticalAlignment(0, i, HasVerticalAlignment.ALIGN_TOP);
		}
	}

	@Override
	public void doTaskSelected(Task task) {
		int row = visibleItems.indexOf(task);
		if(row>-1)
			bodyTable.getRowFormatter().addStyleName(row, "selectedRow");
	}

	@Override
	public void doTaskDeselected(Task task) {
		int row = visibleItems.indexOf(task);
		if(row>-1)
			bodyTable.getRowFormatter().removeStyleName(row, "selectedRow");
	}

	@Override
	public void doScroll(int x, int y) {
		bodyPanel.setScrollPosition(y);
//		bodyPanel.setHorizontalScrollPosition(x);
//		headerTable.getElement().getStyle().setLeft(x*-1, Unit.PX);
	}

	
	void resizeColumn(int columnIndex, int width) {
		
		if(columnIndex==0)
			return;
		
		//store the width
		columnWidthArray[columnIndex] = width;
		//update the table
		headerTable.getCellFormatter().setWidth(
				0, columnIndex, width+"px");
		bodyTable.getCellFormatter().setWidth(
				0, columnIndex, width+"px");
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}


}
