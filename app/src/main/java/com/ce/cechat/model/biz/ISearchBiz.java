package com.ce.cechat.model.biz;

import com.ce.cechat.model.bean.User;
import com.hyphenate.EMCallBack;

/**
 * @author CE Chen
 *
 * 搜索用户逻辑接口
 */
public interface ISearchBiz {

    /**
     * 查找用户
     * @param pUserId 用户id
     * @return User
     */
    User search(String pUserId);


    /**
     * 判断搜索的用户 id 是否为空
     * @param pUserId 用户 id
     * @return 是返回true 否返回 false
     */
    boolean isUserIdEmpty(String pUserId);


    /**
     * 添加新用户
     * @param pUserId 用户名
     * @param pReason 添加的原因
     */
    void addNewFriend(String pUserId, String pReason, EMCallBack pCallBack);
}
