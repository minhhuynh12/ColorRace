package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.adapter.TicketAdapter;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.model.TicketItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitinhHienAnh on 30-10-18.
 */

public class TicketFragment extends Fragment {
    private TicketAdapter mAdapter;
    private List<TicketItems> list;
    private RecyclerView recycler;

    public static TicketFragment newInstance() {
        TicketFragment fragmentFirst = new TicketFragment();
        return fragmentFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ticket , container , false);
        recycler = view.findViewById(R.id.recycler);

        list = new ArrayList<>();
        list.add(new TicketItems("", "" , ""));
        list.add(new TicketItems("minhhc", "2 + 1 = 3" , "x"));
        list.add(new TicketItems("gift", "2 + 1 = 3" , "x"));
        list.add(new TicketItems("gift", "2 + 1 = 3" , "x"));
        list.add(new TicketItems("gift", "2 + 1 = 3" , "x"));
        mAdapter = new TicketAdapter(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(mLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(mAdapter);
        mAdapter.setData(list);

        return view;
    }



}
