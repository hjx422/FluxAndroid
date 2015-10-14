package com.example.fluxandroid.store.interfaces;

import com.example.fluxandroid.action.TodoAddAction;
import com.example.fluxandroid.action.TodoDeleteAction;
import com.example.fluxandroid.action.TodoDeleteCompletedAction;
import com.example.fluxandroid.action.TodoLoadAction;
import com.example.fluxandroid.action.TodoUndoDeletionAction;
import com.example.fluxandroid.action.TodoUpdateCompletedAction;
import com.example.fluxandroid.action.TodoUpdateCompletedAllAction;

/**
 * @author hjx
 * @date 10/9/2015
 * @time 17:26
 * @description
 */
public interface ITodoStore {

    public void onEventMainThread(TodoLoadAction action);
    public void onEventMainThread(TodoAddAction action);
    public void onEventMainThread(TodoDeleteAction action);
    public void onEventMainThread(TodoDeleteCompletedAction action);
    public void onEventMainThread(TodoUpdateCompletedAction action);
    public void onEventMainThread(TodoUpdateCompletedAllAction action);
    public void onEventMainThread(TodoUndoDeletionAction action);
}
