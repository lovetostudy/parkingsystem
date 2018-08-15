package com.parkingsystem.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.TopupInfo;

import java.util.ArrayList;

public class SettingAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<String> arrayList;

    public SettingAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = View.inflate(mContext, R.layout.setting_item, null);
        }

        TextView settingTv = (TextView) view.findViewById(R.id.setting_tv);

        String display = arrayList.get(position);

        settingTv.setText(display);

        return view;
    }
}
