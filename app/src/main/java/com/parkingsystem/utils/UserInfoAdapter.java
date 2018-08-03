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

import java.util.List;

public class UserInfoAdapter extends ArrayAdapter<UserInfo> {

    private int resourceId;


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
    }
}
