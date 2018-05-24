package com.hfxief.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.hfxief.R;
import com.hfxief.adapter.gridview.base.BaseGridViewAdapter;

import java.util.Hashtable;

/**
 * Created by xie on 2018/5/24.
 */

public class GridViewLayout extends LinearLayout {
    private TypedArray typedArray;
    private int columns = 1;
    private int columnWidth;

    private Hashtable<View, ChildDimen> map = new Hashtable<View, ChildDimen>();

    public GridViewLayout(Context context) {
        this(context, null);
    }

    public GridViewLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.GridViewLayout, defStyleAttr, 0);
        columns = typedArray.getInteger(R.styleable.GridViewLayout_column, 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        columnWidth = widthSpecSize / columns;
        int mCount = getChildCount();
        int j = 0;
        int mLeft;
        int mRight;
        int mTop = 0;
        int mBottom = 0;
        for (int i = 0; i < mCount; i++) {
            final View child = getChildAt(i);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            mLeft = getLeftWidth(i - j, i);
            mRight = mLeft + columnWidth;
            if (mRight > widthSpecSize) {
                j = i;
                mLeft = getPaddingLeft();
                mRight = mLeft + columnWidth;
                mTop += child.getMeasuredHeight();
            }
            mBottom = mTop + child.getMeasuredHeight();
            ChildDimen childDimen = new ChildDimen(mLeft, mTop, mRight, mBottom);
            map.put(child, childDimen);
        }
        setMeasuredDimension(widthSpecSize, mBottom + getPaddingTop() + getPaddingBottom());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ChildDimen pos = map.get(child);
            if (pos != null) {
                child.layout(pos.left, pos.top, pos.right, pos.bottom);
            }
        }
    }

    private int getLeftWidth(int IndexInRow, int childIndex) {
        if (IndexInRow > 0) {
            return getLeftWidth(IndexInRow - 1, childIndex - 1) + columnWidth;
        }
        return getPaddingLeft();
    }

    public void setAdapter(BaseGridViewAdapter adapter) {
        removeAllViews();
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            View convertView = adapter.getConvertView(i);
            addView(convertView, params);
        }
    }

    private class ChildDimen {
        public int left, top, right, bottom;

        public ChildDimen(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }
}
