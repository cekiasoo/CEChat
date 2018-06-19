package com.ce.cechat.model.biz;

import com.hyphenate.EMCallBack;

/**
 * @author CE Chen
 *
 * 主页面业务逻辑接口
 */
public interface IMainBiz {

    /**
     * 退出登录
     */
    void logout(EMCallBack callBack);

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    String getCurrentUser();

    /**
     * 关闭数据库
     */
    void closeDb();

}
