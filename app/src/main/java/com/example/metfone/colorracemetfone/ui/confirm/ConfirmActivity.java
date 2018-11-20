package com.example.metfone.colorracemetfone.ui.confirm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.MainActivity;
import com.example.metfone.colorracemetfone.ui.confirm.model.GetTicketItem;
import com.example.metfone.colorracemetfone.ui.login.LoginActivity;
import com.example.metfone.colorracemetfone.ui.login.model.CheckOTPItem;
import com.example.metfone.colorracemetfone.ui.login.model.GetOtpItem;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vitinhHienAnh on 28-10-18.
 */

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener{
    private Button btnConfirm;
    private Handler handler = new Handler();
    private Runnable runnable;
    private String EVENT_DATE_TIME ;
    private String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private TextView tvDay;
    private TextView tvHour;
    private TextView tvMinute;
    private TextView tvSecond;
    private ImageView imgCheckBox;
    private LinearLayout llImageCheck;
    private boolean flag = false;
    private Activity mActivity;
    private List<GetTicketItem> items;
    private String otp;
    private String isdn;
    ArrayList arrListGift = new ArrayList();
    private SharePreferenceUtils sharedPreferences;
    String language;
    boolean flagHasRead;
    NavigationView navigationView;
    private LinearLayout llLogOut;
    TextView tvPhoneNumberNavi;
    private ImageView imgConfirm;
    public List<CheckOTPItem> itemCheckOTP;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);

        init();

        mActivity = this;

        sharedPreferences = new SharePreferenceUtils(this);
        language = sharedPreferences.getLanguage();
        if ("kh".equals(language)){
            imgConfirm.setBackground(this.getResources().getDrawable(R.drawable.nightrace_cofirm_kh));
        }else {
            imgConfirm.setBackground(this.getResources().getDrawable(R.drawable.nightrace_cofirm_en));
        }

        otp = sharedPreferences.getOTP();
        isdn = sharedPreferences.getISDN();
        EVENT_DATE_TIME = sharedPreferences.getTimeNightRace();

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


        countDownStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownStart();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnConfirm:
                if (flagHasRead){
                    new CallWSAsyncTask().execute("1", isdn , otp);
                }else {
                    Toast.makeText(ConfirmActivity.this, ConfirmActivity.this.getResources().getString(R.string.has_read) , Toast.LENGTH_SHORT).show();
                }
//                new CallWSAsyncTask().execute("1", isdn , otp);
                break;
            case R.id.llImageCheck:
                if (!flag){
                    flag = true;
                    flagHasRead = true;
                    btnConfirm.setVisibility(View.VISIBLE);
                    imgCheckBox.setBackground(getResources().getDrawable(R.drawable.ic_checked_checkbox));
                }else {
                    flag = false;
                    flagHasRead = false;
                    btnConfirm.setVisibility(View.GONE);
                    imgCheckBox.setBackground(getResources().getDrawable(R.drawable.ic_unchecked_checkbox));
                }
                break;
            case R.id.llLogOut:
                sharedPreferences.putPinCode("");
                Intent returnIntent = new Intent();
                setResult(12,returnIntent);
                finish();
                break;
            case R.id.imgConfirm:
                Intent intent = new Intent(ConfirmActivity.this , ShowConfirmActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void init(){
        tvDay = findViewById(R.id.tvDay);
        tvHour = findViewById(R.id.tvHour);
        tvMinute = findViewById(R.id.tvMinute);
        tvSecond = findViewById(R.id.tvSecond);
        imgCheckBox = findViewById(R.id.imgCheckBox);
        llImageCheck =  findViewById(R.id.llImageCheck);
        imgConfirm = findViewById(R.id.imgConfirm);
        imgConfirm.setOnClickListener(this);
        llImageCheck.setOnClickListener(this);



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
                        //
                        tvDay.setText(String.format("%02d", Days));
                        tvHour.setText(String.format("%02d", Hours));
                        tvMinute.setText(String.format("%02d", Minutes));
                        tvSecond.setText(String.format("%02d", Seconds));
                    } else {
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    private class CallWSAsyncTask extends AsyncTask<String, Byte, Integer> {

        private ProgressDialog progress;
        private RequestGetwayWS req;

        public CallWSAsyncTask() {
            req = new RequestGetwayWS(mActivity);
            progress = new ProgressDialog(mActivity);
            progress.setCanceledOnTouchOutside(false);
            progress.setCancelable(false);
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            int res = 0;
            boolean isNetwork = CommonActivity.isNetworkConnected(mActivity);
            if (isNetwork) {
                int id = Integer.parseInt(params[0]);
                int resReq = -1;
                try {
                    switch (id) {
                        case 1:
                            req.addParam("isdn", params[1]);
                            req.addParam("otp", params[2]);
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "getTicket", "login",
                                    "getlist", mActivity);
                            break;

                    }
                    res = id * resReq;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return res;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result > 0) {
                String errorGW = req.getErrorCodeGW();
                String errorWS = req.getErrorCode();
                if ((errorGW != null && errorGW.equals("0")) && (errorWS != null && errorWS.equals("00"))) {
                    switch (result) {
                        case 1:
                            itemCheckOTP = req.parseXMLToListObject("return", CheckOTPItem.class);
                            String statusTicket = itemCheckOTP.get(0).getTicket().getStatus();
                            sharedPreferences.putStatusCustomer(statusTicket);

                            Intent intent = new Intent(ConfirmActivity.this , MainActivity.class);
//                            intent.putStringArrayListExtra("LIST_GIFT", arrListGift);
                            startActivityForResult(intent , 1);
                            progress.dismiss();
                            break;
                    }
                } else {
                    String errorDec = req.getErrorDecription();

                    items =  req.parseXMLToListObject("return", GetTicketItem.class);
                    if (errorDec != null && !errorDec.isEmpty()) {
                        if (errorWS.equals(Constant.TIMEOUT_CODE)) {
                            CommonActivity.createAlertDialog(mActivity, errorDec,
                                    getString(R.string.app_name), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View arg0) {

                                        }
                                    }).show();
                        } else {
                            CommonActivity.createAlertDialog(mActivity, errorDec, getString(R.string.app_name)).show();
                        }
                    } else {
                        if (items != null){
                            if (language.toLowerCase().equals("kh")) {
                                CommonActivity.createAlertDialog(mActivity, items.get(0).getMessageKh(), getString(R.string.app_name)).show();
                            }else {
                                CommonActivity.createAlertDialog(mActivity, items.get(0).getMessageEn(), getString(R.string.app_name)).show();
                            }
                        }else {
                            CommonActivity.createAlertDialog(mActivity, getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
                        }
                    }
                }
            } else {// Goi ws that bai
                if (result == 0) {// Do khong co mang
                    CommonActivity.createAlertDialog(mActivity,
                            getString(R.string.errorNetworkAccess), getString(R.string.app_name)).show();
                } else {// Loi khi goi ws
                    CommonActivity.createAlertDialog(mActivity,
                            getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
                }
            }
            progress.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
