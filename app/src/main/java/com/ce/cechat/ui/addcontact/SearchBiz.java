package com.ce.cechat.ui.addcontact;

import android.text.TextUtils;

import com.ce.cechat.bean.User;
import com.ce.cechat.utils.ThreadPools;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * 查找用户逻辑实现
 */
public class SearchBiz implements ISearchContract.ISearchBiz {

    @Inject
    public SearchBiz() {
    }

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
