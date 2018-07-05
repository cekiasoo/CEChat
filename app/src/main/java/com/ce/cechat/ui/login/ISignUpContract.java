package com.ce.cechat.ui.login;

import com.ce.cechat.app.IBaseContact;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface ISignUpContract {

    public interface ISignUpView extends ILoginContract.ILoginView {

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

    /**
     * @author CE Chen
     *
     * 注册业务逻辑接口
     */
    public interface ISignUpBiz extends IBaseContact.IBaseBiz{

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


    public interface IPresenter {


        /**
         * 用户 id 是否是空的
         * @return 是返回true 否返回false
         */
        public boolean isEmptyUserId();

        /**
         * 密码是否是空的
         * @return 是返回true 否返回false
         */
        public boolean isEmptyPassword();

        /**
         * 是否是有效的用户 id
         * @return 是返回true 否返回false
         */
        public boolean isUserIdValid();

        /**
         * 密码是否是空的
         * @return 是返回 true 否返回 false
         */
        public boolean isConfirmPasswordEmpty();


        /**
         * 密码是否有效
         * @return 是返回 true 否返回 false
         */
        public boolean isPasswordVail();

        /**
         * 确认密码是否一致
         * @return 是返回 true 否返回 false
         */
        public boolean isConfirmPasswordVail();


        /**
         * 注册
         */
        public void signUp();

    }

}
