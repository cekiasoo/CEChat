package com.ce.cechat.model.biz;

import com.ce.cechat.model.thread.ThreadPools;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 */
public class DetailBiz implements IDetailBiz {


    @Override
    public void deleteContact(final String pHyphenateId, final DeleteContactListener pDeleteContactListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(pHyphenateId);
                    DbBiz.newInstance().getDbManager().getContactDao().deleteContactByHyphenateId(pHyphenateId);
                    pDeleteContactListener.onDeleteSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pDeleteContactListener.onDeleteFailed(e);
                }
            }
        });
    }

}
