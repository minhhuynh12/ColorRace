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
import com.example.metfone.colorracemetfone.ui.report.model.ReportItem;
import com.example.metfone.colorracemetfone.ui.report.model.TicketGiftDepartmentItem;
import com.example.metfone.colorracemetfone.ui.report.model.TicketGiftItem;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {
    private List<TicketGiftDepartmentItem> list;

    private Context context;
    private ReportAdapterItem mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    List<ReportItem> items;

    public ReportAdapter(Context context) {
        if (list == null){
            list = new ArrayList<>();
        }
        this.context = context;



    }

    public void setData(List<TicketGiftDepartmentItem> list ){
        this.list = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.report_showroom_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvShowroom.setText(list.get(position).getDepartment());
        if (items == null){
            items = new ArrayList<>();
        }

        mAdapter.setData(list.get(position).getLstGift());
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
            recylerReportItem = (RecyclerView) itemView.findViewById(R.id.recylerReportItem);
            tvShowroom = itemView.findViewById(R.id.tvShowroom);

            mAdapter = new ReportAdapterItem(context);
            mLayoutManager = new LinearLayoutManager(context);
            recylerReportItem.setLayoutManager(mLayoutManager);
            recylerReportItem.setItemAnimator(new DefaultItemAnimator());
            recylerReportItem.setAdapter(mAdapter);

        }
    }
}
