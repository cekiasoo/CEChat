package com.ce.cechat.presenter;

import android.util.Log;

import com.ce.cechat.model.biz.ISignUpBiz;
import com.ce.cechat.model.biz.ISignUpListener;
import com.ce.cechat.model.biz.SignUpBiz;
import com.ce.cechat.view.fragment.login.ISignUpView;
import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 *
 * Sign in presenter
 */
public class SignUpPresenter extends LoginPresenter{

    private ISignUpView mSignUpView;
    private static final String TAG = "SignUpPresenter";

    public SignUpPresenter(ISignUpView pSignUpView) {
        super(pSignUpView);
        this.mSignUpView = pSignUpView;
    }

    /**
     * 密码是否是空的
     * @return 是返回 true 否返回 false
     */
    public boolean isConfirmPasswordEmpty(){
        ISignUpBiz signUpBiz = new SignUpBiz();
        boolean isEmpty = signUpBiz.isConfirmPasswordEmpty(mSignUpView.getConfirmPassword());
        if (isEmpty) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mSignUpView.isActive()) {
                        mSignUpView.emptyConfirmPassword();
                    }
                }
            });
        }

        return isEmpty;
    }

    /**
     * 密码是否有效
     * @return 是返回 true 否返回 false
     */
    public boolean isPasswordVail() {
        ISignUpBiz signUpBiz = new SignUpBiz();
        boolean isVail =  signUpBiz.isPasswordVail(mSignUpView.getPassword());
        if (!isVail) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mSignUpView.isActive()) {
                        mSignUpView.invalidPassword();
                    }
                }
            });
        }
        return isVail;
    }


    /**
     * 确认密码是否一致
     * @return 是返回 true 否返回 false
     */
    public boolean isConfirmPasswordVail() {
        ISignUpBiz signUpBiz = new SignUpBiz();
        boolean isVail =  signUpBiz.confirmPassword(mSignUpView.getPassword(), mSignUpView.getConfirmPassword());
        if (!isVail) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mSignUpView.isActive()) {
                        mSignUpView.invalidConfirmPassword();
                    }
                }
            });
        }
        return isVail;
    }

    /**
     * 注册
     */
    public void signUp() {
        ISignUpBiz signUpBiz = new SignUpBiz();
        signUpBiz.signUp(mSignUpView.getUserId(), mSignUpView.getPassword(), new ISignUpListener() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //注册成功
                        if (mSignUpView.isActive()) {
                            mSignUpView.onSuccess();
                        }
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.v(TAG, "ErrorCode = " + e.getErrorCode() + ", Description = " + e.getDescription());
                        //注册失败
                        if (mSignUpView.isActive()) {
                            mSignUpView.onFailed(e.getErrorCode(), e.getDescription());
                        }
                    }
                });

            }
        });
    }

}
