package com.example.administrator.samepledemos.ui.activity.banner.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.samepledemos.R;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hfxief.adapter.recyclerview.base.baseitem.ItemViewDelegate;
import com.hfxief.adapter.recyclerview.base.baseitem.ViewHolder;
import com.hfxief.app.BaseManagers;
import com.hfxief.bean.BannerBean;
import com.hfxief.bean.IBean;
import com.hfxief.bean.PictureBean;
import com.hfxief.view.banner.BannerView;
import com.hfxief.view.structure.IInnerImageSetter;
import com.hfxief.view.structure.OnBannerItemClickLisenter;

/**
 * Created by xie on 2018/6/28.
 */

public class BannerItemDelagate implements ItemViewDelegate<BannerBean>, IInnerImageSetter, OnBannerItemClickLisenter {
    private RecyclerView.RecycledViewPool pool;
    private BannerAdapter bannerAdapter;

    public BannerItemDelagate(BannerAdapter bannerAdapter, RecyclerView.RecycledViewPool pool) {
        this.bannerAdapter = bannerAdapter;
        this.pool = pool;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_banner;
    }

    @Override
    public boolean isForViewType(BannerBean item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, BannerBean iBean, int position) {
        BannerBean bean = (BannerBean) iBean;
        BannerView bnv = holder.getView(R.id.bnv);
        if (bnv != null) {
            bnv.setImageSetter(this, SimpleDraweeView.class);
            bnv.setOnBannerItemClickLisenter(this);
            RecycleBannerPagerApapter adapter = new RecycleBannerPagerApapter(bannerAdapter, pool, bean);
            bean.setPagerApapter(adapter);
            bnv.postBindView(iBean);
        }

    }

    @Override
    public void recycled(ViewHolder holder, BannerBean item) {
        BannerBean bean = (BannerBean) item;
        BannerView bnv = holder.getView(R.id.bnv);
        bean.setCurrentItem(bnv.getCurrentItem());
    }


    @Override
    public <V extends ImageView> void doLoadImageUrl(@NonNull V v, @NonNull IBean picture) {
        if (v instanceof SimpleDraweeView && picture instanceof PictureBean) {
            PictureBean pictureBean = (PictureBean) picture;
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) v;
            GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
            hierarchy.setFailureImage(R.mipmap.ic_error);
//            hierarchy.setRoundingParams(new RoundingParams().setCornersRadius(1));
//            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//            simpleDraweeView.setHierarchy(hierarchy);
            simpleDraweeView.setImageURI(pictureBean.getUrl());
        }
    }

    @Override
    public <T extends IBean> void onBannerItemClick(View v, @NonNull T item, @Nullable int position) {
        BaseManagers.getToastor().showSingletonToast("onBannerItemClick===" + position);
    }
}
