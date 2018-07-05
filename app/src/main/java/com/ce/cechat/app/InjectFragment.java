package com.ce.cechat.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public abstract class InjectFragment<T extends IBaseContact.IBasePresenter> extends BaseFragment
        implements IBaseContact.IBaseView {

    @Inject
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takeView();
    }

    @Override
    public void onDestroy() {
        dropView();
        super.onDestroy();
    }

    @Override
    public void takeView() {
        mPresenter.takeView(this);
    }

    @Override
    public void dropView() {
        mPresenter.dropView();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
