package com.hfxief.event;

/**
 * Created by xie on 2018/8/2.
 */

public class NetEvent extends IEvent {
    public boolean isConnected;

    public NetEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
