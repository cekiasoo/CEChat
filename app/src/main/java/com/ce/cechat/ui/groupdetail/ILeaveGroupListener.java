package com.ce.cechat.ui.groupdetail;

import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 * <p>
 * 作用 : 退出群回调
 */
public interface ILeaveGroupListener {

    /**
     * 退出群成功
     */
    void leaveGroupSuccess();

    /**
     * 退出群失败
     * @param e 异常
     */
    void leaveGroupFailed(HyphenateException e);

}
