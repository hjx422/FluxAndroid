package com.example.fluxandroid.dispatcher;

import com.example.fluxandroid.action.BaseAction;

import de.greenrobot.event.EventBus;

/**
 * @author hjx
 * @date 9/29/2015
 * @time 17:39
 * @description
 */
public class Dispatcher {

    private static final EventBus sBus = new EventBus();

    public static void register(final Object subscriber) {
        sBus.register(subscriber);
    }

    public static void unregister(final Object subscriber) {
        sBus.unregister(subscriber);
    }

    public static void dispatch(BaseAction action) {
        if (action == null) {
            throw new IllegalArgumentException("action must not be null");
        }
        sBus.post(action);
    }
}
