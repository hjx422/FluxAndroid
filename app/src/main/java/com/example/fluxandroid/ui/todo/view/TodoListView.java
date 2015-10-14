package com.example.fluxandroid.ui.todo.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fluxandroid.R;
import com.example.fluxandroid.creator.TodoCreator;

/**
 * @author hjx
 * @date 10/10/2015
 * @time 11:53
 * @description
 */
public class TodoListView extends RelativeLayout {

    private RecyclerView todoListRv;
    private TextView clearCompletedTv;
    private TextView undoDeletionTv;

    public TodoListView(Context context) {
        super(context);
        init(context);
    }

    public TodoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TodoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_todo_list, this);

        todoListRv = (RecyclerView) findViewById(R.id.rv_list);
        clearCompletedTv = (TextView) findViewById(R.id.tv_clear_completed);
        undoDeletionTv = (TextView) findViewById(R.id.tv_undo_deletion);

        todoListRv.setLayoutManager(new LinearLayoutManager(context));

        clearCompletedTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoCreator.deleteCompleted();
            }
        });
        undoDeletionTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoCreator.undoDeletion();
            }
        });

    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        todoListRv.setAdapter(adapter);
    }

    public void setUndoVisibility(int visibility) {
        undoDeletionTv.setVisibility(visibility);
    }
}
