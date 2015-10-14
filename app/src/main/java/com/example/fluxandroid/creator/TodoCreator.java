package com.example.fluxandroid.creator;

import android.text.TextUtils;

import com.example.fluxandroid.action.TodoAddAction;
import com.example.fluxandroid.action.TodoDeleteAction;
import com.example.fluxandroid.action.TodoDeleteCompletedAction;
import com.example.fluxandroid.action.TodoLoadAction;
import com.example.fluxandroid.action.TodoUndoDeletionAction;
import com.example.fluxandroid.action.TodoUpdateCompletedAction;
import com.example.fluxandroid.action.TodoUpdateCompletedAllAction;
import com.example.fluxandroid.db.dao.TodoDao;
import com.example.fluxandroid.db.po.TodoPo;
import com.example.fluxandroid.dispatcher.Dispatcher;
import com.example.fluxandroid.vo.TodoVo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author hjx
 * @date 9/29/2015
 * @time 17:35
 * @description
 */
public class TodoCreator {

    private static final String TAG = "TodoCreator";

    public static void loadTodos() {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    TodoDao todoDao = TodoDao.getInstance();
                    List<TodoPo> todoPos = todoDao.queryAll();
                    List<TodoVo> todoVos = new ArrayList<TodoVo>();
                    List<TodoVo> lastDeleted = new ArrayList<TodoVo>();

                    if (todoPos == null) {
                        return;
                    }
                    for (TodoPo todoPo : todoPos) {
                        if (todoPo.isLastDeleted()) {
                            lastDeleted.add(createTodoVo(todoPo));
                        } else {
                            todoVos.add(createTodoVo(todoPo));
                        }
                    }
                    Dispatcher.dispatch(new TodoLoadAction(todoVos, lastDeleted));
                } catch (SQLException e) {
                    throw new android.database.SQLException("Load todos failed");
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static void toggleComplete(final long todoId) {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    TodoDao todoDao = TodoDao.getInstance();
                    TodoPo todo = todoDao.query(todoId);
                    if (todo != null) {
                        todo.setCompleted(!todo.isCompleted());
                        todoDao.update(todo);
                        Dispatcher.dispatch(new TodoUpdateCompletedAction(todo.getId(), todo.isCompleted()));
                    }
                } catch (SQLException e) {
                    throw new android.database.SQLException("toggle todo completion failed, todo id: " + todoId);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static void toggleCompleteAll() {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    TodoDao todoDao = TodoDao.getInstance();
                    List<TodoPo> todos = todoDao.queryUnCompleted();
                    if (todos == null) {
                        return;
                    }
                    if (todos.size() > 0) {
                        todoDao.updateAllCompleted(true);
                        Dispatcher.dispatch(new TodoUpdateCompletedAllAction(true));
                    } else {
                        todoDao.updateAllCompleted(false);
                        Dispatcher.dispatch(new TodoUpdateCompletedAllAction(false));
                    }
                } catch (SQLException e) {
                    throw new android.database.SQLException("toggle all todo completion failed");
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static void addTodo(final String text) {
        Observable.create(new Observable.OnSubscribe<TodoVo>() {
            @Override
            public void call(Subscriber<? super TodoVo> subscriber) {
                try {
                    String txt = text.trim();
                    if (TextUtils.isEmpty(txt)) {
                        return;
                    }
                    TodoDao todoDao = TodoDao.getInstance();
                    TodoPo todo = new TodoPo();
                    todo.setText(text);
                    todoDao.add(todo);
                    TodoVo todoVo = createTodoVo(todo);
//                    ReflcetUtil.merge(todo, todoVo);
                    Dispatcher.dispatch(new TodoAddAction(todoVo));
                } catch (SQLException e) {
                    throw new android.database.SQLException("add todo failed");
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static void deleteTodo(final long todoId) {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    TodoDao todoDao = TodoDao.getInstance();
                    todoDao.deleteById(todoId);
                    Dispatcher.dispatch(new TodoDeleteAction(todoId));
                } catch (SQLException e) {
                    throw new android.database.SQLException("delete todo failed");
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static void deleteCompleted() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    TodoDao todoDao = TodoDao.getInstance();
                    todoDao.deleteCompleted();
                    Dispatcher.dispatch(new TodoDeleteCompletedAction());
                } catch (SQLException e) {
                    throw new android.database.SQLException("toggle all todo completion failed");
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }

    public static void undoDeletion() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    TodoDao todoDao = TodoDao.getInstance();
                    todoDao.recoveryLastDeleted();
                    Dispatcher.dispatch(new TodoUndoDeletionAction());
                } catch (SQLException e) {
                    throw new android.database.SQLException("undo last deleted todo failed");
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    private static TodoVo createTodoVo(TodoPo todoPo) {
        TodoVo todoVo = new TodoVo();
        todoVo.setId(todoPo.getId());
        todoVo.setText(todoPo.getText());
        todoVo.setCompleted(todoPo.isCompleted());
        todoVo.setCreateTime(todoPo.getCreateTime());
        return todoVo;
    }

    private static List<TodoVo> createTodoVos(List<TodoPo> todoPos) {
        if (todoPos == null) {
            return null;
        }
        List<TodoVo> todoVos = new ArrayList<>();
        for (TodoPo po : todoPos) {
            todoVos.add(createTodoVo(po));
        }
        return todoVos;
    }
}
