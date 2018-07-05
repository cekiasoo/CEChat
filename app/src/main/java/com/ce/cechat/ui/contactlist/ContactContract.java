package com.ce.cechat.ui.contactlist;

import com.ce.cechat.app.IBaseContact;
import com.ce.cechat.bean.User;
import com.hyphenate.easeui.domain.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface ContactContract {

    /**
     * @author CE Chen
     */
    public interface IContactView extends IBaseContact.IBaseView {

        /**
         * 显示有邀请消息的红点
         */
        void showInviteRedDot();


        /**
         * 隐藏邀请消息的红点
         */
        void hideInviteRedDot();

        /**
         * 获取所有联系人成功
         * @param pAllContact 所有联系人
         */
        void onGetAllContactSuccess(List<User> pAllContact);


        /**
         * 获取所有联系人失败
         * @param e 异常
         */
        void onGetAllContactFailed(HyphenateException e);

        /**
         * 更新联系人列表
         * @param pUserMap UserMap
         */
        void updateContactList(Map<String, EMGroup> pUserMap);

    }


    /**
     * @author CE Chen
     *
     * 联系人业务逻辑操作接口
     */
    public interface IContactBiz extends IBaseContact.IBaseBiz {

        /**
         * 是否有添加好友的红点
         * @return 是返回true 否返回false
         */
        public boolean haveInviteRedDot();


        /**
         * 删除红点记录
         */
        public void clearInviteRedDot();

        /**
         * 获取所有联系人
         *
         * @param pFromServer 是否从网络获取
         * @param pContactListener IContactListener
         */
        public void getContacts(boolean pFromServer, IContactListener pContactListener);

        /**
         * List 转成 Map
         * @param pAllContacts List
         * @return Map<String, EMGroup>
         */
        public Map<String, EMGroup> list2UserMap(List<User> pAllContacts);
    }

    /**
     * @author CE Chen
     *
     * Contact Presenter
     */
    public interface IPresenter {

        public void haveInviteRedDot();

        public void hideInviteRedDot();

        public void showInviteRedDot();

        public void getContactsMap(boolean pFromServer);

        public void list2UserMap(List<User> pAllContacts);

    }

}
