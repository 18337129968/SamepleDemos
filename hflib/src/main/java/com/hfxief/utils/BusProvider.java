package com.hfxief.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * SampleAndroidProject
 * com.app.sampleandroidproject.utils
 *
 * @Author: xie
 * @Time: 2016/9/2 14:15
 * @Description:
 */

public class BusProvider {

    private BusProvider() {
    }

    public static <T> void post (T t) {
        EventBus.getDefault().post(t);
    }

    public static void register (Object obj) {
        EventBus.getDefault().register(obj);
    }

    public static void unregister (Object obj) {
        EventBus.getDefault().unregister(obj);
    }
}
