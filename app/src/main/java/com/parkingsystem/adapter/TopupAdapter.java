package com.parkingsystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.TopupInfo;
import com.parkingsystem.entity.TopupItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TopupAdapter extends RecyclerView.Adapter<TopupAdapter.ViewHloder> implements View.OnClickListener{

    private int FLAG = 0;

    private Context mContext;

    private ArrayList<TopupItem> arrayList;

    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

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

    class TextViewHloder extends ViewHloder {
        TextView priceView;
        private int lastPressIndex = -1;

        public TextViewHloder(final View itemView) {
            super(itemView);

            priceView = (TextView) itemView.findViewById(R.id.tv_num_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    Log.e("TAG", "OneViewHolder: " + position);
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                }

            });
        }

    }

    static class EditViewHloder extends ViewHloder {
        EditText priceView;

        public EditViewHloder(View itemView) {
            super(itemView);
            priceView = (EditText) itemView.findViewById(R.id.et_price_item);
        }
    }

    @NonNull
    @Override
    public TopupAdapter.ViewHloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TopupItem.TEXT_TYPE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.num_item, parent, false);
                TextViewHloder viewHolder = new TextViewHloder(view);
                // 将创建的view注册点击事件
                view.setOnClickListener(this);
                return viewHolder;
            case TopupItem.EDIT_TYPE:
                return new TextViewHloder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.edit_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TopupAdapter.ViewHloder holder, int position) {
        TopupItem topupItem = arrayList.get(position);
        holder.priceView.setText(topupItem.getPrice());

        // 将position保存在itemView的tag中,以便点击时获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount()) {
            return TopupItem.TEXT_TYPE;
        } else {
            return TopupItem.EDIT_TYPE;
        }
    }

    public void setmOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
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
