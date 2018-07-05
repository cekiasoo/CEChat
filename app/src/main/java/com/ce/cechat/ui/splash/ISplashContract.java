package com.ce.cechat.ui.splash;

import com.ce.cechat.app.IBaseContact;
import com.ce.cechat.ui.login.LoggedInListener;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface ISplashContract {

    /**
     * @author CE Chen
     */
    public interface ISplashBiz extends IBaseContact.IBaseBiz {

        /**
         * 判断之前是否登录过
         *
         * @param pLoggedInListener LoggedInListener
         */
        void isLoggedBefore(LoggedInListener pLoggedInListener);

        /**
         * 判断之前登录了的用户是否在数据库中
         *
         * @return 是返回true 否返回false
         */
        boolean isUserInDb();

    }

    /**
     *
     */
    public interface ISplashView extends IBaseContact.IBaseView {

        /**
         * 之前登录过
         */
        void loggedIn();

        /**
         * 之前没登录过
         */
        void noLoggedIn();

        /**
         * 之前登录过的用户在数据库中
         */
        void userInDb();

        /**
         * 之前登录过的用户没在数据库中
         */
        void userNoInDb();
    }


    public interface ISplashPresenter {

        /**
         * 判断用户之前是否登录过
         */
        public void isLoggedIn();

        /**
         * 判断用户是否在数据库中
         */
        public void isUserInDb();

    }

}
