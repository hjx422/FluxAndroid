package com.example.fluxandroid.action;

/**
 * @author hjx
 * @date 10/12/2015
 * @time 18:40
 * @description
 */
public class TodoUpdateCompletedAllAction extends BaseAction {

    private boolean completed;

    public TodoUpdateCompletedAllAction(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }
}
