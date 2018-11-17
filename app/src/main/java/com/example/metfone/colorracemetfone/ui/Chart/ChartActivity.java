package com.example.metfone.colorracemetfone.ui.Chart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.Chart.adapter.ChartAdapter;
import com.example.metfone.colorracemetfone.ui.Chart.model.ChartItem;
import com.example.metfone.colorracemetfone.ui.Chart.model.TicketGiftItem;
import com.example.metfone.colorracemetfone.ui.SignData.SignDataActivity;
import com.example.metfone.colorracemetfone.ui.checkInSecond.CheckInSecondActivity;
import com.example.metfone.colorracemetfone.ui.informationTicket.InformationTicketActivity;
import com.example.metfone.colorracemetfone.ui.login.LoginActivity;
import com.example.metfone.colorracemetfone.ui.showResultQrCode.ShowResultQrCodeActivity;
import com.example.metfone.colorracemetfone.ui.showResultQrCode.model.ContactItem;
import com.example.metfone.colorracemetfone.util.DBCheckInSecond;
import com.example.metfone.colorracemetfone.util.DBHelper;
import com.example.metfone.colorracemetfone.util.RSAUtil;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;
import com.example.metfone.colorracemetfone.util.RsaBase64;
import com.example.metfone.colorracemetfone.util.RuntimePermission;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.example.metfone.colorracemetfone.util.RsaBase64.encrypt;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private Activity mActivity;
    private String otp;
    private String isdn;
    private List<TicketGiftItem> itemsReport;
    private RecyclerView recyclerTikect;
    private RecyclerView recyclerGift;
    private ChartAdapter mAdapterTicket;
    private ChartAdapter mAdapterGift;
    private Runnable runnable;
    private Handler handler = new Handler();
    private String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private String EVENT_DATE_TIME;
    private TextView tvDay;
    private TextView tvHour;
    private TextView tvMinute;
    private TextView tvSecond;
    private TextView tvPhoneNumberNavi;
    private ImageView imgCreateQR;
    private boolean flagPermission;
    private android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout llLogOut;
    private LinearLayout llSign;
    NavigationView navigationView;
    private String permission;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private static final int REQUEST_CODE_QR_SCAN_AGAIN = 2;
    private SharePreferenceUtils sharePreferenceUtils;
    private DBCheckInSecond dbCheckInSecond;
    private String isdnCus;
    private DBHelper dbHelper;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);

        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        sharePreferenceUtils = new SharePreferenceUtils(this);
        dbCheckInSecond = new DBCheckInSecond(this);
        dbHelper = new DBHelper(this);

        recyclerTikect = findViewById(R.id.recyclerTikect);
        recyclerGift = findViewById(R.id.recyclerGift);
        tvDay = findViewById(R.id.tvDay);
        tvHour = findViewById(R.id.tvHour);
        tvMinute = findViewById(R.id.tvMinute);
        tvSecond = findViewById(R.id.tvSecond);
        imgCreateQR = findViewById(R.id.imgCreateQR);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        llLogOut = header.findViewById(R.id.llLogOut);
        llSign = header.findViewById(R.id.llSign);
        llSign.setVisibility(View.VISIBLE);
        llSign.setOnClickListener(this);
        tvPhoneNumberNavi = header.findViewById(R.id.tvPhoneNumberNavi);

        imgCreateQR.setOnClickListener(this);
        llLogOut.setOnClickListener(this);

        mActivity = this;

        otp = sharePreferenceUtils.getOTP();
        isdn = sharePreferenceUtils.getISDN();
        permission = sharePreferenceUtils.getPermission();
        EVENT_DATE_TIME = sharePreferenceUtils.getTimeNightRace();
        countDownStart();
        initializeRecyclerview();
        if (isdn.startsWith("0")){
            tvPhoneNumberNavi.setText(isdn);
        }else {
            tvPhoneNumberNavi.setText("0" + isdn);
        }
        new CallWSAsyncTask().execute("1", isdn, otp);


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
            if (toolbar.getChildAt(i) instanceof ImageButton) {
                toolbar.getChildAt(i).setScaleX(0.5f);
                toolbar.getChildAt(i).setScaleY(0.5f);
            }
        }

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownStart();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imgCreateQR:
                flagPermission = RuntimePermission.CheckingPermissionIsEnabledOrNot(this);

                if (!flagPermission) {
                    RuntimePermission.requestReadAndPermission(this);
                } else {
                    Intent i = new Intent(ChartActivity.this, QrCodeActivity.class);
                    startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                }


                break;
            case R.id.llLogOut:
                sharePreferenceUtils.putPinCode("");
                Intent returnIntent = new Intent();
                setResult(12, returnIntent);
                finish();
                break;
            case R.id.llSign:
                Intent intent = new Intent(ChartActivity.this, SignDataActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RuntimePermission.REQUEST_PHONE_STATE_PERMISSION_CODE:
                flagPermission = RuntimePermission.CheckingPermissionIsEnabledOrNot(ChartActivity.this);
                if (!flagPermission) {
                    CommonActivity.createAlertDialog(mActivity, getResources().getString(R.string.allow_permission_cam), getString(R.string.app_name)).show();
                } else {
                    Intent i = new Intent(ChartActivity.this, QrCodeActivity.class);
                    startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                }
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            String text = "";
            if (data == null) {
                return;
            }
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            byte[] dataBase64 = new byte[0];
            try {
                dataBase64 = Base64.decode(result, Base64.DEFAULT);
            } catch (Exception ex) {
            }

            try {
                
                text = new String(dataBase64, "UTF-8");
                String[] separated = text.split(";");
                for (int i = 0; i < separated.length; i++) {
                    String[] arrStr = separated[i].split("=");
                    if ("isdn ".equals(arrStr[0])) {
                        if (arrStr.length > 1) {
                            isdnCus = arrStr[1];
                        } else {
                            isdnCus = "";
                        }
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            if ("STAFF_SYNC".equals(permission)) {
                ContactItem contactItem = dbHelper.getStatus(isdnCus.trim());
                if ("1".equals(contactItem.getStatus())) {
                    Intent intent = new Intent(ChartActivity.this, CheckInSecondActivity.class);
                    intent.putExtra("ISDN_CUS", contactItem.getPhonenumber());
                    intent.putExtra("datetime", contactItem.getDatetime());
                    intent.putExtra("data", text);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ChartActivity.this, ShowResultQrCodeActivity.class);
                    intent.putExtra("data", text);
                    startActivityForResult(intent, REQUEST_CODE_QR_SCAN_AGAIN);
                }
            } else {
                Intent intent = new Intent(ChartActivity.this, InformationTicketActivity.class);
                intent.putExtra("OTP", otp);
                intent.putExtra("ISDN", isdn);
                intent.putExtra("data", text);
                startActivity(intent);
            }

//            String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
//            String setDay = "6/11/2018";
//            boolean flag = compare(today,setDay);
        } else {
            if (resultCode != 4) {
                Intent i = new Intent(ChartActivity.this, QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
            }

        }
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
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "getReport", "Chart",
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
                            if (itemsReport == null) {
                                itemsReport = new ArrayList<>();
                            }
                            itemsReport = req.parseXMLToListObject("TicketGift", TicketGiftItem.class);
                            List<ChartItem> listTicket = new ArrayList<>();
                            List<ChartItem> listGift = new ArrayList<>();

                            for (int i = 0; i < itemsReport.size(); i++) {
                                if ("1".equals(itemsReport.get(i).getType())) {
                                    listTicket.add(new ChartItem(itemsReport.get(i).getId(), itemsReport.get(i).getCode(), itemsReport.get(i).getName(),
                                            itemsReport.get(i).getTotal(), itemsReport.get(i).getUsed(), itemsReport.get(i).getType()));
                                } else {
                                    listGift.add(new ChartItem(itemsReport.get(i).getId(), itemsReport.get(i).getCode(), itemsReport.get(i).getName(),
                                            itemsReport.get(i).getTotal(), itemsReport.get(i).getUsed(), itemsReport.get(i).getType()));
                                }
                            }

                            mAdapterTicket.setData(listTicket);
                            mAdapterGift.setData(listGift, 1);
                            progress.dismiss();
                            break;
                    }
                } else {
                    String errorDec = req.getErrorDecription();

//                    items =  req.parseXMLToListObject("return", GetTicketItem.class);
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
//                        if (items != null){
//                            CommonActivity.createAlertDialog(mActivity, items.get(0).getMessageEn(), getString(R.string.app_name)).show();
//                        }else {
//                            CommonActivity.createAlertDialog(mActivity, getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
//                        }
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

    private void initializeRecyclerview() {
        mAdapterTicket = new ChartAdapter(mActivity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ChartActivity.this);
        recyclerTikect.setLayoutManager(mLayoutManager);
        recyclerTikect.setItemAnimator(new DefaultItemAnimator());
        recyclerTikect.setAdapter(mAdapterTicket);

        RecyclerView.LayoutManager mLayoutManagerGift = new LinearLayoutManager(ChartActivity.this);
        mAdapterGift = new ChartAdapter(mActivity);
        recyclerGift.setLayoutManager(mLayoutManagerGift);
        recyclerGift.setItemAnimator(new DefaultItemAnimator());
        recyclerGift.setAdapter(mAdapterGift);
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
//                        linear_layout_1.setVisibility(View.VISIBLE);
//                        linear_layout_2.setVisibility(View.GONE);
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

    private boolean compare(String today, String setDay) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateToday = null;
        Date dateSetDay = null;
        try {
            dateToday = simpleDateFormat.parse(today);
            dateSetDay = simpleDateFormat.parse(setDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //  0 comes when two date are same,
        //  1 comes when date1 is higher then date2
        // -1 comes when date1 is lower then date2

        if (dateToday.compareTo(dateSetDay) == 1 || dateToday.compareTo(dateSetDay) == 0) {

            return true;
        }
        return false;
    }
}
