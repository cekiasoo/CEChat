package com.ce.cechat.ui.selectcontact;

import com.ce.cechat.app.IBaseContact;
import com.ce.cechat.bean.User;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;

import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface ISelectContactContract {


    /**
     * @author CE Chen
     *
     */
    public interface ISelectContactView extends IBaseContact.IBaseView {

        /**
         * 刷新数据
         * @param pUsers Users
         */
        void refresh(List<User> pUsers);

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
         * 邀请联系人进群成功
         */
        void inviteContactsSuccess();

        /**
         * 邀请联系人进群失败
         * @param code code
         * @param error error
         */
        void inviteContactsFailed(int code, String error);

        void getGroupDetailSuccess(EMGroup value);

        void getGroupDetailFailed(int error, String errorMsg);

    }


    /**
     * @author CE Chen
     *
     * 选择联系人业务逻辑操作接口
     */
    public interface ISelectContactBiz extends IBaseContact.IBaseBiz {

        /**
         * 获取所有联系人
         * @return 所有联系人
         */
        List<User> getAllContact();

        /**
         * 获取群 style
         * @param style style
         * @return 群 style
         */
        EMGroupManager.EMGroupStyle getGroupStyle(int style);


    }

    /**
     * @author CE Chen
     *
     * Select Contact Presenter
     */
    public interface IPresenter {

        public void getAllContact();

        public void createGroup(final String groupName,
                                final String desc,
                                final String[] allMembers,
                                final String reason,
                                final EMGroupOptions option);

        public EMGroupManager.EMGroupStyle getGroupStyle(int style);

        /**
         * 邀请联系人进群
         * @param pGroupId 群Id
         * @param pContacts 要邀请的联系人
         * @param reason 邀请理由
         */
        public void inviteContactsInGroup(String pGroupId, List<String> pContacts, String reason);

        /**
         * 获取所有不在群里的联系人
         * @param value 群信息
         * @return 所有不在群里的联系人
         */
        public LinkedList<User> getAllNotInGroupContact(EMGroup value);

        public void getGroupDetail(String pGroupId);

    }


}
