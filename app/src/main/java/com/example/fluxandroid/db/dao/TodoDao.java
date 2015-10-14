package com.example.fluxandroid.db.dao;

import android.content.Context;

import com.example.fluxandroid.db.po.TodoPo;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * @author hjx
 * @date 10/12/2015
 * @time 14:51
 * @description
 */
public class TodoDao extends BaseDao<TodoPo> {

    private static TodoDao instance;

    private TodoDao() throws SQLException {
        super();
    }

    public static synchronized TodoDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new TodoDao();
        }
        return instance;
    }

    public int add(TodoPo model) throws SQLException {
        model.setCompleted(false);
        model.setLastDeleted(false);
        return super.add(model);
    }

    @Override
    public int delete(TodoPo model) throws SQLException {
        resetLastDeleted();
        super.delete(model);
        UpdateBuilder builder = dao.updateBuilder();
        builder.where().eq(TodoPo.DELETED, true);
        builder.updateColumnValue(TodoPo.LAST_DELETED, true);
        return builder.update();
    }

    @Override
    public int deleteById(Long id) throws SQLException {
        resetLastDeleted();
        super.deleteById(id);
        UpdateBuilder builder = dao.updateBuilder();
        builder.where().eq(TodoPo.DELETED, true);
        builder.updateColumnValue(TodoPo.LAST_DELETED, true);
        return builder.update();
    }

    public int deleteCompleted() throws SQLException {
        resetLastDeleted();
        UpdateBuilder builder = dao.updateBuilder();
        builder.where().eq(TodoPo.COMPLETED, true);
        builder.updateColumnValue(TodoPo.DELETED, true).updateColumnValue(TodoPo.LAST_DELETED, true);
        return builder.update();
    }

    public int resetLastDeleted() throws SQLException {
        UpdateBuilder builder = dao.updateBuilder();
        builder.where().eq(TodoPo.LAST_DELETED, true);
        builder.updateColumnValue(TodoPo.LAST_DELETED, false);
        return builder.update();
    }

    public int recoveryLastDeleted() throws SQLException {
        UpdateBuilder builder = dao.updateBuilder();
        builder.where().eq(TodoPo.LAST_DELETED, true);
        builder.updateColumnValue(TodoPo.DELETED, false)
            .updateColumnValue(TodoPo.LAST_DELETED, false);
        return builder.update();
    }

    public List<TodoPo> queryUnCompleted() throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.where().eq(TodoPo.COMPLETED, false);
        return builder.query();
    }

    public int updateAllCompleted(boolean completed) throws SQLException {
        UpdateBuilder builder = dao.updateBuilder();
        builder.where().eq(TodoPo.COMPLETED, !completed);
        builder.updateColumnValue(TodoPo.COMPLETED, completed);
        return builder.update();
    }

}
