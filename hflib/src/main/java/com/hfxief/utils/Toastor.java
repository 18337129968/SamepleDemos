package com.hfxief.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * Created by IntelliJ IDEA in MaitianCommonLibrary
 * cn.maitian.app.library.utils.assist
 *
 * @Author: xie
 * @Time: 2016/6/6 17:32
 * @Description:
 */
public class Toastor {

    private Toast mToast;
    private Context context;

    public Toastor(Context context) {
        this.context = context.getApplicationContext();
    }

    public Toast getSingletonToast(int resId) {
        if (mToast == null) {
            mToast = makeText(context, resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        return mToast;
    }

    public Toast getSingletonToast(String text) {
        if (mToast == null) {
            mToast = makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    public Toast getSingleLongToast(int resId) {
        if (mToast == null) {
            mToast = makeText(context, resId, Toast.LENGTH_LONG);
        } else {
            mToast.setText(resId);
        }
        return mToast;
    }

    public Toast getSingleLongToast(String text) {
        if (mToast == null) {
            mToast = makeText(context, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    public Toast getToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        } else {
            mToast.cancel();
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }
        return mToast;
    }

    public Toast getToast(String text) {
        if (mToast == null) {
            mToast = makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.cancel();
            mToast = makeText(context, text, Toast.LENGTH_SHORT);
        }
        return mToast;
    }

    public Toast getLongToast(int resId) {
        if (mToast == null) {
            mToast = makeText(context, resId, Toast.LENGTH_LONG);
        } else {
            mToast.cancel();
            mToast = makeText(context, resId, Toast.LENGTH_LONG);
        }
        return mToast;
    }

    public Toast getLongToast(String text) {
        if (mToast == null) {
            mToast = makeText(context, text, Toast.LENGTH_LONG);
        } else {
            mToast.cancel();
            mToast = makeText(context, text, Toast.LENGTH_LONG);
        }
        return mToast;
    }

    public void showSingletonToast(int resId) {
        getSingletonToast(resId).show();
    }


    public void showSingletonToast(String text) {
        if (!TextUtils.isEmpty(text))
            getSingletonToast(text).show();
    }

    public void showSingleLongToast(int resId) {
        getSingleLongToast(resId).show();
    }


    public void showSingleLongToast(String text) {
        if (!TextUtils.isEmpty(text))
            getSingleLongToast(text).show();
    }

    public void showToast(int resId) {
        getToast(resId).show();
    }

    public void showToast(String text) {
        getToast(text).show();
    }

    public void showLongToast(int resId) {
        getLongToast(resId).show();
    }

    public void showLongToast(String text) {
        getLongToast(text).show();
    }

}
