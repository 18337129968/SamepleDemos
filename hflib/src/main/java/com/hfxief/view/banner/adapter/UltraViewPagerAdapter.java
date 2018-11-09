package com.hfxief.view.banner.adapter;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hfxief.view.banner.listener.UltraViewPagerCenterListener;

/**
 * Created by xie on 2018/5/11.
 */

public class UltraViewPagerAdapter extends PagerAdapter {
    private final int LOOP_RATIO = 400;//循环系数
    private PagerAdapter adapter;
    private boolean enableLoop;//是否循环
    private float multiScrRatio = Float.NaN;//子view的宽度系数
    private boolean hasCentered; //ensure that the first item is in the middle when enabling loop-mode
    private int scrWidth;
    private int loopRatio;
    private UltraViewPagerCenterListener centerListener;
    private SparseArray viewArray = new SparseArray();

    public UltraViewPagerAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
        this.loopRatio = LOOP_RATIO;
    }

    @Override
    public int getCount() {
        int count;
        if (enableLoop) {
            if (adapter.getCount() == 0) {
                count = 0;
            } else {
                count = adapter.getCount() * loopRatio;
            }
        } else {
            count = adapter.getCount();
        }
        return count;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position;
        if (enableLoop && adapter.getCount() != 0) {
            realPosition = position % adapter.getCount();
        }
        Object item = adapter.instantiateItem(container, realPosition);
        View childView = null;
        if (item instanceof View) childView = (View) item;
        if (item instanceof RecyclerView.ViewHolder)
            childView = ((RecyclerView.ViewHolder) item).itemView;
        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = container.getChildAt(i);
            if (isViewFromObject(child, item)) {
                viewArray.put(realPosition, child);
                break;
            }
        }
        if (isEnableMultiScr()) {
            if (scrWidth == 0) {
                scrWidth = container.getResources().getDisplayMetrics().widthPixels;
            }
            RelativeLayout relativeLayout = new RelativeLayout(container.getContext());
            if (childView.getLayoutParams() != null) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        (int) (scrWidth * multiScrRatio), ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                childView.setLayoutParams(layoutParams);
            }
            container.removeView(childView);
            //这里强制childView的父布局为RelativeLayout
            relativeLayout.addView(childView);
            container.addView(relativeLayout);
        }
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = position;
        if (enableLoop && adapter.getCount() != 0) {
            realPosition = position % adapter.getCount();
        }
        if (isEnableMultiScr() && object instanceof RelativeLayout) {
            View child = ((RelativeLayout) object).getChildAt(0);
            ((RelativeLayout) object).removeAllViews();
            adapter.destroyItem(container, realPosition, child);
        } else {
            adapter.destroyItem(container, realPosition, object);
        }
        viewArray.remove(realPosition);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        adapter.setPrimaryItem(container, position, object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        // only need to set the center position  when the loop is enabled
        if (!hasCentered) {
            if (adapter.getCount() > 0 && getCount() > adapter.getCount()) {
                centerListener.center();
            }
        }
        hasCentered = true;
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int virtualPosition = position % adapter.getCount();
        return adapter.getPageTitle(virtualPosition);
    }

    @Override
    public float getPageWidth(int position) {
        return adapter.getPageWidth(position);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return adapter.getItemPosition(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        adapter.restoreState(state, loader);
    }

    public PagerAdapter getAdapter() {
        return adapter;
    }

    public boolean isEnableMultiScr() {
        return !Float.isNaN(multiScrRatio) && multiScrRatio < 1f;
    }

    public View getViewAtPosition(int position) {
        return (View) viewArray.get(position);
    }

    public void setEnableLoop(boolean status) {
        this.enableLoop = status;
        notifyDataSetChanged();
        if (!status) {
            centerListener.resetPosition();
        }
    }

    public void setCenterListener(UltraViewPagerCenterListener listener) {
        centerListener = listener;
    }

    /**
     * @description 描述：设置循环系数
     * @param loopRatio 系数
     * @date 2018/5/14
     * @author xie
     */
    public void setLoopRatio(int loopRatio) {
        this.loopRatio = loopRatio;
    }

    public boolean isEnableLoop() {
        return enableLoop;
    }

    public void setMultiScrRatio(float ratio) {
        multiScrRatio = ratio;
    }

    public int getRealCount() {
        return adapter.getCount();
    }
}
