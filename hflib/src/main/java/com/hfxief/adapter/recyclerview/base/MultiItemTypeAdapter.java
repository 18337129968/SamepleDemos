package com.hfxief.adapter.recyclerview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hfxief.adapter.recyclerview.base.baseitem.ItemViewDelegate;
import com.hfxief.adapter.recyclerview.base.baseitem.ItemViewDelegateManager;
import com.hfxief.adapter.recyclerview.base.baseitem.ViewHolder;

import java.util.List;

/**
 * @Title: MultiItemTypeAdapter
 * @Description: 多样式Item适配器
 * @date 2016/11/29 11:35
 * @auther xie
 */
public abstract class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;
    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;
    private boolean loadingMoreEnabled = false;
    private final int TYPE_FOOTER = 10001;
    private View mFooterView;

    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public void setLoadMoreEnable(boolean enable, View mFooterView) {
        loadingMoreEnabled = enable;
        this.mFooterView = mFooterView;
    }

    public boolean getLoadMoreEnable() {
        return loadingMoreEnabled;
    }

    public boolean isFooter(int position) {
        if (loadingMoreEnabled) {
            return position == getItemCount() - 1;
        } else {
            return false;
        }
    }

    public int getItemType(T item, int position) {
        return mItemViewDelegateManager.getItemViewType(item, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooter(position)) return TYPE_FOOTER;
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return getItemType(mDatas.get(position), position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new ViewHolder(mContext, mFooterView);
        }
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate == null ? 0 : itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder;
        holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public View getMFooterView() {
        return mFooterView;
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    private void recycled(ViewHolder holder, T t) {
        mItemViewDelegateManager.recycled(holder, t);
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < mDatas.size())
            convert(holder, mDatas.get(position));
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        recycled(holder, mDatas.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        if (loadingMoreEnabled && itemCount > 0) return itemCount + 1;
        return itemCount;
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
