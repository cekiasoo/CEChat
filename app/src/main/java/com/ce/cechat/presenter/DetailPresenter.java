package com.ce.cechat.presenter;

import android.os.Handler;

import com.ce.cechat.model.biz.DeleteContactListener;
import com.ce.cechat.model.biz.DetailBiz;
import com.ce.cechat.model.biz.IDetailBiz;
import com.ce.cechat.view.fragment.detail.IContactDetailView;
import com.hyphenate.exceptions.HyphenateException;

/**
 * @author CE Chen
 *
 * Detail Presenter
 */
public class DetailPresenter {

    private IContactDetailView mContactDetailView;

    private Handler mHandler = new Handler();

    public DetailPresenter(IContactDetailView pContactDetailView) {
        this.mContactDetailView = pContactDetailView;
    }

    /**
     * 删除指定 HyphenateId 的联系人
     * @param pHyphenateId HyphenateId
     */
    public void deleteContact(String pHyphenateId) {
        IDetailBiz detailBiz = new DetailBiz();
        detailBiz.deleteContact(pHyphenateId, new DeleteContactListener() {
            @Override
            public void onDeleteSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mContactDetailView.onDeleteSuccess();
                    }
                });
            }

            @Override
            public void onDeleteFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mContactDetailView.onDeleteFailed(e);
                    }
                });
            }
        });
    }

}
