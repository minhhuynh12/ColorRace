package com.example.metfone.colorracemetfone.ui.Chart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.Chart.model.ChartItem;
import com.example.metfone.colorracemetfone.ui.Chart.model.TicketGiftItem;


import java.util.ArrayList;
import java.util.List;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {
    private Context context;
    private List<ChartItem> list;

    float percentComplain = 0;
    float percentNew = 0;
    int flag = 0;

    public ChartAdapter(Context context) {
        this.context = context;
        if (list == null) {
            list = new ArrayList<>();
        }
    }

    public void setData(List<ChartItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setData(List<ChartItem> list, int flag) {
        this.list = list;
        this.flag = flag;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chart_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        float fNotYet = list.get(position).getTotal() - list.get(position).getUsed();
        float fRecieve = list.get(position).getTotal() - fNotYet;

        String strRecive = String.format("%,.0f", fRecieve);
        String strNotYet = String.format("%,.0f", fNotYet);
        String strTotal = String.format("%,.0f", list.get(position).getTotal());

        holder.tvName.setText(list.get(position).getName());
        holder.tvReceived.setText(context.getResources().getString(R.string.received) + " (" + strRecive + ")");

        holder.tvNotYet.setText(context.getResources().getString(R.string.not_yet) + " (" + strNotYet + ")");
        holder.tvTotal.setText(strTotal);

        percentNew = list.get(position).getUsed();
        percentNew = percentNew / list.get(position).getTotal();
        percentComplain = list.get(position).getUsed() * 100.0f / list.get(position).getTotal();

        setProgressDrawable(holder.pgComplainTotal, R.drawable.circle_progress_background);
        holder.pgComplainTotal.setProgress((int) percentComplain);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvTotal;
        private TextView tvReceived;
        private TextView tvNotYet;
        ProgressBar pgNewTotal;
        ProgressBar pgComplainTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvReceived = itemView.findViewById(R.id.tvReceived);
            tvNotYet = itemView.findViewById(R.id.tvNotYet);
            pgNewTotal = (ProgressBar) itemView.findViewById(R.id.pg_percent_new_total);
            pgComplainTotal = (ProgressBar) itemView.findViewById(R.id.pg_percent_complain_total);
        }
    }

    private void setProgressDrawable(ProgressBar progressBar, int drawableId) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            progressBar.setProgressDrawable(context.getResources().getDrawable(drawableId, context.getTheme()));
            progressBar.setProgress(0);
            progressBar.setProgress(100);
        } else {
            progressBar.setProgressDrawable(context.getResources().getDrawable(drawableId));
            progressBar.setProgress(0);
            progressBar.setProgress(100);
        }
    }
}
