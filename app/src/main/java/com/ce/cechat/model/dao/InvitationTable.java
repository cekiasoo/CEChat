package com.ce.cechat.model.dao;


/**
 * @author CE Chen
 */
public class InvitationTable {

    public static final String TABLE_NAME = "invitation_table";

    public static final String ID = "id";

    public static final String USER_HYPHENATE_ID = "user_hyphenate_id";

    public static final String USER_NAME = "user_name";

    public static final String GROUP_HYPHENATE_ID = "group_hyphenate_id";

    public static final String GROUP_NAME = "group_name";

    public static final String REASON = "reason";

    public static final String STATUS = "status";

    public static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, "
            + USER_HYPHENATE_ID + " TEXT, "
            + USER_NAME + " TEXT, "
            + GROUP_HYPHENATE_ID + " TEXT UNIQUE, "
            + GROUP_NAME + " TEXT, "
            + REASON + " TEXT, "
            + STATUS + " INTEGER);";

}
