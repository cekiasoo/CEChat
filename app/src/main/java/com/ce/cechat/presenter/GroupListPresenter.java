package com.ce.cechat.presenter;

import android.os.Handler;

import com.ce.cechat.model.biz.GroupListBiz;
import com.ce.cechat.model.biz.IGroupListBiz;
import com.ce.cechat.view.fragment.main.IGroupListView;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;

import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 *
 * Group List Presenter
 */
public class GroupListPresenter {

    private static final String TAG = "GroupListPresenter";
    private IGroupListView mGroupListView;

    private Handler mHandler = new Handler();

    public GroupListPresenter(IGroupListView mGroupListView) {
        this.mGroupListView = mGroupListView;
    }

    public void getGroupList() {
        IGroupListBiz groupListBiz = new GroupListBiz();
        groupListBiz.getGroupList(new EMValueCallBack<List<EMGroup>>() {
            @Override
            public void onSuccess(final List<EMGroup> value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mGroupListView.onGetGroupListSuccess(value);
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mGroupListView.onGetGroupListFailed(error, errorMsg);
                    }
                });
            }
        });
    }

    public void list2GroupMap(List<EMGroup> value) {
        IGroupListBiz groupListBiz = new GroupListBiz();
        Map<String, EMGroup> groupMap = groupListBiz.list2GroupMap(value);
        mGroupListView.updateGroupList(groupMap);
    }
}
