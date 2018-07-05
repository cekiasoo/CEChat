package com.ce.cechat.app;

import com.ce.cechat.di.ActivityScoped;
import com.ce.cechat.di.BaseActivityComponent;
import com.ce.cechat.ui.main.MainActivity;
import com.ce.cechat.ui.main.MainModule;
import com.ce.cechat.ui.splash.SplashActivity;
import com.ce.cechat.ui.splash.SplashModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
@Module(subcomponents = {BaseActivityComponent.class})
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity splashActivity();


    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
