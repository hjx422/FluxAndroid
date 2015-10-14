package com.example.fluxandroid.db.dao;

import com.example.fluxandroid.App;
import com.example.fluxandroid.db.DatabaseHelper;
import com.example.fluxandroid.db.po.BasePo;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * @author hjx
 * @date 10/12/2015
 * @time 14:51
 * @description
 */
public class BaseDao<T extends BasePo> {

    protected DatabaseHelper helper;
    protected Dao<T, Long> dao;

    public BaseDao() throws SQLException {
        helper = DatabaseHelper.getInstance(App.getAppContext());
        dao = helper.getDao(getTClass());
    }

    public int add(T model) throws SQLException {
        long ts = System.currentTimeMillis();
        model.setDeleted(false);
        model.setCreateTime(ts);
        model.setUpdateTime(ts);
        return dao.create(model);
    }

    public int delete(T model) throws SQLException {
        model.setDeleted(true);
        return dao.update(model);
    }

    public int deleteById(Long id) throws SQLException {
        T model = query(id);
        if (model != null) {
            model.setDeleted(true);
            return dao.update(model);
        }
        return 0;
    }

    public int update(T model) throws SQLException {
        model.setUpdateTime(System.currentTimeMillis());
        return dao.update(model);
    }

    public T query(Long id) throws SQLException {
        return dao.queryForId(id);
    }

    public List<T> queryAll() throws SQLException {
        return dao.queryForAll();
    }

    private Class<T> getTClass() {
        return  (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
