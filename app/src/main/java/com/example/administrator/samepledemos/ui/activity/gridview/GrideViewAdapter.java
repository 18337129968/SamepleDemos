package com.example.administrator.samepledemos.ui.activity.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.samepledemos.R;
import com.hfxief.adapter.gridview.base.BaseGridViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xie on 2018/5/24.
 */

public class GrideViewAdapter extends BaseGridViewAdapter {
    @BindView(R.id.img_machine_icon)
    ImageView imgMachineIcon;
    @BindView(R.id.tv_machine_name)
    TextView tvMachineName;
    private List<String > data;

    public GrideViewAdapter(Context context, List<String> data) {
        super(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_fx_rent_info_machine, null, false);
        ButterKnife.bind(this, view);
        tvMachineName.setText(data.get(position));
        return view;
    }

}
