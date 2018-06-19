package com.ce.cechat.model.biz;

import android.text.TextUtils;

import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.thread.ThreadPools;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * @author CE Chen
 *
 * 查找用户逻辑实现
 */
public class SearchBiz implements ISearchBiz {
    @Override
    public User search(String pUserId) {
        return new User(pUserId);
    }

    @Override
    public boolean isUserIdEmpty(String pUserId) {
        return TextUtils.isEmpty(pUserId);
    }

    @Override
    public void addNewFriend(final String pUserId, final String pReason, final EMCallBack pCallBack) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().contactManager().aysncAddContact(pUserId, pReason, pCallBack);
            }
        });
    }

}
