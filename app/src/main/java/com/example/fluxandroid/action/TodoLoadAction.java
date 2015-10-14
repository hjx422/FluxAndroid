package com.example.fluxandroid.action;

import com.example.fluxandroid.vo.TodoVo;

import java.util.List;

/**
 * @author hjx
 * @date 10/12/2015
 * @time 20:07
 * @description
 */
public class TodoLoadAction extends BaseAction {
    private List<TodoVo> todos;
    private List<TodoVo> lastDeleted;

    public TodoLoadAction(List<TodoVo> todos, List<TodoVo> lastDeleted) {
        this.todos = todos;
        this.lastDeleted = lastDeleted;
    }

    public List<TodoVo> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoVo> todos) {
        this.todos = todos;
    }

    public List<TodoVo> getLastDeleted() {
        return lastDeleted;
    }

    public void setLastDeleted(List<TodoVo> lastDeleted) {
        this.lastDeleted = lastDeleted;
    }
}
