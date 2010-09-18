package com.bradrydzewski.gwtgantt;

import java.util.List;

public interface ItemPresenter<T> {

	public void refresh();
	public void sortItems(List<T> itemList);
    public int getHorizontalScrollPosition();
    public int getVerticalScrollPosition();
    
    public void onItemClicked(T item);
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
