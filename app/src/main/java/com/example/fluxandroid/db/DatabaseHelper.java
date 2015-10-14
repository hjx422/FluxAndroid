package com.example.fluxandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.fluxandroid.db.po.TodoPo;
import com.example.fluxandroid.utils.LogUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * @author hjx
 * @date 10/12/2015
 * @time 14:24
 * @description
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DB_NAME = "todo.db";

    private static int dbVersion = 1;
    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, dbVersion);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TodoPo.class);
        } catch (SQLException e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
