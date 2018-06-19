package com.ce.cechat.presenter;

import android.os.Handler;

import com.ce.cechat.model.bean.Invitation;
import com.ce.cechat.model.biz.IInviteBiz;
import com.ce.cechat.model.biz.InviteBiz;
import com.ce.cechat.model.biz.InviteListener;
import com.ce.cechat.view.fragment.main.IInviteView;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * @author CE Chen
 *
 * Invite Presenter
 */
public class InvitePresenter {

    private Handler mHandler = new Handler();

    private IInviteView mInviteView;

    public InvitePresenter(IInviteView pInviteView) {
        this.mInviteView = pInviteView;
    }

    public void clearInviteRedDot() {
        IInviteBiz inviteBiz = new InviteBiz();
        inviteBiz.clearInviteRedDot();
    }

    public void getInvitations() {
        IInviteBiz inviteBiz = new InviteBiz();
        final List<Invitation> invitations = inviteBiz.getInvitations();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mInviteView.setInvitations(invitations);
            }
        });
    }

    /**
     * 同意被邀请做好友
     * @param pInvitation Invitation
     */
    public void acceptInviteContact(final Invitation pInvitation) {
        final IInviteBiz inviteBiz = new InviteBiz();
        inviteBiz.acceptInviteContact(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                inviteBiz.updateAcceptInviteContactStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onAcceptInviteContactSuccess();
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onAcceptInviteContactFailed(e);
                    }
                });
            }
        });
    }

    /**
     * 同意邀请入群
     * @param pInvitation Invitation
     */
    public void acceptInviteGroup(final Invitation pInvitation) {
        final IInviteBiz inviteBiz = new InviteBiz();
        inviteBiz.acceptInviteGroup(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                inviteBiz.updateAcceptInviteGroupStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onAcceptInviteGroupSuccess();
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onAcceptInviteGroupFailed(e);
                    }
                });
            }
        });
    }

    /**
     * 同意申请入群
     * @param pInvitation Invitation
     */
    public void acceptApplicationGroup(final Invitation pInvitation) {
        final IInviteBiz inviteBiz = new InviteBiz();
        inviteBiz.acceptApplicationGroup(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                inviteBiz.updateAcceptApplicationGroupStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onAcceptApplicationGroupSuccess();
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onAcceptApplicationGroupFailed(e);
                    }
                });
            }
        });
    }

    /**
     * 拒绝被邀请做好友
     * @param pInvitation Invitation
     */
    public void refuseInviteContact(final Invitation pInvitation) {
        final IInviteBiz inviteBiz = new InviteBiz();

        inviteBiz.refuseInviteContact(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                inviteBiz.updateRefuseInviteContactStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onRefuseInviteContactSuccess();
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onRefuseInviteContactFailed(e);
                    }
                });
            }
        });
    }

    /**
     * 拒绝被邀请入群
     * @param pInvitation Invitation
     */
    public void refuseInviteGroup(final Invitation pInvitation) {
        final IInviteBiz inviteBiz = new InviteBiz();

        inviteBiz.refuseInviteGroup(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                inviteBiz.updateRefuseInviteGroupStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onRefuseInviteGroupSuccess();
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onRefuseInviteGroupFailed(e);
                    }
                });
            }
        });
    }

    /**
     * 拒绝被邀请入群
     * @param pInvitation Invitation
     */
    public void refuseApplicationGroup(final Invitation pInvitation) {
        final IInviteBiz inviteBiz = new InviteBiz();

        inviteBiz.refuseApplicationGroup(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                inviteBiz.updateRefuseApplicationGroupStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onRefuseApplicationGroupSuccess();
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInviteView.onRefuseApplicationGroupFailed(e);
                    }
                });
            }
        });
    }
}
