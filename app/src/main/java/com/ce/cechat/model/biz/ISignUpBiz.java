package com.ce.cechat.model.biz;

/**
 * @author CE Chen
 *
 * 注册业务逻辑接口
 */
public interface ISignUpBiz {

    /**
     * 确认密码是否是空的
     * @param password 确认密码
     * @return 是返回 true 否返回 false
     */
    boolean isConfirmPasswordEmpty(String password);

    /**
     * 密码是否有效
     * @param password 密码
     * @return 是返回 true 否返回 false
     */
    boolean isPasswordVail(String password);


    /**
     * 确认两次密码是否一致
     * @param password 第一次输入的密码
     * @param confirmPassword 第二次输入的密码
     * @return 是返回 true 否返回 false
     */
    boolean confirmPassword(String password, String confirmPassword);

    /**
     * 注册
     * @param id 用户id
     * @param password 用户密码
     * @param pSignUpListener 注册回调接口
     */
    void signUp(String id, String password, ISignUpListener pSignUpListener);

}
