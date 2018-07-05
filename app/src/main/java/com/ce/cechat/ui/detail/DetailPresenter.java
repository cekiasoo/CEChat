package com.ce.cechat.ui.detail;

import com.ce.cechat.app.BasePresenter;
import com.hyphenate.exceptions.HyphenateException;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * Detail Presenter
 */
public class DetailPresenter extends BasePresenter<IDetailContract.IContactDetailView, DetailBiz>
        implements IDetailContract.IPresenter {

    @Inject
    public DetailPresenter() {
    }

    /**
     * 删除指定 HyphenateId 的联系人
     * @param pHyphenateId HyphenateId
     */
    @Override
    public void deleteContact(String pHyphenateId) {
        mBiz.deleteContact(pHyphenateId, new DeleteContactListener() {
            @Override
            public void onDeleteSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onDeleteSuccess();
                        }
                    }
                });
            }

            @Override
            public void onDeleteFailed(final HyphenateException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mView.onDeleteFailed(e);
                        }
                    }
                });
            }
        });
    }

}
