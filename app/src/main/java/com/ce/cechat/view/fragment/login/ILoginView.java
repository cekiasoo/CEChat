package com.ce.cechat.view.fragment.login;

import com.ce.cechat.view.fragment.main.IFragView;

/**
 *
 */
public interface ILoginView extends IFragView {

    /**
     * 获取用户id
     * @return id
     */
    String getUserId();

    /**
     * 获取密码
     * @return password
     */
    String getPassword();

    void showProgressBar();

    void hideProgressBar();

    /**
     * 成功
     */
    void onSuccess();

    /**
     * 失败
     * @param error error
     */
    void onFailed(int code, final String error);

    /**
     * 用户 id 是空的
     */
    void emptyUserId();

    /**
     * 用户密码是空的
     */
    void emptyPassword();

    /**
     * 无效的用户 id
     */
    void invalidUserId();

}
