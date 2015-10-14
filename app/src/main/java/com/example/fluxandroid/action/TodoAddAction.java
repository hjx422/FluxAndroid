package com.example.fluxandroid.action;

import com.example.fluxandroid.vo.TodoVo;

/**
 * @author hjx
 * @date 10/8/2015
 * @time 17:38
 * @description
 */
public class TodoAddAction extends BaseAction {

    private TodoVo todo;

    public TodoAddAction(TodoVo todo) {
        this.todo = todo;
    }

    public TodoVo getTodo() {
        return todo;
    }

    public void setTodo(TodoVo todo) {
        this.todo = todo;
    }
}
