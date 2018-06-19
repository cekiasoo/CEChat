package com.ce.cechat.model.biz;

import com.ce.cechat.model.bean.Invitation;
import com.hyphenate.EMCallBack;

import java.util.List;

/**
 * @author CE Chen
 *
 * 邀请消息业务逻辑操作接口
 */
public interface IInviteBiz {

    /**
     * 获取所有的邀请消息
     * @return 所有的邀请消息
     */
    List<Invitation> getInvitations();

    /**
     * 删除红点记录
     */
    void clearInviteRedDot();

    /**
     * 同意被邀请做好友
     * @param pInvitation Invitation
     * @param pInviteListener InviteListener
     */
    void acceptInviteContact(final Invitation pInvitation, final InviteListener pInviteListener);

    /**
     * 同意邀请加入群
     * @param pInvitation Invitation
     * @param pInviteListener InviteListener
     */
    void acceptInviteGroup(Invitation pInvitation, InviteListener pInviteListener);

    /**
     * 同意申请加入群
     * @param pInvitation Invitation
     * @param pInviteListener InviteListener
     */
    void acceptApplicationGroup(Invitation pInvitation, InviteListener pInviteListener);

    /**
     * 拒绝被邀请做好友
     * @param pInvitation Invitation
     * @param pInviteListener InviteListener
     */
    void refuseInviteContact(Invitation pInvitation, InviteListener pInviteListener);

    /**
     * 拒绝被邀请入群
     * @param pInvitation Invitation
     * @param pInviteListener InviteListener
     */
    void refuseInviteGroup(Invitation pInvitation, InviteListener pInviteListener);

    /**
     * 拒绝申请入群
     * @param pInvitation Invitation
     * @param pInviteListener InviteListener
     */
    void refuseApplicationGroup(Invitation pInvitation, InviteListener pInviteListener);

    /**
     * 更新同意被邀请做好友的数据库状态
     * @param pInvitation Invitation
     */
    void updateAcceptInviteContactStatus(Invitation pInvitation);

    /**
     * 更新拒绝被邀请做好友的数据库状态
     * @param pInvitation Invitation
     */
    void updateAcceptInviteGroupStatus(Invitation pInvitation);

    /**
     * 更新同意申请入群的数据库状态
     * @param pInvitation Invitation
     */
    void updateAcceptApplicationGroupStatus(Invitation pInvitation);

    /**
     * 更新拒绝申请入群的数据库状态
     * @param pInvitation Invitation
     */
    void updateRefuseInviteContactStatus(Invitation pInvitation);

    /**
     * 更新同意被邀请入群的数据库状态
     * @param pInvitation Invitation
     */
    void updateRefuseInviteGroupStatus(Invitation pInvitation);

    /**
     * 更新拒绝被邀请入群的数据库状态
     * @param pInvitation Invitation
     */
    void updateRefuseApplicationGroupStatus(Invitation pInvitation);

    /**
     * 邀请联系人进群
     * @param pGroupId 邀请进的群id
     * @param pContacts 邀请联系人
     * @param reason 邀请理由
     * @param callback 回调
     */
    void inviteContactsInGroup(String pGroupId, List<String> pContacts, String reason, EMCallBack callback);
}
