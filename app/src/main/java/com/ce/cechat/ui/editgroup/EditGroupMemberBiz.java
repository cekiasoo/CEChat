package com.ce.cechat.ui.editgroup;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import javax.inject.Inject;

/**
 * @author CE Chen
 * <p>
 * 作用 : 编辑群成员业务逻辑操作类
 */
public class EditGroupMemberBiz implements IEditGroupContract.IEditGroupMemberBiz {

    @Inject
    public EditGroupMemberBiz() {
    }

    @Override
    public void deleteGroupMembers(String pGroupId, String pGroupMember, EMCallBack callback) {
        EMClient.getInstance().groupManager().asyncRemoveUserFromGroup(pGroupId, pGroupMember, callback);
    }

    @Override
    public void cancelGroupAdmin(String pGroupId, String pGroupMember, EMValueCallBack<EMGroup> callback) {
        EMClient.getInstance().groupManager().asyncRemoveGroupAdmin(pGroupId, pGroupMember, callback);
    }

    @Override
    public void setGroupAdmin(String pGroupId, String pGroupMember, EMValueCallBack<EMGroup> callback) {
        EMClient.getInstance().groupManager().asyncAddGroupAdmin(pGroupId, pGroupMember, callback);
    }


}
