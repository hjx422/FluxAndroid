package com.example.fluxandroid.store;

import com.example.fluxandroid.store.event.BaseStoreChangeEvent;

import de.greenrobot.event.EventBus;

/**
 * @author hjx
 * @date 9/29/2015
 * @time 18:16
 * @description
 */
public class BaseStore<T extends BaseStoreChangeEvent> {

    private EventBus mBus;

    public BaseStore() {
        mBus = new EventBus();
    }

    protected void emitChange(T event) {
        mBus.post(event);
    }

    public void addChangeListener(Object listener) {
        mBus.register(listener);
    }

    public void removeChangeListener(Object listener) {
        mBus.unregister(listener);
    }
}
