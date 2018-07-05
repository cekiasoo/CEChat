package com.ce.cechat.di;

import com.ce.cechat.app.BaseFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
@Subcomponent(modules = AndroidSupportInjectionModule.class)
public interface BaseFragmentComponent extends AndroidInjector<BaseFragment> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<BaseFragment> {

    }

}
