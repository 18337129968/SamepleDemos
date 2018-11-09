package com.example.administrator.samepledemos.ui.activity.banner;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.example.administrator.samepledemos.R;
import com.example.administrator.samepledemos.ui.activity.banner.adapter.BannerAdapter;
import com.example.administrator.samepledemos.ui.activity.base.BaseActivity;
import com.hfxief.bean.BannerBean;
import com.hfxief.bean.BannerIndicatorBean;
import com.hfxief.bean.PictureBean;
import com.hfxief.utils.DensityUtils;
import com.hfxief.view.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BannerActivity extends BaseActivity {
    @BindView(R.id.rv_banner)
    RecyclerView rvBanner;
    private BannerAdapter adapter;

    private List<BannerBean> data = new ArrayList<>();

    private void notifyAdapter() {
        if (adapter == null) {
            adapter = new BannerAdapter(this, data, rvBanner.getRecycledViewPool());
            rvBanner.setLayoutManager(new LinearLayoutManager(this));
            rvBanner.setHasFixedSize(true);
            rvBanner.setAdapter(adapter);
            rvBanner.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private BannerBean getPictures(BannerBean bean) {
        PictureBean picturesBean = new PictureBean();
        picturesBean.setType(BannerAdapter.TYPE_IMAGE);
        picturesBean.setUrl("https://gw.alicdn.com/tfscom/TB2tDyXnwNlpuFjy0FfXXX3CpXa_!!0-juitemmedia.jpg");
        bean.getPictures().add(picturesBean);
        PictureBean picturesBean1 = new PictureBean();
        picturesBean1.setUrl("https://gw.alicdn.com/tfscom/TB2RShWdiBnpuFjSZFzXXaSrpXa_!!0-juitemmedia.jpg");
        bean.getPictures().add(picturesBean1);
        PictureBean picturesBean2 = new PictureBean();
        picturesBean2.setUrl("https://gw.alicdn.com/tfscom///img.alicdn.com/bao/uploaded/TB1h3f5LFXXXXc.XXXXSutbFXXX.jpg");
        bean.getPictures().add(picturesBean2);
        PictureBean picturesBean3 = new PictureBean();
        picturesBean3.setUrl("https://gw.alicdn.com/tfscom/TB2RShWdiBnpuFjSZFzXXaSrpXa_!!0-juitemmedia.jpg");
        bean.getPictures().add(picturesBean3);
        PictureBean picturesBean4 = new PictureBean();
        picturesBean4.setUrl("https://gw.alicdn.com/tfscom///img.alicdn.com/bao/uploaded/TB1h3f5LFXXXXc.XXXXSutbFXXX.jpg");
        bean.getPictures().add(picturesBean4);
        PictureBean picturesBean5 = new PictureBean();
        picturesBean5.setUrl("https://gw.alicdn.com/tfscom/TB2M3.FmNxmpuFjSZFNXXXrRXXa_!!2368290057-0-beehive-scenes.jpg");
        bean.getPictures().add(picturesBean5);
        return bean;
    }

    private BannerBean getNormalBanner() {
        BannerBean bean = new BannerBean();
        bean.setPictures(new ArrayList<>());
        getPictures(bean);
        bean.setBackgroundColor(R.color.gray_cccccc);
        bean.setIndicatorBean(new BannerIndicatorBean());
        return bean;
    }

    private BannerBean getBannerPictureIndicator(BannerBean bean) {
        PictureBean focusUrl = new PictureBean();
//        focusUrl.setUrl("https://img.alicdn.com/tps/TB16i4qNXXXXXbBXFXXXXXXXXXX-32-4.png");
        focusUrl.setUrl("https://gw.alicdn.com/tps/TB1m79uLpXXXXXUXVXXXXXXXXXX-18-16.png");
        PictureBean norUrl = new PictureBean();
//        norUrl.setUrl("https://img.alicdn.com/tps/TB1XRNFNXXXXXXKXXXXXXXXXXXX-18-4.png");
        norUrl.setUrl("https://gw.alicdn.com/tps/TB16RaxLpXXXXbIXFXXXXXXXXXX-14-14.png");
        bean.getIndicatorBean().setPictures(new Pair<>(focusUrl, norUrl));
        return bean;
    }

    private BannerBean getBannerPointIndicator(BannerBean bean) {
        bean.getIndicatorBean().setNorColor(getResources().getColor(R.color.gray_cccccc));
        bean.getIndicatorBean().setFocusColor(getResources().getColor(R.color.white_fff));
        bean.getIndicatorBean().setRadius(10);
        return bean;
    }

    @Override
    protected void startWork(Bundle savedInstanceState) {
        setTittleText(this.getClass().getSimpleName());
//        notifyAdapter();
        BannerBean banner1 = getBannerPointIndicator(getNormalBanner());
        banner1.getIndicatorBean().setIndicatorPadding(8);
        data.add(banner1);
        BannerBean banner2 = getBannerPictureIndicator(getNormalBanner());
        banner2.getIndicatorBean().setIndicatorContinu(true);
        banner2.getIndicatorBean().setIndicatorGravity(BannerView.GRAVITY_CENTER);
        data.add(banner2);
        BannerBean banner3 = getBannerPictureIndicator(getNormalBanner());
        banner3.getIndicatorBean().setmIndicatorMargin(2);
        banner3.getIndicatorBean().setmIndicatorMarginBottom(5);
        banner3.getIndicatorBean().setmIndicatorMarginTop(5);
        banner3.getIndicatorBean().setIndicatorOut(true);
        banner3.getIndicatorBean().setIndicatorGravity(BannerView.GRAVITY_CENTER);
        data.add(banner3);
        BannerBean banner4 = getBannerPictureIndicator(getNormalBanner());
        banner4.getIndicatorBean().setIndicatorGravity(BannerView.GRAVITY_CENTER);
        banner4.getIndicatorBean().setmIndicatorMarginBottom(25);
        banner4.setPageRatio(1);
        data.add(banner4);
        BannerBean banner5 = getBannerPictureIndicator(getNormalBanner());
        banner5.getIndicatorBean().setIndicatorGravity(BannerView.GRAVITY_CENTER);
        banner5.getIndicatorBean().setIndicatorOut(true);
        banner5.setItemWidthRatio(0.5f);
        banner5.setItemRatio(1);
        banner5.setPaddingLeft(10);
        banner5.setPaddingRight(10);
        banner5.setPageMargin(10);
        banner5.setEnableLoop(true);
        data.add(banner5);
        BannerBean banner6 = getBannerPictureIndicator(getNormalBanner());
        banner6.getIndicatorBean().setIndicatorGravity(BannerView.GRAVITY_CENTER);
        banner6.getIndicatorBean().setIndicatorOut(true);
        float withRatio = 0.97f;
        float sum = DensityUtils.px2dp(this, (float) (getResources().getDisplayMetrics().widthPixels));
        float padding = (1 - withRatio) * sum / (2 - withRatio);
        banner6.setItemWidthRatio(withRatio);
        banner6.setItemRatio(1);
        banner6.setPaddingLeft(padding);
        banner6.setPageMargin(padding);
        banner6.setEnableLoop(true);
        data.add(banner6);
        BannerBean banner7 = getBannerPictureIndicator(getNormalBanner());
        banner7.getIndicatorBean().setIndicatorGravity(BannerView.GRAVITY_CENTER);
        banner7.getIndicatorBean().setIndicatorOut(true);
        banner7.getIndicatorBean().setIndicatorContinu(true);
        banner7.setItemRatio(4 / 6f);
        banner7.setItemWidthRatio(0.8f);
        banner7.setAutoScrollMillis(2000);
        banner7.setPageMargin(10);
        banner7.setCenterPage(true);
        banner7.setEnableLoop(true);
        data.add(banner7);
        notifyAdapter();
    }

    @Override
    protected void stopWork() {

    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_banner;
    }


}
