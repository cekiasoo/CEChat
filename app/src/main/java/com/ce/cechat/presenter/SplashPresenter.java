package com.ce.cechat.presenter;

import android.os.Handler;

import com.ce.cechat.model.biz.ISplashBiz;
import com.ce.cechat.model.biz.LoggedInListener;
import com.ce.cechat.model.biz.SplashBiz;
import com.ce.cechat.view.activity.ISplashView;

/**
 * @author CE Chen
 *
 * Splash Presenter
 */
public class SplashPresenter {

    private ISplashView mSplashView;

    private Handler mHandler = new Handler();

    public SplashPresenter(ISplashView pSplashView) {
        this.mSplashView = pSplashView;
    }

    /**
     * 判断用户之前是否登录过
     */
    public void isLoggedIn() {
        ISplashBiz splashBiz = new SplashBiz();
        splashBiz.isLoggedBefore(new LoggedInListener() {
            @Override
            public void isLoggedIn(boolean isLoggedIn) {
                if (isLoggedIn) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mSplashView.loggedIn();
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mSplashView.noLoggedIn();
                        }
                    });
                }
            }
        });
    }

    /**
     * 判断用户是否在数据库中
     */
    public void isUserInDb() {
        ISplashBiz splashBiz = new SplashBiz();
        boolean userInDb = splashBiz.isUserInDb();
        if (userInDb) {
            mSplashView.userInDb();
        } else {
            mSplashView.userNoInDb();
        }
    }

}
