package com.ce.cechat.ui.editgroup;

import com.ce.cechat.app.BasePresenter;
import com.ce.cechat.bean.GroupMember;
import com.ce.cechat.ui.groupdetail.IGroupDetailContract;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;

import java.util.List;

import javax.inject.Inject;


/**
 * @author CE Chen
 * <p>
 * 作用 : Edit Group Member Presenter
 */
public class EditGroupMemberPresenter extends BasePresenter<IEditGroupContract.IEditGroupMemberView, EditGroupMemberBiz>
        implements IEditGroupContract.IPresenter {

    @Inject
    protected IGroupDetailContract.IGroupDetailBiz mGroupDetailBiz;

    @Inject
    public EditGroupMemberPresenter() {
    }

    /**
     * 删除群成员
     * @param pGroupId Group Id
     * @param pGroupMember 群成员 id
     */
    @Override
    public void deleteGroupMembers(String pGroupId, String pGroupMember) {

        mBiz.deleteGroupMembers(pGroupId, pGroupMember, new EMCallBack() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onDeleteGroupMemberSuccess();
                        }
                    }
                });
            }

            @Override
            public void onError(final int code, final String error) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onDeleteGroupMemberFailed(code, error);
                        }
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
    @Override
    public void cancelGroupAdmin(String pGroupId, String pGroupMember) {

        mBiz.cancelGroupAdmin(pGroupId, pGroupMember, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onCancelGroupAdminSuccess(value);
                        }
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onCancelGroupAdminFailed(error, errorMsg);
                        }
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
    @Override
    public void setGroupAdmin(String pGroupId, String pGroupMember) {

        mBiz.setGroupAdmin(pGroupId, pGroupMember, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onSetGroupAdminSuccess(value);
                        }
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onSetGroupAdminFailed(error, errorMsg);
                        }
                    }
                });
            }
        });
    }

    /**
     * 获取群信息
     * @param pGroupId Group id
     */
    @Override
    public void getGroupDetail(String pGroupId) {
        mGroupDetailBiz.getGroupDetail(pGroupId, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onGetGroupDetailSuccess(value);
                        }
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onGetGroupDetailFailed(error, errorMsg);
                        }
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
    @Override
    public List<GroupMember> getGroupAllMembers(EMGroup value) {
        return mGroupDetailBiz.getGroupAllMembers(value);
    }

    /**
     * 判断当前用户是否是群主
     * @param value 群信息
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isGroupOwner(EMGroup value) {
        return mGroupDetailBiz.isGroupOwner(value);
    }

    /**
     * 判断当前用户是否是群管理
     * @param value 群信息
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isGroupAdmin(EMGroup value) {
        return mGroupDetailBiz.isGroupAdmin(value);
    }

}
