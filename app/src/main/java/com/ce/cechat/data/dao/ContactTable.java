package com.ce.cechat.data.dao;

/**
 * @author CE Chen
 *
 * Contact 联系人表相关 表名 字段 建表语句
 */
public class ContactTable {

    public static final String TABLE_NAME = "contact_table";

    public static final String ID = "id";

    public static final String HYPHENATE_ID = "hyphenate_id";

    public static final String NAME = "name";

    public static final String NICKNAME = "nickname";

    public static final String HEAD = "head";

    //1 表示是 0表示不是
    public static final String IS_CONTACT = "is_contact";


    public static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL, "
            + HYPHENATE_ID + " TEXT NOT NULL UNIQUE, "
            + NAME + " TEXT, "
            + NICKNAME + " TEXT, "
            + HEAD + " TEXT, "
            + IS_CONTACT + " INTEGER DEFAULT (1));";

}
