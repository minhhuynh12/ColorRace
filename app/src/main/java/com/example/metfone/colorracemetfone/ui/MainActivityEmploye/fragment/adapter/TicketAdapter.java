package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.model.TicketItems;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    private List<TicketItems> list;
    private Context context;
    public TicketAdapter(Context context) {
        this.context = context;
        if (list == null){
            this.list = new ArrayList<>();
        }
    }

    public void setData(List<TicketItems> list){
        this.list = list;
    }

    @NonNull
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragmnet_ticket_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.ViewHolder holder, int position) {
        TicketItems items = list.get(position);
        if (position == 0){
            holder.llItemsTicket.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.header_recycler));
            holder.tvGift.setText("GIFT");
            holder.tvGift.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.tvNightRace.setText("Amount");
            holder.tvNightRace.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.tvMap.setText("Recieve");
            holder.tvMap.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        }else {
            holder.tvGift.setText(items.getGift());
            holder.tvNightRace.setText(items.getReceive());
            holder.tvMap.setText(items.getAmount());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGift;
        private TextView tvNightRace;
        private TextView tvMap;
        private LinearLayout llItemsTicket;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGift = itemView.findViewById(R.id.tvGift);
            tvNightRace = itemView.findViewById(R.id.tvNightRace);
            tvMap = itemView.findViewById(R.id.tvMap);
            llItemsTicket = itemView.findViewById(R.id.llItemsTicket);
        }
    }
}
