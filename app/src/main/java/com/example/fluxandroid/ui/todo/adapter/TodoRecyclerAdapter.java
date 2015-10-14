package com.example.fluxandroid.ui.todo.adapter;

import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.fluxandroid.R;
import com.example.fluxandroid.creator.TodoCreator;
import com.example.fluxandroid.utils.LogUtil;
import com.example.fluxandroid.vo.TodoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjx
 * @date 10/8/2015
 * @time 16:01
 * @description
 */
public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder> {

    private List<TodoVo> todos;

    public TodoRecyclerAdapter() {
       todos = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_todo_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(todos.get(position));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setItems(List<TodoVo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTv;
        public CheckBox completeCb;
        public Button deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            textTv = (TextView) itemView.findViewById(R.id.tv_text);
            completeCb = (CheckBox) itemView.findViewById(R.id.cb_complete);
            deleteBtn = (Button) itemView.findViewById(R.id.btn_delete);
        }

        public void bindView(final TodoVo todo) {
            if (todo.isCompleted()) {
                SpannableString span = new SpannableString(todo.getText());
                span.setSpan(new StrikethroughSpan(), 0, span.length(), 0);
                textTv.setText(span);
            } else {
                textTv.setText(todo.getText());
            }
            completeCb.setChecked(todo.isCompleted());
            completeCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.v("huangjx", "onclick");
                    TodoCreator.toggleComplete(todo.getId());
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoCreator.deleteTodo(todo.getId());
                }
            });
        }
    }
}
