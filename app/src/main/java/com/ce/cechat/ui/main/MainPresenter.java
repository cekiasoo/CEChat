package com.ce.cechat.ui.main;

import com.ce.cechat.app.BasePresenter;
import com.ce.cechat.bean.User;
import com.ce.cechat.data.biz.DbBiz;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Main Presenter
 */
public class MainPresenter extends BasePresenter<IMainContract.IMainView, MainBiz> implements IMainContract.IPresenter {

    @Inject
    public MainPresenter() {
    }

    /**
     * 退出当前账号
     */
    @Override
    public void logout() {
        mBiz.logout(new EMCallBack() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBiz.closeDb();
                        if (mView != null) {
                            mView.logoutSuccess();
                        }
                    }
                });
            }

            @Override
            public void onError(final int code, final String error) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.logoutFailed(code, error);
                        }
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
    @Override
    public void setName() {
        mView.setName(mBiz.getCurrentUser());
    }


    @Override
    public void initDb() {
        if (DbBiz.newInstance().getDbManager() == null) {
            String user = EMClient.getInstance().getCurrentUser();
            if (user != null) {
                DbBiz.newInstance().loginSuccess(new User(user));
            }
        }

    }
}
