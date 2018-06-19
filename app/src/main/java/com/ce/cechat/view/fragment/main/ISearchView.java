package com.ce.cechat.view.fragment.main;

import com.ce.cechat.model.bean.User;

/**
 * @author CE Chen
 *
 */
public interface ISearchView extends IFragView{

    /**
     * 获取要查找的用户 id
     * @return 用户 id
     */
    String getUserId();

    /**
     * 获取要添加的用户 id
     * @return 用户 id
     */
    String getAddUserId();

    /**
     * 查找用户成功
     *
     * @param pUser User
     */
    void searchSuccess(User pUser);

    /**
     * 查找用户失败
     */
    void searchFailed();


    /**
     * 用户 id 是空的
     */
    void emptyUserId();

    /**
     * 添加新用户成功
     */
    void addSuccess();

    /**
     * 添加新用户失败
     *
     * @param code code
     * @param error Error
     */
    void addFailed(int code, String error);

    /**
     * 清除搜索内容
     */
    void clearSearch();
}
