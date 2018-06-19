package com.ce.cechat.view.activity;

public interface ISplashView {

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
