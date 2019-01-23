package com.hfxief.adapter.gridview.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hfxief.view.GridViewLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * 自定义GridView
 * Created by xie on 2018/5/24.
 */

public abstract class BaseGridViewAdapter<T> {
    private Context context;
    private Observable<Void> observable;
    private Subscription subscription;
    private LayoutInflater layoutInflater;
    private GridViewLayout.GridViewAction action;
    protected List<T> mList = new ArrayList<>();
    protected OnGridViewItemClick onGridViewItemClick;

    public int getCount() {
        return mList.size();
    }

    public abstract View getView(View view, T item, int position, LayoutInflater layoutInflater);

    public BaseGridViewAdapter(Context context) {
        this.context = context;
        observable = Observable.empty();
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public BaseGridViewAdapter(Context context, List<T> mList) {
        if (mList == null) throw new NullPointerException("mList is null , please check");
        this.context = context;
        this.mList.addAll(mList);
        observable = Observable.empty();
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public void setData(List<T> mList) {
        if (mList == null) throw new NullPointerException("mList is null , please check");
        this.mList.clear();
        this.mList.addAll(mList);
        notifyAdapter();
    }

    public View getConvertView(View view, int position) {
        return getView(view, mList.get(position), position, layoutInflater);
    }

    public Context getContext() {
        return context;
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public void notifyAdapter() {
        if (action != null) subscription = observable.subscribe(action);
    }

    public void setAction(GridViewLayout.GridViewAction action) {
        this.action = action;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setOnGridViewItemClick(OnGridViewItemClick onGridViewItemClick) {
        this.onGridViewItemClick = onGridViewItemClick;
    }

    public interface OnGridViewItemClick {
        void onItemCLick(int position);
    }
}
