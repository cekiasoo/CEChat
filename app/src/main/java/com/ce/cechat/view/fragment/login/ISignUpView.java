package com.ce.cechat.view.fragment.login;

public interface ISignUpView extends ILoginView {

    /**
     * 获取确认密码
     * @return 确认密码
     */
    String getConfirmPassword();

    /**
     * 确认密码是空的
     */
    void emptyConfirmPassword();

    /**
     * 无效的密码
     */
    void invalidPassword();

    /**
     * 无效的确认密码
     */
    void invalidConfirmPassword();


}
