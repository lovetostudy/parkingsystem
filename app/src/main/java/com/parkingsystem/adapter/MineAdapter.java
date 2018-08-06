package com.parkingsystem.adapter;

import android.content.Context;
import android.opengl.GLException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.Mine;

import java.util.List;

public class MineAdapter extends ArrayAdapter<Mine> {

    private int resourceId;


    public MineAdapter(@NonNull Context context, int resource,
                       @NonNull List<Mine> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Mine mine = getItem(position);  // 获取当前项的Mine实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mineName = (TextView) view.findViewById(R.id.tv_mine_name);
            view.setTag(viewHolder);    // 将 ViewHolder 存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); //重新获取 ViewHolder
        }
        viewHolder.mineName.setText(mine.getName());

        return view;
    }

    private class ViewHolder {

        TextView mineName;
    }
}
