package com.ce.cechat.ui.login;

import android.util.Log;

import com.ce.cechat.app.BasePresenter;
import com.hyphenate.exceptions.HyphenateException;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Sign in presenter
 */
public class SignUpPresenter extends BasePresenter<ISignUpContract.ISignUpView, SignUpBiz> implements ISignUpContract.IPresenter {

    private static final String TAG = "SignUpPresenter";


    @Inject
    public SignUpPresenter() {
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
     * 密码是否是空的
     * @return 是返回 true 否返回 false
     */
    @Override
    public boolean isConfirmPasswordEmpty(){
        boolean isEmpty = mBiz.isConfirmPasswordEmpty(mView.getConfirmPassword());
        if (isEmpty) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mView != null && mView.isActive()) {
                        mView.emptyConfirmPassword();
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
    @Override
    public boolean isPasswordVail() {
        boolean isVail =  mBiz.isPasswordVail(mView.getPassword());
        if (!isVail) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mView != null && mView.isActive()) {
                        mView.invalidPassword();
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
    @Override
    public boolean isConfirmPasswordVail() {
        boolean isVail =  mBiz.confirmPassword(mView.getPassword(), mView.getConfirmPassword());
        if (!isVail) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mView != null && mView.isActive()) {
                        mView.invalidConfirmPassword();
                    }
                }
            });
        }
        return isVail;
    }

    /**
     * 注册
     */
    @Override
    public void signUp() {
        mBiz.signUp(mView.getUserId(), mView.getPassword(), new ISignUpListener() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //注册成功
                        if (mView != null) {
                            mView.onSuccess();
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
                        if (mView != null) {
                            mView.onFailed(e.getErrorCode(), e.getDescription());
                        }
                    }
                });

            }
        });
    }

}
