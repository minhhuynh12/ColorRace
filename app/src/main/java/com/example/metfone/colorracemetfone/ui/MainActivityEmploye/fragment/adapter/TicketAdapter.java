package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.model.TicketItems;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    private List<String> list;
    private Context context;
    private String status;
    public TicketAdapter(Context context) {
        this.context = context;
        if (list == null){
            this.list = new ArrayList<>();
        }
    }

    public void setData(List<String> list , String stattus){
        this.list = list;
        this.status = stattus;
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

        if (position == 0){
            holder.tvGift.setText(context.getResources().getString(R.string.receive));
            holder.tvGift.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.tvNightRace.setText(context.getResources().getString(R.string.night_race));
            holder.tvNightRace.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.tvMap.setText(context.getResources().getString(R.string.map));
            holder.tvMap.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.llIamge.setVisibility(View.GONE);
        }else {
            holder.tvGift.setText(list.get(position).toString());
            holder.tvNightRace.setText("1");
            holder.tvMap.setVisibility(View.GONE);
            holder.llIamge.setVisibility(View.VISIBLE);
            if ("1".equals(status)){
                if (position == 1){
                    holder.imgCheckBox.setBackground(context.getResources().getDrawable(R.drawable.ic_checked_checkbox));
                }else {
                    holder.imgCheckBox.setBackground(context.getResources().getDrawable(R.drawable.ic_unchecked_checkbox));
                }
            }else {
                holder.imgCheckBox.setBackground(context.getResources().getDrawable(R.drawable.ic_checked_checkbox));
            }
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
        private LinearLayout llIamge;
        private ImageView imgCheckBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGift = itemView.findViewById(R.id.tvGift);
            tvNightRace = itemView.findViewById(R.id.tvNightRace);
            tvMap = itemView.findViewById(R.id.tvMap);
            llItemsTicket = itemView.findViewById(R.id.llItemsTicket);
            llIamge = itemView.findViewById(R.id.llIamge);
            imgCheckBox = itemView.findViewById(R.id.imgCheckBox);
        }
    }
}
