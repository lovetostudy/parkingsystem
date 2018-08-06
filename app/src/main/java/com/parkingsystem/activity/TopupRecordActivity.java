package com.parkingsystem.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parkingsystem.R;
import com.parkingsystem.adapter.TopupRecordAdapter;
import com.parkingsystem.entity.TopupInfo;
import com.parkingsystem.entity.User;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ToastUtils;

import java.util.ArrayList;

public class TopupRecordActivity extends AppCompatActivity {

    private Context mContext;

    ArrayList<TopupInfo> topupInfoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_record);
        mContext = this;

        QueryUtils queryUtils = new QueryUtils(mContext);
        String userName = queryUtils.queryUserName();
        topupInfoArrayList = queryUtils.queryLocalTopupRecord(userName);

        TopupRecordAdapter adapter = new TopupRecordAdapter(mContext, topupInfoArrayList);
        ListView listView = (ListView) findViewById(R.id.lv_topup_record);
        listView.setAdapter(adapter);
    }

}
