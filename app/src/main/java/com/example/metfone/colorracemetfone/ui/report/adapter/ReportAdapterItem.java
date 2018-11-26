package com.example.metfone.colorracemetfone.ui.report.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class ReportAdapterItem extends RecyclerView.Adapter<ReportAdapterItem.ViewHolder> {
    private Context context;
    private List<ShowroomItem> list;
    public ReportAdapterItem(Context context) {
        this.context = context;
        if (list == null){
            list = new ArrayList<>();
        }
    }

    public void setData(List<ShowroomItem> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvTotal.setText(list.get(position).getTotal());
        holder.tvUsed.setText(list.get(position).getUsed());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvTotal;
        private TextView tvUsed;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvUsed = itemView.findViewById(R.id.tvUsed);
        }
    }
}
