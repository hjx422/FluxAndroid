package com.example.fluxandroid.action;

/**
 * @author hjx
 * @date 10/12/2015
 * @time 18:42
 * @description
 */
public class TodoUpdateCompletedAction extends BaseAction {

    private long id;
    private boolean completed;

    public TodoUpdateCompletedAction(long id, boolean completed) {
        this.id = id;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public boolean isCompleted() {
        return completed;
    }
}
