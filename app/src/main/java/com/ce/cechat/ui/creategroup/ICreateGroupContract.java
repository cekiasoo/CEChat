package com.ce.cechat.ui.creategroup;

import com.ce.cechat.app.IBaseContact;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface ICreateGroupContract {

    /**
     * @author CE Chen
     *
     *
     */
    public interface ICreateGroupView extends IBaseContact.IBaseView {

        /**
         * 创建群成功
         * @param value EMGroup
         */
        void createGroupSuccess(EMGroup value);

        /**
         * 创建群失败
         * @param error error
         * @param errorMsg error Msg
         */
        void createGroupFailed(int error, String errorMsg);

        /**
         * 群名称是空的
         */
        void groupNameEmpty();

        /**
         * 群简介是空的
         */
        void groupDescriptionEmpty();

        /**
         * 建群理由是空的
         */
        void groupReasonEmpty();

        /**
         * 获取群名称
         * @return 群名称
         */
        String getGroupName();

        /**
         * 获取群简介
         * @return 群简介
         */
        String getGroupDescription();

        /**
         * 获取建群理由
         * @return 建群理由
         */
        String getGroupReason();

        /**
         * 获取是否公开
         * @return 是否公开
         */
        boolean getPublic();

        /**
         * 获取是否验证
         * @return 是否验证
         */
        boolean getCheck();

    }



    /**
     * @author CE Chen
     *
     * 创建群业务逻辑操作类接口
     */
    public interface ICreateGroupBiz extends IBaseContact.IBaseBiz {

        /**
         * 创建群
         * @param groupName 群名称
         * @param desc 群简介
         * @param allMembers 成员
         * @param reason 理由
         * @param option option
         * @param callback callback
         */
        void createGroup(String groupName,
                         String desc,
                         String[] allMembers,
                         String reason,
                         EMGroupOptions option,
                         EMValueCallBack<EMGroup> callback);


        /**
         * 群名称是否是空的
         *
         * @param pGroupName 群名称
         * @return 是返回true 否返回false
         */
        boolean isGroupNameEmpty(String pGroupName);

        /**
         * 群简介是否是空的
         *
         * @param pGroupDescription Group Description
         * @return 是返回true 否返回false
         */
        boolean isGroupDescriptionEmpty(String pGroupDescription);

        /**
         * 建群理由是否是空的
         *
         * @param pGroupReason 建群理由
         * @return 是返回true 否返回false
         */
        boolean isGroupReasonEmpty(String pGroupReason);

        /**
         * 获取群样式
         * @param isPublic 是否公开
         * @param isCheck 是否验证
         * @return EMGroupStyle
         */
        EMGroupManager.EMGroupStyle getGroupStyle(boolean isPublic, boolean isCheck);

    }

    /**
     * @author CE Chen
     *
     * Create Group Presenter
     */
    public interface IPresenter {

        public void createGroup(final String groupName,
                                final String desc,
                                final String[] allMembers,
                                final String reason,
                                final EMGroupOptions option);


        /**
         * 群名称是否是空的
         *
         * @return 是返回true 否返回false
         */
        public boolean isGroupNameEmpty();

        /**
         * 群简介是空的
         *
         * @return 是返回true 否返回false
         */
        public boolean isGroupDescriptionEmpty();

        /**
         * 建群理由是空的
         *
         * @return 是返回true 否返回false
         */
        public boolean isGroupReasonEmpty();

        public EMGroupManager.EMGroupStyle getGroupStyle();


    }

}
