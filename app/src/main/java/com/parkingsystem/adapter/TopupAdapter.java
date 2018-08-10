package com.parkingsystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.TopupInfo;
import com.parkingsystem.entity.TopupItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TopupAdapter extends RecyclerView.Adapter<TopupAdapter.ViewHloder>{

    private Context mContext;

    private ArrayList<TopupItem> arrayList;

    public TopupAdapter(Context context, ArrayList<TopupItem> arrayList) {
        this.mContext = context;
        this.arrayList = arrayList;
    }

    static class ViewHloder extends RecyclerView.ViewHolder {
        TextView priceView;

        public ViewHloder(View itemView) {
            super(itemView);
            priceView = (TextView) itemView.findViewById(R.id.tv_num_item);
        }
    }

    @NonNull
    @Override
    public TopupAdapter.ViewHloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHloder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.num_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopupAdapter.ViewHloder holder, int position) {
        TopupItem topupItem = arrayList.get(position);
        holder.priceView.setText(topupItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    /*public TopupAdapter(Context mContext*//*, ArrayList<TopupItem> arrayList*//*) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            view = View.inflate(mContext, R.layout.topup_item, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.tv_num_item);

        *//*TopupItem topupItem = arrayList.get(position);*//*

        textView.setText("1 元");

        return view;
    }*/
}
