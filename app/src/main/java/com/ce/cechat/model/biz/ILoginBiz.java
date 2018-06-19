package com.ce.cechat.model.biz;

import com.hyphenate.EMCallBack;

/**
 * @author CE Chen
 *
 * 登录业务逻辑接口
 */
public interface ILoginBiz {

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
