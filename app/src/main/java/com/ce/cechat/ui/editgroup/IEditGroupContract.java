package com.ce.cechat.ui.editgroup;

import com.ce.cechat.app.IBaseContact;
import com.ce.cechat.bean.GroupMember;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;

import java.util.List;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface IEditGroupContract {

    /**
     * @author CE Chen
     * <p>
     * 作用 : Edit Group Member View
     */
    public interface IEditGroupMemberView extends IBaseContact.IBaseView {

        /**
         * 删除群成员成功
         */
        void onDeleteGroupMemberSuccess();

        /**
         * 删除群成员失败
         * @param code code
         * @param error error
         */
        void onDeleteGroupMemberFailed(int code, String error);

        /**
         * 获取群信息成功
         * @param pGroup 群信息
         */
        void onGetGroupDetailSuccess(EMGroup pGroup);

        /**
         * 获取群信息失败
         * @param error error
         * @param errorMsg error Msg
         */
        void onGetGroupDetailFailed(int error, String errorMsg);

        /**
         * 取消管理员成功
         * @param value 群信息
         */
        void onCancelGroupAdminSuccess(EMGroup value);

        /**
         * 取消管理员失败
         * @param error error
         * @param errorMsg error Msg
         */
        void onCancelGroupAdminFailed(int error, String errorMsg);

        /**
         * 设置管理员成功
         * @param value 群信息
         */
        void onSetGroupAdminSuccess(EMGroup value);

        /**
         * 设置管理员失败
         * @param error error
         * @param errorMsg error Msg
         */
        void onSetGroupAdminFailed(int error, String errorMsg);
    }


    /**
     * @author CE Chen
     * <p>
     * 作用 : 编辑群成员业务逻辑操作接口
     */
    public interface IEditGroupMemberBiz extends IBaseContact.IBaseBiz {

        /**
         * 删除群成员
         * @param pGroupId Group Id
         * @param pGroupMember 群成员
         * @param callback 回调
         */
        void deleteGroupMembers(String pGroupId, String pGroupMember, EMCallBack callback);

        /**
         * 取消群管理
         * @param pGroupId Group Id
         * @param pGroupMember 要取消的管理
         * @param callback 回调
         */
        void cancelGroupAdmin(String pGroupId, String pGroupMember, EMValueCallBack<EMGroup> callback);

        /**
         * 设置群管理
         * @param pGroupId Group Id
         * @param pGroupMember 要设置成为群管理的群成员
         * @param callback 回调
         */
        void setGroupAdmin(String pGroupId, String pGroupMember, EMValueCallBack<EMGroup> callback);
    }


    public interface IPresenter {

        /**
         * 删除群成员
         * @param pGroupId Group Id
         * @param pGroupMember 群成员 id
         */
        public void deleteGroupMembers(String pGroupId, String pGroupMember);

        /**
         * 取消群管理
         * @param pGroupId Group Id
         * @param pGroupMember 群管理的id
         */
        public void cancelGroupAdmin(String pGroupId, String pGroupMember);

        /**
         * 设置群管理
         * @param pGroupId Group Id
         * @param pGroupMember 要设置成管理的成员id
         */
        public void setGroupAdmin(String pGroupId, String pGroupMember);

        /**
         * 获取群信息
         * @param pGroupId Group id
         */
        public void getGroupDetail(String pGroupId);


        /**
         * 获取群所有成员 包括 群主和管理员
         * @param value 群信息
         * @return 群所有成员
         */
        public List<GroupMember> getGroupAllMembers(EMGroup value);

        /**
         * 判断当前用户是否是群主
         * @param value 群信息
         * @return 是返回true 否返回false
         */
        public boolean isGroupOwner(EMGroup value);


        /**
         * 判断当前用户是否是群管理
         * @param value 群信息
         * @return 是返回true 否返回false
         */
        public boolean isGroupAdmin(EMGroup value);

    }

}
