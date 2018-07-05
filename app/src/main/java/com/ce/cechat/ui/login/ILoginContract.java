package com.ce.cechat.ui.login;

import com.ce.cechat.app.IBaseContact;
import com.hyphenate.EMCallBack;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface ILoginContract {


    /**
     *
     */
    public interface ILoginView extends IBaseContact.IBaseView {

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


    /**
     * @author CE Chen
     *
     * 登录业务逻辑接口
     */
    public interface ILoginBiz extends IBaseContact.IBaseBiz{

        /**
         * 登录
         * @param pName 账号
         * @param pPassword 密码
         * @param pLoginListener 登录回调接口
         */
        void onLogin(String pName, String pPassword, EMCallBack pLoginListener);

        /**
         * 初始化数据库
         * @param pName Name
         */
        void initDb(String pName);

        /**
         * 添加用户到数据库
         * @param pName Name
         */
        void addAccount2Db(String pName);

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
         * 登录
         */
        public void login();

    }


}
