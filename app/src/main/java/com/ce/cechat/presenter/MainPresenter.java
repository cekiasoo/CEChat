package com.ce.cechat.presenter;

import android.os.Handler;

import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.biz.DbBiz;
import com.ce.cechat.model.biz.IMainBiz;
import com.ce.cechat.model.biz.MainBiz;
import com.ce.cechat.view.activity.IMainView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * @author CE Chen
 *
 * Main Presenter
 */
public class MainPresenter {

    private IMainView mMainView;

    private Handler mHandler = new Handler();

    public MainPresenter(IMainView mMainView) {
        this.mMainView = mMainView;
    }

    /**
     * 退出当前账号
     */
    public void logout() {
        final IMainBiz mainBiz = new MainBiz();
        mainBiz.logout(new EMCallBack() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainBiz.closeDb();
                        mMainView.logoutSuccess();
                    }
                });
            }

            @Override
            public void onError(final int code, final String error) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.logoutFailed(code, error);
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 获取当前账号的名称并设置
     */
    public void setName() {
        IMainBiz mainBiz = new MainBiz();
        mMainView.setName(mainBiz.getCurrentUser());
    }


    public void initDb() {
        if (DbBiz.newInstance().getDbManager() == null) {
            String user = EMClient.getInstance().getCurrentUser();
            if (user != null) {
                DbBiz.newInstance().loginSuccess(new User(user));
            }
        }

    }
}
