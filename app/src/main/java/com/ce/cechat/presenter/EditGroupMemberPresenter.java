package com.ce.cechat.presenter;

import android.os.Handler;

import com.ce.cechat.model.bean.GroupMember;
import com.ce.cechat.model.biz.EditGroupMemberBiz;
import com.ce.cechat.model.biz.GroupDetailBiz;
import com.ce.cechat.model.biz.IEditGroupMemberBiz;
import com.ce.cechat.model.biz.IGroupDetailBiz;
import com.ce.cechat.view.fragment.chat.IEditGroupMemberView;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;

import java.util.List;


/**
 * @author CE Chen
 * <p>
 * 作用 : Edit Group Member Presenter
 */
public class EditGroupMemberPresenter {

    private IEditGroupMemberView mEditGroupMemberView;

    private Handler mHandler = new Handler();

    public EditGroupMemberPresenter(IEditGroupMemberView mEditGroupMemberView) {
        this.mEditGroupMemberView = mEditGroupMemberView;
    }

    /**
     * 删除群成员
     * @param pGroupId Group Id
     * @param pGroupMember 群成员 id
     */
    public void deleteGroupMembers(String pGroupId, String pGroupMember) {

        IEditGroupMemberBiz editGroupMemberBiz = new EditGroupMemberBiz();
        editGroupMemberBiz.deleteGroupMembers(pGroupId, pGroupMember, new EMCallBack() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditGroupMemberView.onDeleteGroupMemberSuccess();
                    }
                });
            }

            @Override
            public void onError(final int code, final String error) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditGroupMemberView.onDeleteGroupMemberFailed(code, error);
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 取消群管理
     * @param pGroupId Group Id
     * @param pGroupMember 群管理的id
     */
    public void cancelGroupAdmin(String pGroupId, String pGroupMember) {

        IEditGroupMemberBiz editGroupMemberBiz = new EditGroupMemberBiz();
        editGroupMemberBiz.cancelGroupAdmin(pGroupId, pGroupMember, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditGroupMemberView.onCancelGroupAdminSuccess(value);
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditGroupMemberView.onCancelGroupAdminFailed(error, errorMsg);
                    }
                });
            }
        });
    }

    /**
     * 设置群管理
     * @param pGroupId Group Id
     * @param pGroupMember 要设置成管理的成员id
     */
    public void setGroupAdmin(String pGroupId, String pGroupMember) {

        IEditGroupMemberBiz editGroupMemberBiz = new EditGroupMemberBiz();
        editGroupMemberBiz.setGroupAdmin(pGroupId, pGroupMember, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditGroupMemberView.onSetGroupAdminSuccess(value);
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditGroupMemberView.onSetGroupAdminFailed(error, errorMsg);
                    }
                });
            }
        });
    }

    /**
     * 获取群信息
     * @param pGroupId Group id
     */
    public void getGroupDetail(String pGroupId) {
        final IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        groupDetailBiz.getGroupDetail(pGroupId, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditGroupMemberView.onGetGroupDetailSuccess(value);
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditGroupMemberView.onGetGroupDetailFailed(error, errorMsg);
                    }
                });
            }
        });
    }

    /**
     * 获取群所有成员 包括 群主和管理员
     * @param value 群信息
     * @return 群所有成员
     */
    public List<GroupMember> getGroupAllMembers(EMGroup value) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        return groupDetailBiz.getGroupAllMembers(value);
    }

    /**
     * 判断当前用户是否是群主
     * @param value 群信息
     * @return 是返回true 否返回false
     */
    public boolean isGroupOwner(EMGroup value) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        return groupDetailBiz.isGroupOwner(value);
    }

    /**
     * 判断当前用户是否是群管理
     * @param value 群信息
     * @return 是返回true 否返回false
     */
    public boolean isGroupAdmin(EMGroup value) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        return groupDetailBiz.isGroupAdmin(value);
    }

}
