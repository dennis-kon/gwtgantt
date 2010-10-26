package com.bradrydzewski.gwtgantt;

import java.util.List;

import com.bradrydzewski.gwtgantt.geometry.Point;

/**
 * Defines operations for components responsible for
 * producing and updating rendering descriptions based on
 * a set of items.
 *
 * Implementations are responsible for figuring out how something should
 * be rendered without actually drawing it to the screen by producing
 * a more abstract description of how elements should be drawn.
 *
 * Methods in this interface are grouped depending on the expected clients.
 *
 * <ol>
 *    <li><code>refresh</code>, <code>sortItems</code>, <code>getHorizontalScrollPosition</code>
 *    and <code>getVerticalScrollPosition</code>; miscellaneous methods to update the
 *    rendering representation or query it.</li>
 *    <li><code>do[...]</code> methods are direct invocations of client code
 *    whenever logic knows for sure the rendered view needs to be updated</li>
 *    <li><code>on[...]</code> methods are invoked
 * </ol>
 *
 *
 * @param <T>
 */
public interface ItemView<T> {

	public void refresh();
	public void sortItems(List<T> itemList);
    public int getHorizontalScrollPosition();
    public int getVerticalScrollPosition();
    
    public void onItemClicked(T item, Point click);
    public void onItemDoubleClicked(T item);
    public void onItemMouseOver(T item);
    public void onItemMouseOut(T item);
    public void onItemExpand(T item);
    public void onItemCollapse(T item);
    public void onScroll(int x, int y);

	public void doItemSelected(T item);
	public void doItemDeselected(T item);
	public void doItemEnter(T item);
	public void doItemExit(T item);
	public void doScroll(int x, int y);
	public void doScrollToItem(T item);
	
}

