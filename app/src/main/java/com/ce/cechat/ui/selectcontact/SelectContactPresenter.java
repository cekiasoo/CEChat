package com.ce.cechat.ui.selectcontact;

import android.util.Log;

import com.ce.cechat.app.BasePresenter;
import com.ce.cechat.bean.User;
import com.ce.cechat.ui.creategroup.ICreateGroupContract;
import com.ce.cechat.ui.groupdetail.IGroupDetailContract;
import com.ce.cechat.ui.newmsg.IInviteContract;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Select Contact Presenter
 */
public class SelectContactPresenter extends BasePresenter<ISelectContactContract.ISelectContactView, SelectContactBiz>
        implements ISelectContactContract.IPresenter {

    private static final String TAG = "SelectContactPresenter";

    @Inject
    protected ICreateGroupContract.ICreateGroupBiz mCreateGroupBiz;

    @Inject
    protected IInviteContract.IInviteBiz mInviteBiz;

    @Inject
    protected IGroupDetailContract.IGroupDetailBiz mGroupDetailBiz;


    @Inject
    public SelectContactPresenter() {
    }

    @Override
    public void getAllContact() {
        List<User> allContact = mBiz.getAllContact();
        if (mView != null) {
            mView.refresh(allContact);
        }
    }

    @Override
    public void createGroup(final String groupName,
                            final String desc,
                            final String[] allMembers,
                            final String reason,
                            final EMGroupOptions option) {
        mCreateGroupBiz.createGroup(groupName, desc, allMembers, reason, option, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(final EMGroup value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.createGroupSuccess(value);
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
                            mView.createGroupFailed(error, errorMsg);
                        }
                    }
                });
            }
        });
    }

    @Override
    public EMGroupManager.EMGroupStyle getGroupStyle(int style) {
        return mBiz.getGroupStyle(style);
    }

    /**
     * 邀请联系人进群
     * @param pGroupId 群Id
     * @param pContacts 要邀请的联系人
     * @param reason 邀请理由
     */
    @Override
    public void inviteContactsInGroup(String pGroupId, List<String> pContacts, String reason) {
        mInviteBiz.inviteContactsInGroup(pGroupId, pContacts, reason, new EMCallBack() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.inviteContactsSuccess();
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
                            mView.inviteContactsFailed(code, error);
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
    @Override
    public LinkedList<User> getAllNotInGroupContact(EMGroup value) {
        return mGroupDetailBiz.getAllNotInGroupContact(value);
    }

    @Override
    public void getGroupDetail(String pGroupId) {
        mGroupDetailBiz.getGroupDetail(pGroupId, new EMValueCallBack<EMGroup>() {
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

}
