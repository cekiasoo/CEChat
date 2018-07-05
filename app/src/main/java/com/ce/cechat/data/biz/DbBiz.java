package com.ce.cechat.data.biz;

import android.content.Context;

import com.ce.cechat.bean.User;
import com.ce.cechat.data.dao.UserDao;
import com.ce.cechat.data.db.DbManager;

/**
 * @author CE Chen
 *
 * 数据库业务逻辑操作类
 */
public class DbBiz implements IDbBiz {

    private volatile static DbBiz sDbBiz;

    private Context mContext;

    private UserDao mUserDao;

    private DbManager mDbManager;

    private DbBiz() {
    }

    public static DbBiz newInstance() {
        if (sDbBiz == null) {
            synchronized (DbBiz.class) {
                if (sDbBiz == null) {
                    sDbBiz = new DbBiz();
                }
            }
        }
        return sDbBiz;
    }

    public void init(Context pContext) {
        this.mContext = pContext;
        mUserDao = new UserDao(mContext);
    }

    @Override
    public UserDao getUserDao() {
        return mUserDao;
    }

    @Override
    public void loginSuccess(User pUser) {

        if (pUser == null) {
            return;
        }

        if (mDbManager != null) {
            mDbManager.close();
        }

        mDbManager = new DbManager(mContext, pUser.getName());
    }

    @Override
    public void closeDb() {
        mDbManager.close();
    }

    public DbManager getDbManager() {
        return mDbManager;
    }
}
