package com.hfxief.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
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

public abstract class BaseApplication extends Application {
    private static Application baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        initInstance();
        initAutoLayout();
        initLogger();
        initStetho();
        initFresco();
        initBaseManagers();
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
