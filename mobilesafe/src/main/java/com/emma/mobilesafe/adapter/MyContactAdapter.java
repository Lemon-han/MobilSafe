//package com.emma.mobilesafe.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.emma.mobilesafe.R;
//
//import java.util.HashMap;
//import java.util.List;
//
//
//public class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.ViewHolder> {
//    private List<HashMap<String, String>> contactList;
//    private static String TAG = "MyContactAdapter";
//
//    public MyContactAdapter(List<HashMap<String, String>> contactList) {
//        this.contactList = contactList;
//        Log.i(TAG,contactList.size()+"aaaaaaa");
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView tv_name;
//        TextView tv_phone;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
//            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_contact_item, parent, false);
//        final ViewHolder holder = new ViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        Log.i(TAG, contactList.get(position).get("name") + "  " + contactList.get(position).get("phone"));
//        holder.tv_name.setText(contactList.get(position).get("name"));
//        holder.tv_phone.setText(contactList.get(position).get("phone"));
//    }
//
//    @Override
//    public int getItemCount() {
//        return contactList.size();
//    }
//}