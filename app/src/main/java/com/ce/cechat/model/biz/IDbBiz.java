package com.ce.cechat.model.biz;

import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.dao.UserDao;

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
