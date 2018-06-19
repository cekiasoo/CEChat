package com.ce.cechat.model.biz;

import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 *
 * 注册回调接口
 */
public interface ISignUpListener {

    /**
     * 注册成功
     */
    void onSuccess();

    /**
     * 注册失败
     *
     * @param e 异常
     */
    void onFailed(HyphenateException e);

}
