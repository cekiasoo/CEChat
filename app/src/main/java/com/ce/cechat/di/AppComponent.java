package com.ce.cechat.di;

import android.app.Application;

import com.ce.cechat.app.ActivityBindingModule;
import com.ce.cechat.app.App;
import com.ce.cechat.app.FragmentBindingModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        FragmentBindingModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application pApplication);

        AppComponent build();

    }


}
