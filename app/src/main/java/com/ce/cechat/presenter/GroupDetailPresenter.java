package com.ce.cechat.presenter;

import android.os.Handler;
import android.util.Log;

import com.ce.cechat.model.bean.GroupMember;
import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.biz.GroupDetailBiz;
import com.ce.cechat.model.biz.IDestroyGroupListener;
import com.ce.cechat.model.biz.IGroupDetailBiz;
import com.ce.cechat.model.biz.ILeaveGroupListener;
import com.ce.cechat.view.fragment.chat.IGroupDetailView;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 *
 * Group Detail Presenter
 */
public class GroupDetailPresenter {

    private static final String TAG = "GroupDetailPresenter";
    private IGroupDetailView mIGroupDetailView;

    private Handler mHandler = new Handler();

    public GroupDetailPresenter(IGroupDetailView pIGroupDetailView) {
        this.mIGroupDetailView = pIGroupDetailView;
    }

    public void getGroupName(String pGroupId) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        String groupName = groupDetailBiz.getGroupName(pGroupId);
        if (mIGroupDetailView.isActive()) {
            mIGroupDetailView.setGroupName(groupName);
        }
    }

    public boolean isGroupOwner(String pOwnerId) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        return groupDetailBiz.isGroupOwner(pOwnerId);
    }

    public void destroyGroup(String pGroupId) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        groupDetailBiz.destroyGroup(pGroupId, new IDestroyGroupListener() {
            @Override
            public void destroyGroupSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mIGroupDetailView.isActive()) {
                            mIGroupDetailView.destroyGroupSuccess();
                        }
                    }
                });
            }

            @Override
            public void destroyGroupFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mIGroupDetailView.isActive()) {
                            mIGroupDetailView.destroyGroupFailed(e);
                        }
                    }
                });
            }
        });
    }

    public void leaveGroup(String pGroupId) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        groupDetailBiz.leaveGroup(pGroupId, new ILeaveGroupListener() {
            @Override
            public void leaveGroupSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mIGroupDetailView.isActive()) {
                            mIGroupDetailView.leaveGroupSuccess();
                        }
                    }
                });
            }

            @Override
            public void leaveGroupFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mIGroupDetailView.isActive()) {
                            mIGroupDetailView.leaveGroupFailed(e);
                        }
                    }
                });
            }
        });
    }

    public void getGroupDetail(String pGroupId) {
        final IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        groupDetailBiz.getGroupDetail(pGroupId, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                Log.v(TAG, "member size = " + value.getMembers().size());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mIGroupDetailView.isActive()) {
                            mIGroupDetailView.getGroupDetailSuccess(value);
                        }
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mIGroupDetailView.isActive()) {
                            mIGroupDetailView.getGroupDetailFailed(error, errorMsg);
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
    public List<GroupMember> getGroupAllAdmin(EMGroup value) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        return groupDetailBiz.getGroupAllAdmins(value);
    }

    /**
     * 获取群所有的成员
     * @param value 群信息
     * @return 群所有的成员
     */
    public List<GroupMember> getGroupAllMember(EMGroup value) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        return groupDetailBiz.getGroupAllMembers(value);
    }

    /**
     * 获取群主
     * @param value 群信息
     * @return 群主
     */
    public GroupMember getOwner(EMGroup value) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        return groupDetailBiz.getOwner(value);
    }

    /**
     * 判断当前用户是否是群主或管理员
     * @param value 群信息
     */
    public void isOwnerOrAdmin(EMGroup value) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        boolean ownerOrAdmin = groupDetailBiz.isOwnerOrAdmin(value);
        if (ownerOrAdmin) {
            if (mIGroupDetailView.isActive()) {
                mIGroupDetailView.showEdit();
            }
        } else {
            if (mIGroupDetailView.isActive()) {
                mIGroupDetailView.hideEdit();
            }
        }
    }

    /**
     * 获取所有不在群里的联系人
     * @param value 群信息
     * @return 所有不在群里的联系人
     */
    public LinkedList<User> getAllNotInGroupContact(EMGroup value) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        return groupDetailBiz.getAllNotInGroupContact(value);
    }

}
