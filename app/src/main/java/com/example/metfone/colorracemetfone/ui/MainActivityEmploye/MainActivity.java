package com.example.metfone.colorracemetfone.ui.MainActivityEmploye;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.CreateQrCode.CreateQrCodeActivity;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.Adapter.MainActivityAdapter;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.FAQfragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.MapFragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.NightRaceFragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.TicketFragment;
import com.example.metfone.colorracemetfone.ui.login.model.LstGiftItem;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener {
    MainActivityAdapter adapter;
    FrameLayout frameMain;
    RelativeLayout llTicket;
    RelativeLayout llNightRace;
    RelativeLayout llMap;
    View vTickket;
    View vNightRace;
    View vMap;
    View vFAQ;
    TextView tvTicket;
    TextView tvNightRace;
    TextView tvMaps;
    TextView tvPhoneNumberNavi;
    TextView tvFAQ;
    LinearLayout cardView;
    List<String> arrListGift;
    List<LstGiftItem> listGift;
    private String status;
    private Runnable runnable;
    private String EVENT_DATE_TIME ;
    private String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private Handler handler = new Handler();
    private TextView tvDay;
    private TextView tvHour;
    private TextView tvMinute;
    private TextView tvSecond;
    private TextView tvTypeTicket;
    private ImageView imgCreateQR;
    private ImageView imgQr;
    private ImageView imgText;
    private String qrCode;
    private String typeTicket;
    private String lat;
    private String log;
    private String isdn;
    NavigationView navigationView;
    private LinearLayout llLogOut;
    private RelativeLayout llFAQ;
    private SharePreferenceUtils sharedPreferences;
    private String language;

    @Override
    public void onBackPressed() {
        Log.d("here" , "show");
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);

        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();
        sharedPreferences = new SharePreferenceUtils(this , gson);
        language = sharedPreferences.getLanguage();

        init();
        arrListGift = new ArrayList();

        arrListGift = sharedPreferences.getList(String.class);

        status = sharedPreferences.getStatus();
        qrCode = sharedPreferences.getQrCode();
        typeTicket = sharedPreferences.getTypeTicket();
        lat = sharedPreferences.getLat();
        log = sharedPreferences.getLong();
        isdn = sharedPreferences.getISDN();
        EVENT_DATE_TIME = sharedPreferences.getTimeNightRace();

        tvTypeTicket.setText(typeTicket);

        adapter = new MainActivityAdapter(getSupportFragmentManager());
        countDownStart();
        createQrCode();

        TicketFragment fragment = TicketFragment.newInstance(arrListGift);
        swichFragemnt(fragment);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        llLogOut = header.findViewById(R.id.llLogOut);
        tvPhoneNumberNavi = header.findViewById(R.id.tvPhoneNumberNavi);
        llLogOut.setOnClickListener(this);
        if (isdn.startsWith("0")){
            tvPhoneNumberNavi.setText(isdn);
        }else {
            tvPhoneNumberNavi.setText("0" + isdn);
        }

        //toobar

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_bugger);
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if(toolbar.getChildAt(i) instanceof ImageButton){
                toolbar.getChildAt(i).setScaleX(0.5f);
                toolbar.getChildAt(i).setScaleY(0.5f);
            }
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void createQrCode(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(qrCode, BarcodeFormat.QR_CODE, 100, 100);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQr.setImageBitmap(bitmap);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    private void init(){
        llTicket = (RelativeLayout) findViewById(R.id.llTicket);
        llNightRace = (RelativeLayout) findViewById(R.id.llNightRace);
        llMap = (RelativeLayout) findViewById(R.id.llMap);
        llFAQ = (RelativeLayout) findViewById(R.id.llFAQ);
        tvTicket = (TextView) findViewById(R.id.tvTicket);
        tvNightRace = (TextView) findViewById(R.id.tvNightRace);
        tvMaps = (TextView) findViewById(R.id.tvMaps);
        tvTypeTicket = (TextView) findViewById(R.id.tvTypeTicket);
        tvFAQ = (TextView) findViewById(R.id.tvFAQ);
        frameMain =  findViewById(R.id.frameMain);
        cardView = (LinearLayout) findViewById(R.id.cardView);
        tvDay = findViewById(R.id.tvDay);
        tvHour = findViewById(R.id.tvHour);
        tvMinute = findViewById(R.id.tvMinute);
        tvSecond = findViewById(R.id.tvSecond);
        imgCreateQR = findViewById(R.id.imgCreateQR);
        vFAQ = findViewById(R.id.vFAQ);
        imgQr = findViewById(R.id.imgQr);
        imgText = findViewById(R.id.imgText);

        vTickket =  findViewById(R.id.vTickket);
        vNightRace =  findViewById(R.id.vNightRace);
        vMap =  findViewById(R.id.vMap);

        llTicket.setOnClickListener(this);
        llNightRace.setOnClickListener(this);
        llMap.setOnClickListener(this);
        llFAQ.setOnClickListener(this);
        imgCreateQR.setOnClickListener(this);
        imgQr.setOnClickListener(this);

        if ("kh".equals(language)){
            imgText.setBackground(this.getResources().getDrawable(R.drawable.follow_km));
        }else {
            imgText.setBackground(this.getResources().getDrawable(R.drawable.follow));
        }

    }

    private void swichFragemnt (Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameMain, fragment,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;

                        tvDay.setText(String.format("%02d", Days));
                        tvHour.setText(String.format("%02d", Hours));
                        tvMinute.setText(String.format("%02d", Minutes));
                        tvSecond.setText(String.format("%02d", Seconds));
                    } else {
                        tvDay.setText(String.format("00"));
                        tvHour.setText(String.format("00"));
                        tvMinute.setText(String.format("00"));
                        tvSecond.setText(String.format("00"));
                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.llTicket:
                TicketFragment fragment = TicketFragment.newInstance(arrListGift);
                swichFragemnt( fragment);
                setBackground(vTickket , getResources().getColor(R.color.color_title) , tvTicket ,  getResources().getColor(R.color.color_title) );
                setBackground(vNightRace , getResources().getColor(R.color.color_input_gray), tvNightRace, getResources().getColor(R.color.color_black));
                setBackground(vMap , getResources().getColor(R.color.color_input_gray) , tvMaps , getResources().getColor(R.color.color_black));
                setBackground(vFAQ , getResources().getColor(R.color.color_input_gray) , tvFAQ , getResources().getColor(R.color.color_black));
                cardView.setVisibility(View.VISIBLE);
                break;
            case R.id.llNightRace:
                NightRaceFragment fragment2 = new NightRaceFragment();
                swichFragemnt( fragment2);
                setBackground(vTickket , getResources().getColor(R.color.color_input_gray), tvTicket, getResources().getColor(R.color.color_black));
                setBackground(vNightRace , getResources().getColor(R.color.color_title) , tvNightRace , getResources().getColor(R.color.color_title));
                setBackground(vMap , getResources().getColor(R.color.color_input_gray), tvMaps , getResources().getColor(R.color.color_black));
                setBackground(vFAQ , getResources().getColor(R.color.color_input_gray) , tvFAQ , getResources().getColor(R.color.color_black));
                cardView.setVisibility(View.GONE);
                break;
            case R.id.llMap:
                MapFragment fragment3 = MapFragment.newInstance(lat , log);
                swichFragemnt( fragment3);
                setBackground(vTickket , getResources().getColor(R.color.color_input_gray), tvTicket, getResources().getColor(R.color.color_black));
                setBackground(vNightRace , getResources().getColor(R.color.color_input_gray) , tvNightRace , getResources().getColor(R.color.color_black));
                setBackground(vMap , getResources().getColor(R.color.color_title),tvMaps ,  getResources().getColor(R.color.color_title));
                setBackground(vFAQ , getResources().getColor(R.color.color_input_gray) , tvFAQ , getResources().getColor(R.color.color_black));
                cardView.setVisibility(View.GONE);
                break;
            case R.id.llFAQ:
                FAQfragment fragment4 = FAQfragment.newInstance();
                swichFragemnt(fragment4);
                setBackground(vTickket , getResources().getColor(R.color.color_input_gray), tvTicket, getResources().getColor(R.color.color_black));
                setBackground(vNightRace , getResources().getColor(R.color.color_input_gray) , tvNightRace , getResources().getColor(R.color.color_black));
                setBackground(vMap , getResources().getColor(R.color.color_input_gray),tvMaps ,  getResources().getColor(R.color.color_black));
                setBackground(vFAQ , getResources().getColor(R.color.color_title) , tvFAQ , getResources().getColor(R.color.color_title));
                cardView.setVisibility(View.GONE);
                break;
            case R.id.imgCreateQR:
                Intent intent = new Intent(MainActivity.this, CreateQrCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.llLogOut:
                Intent returnIntent = new Intent();
                setResult(12,returnIntent);
                sharedPreferences.putPinCode("");
                finish();
                break;
            case R.id.imgQr:
                Intent intentImQr = new Intent(MainActivity.this, CreateQrCodeActivity.class);
                startActivity(intentImQr);
                break;
        }
    }

    private void setBackground(View view , int colorView , TextView textView , int colorText){
        view.setBackgroundColor(colorView);
        textView.setTextColor(colorText);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
