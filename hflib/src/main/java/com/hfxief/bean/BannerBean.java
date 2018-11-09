package com.hfxief.bean;

import android.support.v4.view.PagerAdapter;
import android.util.SparseIntArray;

import java.util.List;

/**
 * Created by xie on 2018/5/15.
 */

public class BannerBean extends IBean {
    private boolean enableLoop;
    private boolean centerPage;
    private int currentItem;
    private int backgroundColor;
    private int autoScrollMillis;
    private float paddingLeft;
    private float paddingRight;
    private float pageMargin;
    private float itemWidthRatio = Float.NaN;
    private float pageRatio = Float.NaN;
    private float ItemRatio = Float.NaN;
    private List<IBean> pictures;
    private BannerIndicatorBean indicatorBean;
    private SparseIntArray intervalArray;
    private PagerAdapter pagerApapter;

    public PagerAdapter getPagerApapter() {
        return pagerApapter;
    }

    public void setPagerApapter(PagerAdapter pagerApapter) {
        this.pagerApapter = pagerApapter;
    }

    public SparseIntArray getIntervalArray() {
        return intervalArray;
    }

    public void setIntervalArray(SparseIntArray intervalArray) {
        this.intervalArray = intervalArray;
    }

    public int getAutoScrollMillis() {
        return autoScrollMillis;
    }

    public void setAutoScrollMillis(int autoScrollMillis) {
        this.autoScrollMillis = autoScrollMillis;
    }

    public boolean isCenterPage() {
        return centerPage;
    }

    public void setCenterPage(boolean centerPage) {
        this.centerPage = centerPage;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public float getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(float paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public float getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(float paddingRight) {
        this.paddingRight = paddingRight;
    }

    public float getPageMargin() {
        return pageMargin;
    }

    public void setPageMargin(float pageMargin) {
        this.pageMargin = pageMargin;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isEnableLoop() {
        return enableLoop;
    }

    public void setEnableLoop(boolean enableLoop) {
        this.enableLoop = enableLoop;
    }

    public List<IBean> getPictures() {
        return pictures;
    }

    public void setPictures(List<IBean> pictures) {
        this.pictures = pictures;
    }

    public BannerIndicatorBean getIndicatorBean() {
        return indicatorBean;
    }

    public void setIndicatorBean(BannerIndicatorBean indicatorBean) {
        this.indicatorBean = indicatorBean;
    }

    public float getItemWidthRatio() {
        return itemWidthRatio;
    }

    public void setItemWidthRatio(float itemWidthRatio) {
        this.itemWidthRatio = itemWidthRatio;
        if (itemWidthRatio <= 0) this.itemWidthRatio = Float.NaN;
    }

    public float getPageRatio() {
        return pageRatio;
    }

    public void setPageRatio(float pageRatio) {
        this.pageRatio = pageRatio;
    }

    public float getItemRatio() {
        return ItemRatio;
    }

    public void setItemRatio(float itemRatio) {
        ItemRatio = itemRatio;
    }
}
