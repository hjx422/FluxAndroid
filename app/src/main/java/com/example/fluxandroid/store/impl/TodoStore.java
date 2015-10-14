package com.example.fluxandroid.store.impl;

import com.example.fluxandroid.action.TodoAddAction;
import com.example.fluxandroid.action.TodoDeleteAction;
import com.example.fluxandroid.action.TodoDeleteCompletedAction;
import com.example.fluxandroid.action.TodoLoadAction;
import com.example.fluxandroid.action.TodoUndoDeletionAction;
import com.example.fluxandroid.action.TodoUpdateCompletedAction;
import com.example.fluxandroid.action.TodoUpdateCompletedAllAction;
import com.example.fluxandroid.store.BaseStore;
import com.example.fluxandroid.store.event.StoreChangeEvents;
import com.example.fluxandroid.store.interfaces.ITodoStore;
import com.example.fluxandroid.utils.LogUtil;
import com.example.fluxandroid.vo.TodoVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author hjx
 * @date 9/30/2015
 * @time 09:38
 * @description
 */
public class TodoStore
        extends BaseStore<StoreChangeEvents.TodoEvent>
        implements ITodoStore {

    private static final String TAG = "TodoStore";

    private static TodoStore instance = new TodoStore();

    private List<TodoVo> todos;
    private List<TodoVo> lastDeleted;

    private TodoStore() {
        todos = new ArrayList<>();
        lastDeleted = new ArrayList<>();
    }

    public static TodoStore getInstance() {
        return instance;
    }

    public List<TodoVo> getTodos() {
        return todos;
    }

    public boolean canUndo() {
        return (lastDeleted != null && lastDeleted.size() > 0);
    }

    @Override
    public void onEventMainThread(TodoLoadAction action) {
        this.todos = action.getTodos();
        this.lastDeleted = action.getLastDeleted();
        emitChange(new StoreChangeEvents.TodoEvent());
    }

    @Override
    public void onEventMainThread(TodoAddAction action) {
        LogUtil.v(TAG, "Add");
        TodoVo todo = action.getTodo();
        if (todo != null) {
            add(todo);
            emitChange(new StoreChangeEvents.TodoEvent());
        }
    }

    @Override
    public void onEventMainThread(TodoDeleteAction action) {
        delete(action.getId());
        emitChange(new StoreChangeEvents.TodoEvent());
    }

    @Override
    public void onEventMainThread(TodoDeleteCompletedAction action) {
        deleteCompleted();
        emitChange(new StoreChangeEvents.TodoEvent());
    }

    @Override
    public void onEventMainThread(TodoUpdateCompletedAction action) {
        updateCompleted(action.getId(), action.isCompleted());
        emitChange(new StoreChangeEvents.TodoEvent());
    }

    @Override
    public void onEventMainThread(TodoUpdateCompletedAllAction action) {
        updateAllCompleted(action.isCompleted());
        emitChange(new StoreChangeEvents.TodoEvent());
    }

    @Override
    public void onEventMainThread(TodoUndoDeletionAction action) {
        UndoDeletion();
        emitChange(new StoreChangeEvents.TodoEvent());
    }

    private void UndoDeletion() {
        if (!canUndo()) {
            return;
        }
        for (TodoVo todo : lastDeleted) {
            todos.add(todo);
        }
        lastDeleted.clear();
        Collections.sort(todos);
    }

    private void toggleCompleted(long id) {
        TodoVo todo = get(id);
        if (todo != null) {
            todo.setCompleted(!todo.isCompleted());
        }
    }

    private void toggleAllCompleted() {
        boolean toggle = !areAllCompleted();
        for (TodoVo todo : todos) {
            todo.setCompleted(toggle);
        }
    }

    private void updateCompleted(long id, boolean completed) {
        TodoVo todo = get(id);
        if (todo != null) {
            todo.setCompleted(completed);
        }
    }

    private void updateAllCompleted(boolean completed) {
        for (TodoVo todo : todos) {
            todo.setCompleted(completed);
        }
    }

    public boolean areAllCompleted() {
        for (TodoVo todo : todos) {
            if (!todo.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    private TodoVo get(long id) {
        Iterator<TodoVo> it = todos.iterator();
        while (it.hasNext()) {
            TodoVo todo = it.next();
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }

//    private void create(String text) {
//        long id = System.currentTimeMillis();
//        TodoVo todo = new TodoVo(id, text);
//        add(todo);
//    }

    private void add(TodoVo todo) {
        todos.add(todo);
        Collections.sort(todos);
    }

    private void delete(long id) {
        lastDeleted.clear();
        Iterator<TodoVo> it = todos.iterator();
        while (it.hasNext()) {
            TodoVo todo = it.next();
            if (todo.getId() == id) {
                lastDeleted.add(todo);
                it.remove();
                break;
            }
        }
    }

    private void deleteCompleted() {
        lastDeleted.clear();
        Iterator<TodoVo> it = todos.iterator();
        while (it.hasNext()) {
            TodoVo todo = it.next();
            if (todo.isCompleted()) {
                lastDeleted.add(todo);
                it.remove();
            }
        }
    }
}
