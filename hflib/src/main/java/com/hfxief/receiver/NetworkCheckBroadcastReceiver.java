package com.hfxief.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hfxief.event.NetEvent;
import com.hfxief.utils.BusProvider;
import com.hfxief.utils.Network;

/**
 * Created by xie on 2018/8/2.
 */

public class NetworkCheckBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = Network.isConnected(context);
        BusProvider.post(new NetEvent(isConnected));
    }

}
