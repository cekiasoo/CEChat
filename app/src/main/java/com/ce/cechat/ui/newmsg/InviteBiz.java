package com.ce.cechat.ui.newmsg;

import android.util.Log;

import com.ce.cechat.bean.Invitation;
import com.ce.cechat.bean.User;
import com.ce.cechat.data.biz.DbBiz;
import com.ce.cechat.app.EventListener;
import com.ce.cechat.utils.ThreadPools;
import com.ce.cechat.utils.SharedPreferencesUtils;
import com.ce.cechat.app.App;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import static com.ce.cechat.bean.Invitation.InvitationStatus.GROUP_ACCEPT_APPLICATION;
import static com.ce.cechat.bean.Invitation.InvitationStatus.GROUP_ACCEPT_INVITE;
import static com.ce.cechat.bean.Invitation.InvitationStatus.GROUP_REFUSE_APPLICATION;
import static com.ce.cechat.bean.Invitation.InvitationStatus.GROUP_REFUSE_INVITE;
import static com.ce.cechat.bean.Invitation.InvitationStatus.INVITE_ACCEPT;
import static com.ce.cechat.bean.Invitation.InvitationStatus.INVITE_REFUSE;


/**
 * @author CE Chen
 *
 *
 */
public class InviteBiz implements IInviteContract.IInviteBiz {

    private static final String TAG = "InviteBiz";

    @Inject
    public InviteBiz() {
    }

    @Override
    public List<Invitation> getInvitations() {
        return DbBiz.newInstance().getDbManager().getInvitationDao().getInvitations();
    }

    @Override
    public void clearInviteRedDot() {
        SharedPreferencesUtils.remove(App.getInstance(), EventListener.KEY_IS_NEW_INVITE);
    }


    /**
     * 同意联系人的邀请
     * @param pInvitation Invitation
     * @param pInviteListener InviteListener
     */
    @Override
    public void acceptInviteContact(final Invitation pInvitation, final InviteListener pInviteListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance()
                            .contactManager()
                            .acceptInvitation(pInvitation.getUser().getHyphenateId());
                    pInviteListener.onSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pInviteListener.onFailed(e);
                }
            }
        });
    }

    /**
     * 同意邀请加入群
     * @param pInvitation Invitation
     * @param pInviteListener InviteListener
     */
    @Override
    public void acceptInviteGroup(final Invitation pInvitation, final InviteListener pInviteListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance()
                            .groupManager()
                            .acceptInvitation(pInvitation.getGroup().getGroupId(),
                                    pInvitation.getGroup().getInviteUser());
                    pInviteListener.onSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pInviteListener.onFailed(e);
                }
            }
        });
    }

    /**
     * 同意申请加入群
     * @param pInvitation Invitation
     * @param pInviteListener InviteListener
     */
    @Override
    public void acceptApplicationGroup(final Invitation pInvitation, final InviteListener pInviteListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance()
                            .groupManager()
                            .acceptApplication(pInvitation.getGroup().getInviteUser(),
                                    pInvitation.getGroup().getGroupId());
                    pInviteListener.onSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pInviteListener.onFailed(e);
                }
            }
        });
    }


    @Override
    public void refuseInviteContact(final Invitation pInvitation, final InviteListener pInviteListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance()
                            .contactManager()
                            .declineInvitation(pInvitation.getUser().getHyphenateId());
                    pInviteListener.onSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pInviteListener.onFailed(e);
                }
            }
        });
    }

    @Override
    public void refuseInviteGroup(final Invitation pInvitation, final InviteListener pInviteListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance()
                            .groupManager()
                            .declineInvitation(pInvitation.getGroup().getGroupId(),
                                    pInvitation.getGroup().getInviteUser(),
                                    pInvitation.getReason());
                    pInviteListener.onSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pInviteListener.onFailed(e);
                }
            }
        });
    }

    @Override
    public void refuseApplicationGroup(final Invitation pInvitation, final InviteListener pInviteListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance()
                            .groupManager()
                            .declineApplication(pInvitation.getGroup().getInviteUser(),
                                    pInvitation.getGroup().getGroupId(),
                                    pInvitation.getReason());
                    pInviteListener.onSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pInviteListener.onFailed(e);
                }
            }
        });
    }

    @Override
    public void updateAcceptInviteContactStatus(Invitation pInvitation) {
        boolean b = DbBiz.newInstance()
                .getDbManager()
                .getInvitationDao()
                .updateContactInvitationStatus(INVITE_ACCEPT, pInvitation.getUser().getHyphenateId());
    }

    @Override
    public void updateAcceptInviteGroupStatus(Invitation pInvitation) {
        boolean b = DbBiz.newInstance()
                .getDbManager()
                .getInvitationDao()
                .updateGroupInvitationStatus(GROUP_ACCEPT_INVITE, pInvitation.getGroup().getGroupId());
    }

    @Override
    public void updateAcceptApplicationGroupStatus(Invitation pInvitation) {
        boolean b = DbBiz.newInstance()
                .getDbManager()
                .getInvitationDao()
                .updateGroupInvitationStatus(GROUP_ACCEPT_APPLICATION, pInvitation.getGroup().getGroupId());
    }

    @Override
    public void updateRefuseInviteContactStatus(Invitation pInvitation) {
        boolean b = DbBiz.newInstance()
                .getDbManager()
                .getInvitationDao()
                .updateContactInvitationStatus(INVITE_REFUSE, pInvitation.getUser().getHyphenateId());
    }

    @Override
    public void updateRefuseInviteGroupStatus(Invitation pInvitation) {
        boolean b = DbBiz.newInstance()
                .getDbManager()
                .getInvitationDao()
                .updateGroupInvitationStatus(GROUP_REFUSE_INVITE, pInvitation.getGroup().getGroupId());
    }

    @Override
    public void updateRefuseApplicationGroupStatus(Invitation pInvitation) {
        boolean b = DbBiz.newInstance()
                .getDbManager()
                .getInvitationDao()
                .updateGroupInvitationStatus(GROUP_REFUSE_APPLICATION, pInvitation.getGroup().getGroupId());
    }

    @Override
    public void inviteContactsInGroup(final String pGroupId, final List<String> pContacts, final String reason, final EMCallBack callback) {
        Log.v(TAG, "inviteContactsInGroup reason = " + reason);
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance()
                            .groupManager()
                            .inviteUser(pGroupId, pContacts.toArray(new String[]{}), reason);
                    callback.onSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    callback.onError(e.getErrorCode(), e.getDescription());
                }
            }
        });

    }

    private List<String> users2StringList(List<User> pContacts) {
        List<String> pUsers = new LinkedList<>();
        for (User pContact : pContacts) {
            pUsers.add(pContact.getHyphenateId());
        }
        return pUsers;
    }

}
