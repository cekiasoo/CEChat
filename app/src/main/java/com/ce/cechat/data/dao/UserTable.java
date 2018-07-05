package com.ce.cechat.data.dao;

/**
 * @author CE Chen
 *
 * User 表相关 表名 字段 建表语句
 */
public class UserTable {

    public static final String TABLE_NAME = "user_table";

    public static final String NAME = "name";

    public static final String HYPHENATE_ID = "hyphenate_id";

    public static final String NICKNAME = "nickname";

    public static final String HEAD = "head";

    public static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + HYPHENATE_ID + " TEXT PRIMARY KEY UNIQUE NOT NULL, "
            + NAME + " TEXT, "
            + NICKNAME + " TEXT, "
            + HEAD + " TEXT);";

}
