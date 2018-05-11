package com.hfxief.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * inspection
 * com.isoftstone.inspection.ui.view
 *
 * @Author: xie
 * @Time: 2016/12/29 9:49
 * @Description:自适应listview解救listview和ScrollView冲突
 */
public class AutoListview extends ListView {

    public AutoListview(Context context) {
        super(context);
    }

    public AutoListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
