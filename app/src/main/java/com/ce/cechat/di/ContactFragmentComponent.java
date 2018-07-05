package com.ce.cechat.di;

import com.ce.cechat.ui.contactlist.ContactFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
@Subcomponent(modules = AndroidSupportInjectionModule.class)
public interface ContactFragmentComponent extends AndroidInjector<ContactFragment> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<ContactFragment> {

    }

}
