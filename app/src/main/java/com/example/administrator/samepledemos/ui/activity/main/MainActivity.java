package com.example.administrator.samepledemos.ui.activity.main;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.samepledemos.R;
import com.example.administrator.samepledemos.ui.activity.banner.BannerActivity;
import com.example.administrator.samepledemos.ui.activity.banner.BannerPoolActivity;
import com.example.administrator.samepledemos.ui.activity.base.BaseActivity;
import com.example.administrator.samepledemos.ui.activity.camera.CameraActivity;
import com.example.administrator.samepledemos.ui.activity.gridview.GridViewActivity;
import com.hfxief.adapter.listview.base.CommonAdapter;
import com.hfxief.adapter.listview.base.baseItem.ViewHolder;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseActivity {
    @BindView(R.id.listView)
    ListView listView;

    private List<Pair<String, String>> mClasses = new ArrayList<>();

    @Override
    protected void startWork(Bundle savedInstanceState) {
        setCheckNetWork(true);
        setTittleText("首页");
        initView();
    }

    private void initView() {
        Observable.just(CameraActivity.class, GridViewActivity.class, BannerActivity.class, BannerPoolActivity.class)
                .collect(ArrayList::new, (Action2<List<Pair<String, String>>, Class<? extends BaseActivity>>) (pairs, aClass) -> {
                    Pair<String, String> pair = new Pair<>(aClass.getSimpleName(), aClass.getName());
                    pairs.add(pair);
                })
                .flatMap(pairs -> {
                    mClasses.addAll(pairs);
                    return Observable.from(pairs).collect(ArrayList::new, (Action2<List<String>, Pair<String, String>>) (names, pair) -> names.add(pair.first));
                })
                .subscribe(pairs -> listView.setAdapter(new CommonAdapter<String>(this, android.R.layout.simple_list_item_1, pairs) {
                    @Override
                    protected void convert(ViewHolder holder, String item, int position) {
                        holder.setText(android.R.id.text1, item);
                    }

                    @Override
                    public void onViewHolderCreated(ViewHolder holder, View itemView) {
                        super.onViewHolderCreated(holder, itemView);
                    }
                }));
        RxAdapterView.itemClicks(listView).throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .map(integer -> mClasses.get(integer).second)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aClass -> {
                    startActivity(new Intent().setComponent(new ComponentName(MainActivity.this, aClass)));
                });
    }

    @Override
    protected void stopWork() {

    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
