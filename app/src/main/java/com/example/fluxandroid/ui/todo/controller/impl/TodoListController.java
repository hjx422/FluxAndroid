package com.example.fluxandroid.ui.todo.controller.impl;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.example.fluxandroid.creator.TodoCreator;
import com.example.fluxandroid.store.event.StoreChangeEvents;
import com.example.fluxandroid.store.impl.TodoStore;
import com.example.fluxandroid.ui.todo.adapter.TodoRecyclerAdapter;
import com.example.fluxandroid.ui.todo.controller.interfaces.ITodoListController;
import com.example.fluxandroid.ui.todo.view.TodoListView;
import com.example.fluxandroid.utils.LogUtil;

/**
 * @author hjx
 * @date 10/10/2015
 * @time 11:21
 * @description
 */
public class TodoListController extends TodoListView implements ITodoListController {

    private TodoRecyclerAdapter listAdapter;

    public TodoListController(Context context) {
        super(context);
        init(context);
    }

    public TodoListController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TodoListController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        listAdapter = new TodoRecyclerAdapter();
        setAdapter(listAdapter);
        updateUI();
    }

    private void updateUI() {
        TodoStore todoStore = TodoStore.getInstance();
        listAdapter.setItems(todoStore.getTodos());
        if (todoStore.canUndo()) {
            setUndoVisibility(View.VISIBLE);
        } else {
            setUndoVisibility(View.GONE);
        }
    }

    @Override
    public void onEventMainThread(StoreChangeEvents.TodoEvent event) {
        LogUtil.v("TodoStore", "TodoListController");
        updateUI();
    }
}
