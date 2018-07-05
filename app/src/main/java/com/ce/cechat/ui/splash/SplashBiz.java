package com.ce.cechat.ui.splash;

import android.util.Log;

import com.ce.cechat.bean.User;
import com.ce.cechat.data.biz.DbBiz;
import com.ce.cechat.ui.login.LoggedInListener;
import com.ce.cechat.utils.ThreadPools;
import com.hyphenate.chat.EMClient;
import com.ce.cechat.ui.splash.ISplashContract.ISplashBiz;

/**
 * @author CE Chen
 *
 */
public class SplashBiz implements ISplashBiz {

    private static final String TAG = "SplashBiz";

    @Override
    public void isLoggedBefore(final LoggedInListener pLoggedInListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
                pLoggedInListener.isLoggedIn(loggedInBefore);
            }
        });
    }

    @Override
    public boolean isUserInDb() {
        User user = DbBiz.newInstance().getUserDao().getUserById(EMClient.getInstance().getCurrentUser());
        Log.v(TAG, "user = " + user);
        if (user != null) {
            DbBiz.newInstance().loginSuccess(user);
        }
        return user != null;
    }
}
