package com.hfxief.view.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.hfxief.view.banner.adapter.UltraViewPagerAdapter;
import com.hfxief.view.banner.listener.UltraViewPagerCenterListener;

/**
 * Created by xie on 2018/5/11.
 */
public class BannerViewPager extends ViewPager implements UltraViewPagerCenterListener {
    private UltraViewPagerAdapter pagerAdapter;
    private boolean enableLoop;
    private float itemRatio = Float.NaN;//item宽度/高度的系数
    private float ratio = Float.NaN;
    private boolean autoMeasureHeight;
    private int itemPaddingLeft;
    private int itemPaddingTop;
    private int itemPaddingRight;
    private int itemPaddingBottom;

    private void onMeasurePage(int widthMeasureSpec, int heightMeasureSpec) {
        View child = pagerAdapter == null ? null : pagerAdapter.getViewAtPosition(getCurrentItem());
        if (child == null) {
            child = getChildAt(0);
        }
        if (child == null) {
            return;
        }
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if ((view.getPaddingLeft() != itemPaddingLeft ||
                    view.getPaddingTop() != itemPaddingTop ||
                    view.getPaddingRight() != itemPaddingRight ||
                    view.getPaddingBottom() != itemPaddingBottom)) {
                view.setPadding(itemPaddingLeft, itemPaddingTop, itemPaddingRight, itemPaddingBottom);
            }
        }

        ViewGroup.LayoutParams lp = child.getLayoutParams();
        final int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
        final int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);

        int childWidth = (int) ((MeasureSpec.getSize(childWidthSpec) - getPaddingLeft() - getPaddingRight()) * pagerAdapter.getPageWidth(getCurrentItem()));
        int childHeight = 0;
        if (!Float.isNaN(itemRatio)) {
            int itemHeight = (int) (childWidth * itemRatio);
            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                view.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
            }
        } else {
            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                view.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), childHeightSpec);
            }
        }
//        childWidth = itemPaddingLeft + child.getMeasuredWidth() + itemPaddingRight;
        childHeight = itemPaddingTop + child.getMeasuredHeight() + itemPaddingBottom;
//
//        if (!Float.isNaN(ratio)) {
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() * ratio), MeasureSpec.EXACTLY);
//            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//            for (int i = 0; i < childCount; i++) {
//                View view = getChildAt(i);
//                view.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
//            }
//        } else {
        if (autoMeasureHeight) {
            setMeasuredDimension(getMeasuredWidth(), childHeight);
        }
//        }
        //如果子view宽度重构则继续
        if (!pagerAdapter.isEnableMultiScr()) {
            return;
        }
        int pageLength = getMeasuredWidth();
        final int childLength = child.getMeasuredWidth();
        // Check that the measurement was successful
        if (childLength > 0) {
//            int difference = pageLength - childLength;
            int difference = 10;
            if (getPageMargin() == 0) {
                setPageMargin(-difference);
            }
            int offscreen = (int) Math.ceil((float) pageLength / (float) childLength) + 1;
            setOffscreenPageLimit(offscreen);
            requestLayout();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        setClipChildren(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public BannerViewPager(Context context) {
        super(context);
        init(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (adapter != null) {
            if (pagerAdapter == null || pagerAdapter != adapter) {
                pagerAdapter = (UltraViewPagerAdapter) adapter;
                pagerAdapter.setCenterListener(this);
                pagerAdapter.setEnableLoop(enableLoop);
                super.setAdapter(pagerAdapter);
            }
        } else {
            super.setAdapter(adapter);
        }
    }

    public PagerAdapter getWrapperAdapter() {
        return super.getAdapter() == null ? null : ((UltraViewPagerAdapter) super.getAdapter()).getAdapter();
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        //设置item在count中间值方便循环滑动
        if (pagerAdapter.getCount() != 0 && pagerAdapter.isEnableLoop()) {
            item = pagerAdapter.getCount() / 2 + item % pagerAdapter.getRealCount();
        }
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        if (pagerAdapter != null && pagerAdapter.getCount() != 0) {
            int position = super.getCurrentItem();
            return position % pagerAdapter.getRealCount();
        }
        return super.getCurrentItem();
    }

    public int getNextItem() {
        if (pagerAdapter != null && pagerAdapter.getCount() != 0) {
            int next = super.getCurrentItem() + 1;
            return next % pagerAdapter.getRealCount();
        }
        return 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onMeasurePage(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void center() {
        setCurrentItem(0);
    }

    @Override
    public void resetPosition() {
        setCurrentItem(getCurrentItem());
    }

    public void setEnableLoop(boolean status) {
        enableLoop = status;
        if (pagerAdapter != null) {
            pagerAdapter.setEnableLoop(enableLoop);
        }
    }

    /**
     * @param itemRatio 高度系数
     * @return void
     * @description 描述：宽度/高度的系数
     * @date 2018/5/14
     * @author xie
     */
    public void setItemRatio(float itemRatio) {
        this.itemRatio = itemRatio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public void setAutoMeasureHeight(boolean autoMeasureHeight) {
        this.autoMeasureHeight = autoMeasureHeight;
    }

    public void setItemPadding(int left, int top, int right, int bottom) {
        itemPaddingLeft = left;
        itemPaddingTop = top;
        itemPaddingRight = right;
        itemPaddingBottom = bottom;
    }

    /**
     * Set the currently selected page.
     *
     * @param item         Item index to select
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately
     */
    public void setCurrentItemFake(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    /**
     * Get the currently selected page.
     */
    public int getCurrentItemFake() {
        return super.getCurrentItem();
    }
}
