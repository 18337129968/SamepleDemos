package com.hfxief.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hfxief.BuildConfig;
import com.hfxief.utils.fresco.ImagePipelineConfigFactory;
import com.orhanobut.logger.Logger;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * HFSampleProject
 * com.hfxief.app
 *
 * @Author: xie
 * @Time: 2016/12/22 14:52
 * @Description:
 */

public abstract class BaseApplication extends Application implements Thread.UncaughtExceptionHandler {
    private static Application baseApplication;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    @Override
    public void onCreate() {
        super.onCreate();
//        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        initInstance();
        initAutoLayout();
        initLogger();
        initStetho();
        initFresco();
        initBaseManagers();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (BuildConfig.CATCH_EX) {
            Logger.e("=========================");
            e.printStackTrace();
            Logger.e("=========================");
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
//            mDefaultHandler.uncaughtException(t, e);

        }
    }

    protected abstract Application getBaseApplication();

    public static Application getInstance() {
        return baseApplication;
    }

    private void initInstance() {
        baseApplication = getBaseApplication();
    }

    private void initAutoLayout() {
        AutoLayoutConifg.getInstance().useDeviceSize();
    }

    private void initLogger() {
        Logger.init("hfxief").methodCount(5);
    }

    private void initStetho() {
//        Stetho.initializeWithDefaults(this);
    }

    private void initFresco() {
        Fresco.initialize(this,
                ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this));
    }

    private void initBaseManagers() {
        BaseManagers.getAppManagers(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
