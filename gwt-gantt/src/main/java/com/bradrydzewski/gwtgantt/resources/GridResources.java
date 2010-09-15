package com.bradrydzewski.gwtgantt.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface GridResources extends ClientBundle {
	
	public static final GridResources INSTANCE =  GWT.create(GridResources.class);
	
	@Source("Grid.css")
	GridStyle style();
	
	public interface GridStyle extends CssResource {
		
	}
}
