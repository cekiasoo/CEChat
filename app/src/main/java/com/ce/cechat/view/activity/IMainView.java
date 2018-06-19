package com.ce.cechat.view.activity;

/**
 * @author CE Chen
 *
 *
 */
public interface IMainView {

    /**
     * 设置用户名称
     *
     * @param pName 名称
     */
    void setName(String pName);

    /**
     * 退出登录成功
     */
    void logoutSuccess();


    /**
     * 退出登录失败
     */
    void logoutFailed(int code, String error);



}
