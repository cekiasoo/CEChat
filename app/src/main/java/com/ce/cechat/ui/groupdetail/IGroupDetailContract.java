package com.ce.cechat.ui.groupdetail;

import com.ce.cechat.app.IBaseContact;
import com.ce.cechat.bean.GroupMember;
import com.ce.cechat.bean.User;
import com.ce.cechat.ui.chat.IDestroyGroupListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface IGroupDetailContract {


    /**
     * @author CE Chen
     * <p>
     * 作用 : IGroupDetailView 接口
     */
    public interface IGroupDetailView extends IBaseContact.IBaseView {

        /**
         * 设置群名称
         * @param pGroupName 群名称
         */
        void setGroupName(String pGroupName);


        /**
         * 解散群成功
         */
        void destroyGroupSuccess();

        /**
         * 解散群失败
         *
         * @param e 异常
         */
        void destroyGroupFailed(HyphenateException e);

        /**
         * 退出群成功
         */
        void leaveGroupSuccess();

        /**
         * 退出群失败
         * @param e 异常
         */
        void leaveGroupFailed(HyphenateException e);


        /**
         * 获取群所有信息成功
         * @param pGroup 群所有信息
         */
        void getGroupDetailSuccess(EMGroup pGroup);

        /**
         * 获取群所有信息失败
         * @param error error
         * @param errorMsg error Msg
         */
        void getGroupDetailFailed(int error, String errorMsg);

        /**
         * 显示编辑
         */
        void showEdit();

        /**
         * 隐藏编辑
         */
        void hideEdit();

    }


    /**
     * @author CE Chen
     *
     * 群资料业务逻辑操作接口
     */
    public interface IGroupDetailBiz extends IBaseContact.IBaseBiz {

        /**
         * 根据群 Id 获取群名称
         * @param pGroupId 群 id
         * @return 群名称
         */
        String getGroupName(String pGroupId);

        /**
         * 是否是群主
         * @param pGroupId GroupId
         * @return 是返回true 否返回false
         */
        boolean isGroupOwner(String pGroupId);

        /**
         * 当前用户判断是否是群主
         * @param pGroup EMGroup
         * @return 是返回true 否返回false
         */
        boolean isGroupOwner(EMGroup pGroup);

        /**
         * 解散群
         * @param pGroupId GroupId
         * @param pDestroyGroupListener Destroy Group Listener
         */
        void destroyGroup(String pGroupId, IDestroyGroupListener pDestroyGroupListener);

        /**
         * 退出群
         * @param pGroupId GroupId
         * @param pILeaveGroupListener Leave Group Listener
         */
        void leaveGroup(String pGroupId, ILeaveGroupListener pILeaveGroupListener);


        /**
         * 获取指定 GroupId 所有信息
         * @param pGroupId GroupId
         * @param callback EMValueCallBack
         */
        void getGroupDetail(String pGroupId, EMValueCallBack<EMGroup> callback);

        /**
         * 获取群所有成员
         * @param pGroup Group
         * @return 群所有成员
         */
        List<GroupMember> getGroupAllMembers(EMGroup pGroup);

        /**
         * 获取群所有管理员
         * @param pGroup Group
         * @return 群所有管理员
         */
        List<GroupMember> getGroupAllAdmins(EMGroup pGroup);

        /**
         * 获取群主
         * @param pGroup Group
         * @return 群主
         */
        GroupMember getOwner(EMGroup pGroup);

        /**
         * String List 转 GroupMember List
         * @param pUserList String List
         * @param pIdentity 身份
         * @return GroupMember List
         */
        List<GroupMember> stringList2GroupMember(List<String> pUserList, int pIdentity);

        /**
         * 判断当前用户是否是群主或管理员
         * @param pGroup Group
         * @return 是返回true 否返回false
         */
        boolean isOwnerOrAdmin(EMGroup pGroup);

        /**
         * 判断当前用户是否是管理员
         * @param pGroup Group
         * @return 是返回true 否返回false
         */
        boolean isGroupAdmin(EMGroup pGroup);

        /**
         * 获取所有不在群里的联系人
         * @param pGroup Group
         * @return 所有不在群里的联系人
         */
        LinkedList<User> getAllNotInGroupContact(EMGroup pGroup);
    }

    public interface IPresenter {

        public void getGroupName(String pGroupId);

        public boolean isGroupOwner(String pOwnerId);

        public void destroyGroup(String pGroupId);

        public void leaveGroup(String pGroupId);

        public void getGroupDetail(String pGroupId);

        /**
         * 获取群所有的管理员
         * @param value 群信息
         * @return 群所有的管理员
         */
        public List<GroupMember> getGroupAllAdmin(EMGroup value);

        /**
         * 获取群所有的成员
         * @param value 群信息
         * @return 群所有的成员
         */
        public List<GroupMember> getGroupAllMember(EMGroup value);

        /**
         * 获取群主
         * @param value 群信息
         * @return 群主
         */
        public GroupMember getOwner(EMGroup value);

        /**
         * 判断当前用户是否是群主或管理员
         * @param value 群信息
         */
        public void isOwnerOrAdmin(EMGroup value);

        /**
         * 获取所有不在群里的联系人
         * @param value 群信息
         * @return 所有不在群里的联系人
         */
        public LinkedList<User> getAllNotInGroupContact(EMGroup value);
    }

}
