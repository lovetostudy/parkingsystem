package com.parkingsystem.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parkingsystem.R;
import com.parkingsystem.entity.User;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ToastUtils;

import java.util.ArrayList;

public class TopupRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_record);
        mContext = this;


        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.del).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);
        tvShow = (TextView) findViewById(R.id.tv_show);
    }

    @Override
    public void onClick(View v) {
        QueryUtils queryUtils = new QueryUtils(mContext);
        switch (v.getId()) {
            case R.id.add:
                User user = new User();
                user.username = "张三";
                user.realname = "李四";
                user.gender = "男";
                user.card = "赣州无敌";
                user.phone = "110120119";
                user.balance = "110.0";
                boolean result = queryUtils.addUserInfo(user);

                if (result) {
                    Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.del:
                int col = queryUtils.delUserInfo("张三");
                Toast.makeText(mContext, "删除了" + col + "行", Toast.LENGTH_SHORT).show();
                break;
            case R.id.update:
                User user2 = new User();
                user2.username = "张三";
                user2.phone = "119245";
                user2.realname = "赵柳";
                int col2 = queryUtils.updateUserInfo(user2);
                Toast.makeText(mContext, "更新了" + col2 + "行", Toast.LENGTH_SHORT).show();
                break;
            case R.id.query:
                /*ArrayList<InfoBean> beanArrayList = infoDao.query("张三");

                MyAdapter myAdapter = new MyAdapter(mContext, beanArrayList);
                lvDatabases.setAdapter(myAdapter);*/

                String userName = queryUtils.queryUserName();
                if ("".equals(userName)) {
                    ToastUtils.show(mContext, "user WRONG");
                } else {
                    ToastUtils.show(mContext, "UserName: " + userName);

                }

   /*             ArrayList<User> userArrayList = queryUtils.queryUserInfo("张三");*/
              /*  if (userArrayList == null) {
                    ToastUtils.show(mContext, "list WRONG");
                }
                ToastUtils.show(mContext, "RealName: " + userArrayList.get(0).getRealname());
                break;*/
            default:
        }
    }
}
