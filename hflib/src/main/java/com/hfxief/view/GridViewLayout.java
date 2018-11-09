package com.hfxief.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.hfxief.R;
import com.hfxief.adapter.gridview.base.BaseGridViewAdapter;

import java.util.concurrent.ConcurrentHashMap;

import rx.functions.Action1;

/**
 * Created by xie on 2018/5/24.
 */

public class GridViewLayout extends LinearLayout {
    private TypedArray typedArray;
    private BaseGridViewAdapter adapter;
    private int columns = 1;
    private int columnWidth;
    private ConcurrentHashMap<View, ChildDimen> map = new ConcurrentHashMap<>();

    private void addView(BaseGridViewAdapter adapter) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            View convertView = adapter.getConvertView(getChildAt(i), i);
            if (getChildAt(i) != null) {
                removeViewAt(i);
            }
            addView(convertView, i, params);
        }
        if (count < getChildCount()) {
            removeViews(count, getChildCount() - count);
        }
    }

    public GridViewLayout(Context context) {
        this(context, null);
    }

    public GridViewLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (typedArray == null) {
            typedArray = context.obtainStyledAttributes(
                    attrs, R.styleable.GridViewLayout, defStyleAttr, 0);
            columns = typedArray.getInteger(R.styleable.GridViewLayout_column, 1);
        }

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
            child.measure(MeasureSpec.makeMeasureSpec(columnWidth, MeasureSpec.EXACTLY), MeasureSpec.UNSPECIFIED);
            mLeft = getLeftWidth(i - j);
            mRight = mLeft + columnWidth;
            if (mRight > widthSpecSize) {
                j = i;
                mLeft = getPaddingLeft();
                mRight = mLeft + columnWidth;
                mTop += child.getMeasuredHeight();
            }
            mBottom = mTop + child.getMeasuredHeight();
            map.put(child, new ChildDimen(mLeft, mTop, mRight, mBottom));
        }
        setMeasuredDimension(widthSpecSize, mBottom + getPaddingTop() + getPaddingBottom());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ChildDimen pos = map.remove(child);
            if (pos != null) {
                child.layout(pos.left, pos.top, pos.right, pos.bottom);
            }
        }
    }

    private int getLeftWidth(int IndexInRow) {
        if (IndexInRow > 0) {
            return getLeftWidth(IndexInRow - 1) + columnWidth;
        }
        return getPaddingLeft();
    }

    public void setAdapter(BaseGridViewAdapter adapter) {
        this.adapter = adapter;
        adapter.getObservable().subscribe(new GridViewAction());
        addView(adapter);
    }

    class ChildDimen {
        public int left, top, right, bottom;

        private ChildDimen(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }

    class GridViewAction implements Action1<Void> {

        @Override
        public void call(Void aVoid) {
            if (adapter != null) {
                addView(adapter);
            }
        }
    }

}
