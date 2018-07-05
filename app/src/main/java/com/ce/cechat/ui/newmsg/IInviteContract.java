package com.ce.cechat.ui.newmsg;

import com.ce.cechat.app.IBaseContact;
import com.ce.cechat.bean.Invitation;
import com.hyphenate.EMCallBack;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface IInviteContract {

    /**
     * @author CE Chen
     */
    public interface IInviteView extends IBaseContact.IBaseView {

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



    /**
     * @author CE Chen
     *
     * 邀请消息业务逻辑操作接口
     */
    public interface IInviteBiz extends IBaseContact.IBaseBiz {

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


    /**
     * @author CE Chen
     *
     * Invite Presenter
     */
    public interface IPresenter {

        public void clearInviteRedDot();

        public void getInvitations();


        /**
         * 同意被邀请做好友
         * @param pInvitation Invitation
         */
        public void acceptInviteContact(final Invitation pInvitation);

        /**
         * 同意邀请入群
         * @param pInvitation Invitation
         */
        public void acceptInviteGroup(final Invitation pInvitation);

        /**
         * 同意申请入群
         * @param pInvitation Invitation
         */
        public void acceptApplicationGroup(final Invitation pInvitation);

        /**
         * 拒绝被邀请做好友
         * @param pInvitation Invitation
         */
        public void refuseInviteContact(final Invitation pInvitation);

        /**
         * 拒绝被邀请入群
         * @param pInvitation Invitation
         */
        public void refuseInviteGroup(final Invitation pInvitation);

        /**
         * 拒绝被邀请入群
         * @param pInvitation Invitation
         */
        public void refuseApplicationGroup(final Invitation pInvitation);

    }

}
