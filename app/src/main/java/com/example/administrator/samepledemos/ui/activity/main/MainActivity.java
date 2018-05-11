package com.example.administrator.samepledemos.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.samepledemos.R;
import com.example.administrator.samepledemos.local.ClassEnum;
import com.example.administrator.samepledemos.ui.activity.base.BaseActivity;
import com.example.administrator.samepledemos.ui.activity.camera.CameraActivity;
import com.hfxief.adapter.listview.base.CommonAdapter;
import com.hfxief.adapter.listview.base.baseItem.ViewHolder;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;

    private List<String> names = new ArrayList<>();

    @Override
    protected void startWork(Bundle savedInstanceState) {
        setTittleText("首页");
        initData();
    }

    private void initClass() {
        names.add(CameraActivity.class.getSimpleName());
        ClassEnum.addClass(CameraActivity.class);
    }

    private void initData() {
        initClass();
        listView.setAdapter(new CommonAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, names) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                holder.setText(android.R.id.text1, item);
            }

            @Override
            public void onViewHolderCreated(ViewHolder holder, View itemView) {
                super.onViewHolderCreated(holder, itemView);

            }
        });
        RxAdapterView.itemClicks(listView).throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .map((Func1<Integer, Class<?>>) integer -> ClassEnum.valueOf(integer))
                .filter(aClass -> aClass != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aClass -> startActivity(new Intent(MainActivity.this, aClass)));
    }

    @Override
    protected void stopWork() {

    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

}
