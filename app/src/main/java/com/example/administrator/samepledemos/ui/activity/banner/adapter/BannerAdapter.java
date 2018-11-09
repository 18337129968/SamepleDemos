package com.example.administrator.samepledemos.ui.activity.banner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.hfxief.adapter.recyclerview.base.MultiItemTypeAdapter;
import com.hfxief.bean.BannerBean;

import java.util.List;

/**
 * Created by xie on 2018/6/27.
 */

public class BannerAdapter extends MultiItemTypeAdapter<BannerBean> {
    public static final int TYPE_IMAGE = 2;

    public BannerAdapter(Context context, List<BannerBean> datas, RecyclerView.RecycledViewPool pool) {
        super(context, datas);
        addItemViewDelegate(new BannerItemDelagate(this, pool));
//        addItemViewDelegate(TYPE_IMAGE, new BannerPageItemDelagate());
    }
}
