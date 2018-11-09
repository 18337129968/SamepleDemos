package com.hfxief.adapter.recyclerview.base;

import android.content.Context;

import com.hfxief.adapter.recyclerview.base.baseitem.ItemViewDelegate;
import com.hfxief.adapter.recyclerview.base.baseitem.ViewHolder;

import java.util.List;

/**
 * @Title: CommonAdapter
 * @Description: 通用单一Item适配器
 * @date 2016/11/29 11:35
 * @auther xie
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
        init();
    }

    private void init() {
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return mLayoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T item, int position) {
                CommonAdapter.this.convert(holder, item, position);
            }

            @Override
            public void recycled(ViewHolder holder, T t) {
                CommonAdapter.this.recycled(holder);
            }
        });
    }

    protected abstract void recycled(ViewHolder holder);

    protected abstract void convert(ViewHolder holder, T t, int position);
}
