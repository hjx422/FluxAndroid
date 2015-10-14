package com.example.fluxandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.fluxandroid.configs.ActionSubscriberRegister;
import com.example.fluxandroid.creator.TodoCreator;

/**
 * @author hjx
 * @date 9/30/2015
 * @time 16:10
 * @description
 */
public class App extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        ActionSubscriberRegister.register();
        TodoCreator.loadTodos();
        Log.v("app", "config");
    }

    public static Context getAppContext() {
        return appContext;
    }
}
