package com.hfxief.os;


import android.os.Handler;
import android.os.Message;
import android.util.SparseIntArray;

/**
 * Created by xie on 2018/6/22.
 */

public class TimerHandler extends Handler {
    public static final int MSG_TIMER_ID = 87108;
    private long interval;
    private boolean isStopped = true;
    private SparseIntArray specialInterval;
    private TimerHandlerListener listener;

    private long getNextInterval(int index) {
        long next = interval;
        if (specialInterval != null) {
            long has = specialInterval.get(index, -1);
            if (has > 0) {
                next = has;
            }
        }
        return next;
    }

    public void tick(int index) {
        sendEmptyMessageDelayed(TimerHandler.MSG_TIMER_ID, getNextInterval(index));
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public void setListener(TimerHandlerListener listener) {
        this.listener = listener;
    }

    public void setSpecialInterval(SparseIntArray specialInterval) {
        this.specialInterval = specialInterval;
    }

    @Override
    public void handleMessage(Message msg) {
        if (MSG_TIMER_ID == msg.what) {
            if (listener != null) {
                int nextIndex = listener.getNextItem();
                listener.callBack();
                tick(nextIndex);
            }
        }
    }

    public TimerHandler(long interval) {
        this.interval = interval;
    }

    public interface TimerHandlerListener {
        int getNextItem();

        void callBack();
    }
}
