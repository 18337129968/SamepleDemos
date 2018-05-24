package com.example.administrator.samepledemos.ui.activity.gridview;

import android.os.Bundle;

import com.example.administrator.samepledemos.R;
import com.example.administrator.samepledemos.ui.activity.base.BaseActivity;
import com.hfxief.view.GridViewLayout;

import java.util.Arrays;

import butterknife.BindView;

public class GridViewActivity extends BaseActivity {

    @BindView(R.id.house_info_assort)
    GridViewLayout houseInfoAssort;

    private String[] names = {"空调","洗衣机","冰箱","电视","空调","洗衣机","冰箱","电视","空调","洗衣机","冰箱","电视"};

    @Override
    protected void startWork(Bundle savedInstanceState) {
        setTittleText(this.getClass().getSimpleName());
        houseInfoAssort.setAdapter(new GrideViewAdapter(this,Arrays.asList(names)));
    }

    @Override
    protected void stopWork() {

    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_grid_view;
    }

}
