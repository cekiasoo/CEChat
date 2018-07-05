package com.ce.cechat.ui.login;


import com.ce.cechat.bean.User;
import com.ce.cechat.data.biz.DbBiz;
import com.ce.cechat.utils.ThreadPools;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * 登录逻辑实现类
 */
public class LoginBiz extends AbstractLoginBiz implements ILoginContract.ILoginBiz {

    @Inject
    public LoginBiz() {
    }

    @Override
    public void onLogin(final String pName, final String pPassword, final EMCallBack pLoginListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(pName, pPassword, pLoginListener);
            }
        });
    }



    @Override
    public void initDb(String pName) {
        DbBiz.newInstance().loginSuccess(new User(pName));
    }

    @Override
    public void addAccount2Db(String pName) {
        DbBiz.newInstance().getUserDao().replaceUser(new User(pName));
    }
}
