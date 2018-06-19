package com.ce.cechat.view.fragment.chat;

import com.ce.cechat.view.fragment.main.IFragView;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 * <p>
 * 作用 : IGroupDetailView 接口
 */
public interface IGroupDetailView extends IFragView {

    /**
     * 设置群名称
     * @param pGroupName 群名称
     */
    void setGroupName(String pGroupName);


    /**
     * 解散群成功
     */
    void destroyGroupSuccess();

    /**
     * 解散群失败
     *
     * @param e 异常
     */
    void destroyGroupFailed(HyphenateException e);

    /**
     * 退出群成功
     */
    void leaveGroupSuccess();

    /**
     * 退出群失败
     * @param e 异常
     */
    void leaveGroupFailed(HyphenateException e);


    /**
     * 获取群所有信息成功
     * @param pGroup 群所有信息
     */
    void getGroupDetailSuccess(EMGroup pGroup);

    /**
     * 获取群所有信息失败
     * @param error error
     * @param errorMsg error Msg
     */
    void getGroupDetailFailed(int error, String errorMsg);

    /**
     * 显示编辑
     */
    void showEdit();

    /**
     * 隐藏编辑
     */
    void hideEdit();

}
