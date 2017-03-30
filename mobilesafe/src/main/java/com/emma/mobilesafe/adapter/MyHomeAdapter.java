package com.emma.mobilesafe.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emma.mobilesafe.R;

public class MyHomeAdapter extends RecyclerView.Adapter<MyHomeAdapter.ViewHolder> {
    private int[] mDrawableIds;
    private String[] mTitleStr;


    public MyHomeAdapter(int[] mDrawableIds, String[] mTitleStr) {
        this.mDrawableIds = mDrawableIds;
        this.mTitleStr = mTitleStr;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView iv_title;
        ImageView iv_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_title = (TextView) itemView.findViewById(R.id.iv_title);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_items, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.iv_title.setText(mTitleStr[position]);
        holder.iv_icon.setBackgroundResource(mDrawableIds[position]);
    }

    @Override
    public int getItemCount() {
        return mTitleStr.length;
    }

}