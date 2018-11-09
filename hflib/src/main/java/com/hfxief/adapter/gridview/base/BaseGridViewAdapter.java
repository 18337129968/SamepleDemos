package com.hfxief.adapter.gridview.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 自定义GridView
 * Created by xie on 2018/5/24.
 */

public abstract class BaseGridViewAdapter<T>{
    private Context context;
    private LayoutInflater layoutInflater;
    private Observable observable;
    private Subscriber<? super Void> mSubscriber;
    private List<T> mList = new ArrayList<>();

    public int getCount() {
        return mList.size();
    }

    public abstract View getView(View view, T item, int position, LayoutInflater layoutInflater);

    public BaseGridViewAdapter(Context context) {
        this.context = context;
        observable = Observable.create(new GridViewSubscribe());
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

   public BaseGridViewAdapter(Context context, List<T> mList) {
        if (mList == null) throw new NullPointerException("mList is null , please check");
        this.context = context;
        this.mList.addAll(mList);
        observable = Observable.create(new GridViewSubscribe());
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

    public View getConvertView(View view,final int position) {
        View mView = getView(view, mList.get(position), position, layoutInflater);
        return mView;
    }

    public Context getContext() {
        return context;
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public void notifyAdapter() {
        if (mSubscriber != null && !mSubscriber.isUnsubscribed()) {
            mSubscriber.onNext(null);
        }
    }

    public Observable getObservable() {
        return observable;
    }

    class GridViewSubscribe implements Observable.OnSubscribe<Void> {
        @Override
        public void call(Subscriber<? super Void> subscriber) {
            mSubscriber = subscriber;
        }
    }

}
