package com.ce.cechat.ui.detail;

import com.ce.cechat.data.biz.DbBiz;
import com.ce.cechat.utils.ThreadPools;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import javax.inject.Inject;

/**
 * @author CE Chen
 */
public class DetailBiz implements IDetailContract.IDetailBiz {

    @Inject
    public DetailBiz() {
    }

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
