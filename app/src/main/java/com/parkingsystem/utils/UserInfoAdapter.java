package com.parkingsystem.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.Mine;
import com.parkingsystem.entity.User;
import com.parkingsystem.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserInfoAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<UserInfo> userArrayList;

    public UserInfoAdapter(Context context, ArrayList<UserInfo> userArrayList) {
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

        TextView tvName = (TextView) view.findViewById(R.id.label_name);
        TextView tvValue = (TextView) view.findViewById(R.id.display_name);


        UserInfo infoBean = userArrayList.get(position);

        tvName.setText(infoBean.getLabelName());
        tvValue.setText(infoBean.getDisplayValue());

        return view;
    }

    /*private int resourceId;


    public UserInfoAdapter(@NonNull Context context, int resource,
                       @NonNull List<UserInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserInfo userInfo =  getItem(position);   // 获取当前项的UserInfo 实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.labelName = (TextView) view.findViewById(R.id.label_name);
            viewHolder.displayValue = (TextView) view.findViewById(R.id.display_name);
            view.setTag(viewHolder);        // 将 ViewHolder 存储在 View 中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();        // 重新获取 ViewHolder
        }
        viewHolder.labelName.setText(userInfo.getLabelName());
        viewHolder.displayValue.setText(userInfo.getDisplayValue());

        return view;
    }

    private class ViewHolder {

        TextView labelName;

        TextView displayValue;
    }*/
}
