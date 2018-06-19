package com.ce.cechat.model.biz;

/**
 * @author CE Chen
 *
 *
 */
public interface ISplashBiz {

    /**
     * 判断之前是否登录过
     * @param pLoggedInListener LoggedInListener
     */
    void isLoggedBefore(LoggedInListener pLoggedInListener);

    /**
     * 判断之前登录了的用户是否在数据库中
     * @return 是返回true 否返回false
     */
    boolean isUserInDb();

}
