package com.hfxief.view.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by xie on 2018/5/11.
 */

public class BannerViewPager extends ViewPager {

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

        } else {
            super.setAdapter(adapter);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onMeasurePage(widthMeasureSpec, heightMeasureSpec);
    }

    private void onMeasurePage(int widthMeasureSpec, int heightMeasureSpec) {
        
    }
}
