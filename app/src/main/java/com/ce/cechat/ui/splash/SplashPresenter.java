package com.ce.cechat.ui.splash;

import com.ce.cechat.app.BasePresenter;
import com.ce.cechat.ui.login.LoggedInListener;
import com.ce.cechat.ui.splash.ISplashContract.ISplashView;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Splash Presenter
 */
public class SplashPresenter extends BasePresenter<ISplashView, SplashBiz> implements ISplashContract.ISplashPresenter {

    @Inject
    public SplashPresenter() {

    }

    /**
     * 判断用户之前是否登录过
     */
    @Override
    public void isLoggedIn() {
        mBiz.isLoggedBefore(new LoggedInListener() {
            @Override
            public void isLoggedIn(boolean isLoggedIn) {
                if (isLoggedIn) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mView != null) {
                                mView.loggedIn();
                            }
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mView != null) {
                                mView.noLoggedIn();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 判断用户是否在数据库中
     */
    @Override
    public void isUserInDb() {
        boolean userInDb = mBiz.isUserInDb();
        if (userInDb) {
            if (mView != null) {
                mView.userInDb();
            }
        } else {
            if (mView != null) {
                mView.userNoInDb();
            }
        }
    }

}
