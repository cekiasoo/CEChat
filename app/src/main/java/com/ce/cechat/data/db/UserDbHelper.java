package com.ce.cechat.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ce.cechat.data.dao.UserTable;

/**
 * @author CE Chen
 *
 * 数据库类
 *
 */
public class UserDbHelper extends SQLiteOpenHelper {

    private volatile static UserDbHelper mUserDbHelper;

    private UserDbHelper(Context context) {
        super(context, UserDbConfig.getDatabaseName(), null, UserDbConfig.getDatabaseVersion());
    }

    public static UserDbHelper getInstance(Context pContext) {
        if (mUserDbHelper == null) {
            synchronized (UserDbHelper.class) {
                if (mUserDbHelper == null) {
                    mUserDbHelper = new UserDbHelper(pContext);
                }
            }
        }
        return mUserDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
