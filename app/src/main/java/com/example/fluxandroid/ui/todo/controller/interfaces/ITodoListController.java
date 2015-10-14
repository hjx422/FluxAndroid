package com.example.fluxandroid.ui.todo.controller.interfaces;

import com.example.fluxandroid.store.event.StoreChangeEvents;

/**
 * @author hjx
 * @date 10/10/2015
 * @time 11:16
 * @description
 */
public interface ITodoListController {
    public void onEventMainThread(StoreChangeEvents.TodoEvent event);
}
