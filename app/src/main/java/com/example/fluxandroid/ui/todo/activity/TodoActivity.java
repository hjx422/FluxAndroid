package com.example.fluxandroid.ui.todo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.fluxandroid.R;
import com.example.fluxandroid.store.impl.TodoStore;
import com.example.fluxandroid.ui.todo.controller.impl.TodoInputController;
import com.example.fluxandroid.ui.todo.controller.impl.TodoListController;
import com.example.fluxandroid.ui.todo.view.TodoInputView;
import com.example.fluxandroid.ui.todo.view.TodoListView;

/**
 * @author hjx
 * @date 9/30/2015
 * @time 17:32
 * @description
 */
public class TodoActivity extends Activity {

    private TodoInputController todoInputController;
    private TodoListController todoListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        initComponent();
        registerListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterListener();
    }

    private void initComponent() {
        todoInputController = (TodoInputController) findViewById(R.id.todo_input);
        todoListController = (TodoListController) findViewById(R.id.todo_list);
    }

    private void registerListener() {
        TodoStore.getInstance().addChangeListener(todoInputController);
        TodoStore.getInstance().addChangeListener(todoListController);
    }

    private void unregisterListener() {
        TodoStore.getInstance().removeChangeListener(todoInputController);
        TodoStore.getInstance().removeChangeListener(todoListController);
    }
}
