package com.ce.cechat.view.fragment.chat;

import com.ce.cechat.view.fragment.main.IFragView;
import com.hyphenate.chat.EMGroup;

/**
 * @author CE Chen
 * <p>
 * 作用 : Edit Group Member View
 */
public interface IEditGroupMemberView extends IFragView {

    /**
     * 删除群成员成功
     */
    void onDeleteGroupMemberSuccess();

    /**
     * 删除群成员失败
     * @param code code
     * @param error error
     */
    void onDeleteGroupMemberFailed(int code, String error);

    /**
     * 获取群信息成功
     * @param pGroup 群信息
     */
    void onGetGroupDetailSuccess(EMGroup pGroup);

    /**
     * 获取群信息失败
     * @param error error
     * @param errorMsg error Msg
     */
    void onGetGroupDetailFailed(int error, String errorMsg);

    /**
     * 取消管理员成功
     * @param value 群信息
     */
    void onCancelGroupAdminSuccess(EMGroup value);

    /**
     * 取消管理员失败
     * @param error error
     * @param errorMsg error Msg
     */
    void onCancelGroupAdminFailed(int error, String errorMsg);

    /**
     * 设置管理员成功
     * @param value 群信息
     */
    void onSetGroupAdminSuccess(EMGroup value);

    /**
     * 设置管理员失败
     * @param error error
     * @param errorMsg error Msg
     */
    void onSetGroupAdminFailed(int error, String errorMsg);
}
