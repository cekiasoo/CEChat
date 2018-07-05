package com.ce.cechat.ui.splash;

import dagger.Module;
import dagger.Provides;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
@Module
public class SplashModule {

    @Provides
    SplashBiz provideSplashBiz() {
        return new SplashBiz();
    }

}
