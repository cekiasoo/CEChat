package com.ce.cechat.data.biz;

import com.ce.cechat.bean.User;
import com.ce.cechat.data.dao.UserDao;

public interface IDbBiz {

    /**
     * 获取 User 数据库业务逻辑操作类
     * @return UserDao
     */
    UserDao getUserDao();

    /**
     * 登录成功初始化属于当前用户的数据库
     * @param pUser User
     */
    void loginSuccess(User pUser);

    /**
     * 关闭数据库
     */
    void closeDb();

}
