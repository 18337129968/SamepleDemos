package com.hfxief.view.structure;

/**
 * Created by xie on 2018/6/26.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hfxief.bean.IBean;

public interface OnBannerItemClickLisenter {
    <T extends IBean> void onBannerItemClick(View v, @NonNull T item, @Nullable int position);
}
