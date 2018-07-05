package com.ce.cechat.app;

import android.os.Handler;

import javax.inject.Inject;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public class BasePresenter<T extends IBaseContact.IBaseView, B extends IBaseContact.IBaseBiz> implements IBaseContact.IBasePresenter<T> {

    protected Handler mHandler = new Handler();

    protected T mView;

    @Inject
    protected B mBiz;

    @Override
    public void takeView(T pView) {
        mView = pView;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
