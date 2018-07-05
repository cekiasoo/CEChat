package com.ce.cechat.ui.addcontact;

import com.ce.cechat.app.IBaseContact;
import com.ce.cechat.bean.User;
import com.hyphenate.EMCallBack;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface ISearchContract {


    /**
     * @author CE Chen
     *
     */
    public interface ISearchView extends IBaseContact.IBaseView {

        /**
         * 获取要查找的用户 id
         * @return 用户 id
         */
        String getUserId();

        /**
         * 获取要添加的用户 id
         * @return 用户 id
         */
        String getAddUserId();

        /**
         * 查找用户成功
         *
         * @param pUser User
         */
        void searchSuccess(User pUser);

        /**
         * 查找用户失败
         */
        void searchFailed();


        /**
         * 用户 id 是空的
         */
        void emptyUserId();

        /**
         * 添加新用户成功
         */
        void addSuccess();

        /**
         * 添加新用户失败
         *
         * @param code code
         * @param error Error
         */
        void addFailed(int code, String error);

        /**
         * 清除搜索内容
         */
        void clearSearch();
    }

    /**
     * @author CE Chen
     *
     * 搜索用户逻辑接口
     */
    public interface ISearchBiz extends IBaseContact.IBaseBiz {

        /**
         * 查找用户
         * @param pUserId 用户id
         * @return User
         */
        User search(String pUserId);


        /**
         * 判断搜索的用户 id 是否为空
         * @param pUserId 用户 id
         * @return 是返回true 否返回 false
         */
        boolean isUserIdEmpty(String pUserId);


        /**
         * 添加新用户
         * @param pUserId 用户名
         * @param pReason 添加的原因
         */
        void addNewFriend(String pUserId, String pReason, EMCallBack pCallBack);
    }

    /**
     * @author CE Chen
     *
     * Search Presenter
     */
    public interface IPresenter {

        /**
         * 判断用户输入的 用户 id 是否为空
         * @param pUserId 用户 id
         * @return 是返回 true 否返回 false
         */
        public boolean isUserIdEmpty(String pUserId);


        /**
         * 根据用户 id 查找用户
         */
        public void search();


        /**
         * 添加新好友
         * @param pReason 添加好友的原因
         */
        public void addNewFriend(String pReason);


    }

}
