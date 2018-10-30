package com.example.metfone.colorracemetfone.ui.MainActivityEmploye;



import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.Adapter.MainActivityAdapter;

public class MainActivity extends AppCompatActivity {
    MainActivityAdapter adapter;
    @Override
    public void onBackPressed() {
        Log.d("here" , "show");
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT < 16) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapter = new MainActivityAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapter);

    }

}
