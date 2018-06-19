package com.ce.cechat.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ce.cechat.R;
import com.ce.cechat.presenter.SplashPresenter;
import com.ce.cechat.utils.CountDownTimerUtils;

/**
 * @author CE Chen
 *
 * 欢迎界面
 */
public class SplashActivity extends AppCompatActivity implements ISplashView {

    private CountDownTimerUtils mCountDownTimer;

    private static final int MILLIS_IN_FUTURE = 1200;

    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashPresenter = new SplashPresenter(this);


        setContentView(R.layout.activity_splash);

        mCountDownTimer = CountDownTimerUtils.getCountDownTimer();
        mCountDownTimer.setMillisInFuture(MILLIS_IN_FUTURE)
                .setFinishDelegate(new CountDownTimerUtils.FinishDelegate() {
            @Override
            public void onFinish() {
                mSplashPresenter.isLoggedIn();
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
        mSplashPresenter.isUserInDb();
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
