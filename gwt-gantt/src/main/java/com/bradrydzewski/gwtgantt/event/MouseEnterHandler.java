package com.bradrydzewski.gwtgantt.event;

import com.google.gwt.event.shared.EventHandler;

public interface MouseEnterHandler<T> extends EventHandler {

	void onMouseEnter(MouseEnterEvent<T> event);
}
