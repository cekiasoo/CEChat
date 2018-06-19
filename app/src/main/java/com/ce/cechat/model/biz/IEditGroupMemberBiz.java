package com.ce.cechat.model.biz;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;

/**
 * @author CE Chen
 * <p>
 * 作用 : 编辑群成员业务逻辑操作接口
 */
public interface IEditGroupMemberBiz {

    /**
     * 删除群成员
     * @param pGroupId Group Id
     * @param pGroupMember 群成员
     * @param callback 回调
     */
    void deleteGroupMembers(String pGroupId, String pGroupMember, EMCallBack callback);

    /**
     * 取消群管理
     * @param pGroupId Group Id
     * @param pGroupMember 要取消的管理
     * @param callback 回调
     */
    void cancelGroupAdmin(String pGroupId, String pGroupMember, EMValueCallBack<EMGroup> callback);

    /**
     * 设置群管理
     * @param pGroupId Group Id
     * @param pGroupMember 要设置成为群管理的群成员
     * @param callback 回调
     */
    void setGroupAdmin(String pGroupId, String pGroupMember, EMValueCallBack<EMGroup> callback);
}
