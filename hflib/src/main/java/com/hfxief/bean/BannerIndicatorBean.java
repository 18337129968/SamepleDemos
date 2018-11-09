package com.hfxief.bean;

import android.util.Pair;

/**
 * Created by xie on 2018/6/11.
 */

public class BannerIndicatorBean extends IBean {
    private Pair<PictureBean, PictureBean> pictures;
    private int norColor;
    private int focusColor;
    private int radius;
    private int indicatorGravity;
    private boolean indicatorOut;
    private boolean indicatorContinu;
    private float indicatorPadding;
    private float mIndicatorMargin = Float.NaN;
    private float mIndicatorMarginTop = Float.NaN;
    private float mIndicatorMarginBottom = Float.NaN;

    public boolean isIndicatorContinu() {
        return indicatorContinu;
    }

    public void setIndicatorContinu(boolean continu) {
        this.indicatorContinu = continu;
    }

    public float getIndicatorPadding() {
        return indicatorPadding;
    }

    public void setIndicatorPadding(float indicatorPadding) {
        this.indicatorPadding = indicatorPadding;
    }

    public boolean isIndicatorOut() {
        return indicatorOut;
    }

    public void setIndicatorOut(boolean indicatorOut) {
        this.indicatorOut = indicatorOut;
    }

    public int getIndicatorGravity() {
        return indicatorGravity;
    }

    public void setIndicatorGravity(int indicatorGravity) {
        this.indicatorGravity = indicatorGravity;
    }

    public float getmIndicatorMarginTop() {
        return mIndicatorMarginTop;
    }

    public void setmIndicatorMarginTop(float mIndicatorMarginTop) {
        this.mIndicatorMarginTop = mIndicatorMarginTop;
    }

    public float getmIndicatorMarginBottom() {
        return mIndicatorMarginBottom;
    }

    public void setmIndicatorMarginBottom(float mIndicatorMarginBottom) {
        this.mIndicatorMarginBottom = mIndicatorMarginBottom;
    }

    public float getmIndicatorMargin() {
        return mIndicatorMargin;
    }

    public void setmIndicatorMargin(float mIndicatorMargin) {
        this.mIndicatorMargin = mIndicatorMargin;
    }

    public Pair<PictureBean, PictureBean> getPictures() {
        return pictures;
    }

    public void setPictures(Pair<PictureBean, PictureBean> pictures) {
        this.pictures = pictures;
    }

    public int getNorColor() {
        return norColor;
    }

    public void setNorColor(int norColor) {
        this.norColor = norColor;
    }

    public int getFocusColor() {
        return focusColor;
    }

    public void setFocusColor(int focusColor) {
        this.focusColor = focusColor;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
