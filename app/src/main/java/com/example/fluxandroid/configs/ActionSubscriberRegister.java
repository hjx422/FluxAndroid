package com.example.fluxandroid.configs;

import com.example.fluxandroid.dispatcher.Dispatcher;
import com.example.fluxandroid.store.impl.TodoStore;

/**
 * @author hjx
 * @date 9/30/2015
 * @time 17:08
 * @description
 */
public class ActionSubscriberRegister {

    public static void register() {
        Dispatcher.register(TodoStore.getInstance());
    }
}
