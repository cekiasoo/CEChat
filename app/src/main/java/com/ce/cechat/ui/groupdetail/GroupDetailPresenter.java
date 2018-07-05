package com.ce.cechat.ui.groupdetail;

import android.util.Log;

import com.ce.cechat.app.BasePresenter;
import com.ce.cechat.bean.GroupMember;
import com.ce.cechat.bean.User;
import com.ce.cechat.ui.chat.IDestroyGroupListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Group Detail Presenter
 */
public class GroupDetailPresenter extends BasePresenter<IGroupDetailContract.IGroupDetailView, GroupDetailBiz>
        implements IGroupDetailContract.IPresenter {

    private static final String TAG = "GroupDetailPresenter";

    @Inject
    public GroupDetailPresenter() {
    }

    @Override
    public void getGroupName(String pGroupId) {
        String groupName = mBiz.getGroupName(pGroupId);
        if (mView != null && mView.isActive()) {
            mView.setGroupName(groupName);
        }
    }

    @Override
    public boolean isGroupOwner(String pOwnerId) {
        return mBiz.isGroupOwner(pOwnerId);
    }

    @Override
    public void destroyGroup(String pGroupId) {
        mBiz.destroyGroup(pGroupId, new IDestroyGroupListener() {
            @Override
            public void destroyGroupSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.destroyGroupSuccess();
                        }
                    }
                });
            }

            @Override
            public void destroyGroupFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.destroyGroupFailed(e);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void leaveGroup(String pGroupId) {
        mBiz.leaveGroup(pGroupId, new ILeaveGroupListener() {
            @Override
            public void leaveGroupSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.leaveGroupSuccess();
                        }
                    }
                });
            }

            @Override
            public void leaveGroupFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.leaveGroupFailed(e);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void getGroupDetail(String pGroupId) {
        mBiz.getGroupDetail(pGroupId, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                Log.v(TAG, "member size = " + value.getMembers().size());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.getGroupDetailSuccess(value);
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
                            mView.getGroupDetailFailed(error, errorMsg);
                        }
                    }
                });
            }
        });
    }

    /**
     * 获取群所有的管理员
     * @param value 群信息
     * @return 群所有的管理员
     */
    @Override
    public List<GroupMember> getGroupAllAdmin(EMGroup value) {
        return mBiz.getGroupAllAdmins(value);
    }

    /**
     * 获取群所有的成员
     * @param value 群信息
     * @return 群所有的成员
     */
    @Override
    public List<GroupMember> getGroupAllMember(EMGroup value) {
        return mBiz.getGroupAllMembers(value);
    }

    /**
     * 获取群主
     * @param value 群信息
     * @return 群主
     */
    @Override
    public GroupMember getOwner(EMGroup value) {
        return mBiz.getOwner(value);
    }

    /**
     * 判断当前用户是否是群主或管理员
     * @param value 群信息
     */
    @Override
    public void isOwnerOrAdmin(EMGroup value) {
        boolean ownerOrAdmin = mBiz.isOwnerOrAdmin(value);
        if (ownerOrAdmin) {
            if (mView != null && mView.isActive()) {
                mView.showEdit();
            }
        } else {
            if (mView != null && mView.isActive()) {
                mView.hideEdit();
            }
        }
    }

    /**
     * 获取所有不在群里的联系人
     * @param value 群信息
     * @return 所有不在群里的联系人
     */
    @Override
    public LinkedList<User> getAllNotInGroupContact(EMGroup value) {
        return mBiz.getAllNotInGroupContact(value);
    }

}
