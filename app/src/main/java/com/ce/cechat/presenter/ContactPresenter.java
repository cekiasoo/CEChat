package com.ce.cechat.presenter;


import android.os.Handler;

import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.biz.ContactBiz;
import com.ce.cechat.model.biz.IContactBiz;
import com.ce.cechat.model.biz.IContactListener;
import com.ce.cechat.view.fragment.main.IContactView;
import com.hyphenate.easeui.domain.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 *
 * Contact Presenter
 */
public class ContactPresenter {

    private IContactView mContactView;

    private Handler mHandler = new Handler();

    public ContactPresenter(IContactView mContactView) {
        this.mContactView = mContactView;
    }

    public void haveInviteRedDot() {
        IContactBiz contactBiz = new ContactBiz();
        boolean haveInviteRedDot = contactBiz.haveInviteRedDot();
        if (haveInviteRedDot) {
            showInviteRedDot();
        } else {
            hideInviteRedDot();
        }
    }

    public void hideInviteRedDot() {
        IContactBiz contactBiz = new ContactBiz();
        contactBiz.clearInviteRedDot();
        if (mContactView.isActive()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mContactView.hideInviteRedDot();
                }
            });
        }
    }

    public void showInviteRedDot() {
        if (mContactView.isActive()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mContactView.showInviteRedDot();
                }
            });
        }
    }

    public void getContactsMap(boolean pFromServer) {
        IContactBiz contactBiz = new ContactBiz();
        contactBiz.getContacts(pFromServer, new IContactListener() {
            @Override
            public void getAllContactSuccess(final List<User> pAllContact) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mContactView.onGetAllContactSuccess(pAllContact);
                    }
                });
            }

            @Override
            public void getAllContactFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mContactView.onGetAllContactFailed(e);
                    }
                });
            }
        });
    }

    public void list2UserMap(List<User> pAllContacts) {
        IContactBiz contactBiz = new ContactBiz();
        final Map<String, EMGroup> userMap = contactBiz.list2UserMap(pAllContacts);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mContactView.updateContactList(userMap);
            }
        });
    }

}
