package com.ce.cechat.presenter;

import android.os.Handler;

import com.ce.cechat.model.biz.CreateGroupBiz;
import com.ce.cechat.model.biz.ICreateGroupBiz;
import com.ce.cechat.view.fragment.main.ICreateGroupView;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;

/**
 * @author CE Chen
 *
 * Create Group Presenter
 */
public class CreateGroupPresenter {

    private ICreateGroupView mCreateGroupView;

    private Handler mHandler = new Handler();

    public CreateGroupPresenter(ICreateGroupView pCreateGroupView) {
        this.mCreateGroupView = pCreateGroupView;
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
                        mCreateGroupView.createGroupSuccess(value);
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCreateGroupView.createGroupFailed(error, errorMsg);
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
    public boolean isGroupNameEmpty() {
        ICreateGroupBiz createGroupBiz = new CreateGroupBiz();
        String groupName = mCreateGroupView.getGroupName();
        boolean nameEmpty = createGroupBiz.isGroupNameEmpty(groupName);
        if (nameEmpty) {
            if (mCreateGroupView.isActive()) {
                mCreateGroupView.groupNameEmpty();
            }
        }
        return nameEmpty;
    }
    /**
     * 群简介是空的
     *
     * @return 是返回true 否返回false
     */
    public boolean isGroupDescriptionEmpty() {
        ICreateGroupBiz createGroupBiz = new CreateGroupBiz();
        String description = mCreateGroupView.getGroupDescription();
        boolean descriptionEmpty = createGroupBiz.isGroupDescriptionEmpty(description);
        if (descriptionEmpty) {
            if (mCreateGroupView.isActive()) {
                mCreateGroupView.groupDescriptionEmpty();
            }
        }
        return descriptionEmpty;
    }


    /**
     * 建群理由是空的
     *
     * @return 是返回true 否返回false
     */
    public boolean isGroupReasonEmpty() {
        ICreateGroupBiz createGroupBiz = new CreateGroupBiz();
        String groupReason = mCreateGroupView.getGroupReason();
        boolean reasonEmpty = createGroupBiz.isGroupReasonEmpty(groupReason);
        if (reasonEmpty) {
            if (mCreateGroupView.isActive()) {
                mCreateGroupView.groupReasonEmpty();
            }
        }
        return reasonEmpty;
    }

    public EMGroupManager.EMGroupStyle getGroupStyle() {
        ICreateGroupBiz createGroupBiz = new CreateGroupBiz();
        return createGroupBiz.getGroupStyle(mCreateGroupView.getPublic(), mCreateGroupView.getCheck());
    }

}
