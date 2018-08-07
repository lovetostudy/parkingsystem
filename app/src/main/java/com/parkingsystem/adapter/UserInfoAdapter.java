package com.parkingsystem.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.User;

import java.util.ArrayList;

public class UserInfoAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<User> userArrayList;

    public UserInfoAdapter(Context context, ArrayList<User> userArrayList) {
        this.mContext = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
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
            view = View.inflate(mContext, R.layout.info_item, null);
        }

        TextView userName = (TextView) view.findViewById(R.id.info_user_name);
        TextView userValue = (TextView) view.findViewById(R.id.info_user_value);
        TextView realName = (TextView) view.findViewById(R.id.info_real_name);
        TextView realValue = (TextView) view.findViewById(R.id.info_real_value);
        TextView genderName = (TextView) view.findViewById(R.id.info_gender_name);
        TextView genderValue = (TextView) view.findViewById(R.id.info_gender_value);
        TextView carName = (TextView) view.findViewById(R.id.info_car_name);
        TextView carValue = (TextView) view.findViewById(R.id.info_car_value);
        TextView phoneName = (TextView) view.findViewById(R.id.info_phone_name);
        TextView phoneValue = (TextView) view.findViewById(R.id.info_phone_value);
        TextView balanceName = (TextView) view.findViewById(R.id.info_balance_name);
        TextView balanceValue = (TextView) view.findViewById(R.id.info_balance_value);


        User user = userArrayList.get(position);

        userName.setText("用 户 名");
        userValue.setText(user.username);
        realName.setText("真实姓名");
        realValue.setText(user.realname);
        genderName.setText("性    别");
        genderValue.setText(user.gender);
        carName.setText("车 牌 号");
        carValue.setText(user.card);
        phoneName.setText("手 机 号");
        phoneValue.setText(user.phone);
        balanceName.setText("余    额");
        balanceValue.setText(user.balance);
        return view;
    }
}
