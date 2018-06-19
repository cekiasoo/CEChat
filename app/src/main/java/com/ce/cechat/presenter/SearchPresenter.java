package com.ce.cechat.presenter;

import android.os.Handler;
import android.util.Log;

import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.biz.ISearchBiz;
import com.ce.cechat.model.biz.SearchBiz;
import com.ce.cechat.view.fragment.main.ISearchView;
import com.hyphenate.EMCallBack;

/**
 * @author CE Chen
 *
 * Search Presenter
 */
public class SearchPresenter {

    private static final String TAG = "SearchPresenter";
    private ISearchView mSearchView;

    private Handler mHandler = new Handler();

    public SearchPresenter(ISearchView mSearchView) {
        this.mSearchView = mSearchView;
    }

    /**
     * 判断用户输入的 用户 id 是否为空
     * @param pUserId 用户 id
     * @return 是返回 true 否返回 false
     */
    public boolean isUserIdEmpty(String pUserId) {
        ISearchBiz searchBiz = new SearchBiz();
        boolean empty = searchBiz.isUserIdEmpty(pUserId);
        if (empty) {
            if (mSearchView.isActive()) {
                mSearchView.emptyUserId();
            }
        }
        return empty;
    }

    /**
     * 根据用户 id 查找用户
     */
    public void search() {
        ISearchBiz searchBiz = new SearchBiz();
        User user = searchBiz.search(mSearchView.getUserId());
        if (mSearchView.isActive()) {
            if (user != null) {
                mSearchView.searchSuccess(user);
                mSearchView.clearSearch();
            } else {
                mSearchView.searchFailed();
            }
        }
    }

    /**
     * 添加新好友
     * @param pReason 添加好友的原因
     */
    public void addNewFriend(String pReason) {
        ISearchBiz searchBiz = new SearchBiz();
        searchBiz.addNewFriend(mSearchView.getAddUserId(), pReason, new EMCallBack() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mSearchView.isActive()) {
                            mSearchView.addSuccess();
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
                        if (mSearchView.isActive()) {
                            mSearchView.addFailed(code, error);
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
