package com.parkingsystem.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.parkingsystem.R;
import com.parkingsystem.entity.User;
import com.parkingsystem.entity.UserInfo;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.adapter.UserInfoAdapter;

import java.util.ArrayList;

public class InfoActivity extends BaseActivity {

    private Context mContext;

    private User user;

    private ArrayList<User> userInfoList = new ArrayList<>();
    private Button logout;
    private QueryUtils queryUtils;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mContext = this;

        queryUtils = new QueryUtils(mContext);
        userName = queryUtils.queryUserName();
        User user = queryUtils.queryUserInfo(userName);
        userInfoList.add(user);

        logout = (Button) findViewById(R.id.bt_info_logout);
        UserInfoAdapter adapter = new UserInfoAdapter(mContext, userInfoList);
        ListView listView = (ListView) findViewById(R.id.info_list_view);
        listView.setAdapter(adapter);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });
    }

    /**
     * 清除本地缓存
     */
    private void clearData() {
        queryUtils.delUserInfo(userName);
        queryUtils.delParkingRecord(userName);
        queryUtils.delTopupRecord(userName);
        finish();
    }

}
