package com.ce.cechat.data.db;

import android.content.Context;
import android.support.annotation.Nullable;

import com.ce.cechat.data.dao.ContactDao;
import com.ce.cechat.data.dao.InvitationDao;

/**
 * @author CE Chen
 *
 * 数据库管理类
 */
public class DbManager {

    private DbHelper mDbHelper;

    private ContactDao mContactDao;

    private InvitationDao mInvitationDao;

    private volatile static DbManager sDbManager;

    public DbManager(@Nullable Context context, @Nullable String name) {
        mDbHelper = new DbHelper(context, name);
        mContactDao = new ContactDao(mDbHelper);
        mInvitationDao = new InvitationDao(mDbHelper);
    }

    public ContactDao getContactDao() {
        return mContactDao;
    }

    public InvitationDao getInvitationDao() {
        return mInvitationDao;
    }

    /**
     * 关闭数据库
     */
    public void close() {
        mDbHelper.close();
    }
}
