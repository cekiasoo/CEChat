package com.ce.cechat.model.biz;

import android.util.Log;

import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.thread.ThreadPools;
import com.hyphenate.chat.EMClient;

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
