package com.hfxief.app;

import android.content.Context;

import com.hfxief.utils.Toastor;
import com.hfxief.utils.cache.CacheManager;

import java.io.File;

/**
 * HFSampleProject
 * com.hfxief.app
 *
 * @Author: xie
 * @Time: 2017/1/3 10:20
 * @Description:
 */


public class BaseManagers {

    private static Toastor toastor;
    protected static BaseManagers managers;
    private static CacheManager cacheManager;
    private static ActivitiesManager activities;
    private static RequestManager requestManager;

    public static BaseManagers getAppManagers(Context context) {
        if (managers == null) {
            managers = new BaseManagers();
        }

        if (cacheManager == null) {
            cacheManager = CacheManager.get(context.getExternalCacheDir().getAbsolutePath() +
                    File.separator + "DataCache");
        }

        if (toastor == null) {
            toastor = new Toastor(context);
        }

        if (requestManager == null) {
            requestManager = RequestManager.get();
        }

        return managers;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }


    public static Toastor getToastor() {
        return toastor;
    }

    public static ActivitiesManager getActivitiesManager() {
        if (activities == null) {
            activities = ActivitiesManager.getInstance();
        }
        return activities;
    }

    public static RequestManager getRequestManager() {
        return requestManager;
    }

}
