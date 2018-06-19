package com.ce.cechat.model.biz;

import com.ce.cechat.model.thread.ThreadPools;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * @author CE Chen
 *
 * 主页面业务逻辑操作类
 */
public class MainBiz implements IMainBiz {

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
