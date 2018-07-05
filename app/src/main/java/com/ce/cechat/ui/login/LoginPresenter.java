package com.ce.cechat.ui.login;

import android.util.Log;

import com.ce.cechat.app.BasePresenter;
import com.hyphenate.EMCallBack;

import javax.inject.Inject;


/**
 * @author CE Chen
 *
 * Login presenter
 */
public class LoginPresenter extends BasePresenter<ILoginContract.ILoginView, LoginBiz> implements ILoginContract.IPresenter {

    private static final String TAG = "LoginPresenter";

    @Inject
    public LoginPresenter() {
    }

    /**
     * 用户 id 是否是空的
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isEmptyUserId() {
        boolean isEmpty = mBiz.isUserIdEmpty(mView.getUserId());
        if (isEmpty) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mView != null && mView.isActive()) {
                        mView.emptyUserId();
                    }
                }
            });
        }
        return isEmpty;
    }

    /**
     * 密码是否是空的
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isEmptyPassword() {
        boolean isEmpty = mBiz.isPasswordEmpty(mView.getPassword());
        if (isEmpty) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mView != null && mView.isActive()) {
                        mView.emptyPassword();
                    }
                }
            });
        }
        return isEmpty;
    }

    /**
     * 是否是有效的用户 id
     * @return 是返回true 否返回false
     */
    @Override
    public boolean isUserIdValid() {
        boolean isValid = mBiz.isUserIdValid(mView.getUserId());
        if (!isValid) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mView != null && mView.isActive()) {
                        mView.invalidUserId();
                    }
                }
            });
        }
        return isValid;
    }

    /**
     * 登录
     */
    @Override
    public void login() {
        mBiz.onLogin(mView.getUserId(), mView.getPassword(), new EMCallBack() {
            @Override
            public void onSuccess() {
                mBiz.addAccount2Db(mView.getUserId());
                mBiz.initDb(mView.getUserId());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //登录成功
                        if (mView != null) {
                            mView.onSuccess();
                        }
                    }
                });
            }

            @Override
            public void onError(final int code, final String error) {
                Log.v(TAG, "code = " + code + ", error = " + error);
                //登录失败
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onFailed(code, error);
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
