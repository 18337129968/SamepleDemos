package com.hfxief.adapter.recyclerview.base.pool;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.io.Closeable;

/**
 * Created by xie on 2018/6/27.
 */

public class InnerRecycledViewPool extends RecyclerView.RecycledViewPool {
    private final int DEFAULT_MAX_SIZE = 5;
    private RecyclerView.RecycledViewPool mInnerPool;
    private SparseIntArray mScrapLength = new SparseIntArray();
    private SparseIntArray mMaxScrap = new SparseIntArray();

    public InnerRecycledViewPool(RecyclerView.RecycledViewPool pool) {
        this.mInnerPool = pool;
    }

    public InnerRecycledViewPool() {
        this(new RecyclerView.RecycledViewPool());
    }

    @Override
    public void clear() {
        for (int i = 0, size = mScrapLength.size(); i < size; i++) {
            int viewType = mScrapLength.keyAt(i);
            RecyclerView.ViewHolder holder = mInnerPool.getRecycledView(viewType);
            while (holder != null) {
                destroyViewHolder(holder);
                holder = mInnerPool.getRecycledView(viewType);
            }
        }

        mScrapLength.clear();
        super.clear();
    }

    @Override
    public void setMaxRecycledViews(int viewType, int max) {
        // When viewType is changed, because can not get items in wrapped pool,
        // destroy all the items for the viewType
        RecyclerView.ViewHolder holder = mInnerPool.getRecycledView(viewType);
        while (holder != null) {
            destroyViewHolder(holder);
            holder = mInnerPool.getRecycledView(viewType);
        }

        // change maxRecycledViews
        this.mMaxScrap.put(viewType, max);
        this.mScrapLength.put(viewType, 0);
        mInnerPool.setMaxRecycledViews(viewType, max);
    }

    @Override
    public RecyclerView.ViewHolder getRecycledView(int viewType) {
        RecyclerView.ViewHolder holder = mInnerPool.getRecycledView(viewType);
        if (holder != null) {
            int scrapHeapSize = mScrapLength.indexOfKey(viewType) >= 0 ? this.mScrapLength.get(viewType) : 0;
            if (scrapHeapSize > 0)
                mScrapLength.put(viewType, scrapHeapSize - 1);
        }

        return holder;
    }

    public int size() {
        int count = 0;

        for (int i = 0, size = mScrapLength.size(); i < size; i++) {
            int val = mScrapLength.valueAt(i);
            count += val;
        }

        return count;
    }

    public void putRecycledView(RecyclerView.ViewHolder scrap) {
        int viewType = scrap.getItemViewType();

        if (mMaxScrap.indexOfKey(viewType) < 0) {
            // does't contains this viewType, initial scrap list
            mMaxScrap.put(viewType, DEFAULT_MAX_SIZE);
            setMaxRecycledViews(viewType, DEFAULT_MAX_SIZE);
        }

        // get current heap size
        int scrapHeapSize = mScrapLength.indexOfKey(viewType) >= 0 ? this.mScrapLength.get(viewType) : 0;

        if (this.mMaxScrap.get(viewType) > scrapHeapSize) {
            // if exceed current heap size
            mInnerPool.putRecycledView(scrap);
            mScrapLength.put(viewType, scrapHeapSize + 1);
        } else {
            // destroy viewHolder
            destroyViewHolder(scrap);
        }
    }

    private void destroyViewHolder(RecyclerView.ViewHolder holder) {
        View view = holder.itemView;
        // if view inherits {@link Closeable}, cal close method
        if (view instanceof Closeable) {
            try {
                ((Closeable) view).close();
            } catch (Exception e) {
                Logger.w(Log.getStackTraceString(e), e);
            }
        }

        if (holder instanceof Closeable) {
            try {
                ((Closeable) holder).close();
            } catch (Exception e) {
                Log.w(Log.getStackTraceString(e), e);
            }
        }
    }
}
