package com.example.metfone.colorracemetfone.ui.MainActivityEmploye;



import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.Adapter.MainActivityAdapter;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.MapFragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.NightRaceFragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.TicketFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MainActivityAdapter adapter;
    FrameLayout frameMain;
    RelativeLayout llTicket;
    RelativeLayout llNightRace;
    RelativeLayout llMap;
    View vTickket;
    View vNightRace;
    View vMap;
    TextView tvTicket;
    TextView tvNightRace;
    TextView tvMaps;
    CardView cardView;
    @Override
    public void onBackPressed() {
        Log.d("here" , "show");
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        adapter = new MainActivityAdapter(getSupportFragmentManager());

        TicketFragment fragment = new TicketFragment();
        swichFragemnt(fragment);

    }

    private void init(){
        llTicket = (RelativeLayout) findViewById(R.id.llTicket);
        llNightRace = (RelativeLayout) findViewById(R.id.llNightRace);
        llMap = (RelativeLayout) findViewById(R.id.llMap);
        tvTicket = (TextView) findViewById(R.id.tvTicket);
        tvNightRace = (TextView) findViewById(R.id.tvNightRace);
        tvMaps = (TextView) findViewById(R.id.tvMaps);
        frameMain =  findViewById(R.id.frameMain);
        cardView = (CardView) findViewById(R.id.cardView);

        vTickket =  findViewById(R.id.vTickket);
        vNightRace =  findViewById(R.id.vNightRace);
        vMap =  findViewById(R.id.vMap);

        llTicket.setOnClickListener(this);
        llNightRace.setOnClickListener(this);
        llMap.setOnClickListener(this);
    }

    private void swichFragemnt (Fragment fragment){
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
                setBackground(vTickket , getResources().getColor(R.color.color_title) , tvTicket ,  getResources().getColor(R.color.color_title) );
                setBackground(vNightRace , getResources().getColor(R.color.color_input_gray), tvNightRace, getResources().getColor(R.color.color_black));
                setBackground(vMap , getResources().getColor(R.color.color_input_gray) , tvMaps , getResources().getColor(R.color.color_black));
                cardView.setVisibility(View.VISIBLE);
                break;
            case R.id.llNightRace:
                NightRaceFragment fragment2 = new NightRaceFragment();
                swichFragemnt( fragment2);
                setBackground(vTickket , getResources().getColor(R.color.color_input_gray), tvTicket, getResources().getColor(R.color.color_black));
                setBackground(vNightRace , getResources().getColor(R.color.color_title) , tvNightRace , getResources().getColor(R.color.color_title));
                setBackground(vMap , getResources().getColor(R.color.color_input_gray), tvMaps , getResources().getColor(R.color.color_black));
                cardView.setVisibility(View.GONE);
                break;
            case R.id.llMap:
                MapFragment fragment3 = new MapFragment();
                swichFragemnt( fragment3);
                setBackground(vTickket , getResources().getColor(R.color.color_input_gray), tvTicket, getResources().getColor(R.color.color_black));
                setBackground(vNightRace , getResources().getColor(R.color.color_input_gray) , tvNightRace , getResources().getColor(R.color.color_black));
                setBackground(vMap , getResources().getColor(R.color.color_title),tvMaps ,  getResources().getColor(R.color.color_title));
                cardView.setVisibility(View.GONE);
                break;
        }
    }

    private void setBackground(View view , int colorView , TextView textView , int colorText){
        view.setBackgroundColor(colorView);
        textView.setTextColor(colorText);
    }
}
