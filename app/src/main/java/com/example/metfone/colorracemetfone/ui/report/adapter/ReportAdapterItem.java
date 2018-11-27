package com.example.metfone.colorracemetfone.ui.report.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.report.model.ListGiftReportItem;
import com.example.metfone.colorracemetfone.ui.report.model.ReportItem;
import com.example.metfone.colorracemetfone.ui.report.model.TicketGiftDepartmentItem;
import com.example.metfone.colorracemetfone.ui.report.model.TicketGiftItem;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapterItem extends RecyclerView.Adapter<ReportAdapterItem.ViewHolder> {
    private Context context;
    private List<TicketGiftItem> list;
    private List<ReportItem> items;
    public ReportAdapterItem(Context context) {
        this.context = context;
        if (list == null){
            list = new ArrayList<>();
        }
        if (items == null){
            items = new ArrayList<>();
        }
    }

    public void setData(List<TicketGiftItem> list){
        this.list = list;
        items.add(new ReportItem("Name" , "Total" , "Used"));
        for (int i = 0; i <= list.size() -1  ; i++){
            items.add(new ReportItem(list.get(i).getName(), list.get(i).getTotal() , list.get(i).getUsed()));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0){
            holder.llReportItem.setBackgroundColor(context.getResources().getColor(R.color.color_input_gray));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvTotal.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvUsed.setTextColor(context.getResources().getColor(R.color.white));
        }
            holder.tvName.setText(items.get(position).getName());
            holder.tvTotal.setText(items.get(position).getTotal());
            holder.tvUsed.setText(items.get(position).getUsed());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvTotal;
        private TextView tvUsed;
        private LinearLayout llReportItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvUsed = itemView.findViewById(R.id.tvUsed);
            llReportItem = itemView.findViewById(R.id.llReportItem);
        }
    }
}
