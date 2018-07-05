package com.ce.cechat.ui.login;

import dagger.Module;
import dagger.Provides;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
@Module
public class SignUpModule {

    @Provides
    SignUpBiz provideSignUpBiz() {
        return new SignUpBiz();
    }

}
