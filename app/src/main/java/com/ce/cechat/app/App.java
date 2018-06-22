package com.ce.cechat.app;

import android.app.Application;

import com.ce.cechat.model.biz.DbBiz;
import com.ce.cechat.model.biz.EventListener;
import com.ce.cechat.model.thread.ThreadPools;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

/**
 * @author CE Chen
 */
public class App extends Application {

    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;

        initHyphenate();

        initLeakCanary();

        initCrashReport();

        initThreadPools();

        initDb();

        initEventListener();

        initShareSDK();

        initJpush();

        initJAnalytics();

    }

    public static App getInstance() {
        return sApp;
    }

    /**
     * 初始化 Bugly
     */
    private void initCrashReport() {
        CrashReport.initCrashReport(getApplicationContext(), "2a4707ceb9", true);
    }

    /**
     * 初始化消息的监听
     */
    private void initEventListener() {
        new EventListener(getApplicationContext());
    }

    /**
     * 初始化内存泄露检测库
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * 初始化线程池
     */
    private void initThreadPools() {
        ThreadPools.newInstance();
    }

    /**
     * 初始化数据库
     */
    private void initDb() {
        DbBiz.newInstance().init(getApplicationContext());
    }

    /**
     * 初始化环信配置
     */
    private void initHyphenate() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 默认添加群时，是不需要验证的，改成需要验证
        options.setAutoAcceptGroupInvitation(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        //初始化
        EaseUI.getInstance().init(this, options);

//        EMClient.getInstance().init(this, options);

        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    /**
     * 初始化 ShareSDK
     */
    private void initShareSDK() {
        MobSDK.init(this);
    }

    /**
     * 初始化极光推送
     */
    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 初始化极光统计
     */
    private void initJAnalytics() {
        JAnalyticsInterface.setDebugMode(true);
        JAnalyticsInterface.initCrashHandler(this);
        JAnalyticsInterface.init(this);
    }
}
