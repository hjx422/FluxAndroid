package com.example.fluxandroid.ui.todo.controller.interfaces;

import com.example.fluxandroid.store.event.StoreChangeEvents;

/**
 * @author hjx
 * @date 10/9/2015
 * @time 13:49
 * @description
 */
public interface ITodoInputController {

    public void onEventMainThread(StoreChangeEvents.TodoEvent event);
}
