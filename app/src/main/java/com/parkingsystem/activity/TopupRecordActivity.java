package com.parkingsystem.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parkingsystem.R;
import com.parkingsystem.entity.TopupInfo;
import com.parkingsystem.entity.User;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ToastUtils;

import java.util.ArrayList;

public class TopupRecordActivity extends AppCompatActivity {

    private Context mContext;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_record);
        mContext = this;

        Button button = (Button) findViewById(R.id.query);
        ArrayList<TopupInfo> topupInfoArrayList;


        QueryUtils queryUtils = new QueryUtils(mContext);
        topupInfoArrayList = queryUtils.queryLocalTopupRecord("刘一");

        ToastUtils.show(mContext, topupInfoArrayList.get(0).money);
    }

}
