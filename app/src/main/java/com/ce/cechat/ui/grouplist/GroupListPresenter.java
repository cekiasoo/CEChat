package com.ce.cechat.ui.grouplist;

import com.ce.cechat.app.BasePresenter;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Group List Presenter
 */
public class GroupListPresenter extends BasePresenter<IGroupListContract.IGroupListView, GroupListBiz>
        implements IGroupListContract.IPresenter {

    private static final String TAG = "GroupListPresenter";

    @Inject
    public GroupListPresenter() {
    }

    @Override
    public void getGroupList() {
        mBiz.getGroupList(new EMValueCallBack<List<EMGroup>>() {
            @Override
            public void onSuccess(final List<EMGroup> value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onGetGroupListSuccess(value);
                        }
                    }
                });
            }

            @Override
            public void onError(final int error, final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onGetGroupListFailed(error, errorMsg);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void list2GroupMap(List<EMGroup> value) {
        Map<String, EMGroup> groupMap = mBiz.list2GroupMap(value);
        if (mView != null) {
            mView.updateGroupList(groupMap);
        }
    }
}
