package com.hfxief.view.banner.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hfxief.adapter.recyclerview.base.pool.InnerRecycledViewPool;

/**
 * Created by xie on 2018/6/28.
 */

public abstract class RecyclablePagerAdapter<VH extends RecyclerView.ViewHolder> extends PagerAdapter {
    private RecyclerView.Adapter<VH> mAdapter;
    private InnerRecycledViewPool mRecycledViewPool;

    public RecyclablePagerAdapter(RecyclerView.Adapter<VH> adapter, RecyclerView.RecycledViewPool pool) {
        this.mAdapter = adapter;
        if (pool instanceof InnerRecycledViewPool) {
            this.mRecycledViewPool = (InnerRecycledViewPool) pool;
        } else {
            this.mRecycledViewPool = new InnerRecycledViewPool(pool);
        }
    }

    @Override
    public abstract int getCount();

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object instanceof RecyclerView.ViewHolder && (((RecyclerView.ViewHolder) object).itemView == view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int itemViewType = getItemViewType(position);
        RecyclerView.ViewHolder holder = mRecycledViewPool.getRecycledView(itemViewType);
        if (holder == null) {
            holder = mAdapter.createViewHolder(container, itemViewType);
        }
        onBindViewHolder((VH) holder, position);
        container.addView(holder.itemView, new ViewPager.LayoutParams());
        return holder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof RecyclerView.ViewHolder) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) object;
            container.removeView(holder.itemView);
            mRecycledViewPool.putRecycledView(holder);
        }
    }

    public abstract int getItemViewType(int position);

    public abstract void onBindViewHolder(VH holder, int position);
}
