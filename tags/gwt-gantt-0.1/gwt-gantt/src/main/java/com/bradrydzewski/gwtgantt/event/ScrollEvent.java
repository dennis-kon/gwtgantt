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
package com.bradrydzewski.gwtgantt.event;

import com.google.gwt.event.shared.GwtEvent;

public class ScrollEvent extends GwtEvent<ScrollHandler> {

	/**
	 * Handler type.
	 */
	private static Type<ScrollHandler> TYPE;

	private final int x;
	private final int y;
	
	public ScrollEvent(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public static  void fire(HasScrollHandlers source, int x, int y) {
		if (TYPE != null) {
			ScrollEvent event = new ScrollEvent(x, y);
			source.fireEvent(event);
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	/**
	 * Gets the type associated with this event.
	 * 
	 * @return returns the handler type
	 */
	public static Type<ScrollHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ScrollHandler>();
		}
		return TYPE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public final Type<ScrollHandler> getAssociatedType() {
		return (Type) TYPE;
	}

	// Because of type erasure, our static type is
	// wild carded, yet the "real" type should use our I param.

	@Override
	protected void dispatch(ScrollHandler handler) {
		handler.onScroll(this);
	}

}
