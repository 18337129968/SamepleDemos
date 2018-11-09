package com.example.administrator.samepledemos.ui.activity.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.samepledemos.R;
import com.hfxief.adapter.gridview.base.BaseGridViewAdapter;
import com.hfxief.app.BaseManagers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xie on 2018/5/24.
 */

public class GrideViewAdapter extends BaseGridViewAdapter<String> {
    private ViewHolder holder;

    public GrideViewAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(View view, String item, int position, LayoutInflater layoutInflater) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_fx_rent_info_machine, null, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvMachineName.setText(item);
        holder.tvMachineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseManagers.getToastor().showSingletonToast("position"+position);
            }
        });
        return view;
    }

    class ViewHolder {
        @BindView(R.id.img_machine_icon)
        ImageView imgMachineIcon;
        @BindView(R.id.tv_machine_name)
        TextView tvMachineName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
