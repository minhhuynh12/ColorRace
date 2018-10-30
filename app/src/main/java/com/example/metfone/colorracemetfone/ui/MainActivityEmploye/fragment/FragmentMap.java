package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.metfone.colorracemetfone.R;

/**
 * Created by vitinhHienAnh on 30-10-18.
 */

public class FragmentMap extends Fragment {
    public static FragmentMap newInstance() {
        FragmentMap fragmentFirst = new FragmentMap();
        return fragmentFirst;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map , container , false);
        return view;
    }
}
