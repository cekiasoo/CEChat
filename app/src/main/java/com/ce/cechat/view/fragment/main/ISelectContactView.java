package com.ce.cechat.view.fragment.main;

import com.ce.cechat.model.bean.User;
import com.hyphenate.chat.EMGroup;

import java.util.List;

/**
 * @author CE Chen
 *
 */
public interface ISelectContactView extends IFragView{

    /**
     * 刷新数据
     * @param pUsers Users
     */
    void refresh(List<User> pUsers);

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
     * 邀请联系人进群成功
     */
    void inviteContactsSuccess();

    /**
     * 邀请联系人进群失败
     * @param code code
     * @param error error
     */
    void inviteContactsFailed(int code, String error);

    void getGroupDetailSuccess(EMGroup value);

    void getGroupDetailFailed(int error, String errorMsg);

}
