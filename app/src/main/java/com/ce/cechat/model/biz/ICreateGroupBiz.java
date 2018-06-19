package com.ce.cechat.model.biz;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;

/**
 * @author CE Chen
 *
 * 创建群业务逻辑操作类接口
 */
public interface ICreateGroupBiz {

    /**
     * 创建群
     * @param groupName 群名称
     * @param desc 群简介
     * @param allMembers 成员
     * @param reason 理由
     * @param option option
     * @param callback callback
     */
    void createGroup(String groupName,
                     String desc,
                     String[] allMembers,
                     String reason,
                     EMGroupOptions option,
                     EMValueCallBack<EMGroup> callback);


    /**
     * 群名称是否是空的
     *
     * @param pGroupName 群名称
     * @return 是返回true 否返回false
     */
    boolean isGroupNameEmpty(String pGroupName);

    /**
     * 群简介是否是空的
     *
     * @param pGroupDescription Group Description
     * @return 是返回true 否返回false
     */
    boolean isGroupDescriptionEmpty(String pGroupDescription);

    /**
     * 建群理由是否是空的
     *
     * @param pGroupReason 建群理由
     * @return 是返回true 否返回false
     */
    boolean isGroupReasonEmpty(String pGroupReason);

    /**
     * 获取群样式
     * @param isPublic 是否公开
     * @param isCheck 是否验证
     * @return EMGroupStyle
     */
    EMGroupManager.EMGroupStyle getGroupStyle(boolean isPublic, boolean isCheck);

}
