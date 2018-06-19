package com.ce.cechat.model.biz;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * @author CE Chen
 */
public abstract class AbstractLoginBiz implements BaseLoginBiz {

    /**
     * 小写字母和数字组合 5 到 12个字符
     */
    private static final Pattern PATTERN_USER_ID = Pattern.compile("^[a-z\\d]{5,12}$");

    @Override
    public boolean isUserIdEmpty(String id) {
        return TextUtils.isEmpty(id);
    }

    @Override
    public boolean isPasswordEmpty(String password) {
        return TextUtils.isEmpty(password);
    }

    @Override
    public boolean isUserIdValid(String id) {
        return PATTERN_USER_ID.matcher(id).matches();
    }
}
