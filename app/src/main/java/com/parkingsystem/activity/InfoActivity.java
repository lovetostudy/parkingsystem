package com.parkingsystem.activity;

import android.content.Context;
import android.os.Bundle;
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

    private ArrayList<UserInfo> userInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mContext = this;

        initData();

        UserInfoAdapter adapter = new UserInfoAdapter(mContext, userInfoList);
        ListView listView = (ListView) findViewById(R.id.info_list_view);
        listView.setAdapter(adapter);
    }

    /**
     * 将用户信息放入 arrayList 中
     */
    private void initData() {
        QueryUtils queryUtils= new QueryUtils(mContext);
        String userName = queryUtils.queryUserName();
        User user = queryUtils.queryUserInfo(userName);
        UserInfo username = new UserInfo("用户名", user.username);
        userInfoList.add(username);
        UserInfo realname = new UserInfo("真实姓名", user.realname);
        userInfoList.add(realname);
        UserInfo gender = new UserInfo("性  别", user.gender);
        userInfoList.add(gender);
        UserInfo card = new UserInfo("车牌号", user.card);
        userInfoList.add(card);
        UserInfo phone = new UserInfo("手机号", user.phone);
        userInfoList.add(phone);
        UserInfo balance = new UserInfo("余  额", user.balance);
        userInfoList.add(balance);
        UserInfo space = new UserInfo("  ", " ");
        userInfoList.add(space);
    }
}
