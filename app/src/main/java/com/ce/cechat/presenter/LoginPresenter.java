package com.ce.cechat.presenter;

import android.os.Handler;
import android.util.Log;

import com.ce.cechat.model.biz.BaseLoginBiz;
import com.ce.cechat.model.biz.ILoginBiz;
import com.ce.cechat.model.biz.LoginBiz;
import com.ce.cechat.model.biz.SignUpBiz;
import com.ce.cechat.view.fragment.login.ILoginView;
import com.hyphenate.EMCallBack;


/**
 * @author CE Chen
 *
 * Login presenter
 */
public class LoginPresenter {

    private static final String TAG = "LoginPresenter";

    protected Handler mHandler = new Handler();

    private ILoginView mLoginView;

    public LoginPresenter(ILoginView mLoginView) {
        this.mLoginView = mLoginView;
    }

    /**
     * 用户 id 是否是空的
     * @return 是返回true 否返回false
     */
    public boolean isEmptyUserId() {
        BaseLoginBiz signUpBiz = new SignUpBiz();
        boolean isEmpty = signUpBiz.isUserIdEmpty(mLoginView.getUserId());
        if (isEmpty) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mLoginView.isActive()) {
                        mLoginView.emptyUserId();
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
    public boolean isEmptyPassword() {
        BaseLoginBiz signUpBiz = new SignUpBiz();
        boolean isEmpty = signUpBiz.isPasswordEmpty(mLoginView.getPassword());
        if (isEmpty) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mLoginView.isActive()) {
                        mLoginView.emptyPassword();
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
    public boolean isUserIdValid() {
        BaseLoginBiz signUpBiz = new SignUpBiz();
        boolean isValid = signUpBiz.isUserIdValid(mLoginView.getUserId());
        if (!isValid) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mLoginView.isActive()) {
                        mLoginView.invalidUserId();
                    }
                }
            });
        }
        return isValid;
    }

    /**
     * 登录
     */
    public void login() {
        final ILoginBiz loginBiz = new LoginBiz();
        loginBiz.onLogin(mLoginView.getUserId(), mLoginView.getPassword(), new EMCallBack() {
            @Override
            public void onSuccess() {
                loginBiz.addAccount2Db(mLoginView.getUserId());
                loginBiz.initDb(mLoginView.getUserId());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //登录成功
                        if (mLoginView.isActive()) {
                            mLoginView.onSuccess();
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
                        if (mLoginView.isActive()) {
                            mLoginView.onFailed(code, error);
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
