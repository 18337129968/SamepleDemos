package com.hfxief.utils;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

/**
 * inspection
 * com.isoftstone.inspection.utils
 *
 * @Author: xie
 * @Time: 2016/12/30 14:20
 * @Description:
 */

public class SpanBuilderUtlis {

    private static SpanBuilderUtlis instanse;
    private StyleSpan span;
    private SpannableStringBuilder spannable;

    public static SpanBuilderUtlis getInstanse() {
        if (instanse == null) instanse = new SpanBuilderUtlis();
        return instanse;
    }

    public void setSpan(String title, String content, TextView tv) {
        if (spannable == null) {
            spannable = new SpannableStringBuilder();
            span = new StyleSpan(Typeface.BOLD);
        } else spannable.clear();
        spannable.append(title);
        spannable.append(" ");
        spannable.append(content);
        spannable.setSpan(span, 0, title.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannable);
    }

    public void releaseSpan() {
        spannable.clear();
        spannable.clearSpans();
        spannable = null;
        span =null;
    }
}
