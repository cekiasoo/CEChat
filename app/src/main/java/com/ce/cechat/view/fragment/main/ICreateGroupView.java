package com.ce.cechat.view.fragment.main;

import com.hyphenate.chat.EMGroup;

/**
 * @author CE Chen
 *
 *
 */
public interface ICreateGroupView extends IFragView{

    /**
     * 创建群成功
     * @param value EMGroup
     */
    void createGroupSuccess(EMGroup value);

    /**
     * 创建群失败
     * @param error error
     * @param errorMsg error Msg
     */
    void createGroupFailed(int error, String errorMsg);

    /**
     * 群名称是空的
     */
    void groupNameEmpty();

    /**
     * 群简介是空的
     */
    void groupDescriptionEmpty();

    /**
     * 建群理由是空的
     */
    void groupReasonEmpty();

    /**
     * 获取群名称
     * @return 群名称
     */
    String getGroupName();

    /**
     * 获取群简介
     * @return 群简介
     */
    String getGroupDescription();

    /**
     * 获取建群理由
     * @return 建群理由
     */
    String getGroupReason();

    /**
     * 获取是否公开
     * @return 是否公开
     */
    boolean getPublic();

    /**
     * 获取是否验证
     * @return 是否验证
     */
    boolean getCheck();

}
