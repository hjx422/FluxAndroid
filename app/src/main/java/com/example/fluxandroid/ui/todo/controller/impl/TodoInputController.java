package com.example.fluxandroid.ui.todo.controller.impl;

import android.content.Context;
import android.util.AttributeSet;

import com.example.fluxandroid.store.event.StoreChangeEvents;
import com.example.fluxandroid.store.impl.TodoStore;
import com.example.fluxandroid.ui.todo.controller.interfaces.ITodoInputController;
import com.example.fluxandroid.ui.todo.view.TodoInputView;
import com.example.fluxandroid.utils.LogUtil;

/**
 * @author hjx
 * @date 10/9/2015
 * @time 13:55
 * @description
 */
public class TodoInputController extends TodoInputView implements ITodoInputController {

    public TodoInputController(Context context) {
        super(context);
        init(context);
    }

    public TodoInputController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TodoInputController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        updateUI();
    }

    @Override
    public void onEventMainThread(StoreChangeEvents.TodoEvent event) {
        LogUtil.v("TodoStore", "TodoInputController");
        updateUI();
    }

    private void updateUI() {
        resetInput();
        TodoStore todoStore = TodoStore.getInstance();
        boolean checked = todoStore.getTodos().size() != 0 && todoStore.areAllCompleted();
        setAllCheck(checked);
    }
}
