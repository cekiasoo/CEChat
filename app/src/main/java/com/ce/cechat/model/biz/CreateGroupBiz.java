package com.ce.cechat.model.biz;

import android.text.TextUtils;

import com.ce.cechat.model.thread.ThreadPools;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;

/**
 * @author CE Chen
 *
 */
public class CreateGroupBiz implements ICreateGroupBiz {

    @Override
    public void createGroup(final String groupName,
                            final String desc,
                            final String[] allMembers,
                            final String reason,
                            final EMGroupOptions option,
                            final EMValueCallBack<EMGroup> callback) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance()
                        .groupManager()
                        .asyncCreateGroup(groupName, desc, allMembers, reason, option, callback);

            }
        });
    }

    @Override
    public boolean isGroupNameEmpty(String pGroupName) {
        return TextUtils.isEmpty(pGroupName);
    }

    @Override
    public boolean isGroupDescriptionEmpty(String pGroupDescription) {
        return TextUtils.isEmpty(pGroupDescription);
    }

    @Override
    public boolean isGroupReasonEmpty(String pGroupReason) {
        return TextUtils.isEmpty(pGroupReason);
    }

    @Override
    public EMGroupManager.EMGroupStyle getGroupStyle(boolean isPublic, boolean isCheck) {
        //公开 且 验证
        if (isPublic && isCheck) {
            return EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
        }

        //公开 且 不验证
        if (isPublic && !isCheck) {
            return EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
        }

        //私有 且 验证
        if (!isPublic && isCheck) {
            return EMGroupManager.EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;
        }

        //私有 且 不验证
        if (!isPublic && !isCheck) {
            return EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
        }
        return EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
    }
}
