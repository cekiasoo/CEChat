package com.ce.cechat.model.biz;

import com.ce.cechat.model.bean.User;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * @author CE Chen
 */
public interface IContactListener {

    /**
     * 获取所有联系人成功
     * @param pAllContact 所有联系人
     */
    void getAllContactSuccess(List<User> pAllContact);

    /**
     * 获取所有联系人失败
     * @param e 异常
     */
    void getAllContactFailed(HyphenateException e);

}
