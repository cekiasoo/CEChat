package com.ce.cechat.model.biz;

import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 * <p>
 * 作用 : 解散群回调
 */
public interface IDestroyGroupListener {

    /**
     * 解散群成功
     */
    void destroyGroupSuccess();

    /**
     * 解散群失败
     * @param e 异常
     */
    void destroyGroupFailed(HyphenateException e);

}
