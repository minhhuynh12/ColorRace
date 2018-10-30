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

public class FragmentNightRace extends Fragment {
    public static FragmentNightRace newInstance() {
        FragmentNightRace fragmentFirst = new FragmentNightRace();
        return fragmentFirst;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_night_race , container , false);
        return view;
    }
}
