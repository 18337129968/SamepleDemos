package com.hfxief.view.structure;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.hfxief.bean.IBean;

/**
 * Created by xie on 2018/6/8.
 */

public interface IInnerImageSetter {
    <V extends ImageView> void doLoadImageUrl(@NonNull V v, @NonNull IBean picture);
}
