package com.ce.cechat.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.ce.cechat.R;
import com.ce.cechat.app.BaseActivity;
import com.ce.cechat.utils.CountDownTimerUtils;
import com.ce.cechat.ui.login.LoginActivity;
import com.ce.cechat.ui.main.MainActivity;
import com.ce.cechat.ui.splash.ISplashContract.ISplashView;

/**
 * @author CE Chen
 *
 * 欢迎界面
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements ISplashView {

    private CountDownTimerUtils mCountDownTimer;

    private static final int MILLIS_IN_FUTURE = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mCountDownTimer = CountDownTimerUtils.getCountDownTimer();
        mCountDownTimer.setMillisInFuture(MILLIS_IN_FUTURE)
                .setFinishDelegate(new CountDownTimerUtils.FinishDelegate() {
            @Override
            public void onFinish() {
                mPresenter.isLoggedIn();
            }
        }).start();

    }

    private void toMainPage() {

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);;
        startActivity(intent);
        finish();
    }

    private void toLogInPage() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);;
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        mCountDownTimer.cancel();
        super.onDestroy();
    }

    @Override
    public void loggedIn() {
        mPresenter.isUserInDb();
    }

    @Override
    public void noLoggedIn() {
        toLogInPage();
    }

    @Override
    public void userInDb() {
        toMainPage();
    }

    @Override
    public void userNoInDb() {
        toLogInPage();
    }
}
