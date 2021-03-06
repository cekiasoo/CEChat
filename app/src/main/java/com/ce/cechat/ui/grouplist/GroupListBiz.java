package com.ce.cechat.ui.grouplist;

import com.ce.cechat.utils.ThreadPools;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 *
 */
public class GroupListBiz implements IGroupListContract.IGroupListBiz {

    @Inject
    public GroupListBiz() {
    }

    @Override
    public void getGroupList(final EMValueCallBack<List<EMGroup>> pEmValueCallBack) {

        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().groupManager().asyncGetJoinedGroupsFromServer(pEmValueCallBack);
            }
        });

    }

    @Override
    public Map<String, EMGroup> list2GroupMap(List<EMGroup> pAllGroups) {
        Map<String, EMGroup> map = new HashMap<>();

        if (pAllGroups == null || pAllGroups.isEmpty()) {
            return map;
        }

        for (EMGroup group :
                pAllGroups) {
            if (group != null) {
                map.put(group.getGroupName(), group);
            }
        }

        return map;

    }
}
