package com.ce.cechat.ui.addcontact;

import android.util.Log;

import com.ce.cechat.app.BasePresenter;
import com.ce.cechat.bean.User;
import com.hyphenate.EMCallBack;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Search Presenter
 */
public class SearchPresenter extends BasePresenter<ISearchContract.ISearchView, SearchBiz> implements ISearchContract.IPresenter {

    private static final String TAG = "SearchPresenter";

    @Inject
    public SearchPresenter() {
    }

    /**
     * 判断用户输入的 用户 id 是否为空
     * @param pUserId 用户 id
     * @return 是返回 true 否返回 false
     */
    @Override
    public boolean isUserIdEmpty(String pUserId) {
        boolean empty = mBiz.isUserIdEmpty(pUserId);
        if (empty) {
            if (mView != null && mView.isActive()) {
                mView.emptyUserId();
            }
        }
        return empty;
    }

    /**
     * 根据用户 id 查找用户
     */
    @Override
    public void search() {
        User user = mBiz.search(mView.getUserId());
        if (mView != null && mView.isActive()) {
            if (user != null) {
                mView.searchSuccess(user);
                mView.clearSearch();
            } else {
                mView.searchFailed();
            }
        }
    }

    /**
     * 添加新好友
     * @param pReason 添加好友的原因
     */
    @Override
    public void addNewFriend(String pReason) {
        mBiz.addNewFriend(mView.getAddUserId(), pReason, new EMCallBack() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.addSuccess();
                        }
                    }
                });

            }

            @Override
            public void onError(final int code, final String error) {
                Log.v(TAG, "code = " + code + ", error = " + error);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null && mView.isActive()) {
                            mView.addFailed(code, error);
                        }
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }
}
