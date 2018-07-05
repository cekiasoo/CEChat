package com.ce.cechat.ui.grouplist;

import com.ce.cechat.app.IBaseContact;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;

import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface IGroupListContract {

    /**
     * @author CE Chen
     *
     *
     */
    public interface IGroupListView extends IBaseContact.IBaseView {

        /**
         * 获取群列表成功
         * @param value 群列表
         */
        void onGetGroupListSuccess(List<EMGroup> value);

        /**
         * 获取群列表失败
         * @param error error
         * @param errorMsg error Msg
         */
        void onGetGroupListFailed(int error, String errorMsg);

        /**
         * 更新群列表
         * @param pGroupMap Group Map
         */
        void updateGroupList(Map<String, EMGroup> pGroupMap);
    }


    /**
     * @author CE Chen
     *
     * 群列表业务逻辑操作类接口
     */
    public interface IGroupListBiz extends IBaseContact.IBaseBiz {

        /**
         * 获取群列表
         * @param pEmValueCallBack EMValueCallBack
         */
        void getGroupList(EMValueCallBack<List<EMGroup>> pEmValueCallBack);

        /**
         * List 转成 Map
         * @param pAllGroups 群 List
         * @return 群 Map
         */
        Map<String, EMGroup> list2GroupMap(List<EMGroup> pAllGroups);

    }

    /**
     * @author CE Chen
     *
     * Group List Presenter
     */
    public interface IPresenter {

        public void getGroupList();

        public void list2GroupMap(List<EMGroup> value);

    }

}
