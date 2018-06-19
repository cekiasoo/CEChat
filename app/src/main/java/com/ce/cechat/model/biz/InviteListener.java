package com.ce.cechat.model.biz;

import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 *
 *
 */
public interface InviteListener {

    /**
     * 成功
     */
    void onSuccess();

    /**
     * 失败
     * @param e 异常
     */
    void onFailed(HyphenateException e);

}
