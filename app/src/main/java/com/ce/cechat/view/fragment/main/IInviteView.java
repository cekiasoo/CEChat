package com.ce.cechat.view.fragment.main;

import com.ce.cechat.model.bean.Invitation;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * @author CE Chen
 */
public interface IInviteView extends IFragView{

    /**
     * 设置邀请消息
     *
     * @param pInvitations Invitations
     */
    void setInvitations(List<Invitation> pInvitations);

    /**
     * 同意被邀请做好友成功
     */
    void onAcceptInviteContactSuccess();

    /**
     * 同意被邀请做好友失败
     *
     * @param e 异常
     */
    void onAcceptInviteContactFailed(HyphenateException e);

    /**
     * 同意邀请入群成功
     */
    void onAcceptInviteGroupSuccess();

    /**
     * 同意邀请入群失败
     *
     * @param e 异常
     */
    void onAcceptInviteGroupFailed(HyphenateException e);

    /**
     * 同意申请入群成功
     */
    void onAcceptApplicationGroupSuccess();

    /**
     * 同意申请入群失败
     *
     * @param e 异常
     */
    void onAcceptApplicationGroupFailed(HyphenateException e);

    /**
     * 拒绝被邀请做好友成功
     */
    void onRefuseInviteContactSuccess();

    /**
     * 拒绝被邀请做好友失败
     * @param e 异常
     */
    void onRefuseInviteContactFailed(HyphenateException e);

    /**
     * 拒绝邀请入群成功
     */
    void onRefuseInviteGroupSuccess();

    /**
     * 拒绝邀请入群失败
     * @param e 异常
     */
    void onRefuseInviteGroupFailed(HyphenateException e);

    /**
     * 拒绝申请入群成功
     */
    void onRefuseApplicationGroupSuccess();

    /**
     * 拒绝申请入群失败
     * @param e 异常
     */
    void onRefuseApplicationGroupFailed(HyphenateException e);

}
