package com.ce.cechat.ui.login;

public interface BaseLoginBiz {

    /**
     * 用户 id 是否是空的
     * @param id 用户 id
     * @return 是返回 true 否返回 false
     */
    boolean isUserIdEmpty(String id);

    /**
     * 密码是否是空的
     * @param password 密码
     * @return 是返回 true 否返回 false
     */
    boolean isPasswordEmpty(String password);

    /**
     * 用户 id 是否有效
     * @param id 用户 id
     * @return 是返回 true 否返回 false
     */
    boolean isUserIdValid(String id);
}
