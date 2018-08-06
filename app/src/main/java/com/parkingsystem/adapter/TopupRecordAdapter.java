package com.parkingsystem.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.TopupInfo;

import java.util.ArrayList;

public class TopupRecordAdapter extends BaseAdapter {

    private final Context mContext;

    private final ArrayList<TopupInfo> topupInfos;

    public TopupRecordAdapter(Context context, ArrayList<TopupInfo> topupInfos) {
        this.mContext = context;
        this.topupInfos = topupInfos;
    }

    @Override
    public int getCount() {
        return topupInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return topupInfos.get(position);
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
            view = View.inflate(mContext, R.layout.topup_item, null);
        }

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        TextView userValue = (TextView) view.findViewById(R.id.user_value);
        TextView topupName = (TextView) view.findViewById(R.id.topup_name);
        TextView topupValue = (TextView) view.findViewById(R.id.topup_value);
        TextView timeName = (TextView) view.findViewById(R.id.time_name);
        TextView timeValue = (TextView) view.findViewById(R.id.time_value);


        TopupInfo topupInfo = topupInfos.get(position);

        userName.setText("用户名");
        userValue.setText(topupInfo.userName);
        topupName.setText("充值金额");
        topupValue.setText(topupInfo.money);
        timeName.setText("充值时间");
        timeValue.setText(topupInfo.time);

        return view;
    }
}
