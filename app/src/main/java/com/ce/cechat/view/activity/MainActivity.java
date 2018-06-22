package com.ce.cechat.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ce.cechat.R;
import com.ce.cechat.presenter.MainPresenter;
import com.ce.cechat.utils.ErrorCode;
import com.ce.cechat.view.fragment.base.BaseFragment;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @author CE Chen
 *
 */
public class MainActivity extends AppCompatActivity
        implements BaseFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener,
        IMainView {
    private static final String TAG = "MainActivity";

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    NavHostFragment navFragMain;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private View mHeadLayout;

    private TextView mTvNickName;

    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainPresenter = new MainPresenter(this);
        mMainPresenter.initDb();
        navFragMain = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_frag_main);
        NavController navController = navFragMain.getNavController();
        setupNavigationMenu(navController);
        navController.addOnNavigatedListener(new NavController.OnNavigatedListener() {
            @Override
            public void onNavigated(@NonNull NavController controller, @NonNull NavDestination destination) {
                Log.v(TAG, "getLabel = " + destination.getLabel());
            }
        });

        navView.setNavigationItemSelectedListener(this);

        mHeadLayout = navView.inflateHeaderView(R.layout.nav_header_main);
        mTvNickName = mHeadLayout.findViewById(R.id.tv_nickname);
        mMainPresenter.setName();
    }

    @Override
    protected void onStart() {
        super.onStart();
        JAnalyticsInterface.onPageStart(this, TAG);
    }

    @Override
    protected void onStop() {
        JAnalyticsInterface.onPageEnd(this, TAG);
        super.onStop();
    }

    private void setupNavigationMenu(NavController navController) {
        NavigationUI.setupWithNavController(navigation, navController);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(Uri uri, Bundle bundle) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_share) {
            showShare();
        } else if (item.getItemId() == R.id.nav_about) {
            about();
        } else if (item.getItemId() == R.id.nav_logout) {
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
       new AlertDialog.Builder(this)
                .setTitle(R.string.logout_current_account)
                .setMessage(R.string.confirm_logout_current_account)
                .setPositiveButton(R.string.confirm_logout, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMainPresenter.logout();
                    }
                })
                .setNegativeButton(R.string.cancels, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void about() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_about_dialog, null, false);
        new android.app.AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(getString(R.string.share));
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageData(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

    @Override
    public void setName(String pName) {
        if (!TextUtils.isEmpty(pName)) {
            mTvNickName.setText(pName);
        }
    }

    @Override
    public void logoutSuccess() {
        toLoginPage();
    }

    @Override
    public void logoutFailed(int code, String error) {
        Toast.makeText(this, ErrorCode.errorCodeToString(this, code), Toast.LENGTH_SHORT).show();
    }

    /**
     * 返回登录界面
     */
    private void toLoginPage() {
        Log.v(TAG, "toLoginPage");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
