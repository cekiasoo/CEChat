package com.ce.cechat.ui.detail;

import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 *
 *
 */
public interface DeleteContactListener {

    /**
     * 删除联系人成功
     */
    void onDeleteSuccess();

    /**
     * 删除联系人失败
     *
     * @param e 异常
     */
    void onDeleteFailed(HyphenateException e);

}
