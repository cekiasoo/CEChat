package com.ce.cechat.view.fragment.main;

import com.hyphenate.chat.EMGroup;

import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 *
 *
 */
public interface IGroupListView extends IFragView{

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
