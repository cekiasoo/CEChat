package com.ce.cechat.model.biz;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;

import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 *
 * 群列表业务逻辑操作类接口
 */
public interface IGroupListBiz {

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
