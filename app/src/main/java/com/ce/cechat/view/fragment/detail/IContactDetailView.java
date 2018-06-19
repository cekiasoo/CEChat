package com.ce.cechat.view.fragment.detail;

import com.ce.cechat.view.fragment.main.IFragView;
import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 */
public interface IContactDetailView extends IFragView {

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
