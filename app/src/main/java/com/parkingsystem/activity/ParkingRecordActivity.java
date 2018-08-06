package com.parkingsystem.activity;

import android.content.Context;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.ParkingInfo;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.ParkingRecordAdapter;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import java.util.ArrayList;

import static com.parkingsystem.utils.Constant.OK;
import static com.parkingsystem.utils.Constant.URL_MINE_PARKING_RECORD;
import static com.parkingsystem.utils.Constant.URL_USER_INFO;

public class ParkingRecordActivity extends BaseActivity {

    private Context mContext;

    private ArrayList<ParkingInfo> parkingInfos = new ArrayList<>();

    ParkingInfo parkingInfo = new ParkingInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_record);
        mContext = this;

        QueryUtils queryUtils= new QueryUtils(mContext);
        String userName = queryUtils.queryUserName();
        parkingInfos = queryUtils.queryLocalParkingRecord(userName);

        /*ParkingRecordAdapter adapter = new ParkingRecordAdapter(mContext);
        ListView listView = (ListView) findViewById(R.id.lv_parking_record);
        listView.setAdapter(adapter);*/


    }




}
