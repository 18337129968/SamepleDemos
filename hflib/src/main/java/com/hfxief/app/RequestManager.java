package com.hfxief.app;

import com.hfxief.event.FEvent;
import com.hfxief.event.StopEvent;
import com.hfxief.utils.BusProvider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA in maimai_a
 * cn.maitian.app.library.core
 *
 * @Author: xie
 * @Time: 2016/7/29 16:00
 * @Description:
 */
public class RequestManager {

    private static RequestManager requestManager = new RequestManager();

    private List<OnRequestListener> listeners = new LinkedList<>();

    private RequestManager() {
        BusProvider.register(this);
    }

    public static RequestManager get() {
        return requestManager;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void error(FEvent e) {
        for (OnRequestListener listener : listeners) {
            listener.onError(e);
        }
        BaseManagers.getToastor().showSingletonToast(e.error);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void stop(StopEvent e) {
        for (OnRequestListener listener : listeners) {
            listener.onStop(e);
        }
    }

    public void addOnRequestListener(OnRequestListener listener) {
        listeners.add(listener);
    }

    public void removeOnRequestListener(OnRequestListener listener) {
        listeners.remove(listener);
    }

    public interface OnRequestListener {
        void onStop(StopEvent e);

        void onError(FEvent e);
    }

}
