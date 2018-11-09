package com.example.metfone.colorracemetfone.ui.login;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.Chart.ChartActivity;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.MainActivity;
import com.example.metfone.colorracemetfone.ui.SignData.SignDataActivity;
import com.example.metfone.colorracemetfone.ui.confirm.ConfirmActivity;
import com.example.metfone.colorracemetfone.ui.login.model.CheckOTPItem;
import com.example.metfone.colorracemetfone.ui.login.model.GetOtpItem;
import com.example.metfone.colorracemetfone.util.DBHelper;
import com.example.metfone.colorracemetfone.util.LanguageUtils;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;
import com.example.metfone.colorracemetfone.util.RuntimePermission;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener {
    Button btnLogin;
    private Activity mActivity;
    private Button btnOTP;
    private List<GetOtpItem> itemGetOTP;
    public List<CheckOTPItem> itemCheckOTP;
    private EditText edOTP;
    private EditText edISDN;
    private String otp;
    private String isdn;
    private LinearLayout llLangEN;
    private LinearLayout llLangKH;
    private TextView tvLangEN;
    private TextView tvLangKH;
    private SharePreferenceUtils sharedPreferences;
    String language;
    private boolean flagPermission;
    private String EVENT_DATE_TIME;
    private String deviceID;
    private String permission;
    private int totalIsdn;
    private DBHelper dbHelper;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RuntimePermission.REQUEST_PHONE_STATE_PERMISSION_CODE:
                flagPermission = RuntimePermission.CheckingPermissionIsEnabledOrNot(LoginActivity.this);
                if (!flagPermission) {
                    CommonActivity.createAlertDialog(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.allow_permission),
                            getString(R.string.app_name), new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    finish();
                                }
                            }).show();
                } else {
                    btnLogin.setOnClickListener(this);
                    btnOTP.setOnClickListener(this);
                    llLangEN.setOnClickListener(this);
                    llLangKH.setOnClickListener(this);
                    mActivity = this;
                    if (language.toLowerCase().equals("kh")) {
                        llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_white));
                        llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_red));
                        tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
                        tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));

                    } else {
                        llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_red));
                        llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_white));
                        tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));
                        tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnOTP = findViewById(R.id.btnOTP);
        edOTP = findViewById(R.id.edOTP);
        edISDN = findViewById(R.id.edISDN);
        llLangEN = findViewById(R.id.llLangEN);
        llLangKH = findViewById(R.id.llLangKH);
        tvLangEN = findViewById(R.id.tvLangEN);
        tvLangKH = findViewById(R.id.tvLangKH);


        sharedPreferences = new SharePreferenceUtils(this);
        language = sharedPreferences.getLanguage();


        flagPermission = RuntimePermission.CheckingPermissionIsEnabledOrNot(this);

        if (!flagPermission) {
            RuntimePermission.requestReadAndPermission(this);
        } else {
            deviceID = getDeviceIMEI();
            btnLogin.setOnClickListener(this);
            btnOTP.setOnClickListener(this);
            llLangEN.setOnClickListener(this);
            llLangKH.setOnClickListener(this);
            mActivity = this;
            if (language.toLowerCase().equals("kh")) {
                llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_white));
                llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_red));
                tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
                tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));

            } else {
                llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_red));
                llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_white));
                tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));
                tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
            }



        }


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llLangEN:
                llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_red));
                llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_white));
                tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));
                tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
                sharedPreferences.putLanguage("en");
                LanguageUtils.setLanguage(getApplication(), sharedPreferences.getLanguage());
                restartActivity();
                break;

            case R.id.llLangKH:
                llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_white));
                llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_red));
                tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
                tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));
                sharedPreferences.putLanguage("kh");
                LanguageUtils.setLanguage(getApplication(), sharedPreferences.getLanguage());
                restartActivity();
                break;

            case R.id.btnLogin:
                otp = edOTP.getText().toString().trim();
                isdn = edISDN.getText().toString().trim();
                if (validate())
                    new CallWSAsyncTask().execute("2", isdn, otp, deviceID, "ANDROID");


                break;
            case R.id.btnOTP:
                isdn = edISDN.getText().toString().trim();
                new CallWSAsyncTask().execute("1", isdn, deviceID, "ANDROID");
                break;
        }
    }


    private boolean validate() {
        if ("".equals(isdn)) {
            Toast.makeText(this, "Please input phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(otp)) {
            Toast.makeText(this, "Please input OTP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "getOtp", "login",
                                    "getlist", mActivity);

                            break;
                        case 2:
                            req.addParam("isdn", params[1]);
                            req.addParam("otp", params[2]);
                            req.addParam("deviceId", params[3]);
                            req.addParam("os", params[4]);
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "checkOtp", "login",
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
                            itemGetOTP = req.parseXMLToListObject("return", GetOtpItem.class);
                            Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            break;
                        case 2:
                            itemCheckOTP = req.parseXMLToListObject("return", CheckOTPItem.class);
                            String lat = itemCheckOTP.get(0).getLatStadium();
                            String log = itemCheckOTP.get(0).getLongStadium();
                            totalIsdn = itemCheckOTP.get(0).getTotalIsdn();
                            EVENT_DATE_TIME = itemCheckOTP.get(0).getTimeNightRace();
                            permission = itemCheckOTP.get(0).getRole().getPermission();
                            ArrayList arrListGift = new ArrayList();

                            if (itemCheckOTP.get(0).getRole().getRoleName().equals("CUSTOMER")) {
                                for (int i = 0; i < itemCheckOTP.get(0).getTicket().getLstGift().size(); i++) {
                                    arrListGift.add(itemCheckOTP.get(0).getTicket().getLstGift().get(i));
                                }
                                String stattus = itemCheckOTP.get(0).getTicket().getStatus();
                                String qrCode = itemCheckOTP.get(0).getTicket().getQrCode();
                                String typeTicket = itemCheckOTP.get(0).getTicket().getTicketType();
                                if ("0".equals(itemCheckOTP.get(0).getTicket().getStatus())) {
                                    Intent intent = new Intent(LoginActivity.this, ConfirmActivity.class);
                                    intent.putExtra("OTP", otp);
                                    intent.putExtra("ISDN", isdn);
                                    intent.putExtra("STATUS", stattus);
                                    intent.putExtra("QR_CODE", qrCode);
                                    intent.putExtra("TYPE_TICKET", typeTicket);
                                    intent.putExtra("LAT", lat);
                                    intent.putExtra("LONG", log);
                                    intent.putExtra("TIME_NIGHT_RACE", EVENT_DATE_TIME);
                                    intent.putStringArrayListExtra("LIST_GIFT", arrListGift);
                                    startActivity(intent);
                                } else {
//                                    String stattus = itemCheckOTP.get(0).getTicket().getStatus();
//                                    String qrCode = itemCheckOTP.get(0).getTicket().getQrCode();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putStringArrayListExtra("LIST_GIFT", arrListGift);
                                    intent.putExtra("STATUS", stattus);
                                    intent.putExtra("QR_CODE", qrCode);
                                    intent.putExtra("TYPE_TICKET", typeTicket);
                                    intent.putExtra("LAT", lat);
                                    intent.putExtra("LONG", log);
                                    intent.putExtra("ISDN", isdn);
                                    intent.putExtra("TIME_NIGHT_RACE", EVENT_DATE_TIME);
                                    startActivity(intent);
                                }
                            } else {
                                if ("STAFF_SYNC".equals(itemCheckOTP.get(0).getRole().getPermission())) {
                                    dbHelper = new DBHelper(LoginActivity.this);
                                    int size = dbHelper.checkSize();

                                    boolean flag = dbHelper.checkSignData();
                                    if (totalIsdn > size) {
                                        Intent intent = new Intent(LoginActivity.this, SignDataActivity.class);
                                        intent.putExtra("OTP", otp);
                                        intent.putExtra("ISDN", isdn);
                                        intent.putExtra("TIME_NIGHT_RACE", EVENT_DATE_TIME);
                                        intent.putExtra("PERMISSION", EVENT_DATE_TIME);
                                        intent.putExtra("TOTAL_ISDN", isdn);
                                        startActivity(intent);
                                        edOTP.setText("");
                                    } else {
                                        if (flag) {
                                            Intent intentChart = new Intent(LoginActivity.this, ChartActivity.class);
                                            intentChart.putExtra("OTP", otp);
                                            intentChart.putExtra("ISDN", isdn);
                                            intentChart.putExtra("TIME_NIGHT_RACE", EVENT_DATE_TIME);
                                            intentChart.putExtra("PERMISSION", permission);
                                            startActivity(intentChart);
                                            edOTP.setText("");
                                        }else {
                                            Intent intent = new Intent(LoginActivity.this, SignDataActivity.class);
                                            intent.putExtra("OTP", otp);
                                            intent.putExtra("ISDN", isdn);
                                            intent.putExtra("TIME_NIGHT_RACE", EVENT_DATE_TIME);
                                            intent.putExtra("PERMISSION", EVENT_DATE_TIME);
                                            intent.putExtra("TOTAL_ISDN", isdn);
                                            startActivity(intent);
                                            edOTP.setText("");
                                        }
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, ChartActivity.class);
                                    intent.putExtra("OTP", otp);
                                    intent.putExtra("ISDN", isdn);
                                    intent.putExtra("TIME_NIGHT_RACE", EVENT_DATE_TIME);
                                    intent.putExtra("PERMISSION", permission);
                                    startActivity(intent);
                                    edOTP.setText("");
                                }
                            }
                            progress.dismiss();
                            break;
                    }
                } else {
                    String errorDec = req.getErrorDecription();
                    String resultXML = req.getResultXML();

                    itemGetOTP = req.parseXMLToListObject("return", GetOtpItem.class);
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
                        if (itemGetOTP != null) {
                            if (language.toLowerCase().equals("kh")) {
                                CommonActivity.createAlertDialog(mActivity, itemGetOTP.get(0).getMessageKh(), getString(R.string.app_name)).show();
                            } else {
                                CommonActivity.createAlertDialog(mActivity, itemGetOTP.get(0).getMessageEn(), getString(R.string.app_name)).show();
                            }

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

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }
}
