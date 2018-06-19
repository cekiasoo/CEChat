package com.ce.cechat.model.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author CE Chen
 *
 * 邀请
 */
public class Invitation {

    private User user;

    private Group group;

    private String reason;

    private int invitationStatus;

    public Invitation() {
    }

    public Invitation(User user, String reason, int invitationStatus) {
        this.user = user;
        this.reason = reason;
        this.invitationStatus = invitationStatus;
    }

    public Invitation(Group group, String reason, int invitationStatus) {
        this.group = group;
        this.reason = reason;
        this.invitationStatus = invitationStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getInvitationStatus() {
        return invitationStatus;
    }

    /**
     *
     * @param invitationStatus invitation Status
     */
    public void setInvitationStatus(@IInvitationStatus int invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    @IntDef({InvitationStatus.NEW_INVITE,
            InvitationStatus.INVITE_ACCEPT,
            InvitationStatus.INVITE_REFUSE,
            InvitationStatus.INVITE_ACCEPT_BY_PEER,
            InvitationStatus.INVITE_REFUSE_BY_PEER,
            InvitationStatus.NEW_GROUP_INVITE,
            InvitationStatus.NEW_GROUP_APPLICATION,
            InvitationStatus.GROUP_INVITE_ACCEPTED,
            InvitationStatus.GROUP_APPLICATION_ACCEPTED,
            InvitationStatus.GROUP_ACCEPT_INVITE,
            InvitationStatus.GROUP_AUTO_ACCEPT_INVITE,
            InvitationStatus.GROUP_ACCEPT_APPLICATION,
            InvitationStatus.GROUP_REFUSE_INVITE,
            InvitationStatus.GROUP_REFUSE_APPLICATION,
            InvitationStatus.GROUP_INVITE_DECLINED,
            InvitationStatus.GROUP_APPLICATION_DECLINED,
            InvitationStatus.GROUP_REMOVE_FROM_GROUP,
            InvitationStatus.GROUP_DESTROY_GROUP,
            InvitationStatus.GROUP_MEMBER_LEAVE,
            InvitationStatus.GROUP_MEMBER_JOIN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IInvitationStatus {

    }

    public static class InvitationStatus{




        // 联系人邀请信息状态

        /**
         * 新邀请
         */
        public static final int NEW_INVITE = 1;

        /**
         * 接受邀请
         */
        public static final int INVITE_ACCEPT = NEW_INVITE + 1;

        /**
         * 拒绝邀请
         */
        public static final int INVITE_REFUSE = INVITE_ACCEPT + 1;

        /**
         * 邀请被接受
         */
        public static final int INVITE_ACCEPT_BY_PEER = INVITE_REFUSE + 1;

        /**
         * 邀请被拒绝
         */
        public static final int INVITE_REFUSE_BY_PEER = INVITE_ACCEPT_BY_PEER + 1;



        //---------------------


        // 群组邀请信息状态

        /**
         * 收到邀请去加入群
         */
        public static final int NEW_GROUP_INVITE = INVITE_REFUSE_BY_PEER + 1;

        /**
         * 收到申请群加入
         */
        public static final int NEW_GROUP_APPLICATION = NEW_GROUP_INVITE + 1;

        /**
         * 群邀请已经被对方接受
         */
        public static final int GROUP_INVITE_ACCEPTED = NEW_GROUP_APPLICATION + 1;

        /**
         * 加群申请被对方同意了
         */
        public static final int GROUP_APPLICATION_ACCEPTED = GROUP_INVITE_ACCEPTED + 1;

        /**
         * 接受了群邀请
         */
        public static final int GROUP_ACCEPT_INVITE = GROUP_APPLICATION_ACCEPTED + 1;

        /**
         * 自动接受了群邀请
         */
        public static final int GROUP_AUTO_ACCEPT_INVITE = GROUP_ACCEPT_INVITE + 1;

        /**
         * 批准加入群的申请
         */
        public static final int GROUP_ACCEPT_APPLICATION = GROUP_AUTO_ACCEPT_INVITE + 1;

        /**
         * 拒绝了群邀请
         */
        public static final int GROUP_REFUSE_INVITE = GROUP_ACCEPT_APPLICATION + 1;

        /**
         * 拒绝了用户的群申请加入
         */
        public static final int GROUP_REFUSE_APPLICATION = GROUP_REFUSE_INVITE + 1;

        /**
         * 群邀请被对方拒绝
         */
        public static final int GROUP_INVITE_DECLINED = GROUP_REFUSE_APPLICATION + 1;

        /**
         * 加群申请被拒绝
         */
        public static final int GROUP_APPLICATION_DECLINED = GROUP_INVITE_DECLINED + 1;

        /**
         * 当前用户被移出群
         */
        public static final int GROUP_REMOVE_FROM_GROUP = GROUP_APPLICATION_DECLINED + 1;

        /**
         * 群被解散了
         */
        public static final int GROUP_DESTROY_GROUP = GROUP_REMOVE_FROM_GROUP + 1;

        /**
         * 群成员退出群
         */
        public static final int GROUP_MEMBER_LEAVE = GROUP_DESTROY_GROUP + 1;

        /**
         * 群成员加入群
         */
        public static final int GROUP_MEMBER_JOIN = GROUP_MEMBER_LEAVE + 1;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "user=" + user +
                ", group=" + group +
                ", invitationStatus=" + invitationStatus +
                '}';
    }
}
