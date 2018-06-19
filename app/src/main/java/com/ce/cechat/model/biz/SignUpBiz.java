package com.ce.cechat.model.biz;

import android.text.TextUtils;

import com.ce.cechat.model.thread.ThreadPools;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 *
 * 注册业务逻辑实现类
 *
 */
public class SignUpBiz extends AbstractLoginBiz implements ISignUpBiz {

    @Override
    public boolean isConfirmPasswordEmpty(String password) {
        return TextUtils.isEmpty(password);
    }

    @Override
    public boolean isPasswordVail(String password) {
        return password.length() > 5;
    }

    @Override
    public boolean confirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public void signUp(final String id, final String password, final ISignUpListener pSignUpListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(id, password);
                    pSignUpListener.onSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pSignUpListener.onFailed(e);
                }
            }
        });
    }

}
