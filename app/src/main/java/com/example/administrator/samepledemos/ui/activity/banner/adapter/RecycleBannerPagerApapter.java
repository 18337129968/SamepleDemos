package com.example.administrator.samepledemos.ui.activity.banner.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.samepledemos.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hfxief.adapter.recyclerview.base.baseitem.ViewHolder;
import com.hfxief.bean.BannerBean;
import com.hfxief.bean.PictureBean;
import com.hfxief.view.banner.adapter.RecyclablePagerAdapter;

/**
 * Created by xie on 2018/6/28.
 */

public class RecycleBannerPagerApapter extends RecyclablePagerAdapter<ViewHolder> {
    private BannerAdapter adapter;
    private BannerBean bannerBean;

    public RecycleBannerPagerApapter(BannerAdapter adapter, RecyclerView.RecycledViewPool pool,  BannerBean bannerBean) {
        super(adapter, pool);
        this.adapter = adapter;
        this.bannerBean = bannerBean;
    }

    @Override
    public int getCount() {
        return bannerBean.getPictures().size();
    }

    @Override
    public int getItemViewType(int position) {
        return adapter.getItemType(bannerBean, position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PictureBean pictureBean = (PictureBean) bannerBean.getPictures().get(position);
        SimpleDraweeView draweeView = holder.getView(R.id.sv_banner);
        if (draweeView != null) draweeView.setImageURI(pictureBean.getUrl());
    }

    @Override
    public float getPageWidth(int position) {
        return Float.isNaN(bannerBean.getItemWidthRatio()) ? 1f : bannerBean.getItemWidthRatio();
    }

}
