package com.ce.cechat.view.fragment.main;

import com.ce.cechat.model.bean.User;
import com.hyphenate.easeui.domain.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 */
public interface IContactView extends IFragView{

    /**
     * 显示有邀请消息的红点
     */
    void showInviteRedDot();


    /**
     * 隐藏邀请消息的红点
     */
    void hideInviteRedDot();

    /**
     * 获取所有联系人成功
     * @param pAllContact 所有联系人
     */
    void onGetAllContactSuccess(List<User> pAllContact);


    /**
     * 获取所有联系人失败
     * @param e 异常
     */
    void onGetAllContactFailed(HyphenateException e);

    /**
     * 更新联系人列表
     * @param pUserMap UserMap
     */
    void updateContactList(Map<String, EMGroup> pUserMap);

}
