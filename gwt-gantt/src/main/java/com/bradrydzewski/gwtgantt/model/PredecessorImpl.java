package com.bradrydzewski.gwtgantt.model;

public class PredecessorImpl implements Predecessor {

    /**
     * The unique identifier of the predecessor task.
     */
    private int UID;
    /**
     * The predecessor type. values are Start to Start, Start to Finish,
     * Finish to Finish and Finish to Start.
     */
    private PredecessorType type;

    public PredecessorImpl() {
    }

    public PredecessorImpl(int UID, PredecessorType type) {
        super();
        this.UID = UID;
        this.type = type;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public PredecessorType getType() {
        return type;
    }

    public void setType(PredecessorType type) {
        this.type = type;
    }
}
