package com.ce.cechat.ui.creategroup;

import com.ce.cechat.app.BasePresenter;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Create Group Presenter
 */
public class CreateGroupPresenter extends BasePresenter<ICreateGroupContract.ICreateGroupView, CreateGroupBiz>
        implements ICreateGroupContract.IPresenter {


    @Inject
    public CreateGroupPresenter() {
    }

    @Override
    public void createGroup(final String groupName,
                            final String desc,
                            final String[] allMembers,
                            final String reason,
                            final EMGroupOptions option) {
        mBiz.createGroup(groupName, desc, allMembers, reason, option, new EMValueCallBack<EMGroup>() {
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

    /**
     * 群名称是否是空的
     *
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isGroupNameEmpty() {
        String groupName = mView.getGroupName();
        boolean nameEmpty = mBiz.isGroupNameEmpty(groupName);
        if (nameEmpty) {
            if (mView != null && mView.isActive()) {
                mView.groupNameEmpty();
            }
        }
        return nameEmpty;
    }
    /**
     * 群简介是空的
     *
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isGroupDescriptionEmpty() {
        String description = mView.getGroupDescription();
        boolean descriptionEmpty = mBiz.isGroupDescriptionEmpty(description);
        if (descriptionEmpty) {
            if (mView != null && mView.isActive()) {
                mView.groupDescriptionEmpty();
            }
        }
        return descriptionEmpty;
    }


    /**
     * 建群理由是空的
     *
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isGroupReasonEmpty() {
        String groupReason = mView.getGroupReason();
        boolean reasonEmpty = mBiz.isGroupReasonEmpty(groupReason);
        if (reasonEmpty) {
            if (mView != null && mView.isActive()) {
                mView.groupReasonEmpty();
            }
        }
        return reasonEmpty;
    }

    @Override
    public EMGroupManager.EMGroupStyle getGroupStyle() {
        return mBiz.getGroupStyle(mView.getPublic(), mView.getCheck());
    }

}
