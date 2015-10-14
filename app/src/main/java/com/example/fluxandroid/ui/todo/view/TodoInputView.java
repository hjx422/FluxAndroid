package com.example.fluxandroid.ui.todo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fluxandroid.R;
import com.example.fluxandroid.creator.TodoCreator;

/**
 * @author hjx
 * @date 10/9/2015
 * @time 13:48
 * @description
 */
public class TodoInputView extends RelativeLayout {

    private CheckBox completeAllCb;
    private EditText inputEt;
    private TextView addTv;

    public TodoInputView(Context context) {
        super(context);
        init(context);
    }

    public TodoInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TodoInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_todo_input, this);

        completeAllCb = (CheckBox) findViewById(R.id.cb_complete_all);
        inputEt = (EditText) findViewById(R.id.et_input);
        addTv = (TextView) findViewById(R.id.tv_add);

        addTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo();
                resetInput();
            }
        });

        completeAllCb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCompleteAll();
            }
        });
    }

    private void toggleCompleteAll() {
        TodoCreator.toggleCompleteAll();
    }

    private void addTodo() {
        TodoCreator.addTodo(getInputText());
    }

    protected void resetInput() {
        inputEt.setText("");
    }

    protected String getInputText() {
        return inputEt.getText().toString().trim();
    }

    public void setAllCheck(boolean allCompleted) {
        completeAllCb.setChecked(allCompleted);
    }

}
