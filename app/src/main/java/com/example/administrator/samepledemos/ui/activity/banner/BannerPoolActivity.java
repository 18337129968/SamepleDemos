package com.example.administrator.samepledemos.ui.activity.banner;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.samepledemos.R;
import com.example.administrator.samepledemos.ui.activity.base.BaseActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class BannerPoolActivity extends BaseActivity {
    private AtomicInteger mTypeId = new AtomicInteger(0);

    @Override
    protected void startWork(Bundle savedInstanceState) {
        setTittleText(this.getClass().getSimpleName());
        setLeftView(View.VISIBLE);

    }

    @Override
    protected void stopWork() {

    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_banner_pool;
    }


}
