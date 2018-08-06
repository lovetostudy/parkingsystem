package com.parkingsystem.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.parkingsystem.R;
import com.parkingsystem.entity.ParkingInfo;
import com.parkingsystem.adapter.ParkingRecordAdapter;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ToastUtils;

import java.util.ArrayList;

public class ParkingRecordActivity extends BaseActivity {

    private Context mContext;

    private ArrayList<ParkingInfo> parkingInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_record);
        mContext = this;

        QueryUtils queryUtils= new QueryUtils(mContext);
        String userName = queryUtils.queryUserName();
        parkingInfos = queryUtils.queryLocalParkingRecord(userName);

        ParkingRecordAdapter adapter = new ParkingRecordAdapter(mContext, parkingInfos);
        ListView listView = (ListView) findViewById(R.id.lv_parking_record);
        listView.setAdapter(adapter);
    }
}
