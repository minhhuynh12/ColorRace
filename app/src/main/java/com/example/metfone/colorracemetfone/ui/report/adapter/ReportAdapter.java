package com.example.metfone.colorracemetfone.ui.report.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.report.model.ShowroomItem;
import com.example.metfone.colorracemetfone.ui.report.model.TicketGiftItem;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {
    private List<ShowroomItem> list;
    private Context context;
    private ReportAdapterItem mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    public ReportAdapter(Context context) {
        if (list == null){
            list = new ArrayList<>();
        }
        this.context = context;
        mAdapter = new ReportAdapterItem(context);
        mLayoutManager = new LinearLayoutManager(context);


    }

    public void setData(List<ShowroomItem> list){
        this.list = list;
        mAdapter.setData(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvShowroom.setText("Showroom " + position);
        holder.recylerReportItem.setLayoutManager(mLayoutManager);
        holder.recylerReportItem.setItemAnimator(new DefaultItemAnimator());
        holder.recylerReportItem.setAdapter(mAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recylerReportItem;
        private TextView tvShowroom;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recylerReportItem = itemView.findViewById(R.id.recylerReportItem);
            tvShowroom = itemView.findViewById(R.id.tvShowroom);
        }
    }
}
