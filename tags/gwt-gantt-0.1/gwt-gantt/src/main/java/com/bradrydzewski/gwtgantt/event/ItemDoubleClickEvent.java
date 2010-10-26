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

public class ItemDoubleClickEvent<T> extends GwtEvent<ItemDoubleClickHandler<T>> {

	/**
	 * Handler type.
	 */
	private static Type<ItemDoubleClickHandler<?>> TYPE;

	/**
	 * Item that was clicked.
	 */
	private final T item;

	public ItemDoubleClickEvent(T item) {
		this.item = item;
	}

	public static <T> void fire(HasItemDoubleClickHandlers<T> source, T item) {
		if (TYPE != null) {
			ItemDoubleClickEvent<T> event = new ItemDoubleClickEvent<T>(item);
			source.fireEvent(event);
		}
	}

	public T getItem() {
		return item;
	}

	/**
	 * Gets the type associated with this event.
	 * 
	 * @return returns the handler type
	 */
	public static Type<ItemDoubleClickHandler<?>> getType() {
		if (TYPE == null) {
			TYPE = new Type<ItemDoubleClickHandler<?>>();
		}
		return TYPE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public final Type<ItemDoubleClickHandler<T>> getAssociatedType() {
		return (Type) TYPE;
	}

	// Because of type erasure, our static type is
	// wild carded, yet the "real" type should use our I param.

	@Override
	protected void dispatch(ItemDoubleClickHandler<T> handler) {
		handler.onItemDoubleClick(this);
	}

}
