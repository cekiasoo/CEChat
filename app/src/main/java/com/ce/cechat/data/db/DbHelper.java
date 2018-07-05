package com.ce.cechat.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.ce.cechat.data.dao.ContactTable;
import com.ce.cechat.data.dao.InvitationTable;

/**
 * @author CE Chen
 *
 * 联系人数据库
 */
public class DbHelper extends SQLiteOpenHelper {

    /**
     * 数据库名由 登录用户 决定
     * @param context Context
     * @param name 数据库名
     */
    public DbHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null, DbConfig.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactTable.TABLE);
        db.execSQL(InvitationTable.TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
