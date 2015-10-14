package com.example.fluxandroid.action;

/**
 * @author hjx
 * @date 10/9/2015
 * @time 10:21
 * @description
 */
public class TodoDeleteAction extends BaseAction {
    private long id;

    public TodoDeleteAction(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
