package com.ce.cechat.app;

import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public class BaseActivity<T extends IBaseContact.IBasePresenter> extends DaggerAppCompatActivity implements IBaseContact.IBaseView {

    @NonNull
    @Inject
    protected T mPresenter;

    protected boolean isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takeView();
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
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onPause() {
        isActive = false;
        super.onPause();
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    protected void onDestroy() {
        dropView();
        super.onDestroy();
    }
}
