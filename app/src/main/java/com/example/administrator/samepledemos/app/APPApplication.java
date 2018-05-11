package com.example.administrator.samepledemos.app;

import android.app.Application;

import com.hfxief.app.BaseApplication;

/**
 * Created by xie on 2018/3/29.
 */

public class APPApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected Application getBaseApplication() {
        return this;
    }
}
