package com.hfxief.adapter.gridview.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by xie on 2018/5/24.
 */

public abstract class BaseGridViewAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public abstract int getCount();

    public abstract View getView(int position, LayoutInflater layoutInflater);

    public BaseGridViewAdapter(Context context) {
        this.context = context;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public View getConvertView(int position) {
        return getView(position, layoutInflater);
    }

    public Context getContext() {
        return context;
    }
}
