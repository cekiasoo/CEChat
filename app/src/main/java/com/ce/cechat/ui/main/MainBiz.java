package com.ce.cechat.ui.main;

import com.ce.cechat.data.biz.DbBiz;
import com.ce.cechat.utils.ThreadPools;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * 主页面业务逻辑操作类
 */
public class MainBiz implements IMainContract.IMainBiz {

    @Inject
    public MainBiz() {
    }

    @Override
    public void logout(final EMCallBack callBack) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().logout(false, callBack);
            }
        });
    }

    @Override
    public String getCurrentUser() {
        return EMClient.getInstance().getCurrentUser();
    }

    @Override
    public void closeDb() {
        DbBiz.newInstance().closeDb();
    }
}
