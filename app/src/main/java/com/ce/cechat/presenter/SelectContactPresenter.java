package com.ce.cechat.presenter;

import android.os.Handler;
import android.util.Log;

import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.biz.CreateGroupBiz;
import com.ce.cechat.model.biz.GroupDetailBiz;
import com.ce.cechat.model.biz.ICreateGroupBiz;
import com.ce.cechat.model.biz.IGroupDetailBiz;
import com.ce.cechat.model.biz.IInviteBiz;
import com.ce.cechat.model.biz.ISelectContactBiz;
import com.ce.cechat.model.biz.InviteBiz;
import com.ce.cechat.model.biz.SelectContactBiz;
import com.ce.cechat.view.fragment.main.ISelectContactView;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;

import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 *
 * Select Contact Presenter
 */
public class SelectContactPresenter {

    private static final String TAG = "SelectContactPresenter";
    private ISelectContactView mSelectContactView;

    private Handler mHandler = new Handler();

    public SelectContactPresenter(ISelectContactView pSelectContactView) {
        this.mSelectContactView = pSelectContactView;
    }

    public void getAllContact() {
        ISelectContactBiz selectContactBiz = new SelectContactBiz();
        List<User> allContact = selectContactBiz.getAllContact();
        mSelectContactView.refresh(allContact);
    }

    public void createGroup(final String groupName,
                            final String desc,
                            final String[] allMembers,
                            final String reason,
                            final EMGroupOptions option) {
        ICreateGroupBiz createGroupBiz = new CreateGroupBiz();
        createGroupBiz.createGroup(groupName, desc, allMembers, reason, option, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSelectContactView.createGroupSuccess(value);
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSelectContactView.createGroupFailed(error, errorMsg);
                    }
                });
            }
        });
    }

    public EMGroupManager.EMGroupStyle getGroupStyle(int style) {
        ISelectContactBiz selectContactBiz = new SelectContactBiz();
        return selectContactBiz.getGroupStyle(style);
    }

    /**
     * 邀请联系人进群
     * @param pGroupId 群Id
     * @param pContacts 要邀请的联系人
     * @param reason 邀请理由
     */
    public void inviteContactsInGroup(String pGroupId, List<String> pContacts, String reason) {
        IInviteBiz inviteBiz = new InviteBiz();
        inviteBiz.inviteContactsInGroup(pGroupId, pContacts, reason, new EMCallBack() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mSelectContactView.isActive()) {
                            mSelectContactView.inviteContactsSuccess();
                        }
                    }
                });
            }

            @Override
            public void onError(final int code, final String error) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mSelectContactView.isActive()) {
                            mSelectContactView.inviteContactsFailed(code, error);
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
     * 获取所有不在群里的联系人
     * @param value 群信息
     * @return 所有不在群里的联系人
     */
    public LinkedList<User> getAllNotInGroupContact(EMGroup value) {
        IGroupDetailBiz groupDetailBiz = new GroupDetailBiz();
        return groupDetailBiz.getAllNotInGroupContact(value);
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
                        if (mSelectContactView.isActive()) {
                            mSelectContactView.getGroupDetailSuccess(value);
                        }
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mSelectContactView.isActive()) {
                            mSelectContactView.getGroupDetailFailed(error, errorMsg);
                        }
                    }
                });
            }
        });
    }

}
