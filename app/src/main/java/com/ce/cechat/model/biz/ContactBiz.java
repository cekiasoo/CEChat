package com.ce.cechat.model.biz;

import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.thread.ThreadPools;
import com.ce.cechat.utils.SharedPreferencesUtils;
import com.ce.cechat.app.App;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 *
 * 联系人业务逻辑操作类
 *
 */
public class ContactBiz implements IContactBiz {

    @Override
    public boolean haveInviteRedDot() {
        return (boolean) SharedPreferencesUtils.get(App.getInstance(), EventListener.KEY_IS_NEW_INVITE, false);
    }

    @Override
    public void clearInviteRedDot() {
        SharedPreferencesUtils.remove(App.getInstance(), EventListener.KEY_IS_NEW_INVITE);
    }

    @Override
    public void getContacts(boolean pFromServer, final IContactListener pContactListener) {

        List<User> contacts = DbBiz.newInstance().getDbManager().getContactDao().getContacts();

        if (pFromServer || contacts.isEmpty()) {
            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<String> allContacts = EMClient.getInstance().contactManager().getAllContactsFromServer();
                        List<User> contacts = new LinkedList<>();
                        for (String id:
                                allContacts) {
                            contacts.add(new User(id));
                        }
                        DbBiz.newInstance().getDbManager().getContactDao().saveContact(contacts, true);
                        pContactListener.getAllContactSuccess(contacts);
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        pContactListener.getAllContactFailed(e);
                    }
                }
            });
        } else {
            pContactListener.getAllContactSuccess(contacts);
        }
    }

    @Override
    public Map<String, EMGroup> list2UserMap(List<User> pAllContacts) {

        Map<String, EMGroup> map = new HashMap<>();

        if (pAllContacts == null || pAllContacts.isEmpty()) {
            return map;
        }

        for (User contact :
                pAllContacts) {
            if (contact != null) {
                EMGroup easeUser = new EMGroup(contact.getHyphenateId());
                map.put(contact.getHyphenateId(), easeUser);
            }
        }

        return map;

    }
}
