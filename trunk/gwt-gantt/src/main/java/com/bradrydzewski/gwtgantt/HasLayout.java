package com.bradrydzewski.gwtgantt;

public interface HasLayout {
    
	public void suspendLayout();

    public void resumeLayout();

    public boolean isDirty();

    public void refresh();
    
    public void refresh(boolean force);
}
