package com.ce.cechat.ui.newmsg;

import com.ce.cechat.app.BasePresenter;
import com.ce.cechat.bean.Invitation;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Invite Presenter
 */
public class InvitePresenter extends BasePresenter<IInviteContract.IInviteView, InviteBiz>
        implements IInviteContract.IPresenter {


    @Inject
    public InvitePresenter() {
    }

    @Override
    public void clearInviteRedDot() {
        mBiz.clearInviteRedDot();
    }

    @Override
    public void getInvitations() {
        final List<Invitation> invitations = mBiz.getInvitations();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mView != null) {
                    mView.setInvitations(invitations);
                }
            }
        });
    }

    /**
     * 同意被邀请做好友
     * @param pInvitation Invitation
     */
    @Override
    public void acceptInviteContact(final Invitation pInvitation) {
        mBiz.acceptInviteContact(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                mBiz.updateAcceptInviteContactStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onAcceptInviteContactSuccess();
                        }
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onAcceptInviteContactFailed(e);
                        }
                    }
                });
            }
        });
    }

    /**
     * 同意邀请入群
     * @param pInvitation Invitation
     */
    @Override
    public void acceptInviteGroup(final Invitation pInvitation) {
        mBiz.acceptInviteGroup(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                mBiz.updateAcceptInviteGroupStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onAcceptInviteGroupSuccess();
                        }
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onAcceptInviteGroupFailed(e);
                        }
                    }
                });
            }
        });
    }

    /**
     * 同意申请入群
     * @param pInvitation Invitation
     */
    @Override
    public void acceptApplicationGroup(final Invitation pInvitation) {
        mBiz.acceptApplicationGroup(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                mBiz.updateAcceptApplicationGroupStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onAcceptApplicationGroupSuccess();
                        }
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onAcceptApplicationGroupFailed(e);
                        }
                    }
                });
            }
        });
    }

    /**
     * 拒绝被邀请做好友
     * @param pInvitation Invitation
     */
    @Override
    public void refuseInviteContact(final Invitation pInvitation) {
        mBiz.refuseInviteContact(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                mBiz.updateRefuseInviteContactStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onRefuseInviteContactSuccess();
                        }
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onRefuseInviteContactFailed(e);
                        }
                    }
                });
            }
        });
    }

    /**
     * 拒绝被邀请入群
     * @param pInvitation Invitation
     */
    @Override
    public void refuseInviteGroup(final Invitation pInvitation) {
        mBiz.refuseInviteGroup(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                mBiz.updateRefuseInviteGroupStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onRefuseInviteGroupSuccess();
                        }
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onRefuseInviteGroupFailed(e);
                        }
                    }
                });
            }
        });
    }

    /**
     * 拒绝被邀请入群
     * @param pInvitation Invitation
     */
    @Override
    public void refuseApplicationGroup(final Invitation pInvitation) {
        mBiz.refuseApplicationGroup(pInvitation, new InviteListener() {
            @Override
            public void onSuccess() {
                mBiz.updateRefuseApplicationGroupStatus(pInvitation);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onRefuseApplicationGroupSuccess();
                        }
                    }
                });
            }

            @Override
            public void onFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onRefuseApplicationGroupFailed(e);
                        }
                    }
                });
            }
        });
    }
}
