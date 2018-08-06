package com.parkingsystem.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.ParkingInfo;

import java.util.ArrayList;

public class ParkingRecordAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<ParkingInfo> parkingInfos;

    public ParkingRecordAdapter(Context context/*, ArrayList<ParkingInfo> parkingInfos*/) {
        this.mContext = context;
        this.parkingInfos = parkingInfos;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return parkingInfos.get(position);
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
            view = View.inflate(mContext, R.layout.parking_item, null);
        }

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        TextView userValue = (TextView) view.findViewById(R.id.user_value);
        /*TextView costName = (TextView) view.findViewById(R.id.cost_name);
        TextView costValue = (TextView) view.findViewById(R.id.cost_value);
        TextView startName = (TextView) view.findViewById(R.id.start_name);
        TextView startValue = (TextView) view.findViewById(R.id.start_value);
        TextView endName = (TextView) view.findViewById(R.id.end_name);
        TextView endValue = (TextView) view.findViewById(R.id.end_value);*/

        ParkingInfo parkingInfo = parkingInfos.get(position);

        userName.setText("用户名");
        userValue.setText("ok");
        /*costName.setText("费用");
        costValue.setText(parkingInfo.cost);
        startName.setText("开始时间");
        startValue.setText(parkingInfo.startTime);
        endName.setText("结束时间");
        endValue.setText(parkingInfo.endTime);*/

        return view;
    }
}
