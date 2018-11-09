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
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;
import java.util.List;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {
    private Context context;
    private List<ChartItem> list;
    private PieData data;
    float percentComplain = 0;
    float percentNew = 0;
    int flag = 0;

    public ChartAdapter(Context context) {
        this.context = context;
        if (list == null){
            list =  new ArrayList<>();
        }
    }

    public void setData(List<ChartItem> list ){
        this.list = list;
        notifyDataSetChanged();
    }
    public void setData(List<ChartItem> list , int flag ){
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

        holder.tvName.setText(list.get(position).getName());
        holder.tvReceived.setText(context.getResources().getString(R.string.received) + " (" +Float.toString(fRecieve) + ")");

        holder.tvNotYet.setText(context.getResources().getString(R.string.not_yet_received) + " (" +Float.toString(fNotYet) + ")");
        holder.tvTotal.setText(Float.toString(list.get(position).getTotal()));

        if (flag == 0){
            percentNew = list.get(position).getUsed();
            percentNew = list.get(position).getTotal() - percentNew;
            percentComplain = list.get(position).getUsed();
        }else {
//            percentNew = list.get(position).getUsed() * 10.0f;
//            percentNew = list.get(position).getTotal() * 10.0f - percentNew;
//            percentComplain = list.get(position).getUsed() * 10.0f;
            percentNew = list.get(position).getUsed();
            percentNew = list.get(position).getTotal() - percentNew;
            percentComplain = list.get(position).getUsed();
        }


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
