package com.example.administrator.samepledemos.ui.activity.gridview;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.samepledemos.R;
import com.example.administrator.samepledemos.ui.activity.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hfxief.adapter.listview.base.MultiItemTypeAdapter;
import com.hfxief.adapter.listview.base.baseItem.ItemViewDelegate;
import com.hfxief.adapter.listview.base.baseItem.ViewHolder;
import com.hfxief.view.GridViewLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class GridViewActivity extends BaseActivity {

    @BindView(R.id.rv_gridview)
    ListView rvGridview;
    private List<GridViewBean> list = new ArrayList();
    private String[] names = {"空调", "洗衣机", "冰箱", "电视", "空调", "洗衣机", "冰箱", "电视", "空调", "洗衣机", "冰箱", "电视"};

    @Override
    protected void startWork(Bundle savedInstanceState) {
        setTittleText(this.getClass().getSimpleName());
        GridViewBean head = new GridViewBean();
        head.setName("https://gw.alicdn.com/tfscom/TB2tDyXnwNlpuFjy0FfXXX3CpXa_!!0-juitemmedia.jpg");
        head.setType(0);
        list.add(head);
        for (int i = 0; i < 125; i++) {
            GridViewBean content = new GridViewBean();
            content.setNames(names);
            content.setType(1);
            list.add(content);
        }
        rvGridview.setAdapter(new MyApapter(this, list));
    }

    @Override
    protected void stopWork() {

    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_grid_view;
    }

    class MyApapter extends MultiItemTypeAdapter<GridViewBean> {

        public MyApapter(Context context, List datas) {
            super(context, datas);
            addItemViewDelegate(new HeadItemViewDelegate());
            addItemViewDelegate(new ContentItemViewDelegate(context));
        }
    }

    class HeadItemViewDelegate implements ItemViewDelegate<GridViewBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_banner_page;
        }

        @Override
        public boolean isForViewType(GridViewBean item, int position) {
            return item.getType() == 0;
        }

        @Override
        public void convert(ViewHolder holder, GridViewBean item, int position) {
            SimpleDraweeView simpleDraweeView = holder.getView(R.id.sv_banner);
            simpleDraweeView.setVisibility(View.GONE);
        }

    }

    class ContentItemViewDelegate implements ItemViewDelegate<GridViewBean> {
        private Context context;

        public ContentItemViewDelegate(Context context) {
            this.context = context;
        }

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_gridview;
        }

        @Override
        public boolean isForViewType(GridViewBean item, int position) {
            return item.getType() == 1;
        }

        @Override
        public void convert(ViewHolder holder, GridViewBean item, int position) {
            GridViewLayout houseInfoAssort = holder.getView(R.id.house_info_assort);
//            houseInfoAssort.setVisibility(View.GONE);
            houseInfoAssort.setAdapter(new GrideViewAdapter(context, Arrays.asList(item.getNames())));
        }

    }


}
