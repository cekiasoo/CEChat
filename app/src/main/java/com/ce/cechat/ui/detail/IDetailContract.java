package com.ce.cechat.ui.detail;

import com.ce.cechat.app.IBaseContact;
import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface IDetailContract {


    /**
     * @author CE Chen
     */
    public interface IContactDetailView extends IBaseContact.IBaseView {

        /**
         * 删除联系人成功
         */
        void onDeleteSuccess();

        /**
         * 删除联系人失败
         *
         * @param e 异常
         */
        void onDeleteFailed(HyphenateException e);

    }


    /**
     * @author CE Chen
     *
     * 详细资料业务逻辑操作接口
     */
    public interface IDetailBiz extends IBaseContact.IBaseBiz {

        /**
         * 删除联系人
         *
         * @param pHyphenateId HyphenateId
         * @param pDeleteContactListener DeleteContactListener
         */
        public void deleteContact(String pHyphenateId, DeleteContactListener pDeleteContactListener);


    }

    public interface IPresenter{
        /**
         * 删除指定 HyphenateId 的联系人
         * @param pHyphenateId HyphenateId
         */
        public void deleteContact(String pHyphenateId);
    }

}
