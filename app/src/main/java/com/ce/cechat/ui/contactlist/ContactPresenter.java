package com.ce.cechat.ui.contactlist;


import com.ce.cechat.app.BasePresenter;
import com.ce.cechat.bean.User;
import com.hyphenate.easeui.domain.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Contact Presenter
 */
public class ContactPresenter extends BasePresenter<ContactContract.IContactView, ContactBiz>
        implements ContactContract.IPresenter {


    @Inject
    public ContactPresenter() {

    }

    @Override
    public void haveInviteRedDot() {
        boolean haveInviteRedDot = mBiz.haveInviteRedDot();
        if (haveInviteRedDot) {
            showInviteRedDot();
        } else {
            hideInviteRedDot();
        }
    }

    @Override
    public void hideInviteRedDot() {
        mBiz.clearInviteRedDot();
        if (mView != null && mView.isActive()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mView.hideInviteRedDot();
                }
            });
        }
    }

    @Override
    public void showInviteRedDot() {
        if (mView != null && mView.isActive()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mView.showInviteRedDot();
                }
            });
        }
    }

    @Override
    public void getContactsMap(boolean pFromServer) {
        mBiz.getContacts(pFromServer, new IContactListener() {
            @Override
            public void getAllContactSuccess(final List<User> pAllContact) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onGetAllContactSuccess(pAllContact);
                        }
                    }
                });
            }

            @Override
            public void getAllContactFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onGetAllContactFailed(e);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void list2UserMap(List<User> pAllContacts) {
        final Map<String, EMGroup> userMap = mBiz.list2UserMap(pAllContacts);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mView != null) {
                    mView.updateContactList(userMap);
                }
            }
        });
    }

}
