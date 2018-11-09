package com.example.administrator.samepledemos.ui.activity.banner.adapter;

import com.example.administrator.samepledemos.R;
import com.hfxief.adapter.recyclerview.base.baseitem.ItemViewDelegate;
import com.hfxief.adapter.recyclerview.base.baseitem.ViewHolder;
import com.hfxief.bean.BannerBean;

/**
 * Created by xie on 2018/6/28.
 */

public class BannerPageItemDelagate implements ItemViewDelegate<BannerBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_banner_page;
    }

    @Override
    public boolean isForViewType(BannerBean item, int position) {
        return item.getPictures().get(position).getType() == 2;
    }

    @Override
    public void convert(ViewHolder holder, BannerBean item, int position) {

    }

    @Override
    public void recycled(ViewHolder holder, BannerBean item) {

    }


}
