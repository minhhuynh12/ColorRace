package com.example.metfone.colorracemetfone.ui.MainActivityEmploye;



import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.Adapter.MainActivityAdapter;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.NightRaceFragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.TicketFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MainActivityAdapter adapter;
    FrameLayout frameMain;
    LinearLayout llTicket;
    LinearLayout llNightRace;
    LinearLayout llMap;
    @Override
    public void onBackPressed() {
        Log.d("here" , "show");
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llTicket = (LinearLayout) findViewById(R.id.llTicket);
        llNightRace = (LinearLayout) findViewById(R.id.llNightRace);
        llMap = (LinearLayout) findViewById(R.id.llMap);
        llTicket.setOnClickListener(this);
        llNightRace.setOnClickListener(this);
        llMap.setOnClickListener(this);

        frameMain =  findViewById(R.id.frameMain);
        adapter = new MainActivityAdapter(getSupportFragmentManager());

        TicketFragment fragment = new TicketFragment();
        swichFragemnt(fragment);

    }

    private void swichFragemnt (Fragment fragment){
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.frameMain, fragment)
//                .commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameMain, fragment,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.llTicket:
                TicketFragment fragment = new TicketFragment();
                swichFragemnt( fragment);
                break;
            case R.id.llNightRace:
                NightRaceFragment fragment2 = new NightRaceFragment();
                swichFragemnt( fragment2);
                break;
            case R.id.llMap:

                break;
        }
    }
}
