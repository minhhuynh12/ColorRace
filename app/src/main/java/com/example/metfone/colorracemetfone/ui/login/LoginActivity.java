package com.example.metfone.colorracemetfone.ui.login;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.example.metfone.colorracemetfone.ui.CreateQrCode.CreateQrCodeActivity;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.MainActivity;
import com.example.metfone.colorracemetfone.ui.Pin.PinActivity;
import com.example.metfone.colorracemetfone.ui.SignData.SignDataActivity;
import com.example.metfone.colorracemetfone.ui.confirm.ConfirmActivity;
import com.example.metfone.colorracemetfone.ui.login.model.CheckOTPItem;
import com.example.metfone.colorracemetfone.ui.login.model.GetOtpItem;
import com.example.metfone.colorracemetfone.util.DBHelper;
import com.example.metfone.colorracemetfone.util.DbQrCode;
import com.example.metfone.colorracemetfone.util.LanguageUtils;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;
import com.example.metfone.colorracemetfone.util.RuntimePermission;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;
import com.example.metfone.colorracemetfone.util.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
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
    private String EVENT_DATE_TIME = "";
    private String deviceID;
    private String permission;
    private int totalIsdn;
    private DBHelper dbHelper;
    private DbQrCode dbQrCode;
    String stattus;
    private String sysDate = "";
    String qrCode;
    public static int START_ACTIVITY_FOR_RESULT_PIN = 94;
    private String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private boolean lang = false;


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
//                    sharedPreferences.putLanguage("kh");
//                    LanguageUtils.setLanguage(getApplication(), sharedPreferences.getLanguage());
//                    language = sharedPreferences.getLanguage();
//                    if (language.toLowerCase().equals("kh")) {
//                        llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_white));
//                        llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_red));
//                        tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
//                        tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));
//                        sharedPreferences.putLanguage("kh");
//                        LanguageUtils.setLanguage(getApplication(), sharedPreferences.getLanguage());
//                        restartActivity();
//
//                    } else {
//                        llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_red));
//                        llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_white));
//                        tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));
//                        tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
//                        sharedPreferences.putLanguage("en");
//                        LanguageUtils.setLanguage(getApplication(), sharedPreferences.getLanguage());
//                        restartActivity();
//                    }
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
        edOTP.setText("");

        Gson gson = new Gson();
        sharedPreferences = new SharePreferenceUtils(this, gson);
        language = sharedPreferences.getLanguage();

        dbHelper = new DBHelper(LoginActivity.this);
        dbQrCode = new DbQrCode(this);
        String pin = "";
        pin = sharedPreferences.getPinCode();

        if (!"".equals(pin)) {
            Intent intent = new Intent(LoginActivity.this, PinActivity.class);
            startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
        }

        if (language.toLowerCase().equals("kh")) {
            llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_white));
            llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_red));
            tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
            tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));
            sharedPreferences.putLanguage("kh");
            LanguageUtils.setLanguage(getApplication(), sharedPreferences.getLanguage());

        } else {
            llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_red));
            llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_white));
            tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));
            tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
            sharedPreferences.putLanguage("en");
            LanguageUtils.setLanguage(getApplication(), sharedPreferences.getLanguage());
        }


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
                sharedPreferences.putLanguage("kh");
                LanguageUtils.setLanguage(getApplication(), sharedPreferences.getLanguage());

            } else {
                llLangEN.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_en_red));
                llLangKH.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.border_lang_kh_white));
                tvLangEN.setTextColor(LoginActivity.this.getResources().getColor(R.color.white));
                tvLangKH.setTextColor(LoginActivity.this.getResources().getColor(R.color.color_title));
                sharedPreferences.putLanguage("en");
                LanguageUtils.setLanguage(getApplication(), sharedPreferences.getLanguage());
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
                if (validate()) {
                    sharedPreferences.putISDN_LOGIN(isdn);
                    sharedPreferences.putDeviceID(deviceID);
                    EVENT_DATE_TIME = sharedPreferences.getTimeNightRace();
                    sysDate = sharedPreferences.getSystemDate();

                    if (!Utils.hasConnection(LoginActivity.this)) {
                        if (!"".equals(EVENT_DATE_TIME) || !"".equals(sysDate)) {
                            if (Utils.compareDatetimeEvent(EVENT_DATE_TIME, sysDate)) {

                                sharedPreferences.putFlagQrCode("1");
                                Intent intent = new Intent(LoginActivity.this, CreateQrCodeActivity.class);
                                startActivity(intent);
                            } else {
                                sharedPreferences.putFlagQrCode("0");
                                new CallWSAsyncTask().execute("2", isdn, otp, deviceID, "ANDROID");
                            }
                        } else {
                            Toast.makeText(this, this.getResources().getString(R.string.no_netword), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        sharedPreferences.putFlagQrCode("0");
                        new CallWSAsyncTask().execute("2", isdn, otp, deviceID, "ANDROID");
                    }
                }

                break;
            case R.id.btnOTP:
                isdn = edISDN.getText().toString().trim();
                sharedPreferences.putISDN_LOGIN(isdn);
                EVENT_DATE_TIME = sharedPreferences.getTimeNightRace();
                sysDate = sharedPreferences.getSystemDate();
                if (validateOTP()) {
                    Utils.hideSoftKeyboard(LoginActivity.this);
                    if (!Utils.hasConnection(LoginActivity.this)) {
                        if (!"".equals(EVENT_DATE_TIME) || !"".equals(sysDate)) {
                            if (Utils.compareDatetimeEvent(EVENT_DATE_TIME, sysDate)) {
                                sharedPreferences.putFlagQrCode("1");
                                Intent intent = new Intent(LoginActivity.this, CreateQrCodeActivity.class);
                                startActivity(intent);
                            } else {
                                sharedPreferences.putFlagQrCode("0");
                                new CallWSAsyncTask().execute("1", isdn, deviceID, "ANDROID");
                            }
                        } else {
                            Toast.makeText(this, this.getResources().getString(R.string.no_netword), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        sharedPreferences.putFlagQrCode("0");
                        new CallWSAsyncTask().execute("1", isdn, deviceID, "ANDROID");
                    }
                }

                break;
        }
    }


    private boolean validate() {

        if ("".equals(isdn)) {
            Toast.makeText(this, this.getResources().getString(R.string.please_input_phone), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.checkNumberPhone(isdn)) {
            Toast.makeText(this, this.getResources().getString(R.string.not_metfone), Toast.LENGTH_SHORT).show();
            return false;
        }

        if ("".equals(otp)) {
            Toast.makeText(this, this.getResources().getString(R.string.please_input_otp), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateOTP() {

        if ("".equals(isdn)) {
            Toast.makeText(this, this.getResources().getString(R.string.please_input_phone), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.checkNumberPhone(isdn)) {
            Toast.makeText(this, this.getResources().getString(R.string.not_metfone), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(mActivity, LoginActivity.this.getResources().getString(R.string.OTP_successfully), Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            break;
                        case 2:

                            itemCheckOTP = req.parseXMLToListObject("return", CheckOTPItem.class);
                            String lat = itemCheckOTP.get(0).getLatStadium();
                            String log = itemCheckOTP.get(0).getLongStadium();
                            totalIsdn = itemCheckOTP.get(0).getTotalIsdn();
                            EVENT_DATE_TIME = itemCheckOTP.get(0).getTimeNightRace();
                            sysDate = itemCheckOTP.get(0).getSystemDate();
                            permission = itemCheckOTP.get(0).getRole().getPermission();
                            sharedPreferences.putOTP(otp);
                            sharedPreferences.putISDN(isdn);

                            sharedPreferences.putLat(lat);
                            sharedPreferences.putLong(log);
                            sharedPreferences.putTimeNightRace(EVENT_DATE_TIME);
                            sharedPreferences.putTotalIsdn(totalIsdn);
                            sharedPreferences.putPermission(permission);
                            sharedPreferences.putSysDate(sysDate);
                            edISDN.setText("");
                            edOTP.setText("");
                            ArrayList arrListGift = new ArrayList();

                            String roleName = itemCheckOTP.get(0).getRole().getRoleName();
                            sharedPreferences.putRoleName(roleName);
                            if (roleName.equals("CUSTOMER")) {
                                for (int i = 0; i < itemCheckOTP.get(0).getTicket().getLstGift().size(); i++) {
                                    arrListGift.add(itemCheckOTP.get(0).getTicket().getLstGift().get(i));
                                }
                                stattus = itemCheckOTP.get(0).getTicket().getStatus();
                                qrCode = itemCheckOTP.get(0).getTicket().getQrCode();
                                String typeTicket = itemCheckOTP.get(0).getTicket().getTicketType();
                                String statusTicket = itemCheckOTP.get(0).getTicket().getStatus();
                                sharedPreferences.putStatus(stattus);
                                sharedPreferences.putQrCode(qrCode);
                                sharedPreferences.putTypeTicket(typeTicket);
                                sharedPreferences.putStatusCustomer(statusTicket);
                                sharedPreferences.putList(arrListGift);

                                CommonActivity.createDialogYesNo(mActivity, LoginActivity.this.getResources().getString(R.string.warning_pin_code), getString(R.string.app_name), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if ("0".equals(stattus)) {

                                            Intent intent = new Intent(LoginActivity.this, ConfirmActivity.class);
                                            startActivity(intent);
                                        } else {
                                            dbQrCode.insertQrCode(isdn, qrCode);

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(LoginActivity.this, PinActivity.class);
                                        startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                                    }
                                }).show();

                            } else {
                                CommonActivity.createDialogYesNo(mActivity, LoginActivity.this.getResources().getString(R.string.warning_pin_code), getString(R.string.app_name), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if ("STAFF_SYNC".equals(permission)) {

                                            int size = dbHelper.checkSize();
                                            boolean flag = dbHelper.checkSignData();

                                            if (totalIsdn > size) {
                                                Intent intent = new Intent(LoginActivity.this, SignDataActivity.class);
                                                startActivity(intent);
                                                edOTP.setText("");
                                            } else {
                                                if (flag) {
                                                    Intent intentChart = new Intent(LoginActivity.this, ChartActivity.class);
                                                    startActivity(intentChart);
                                                    edOTP.setText("");
                                                } else {
                                                    Intent intent = new Intent(LoginActivity.this, SignDataActivity.class);
                                                    startActivity(intent);
                                                    edOTP.setText("");
                                                }
                                            }
                                        } else {
                                            Intent intent = new Intent(LoginActivity.this, ChartActivity.class);
                                            startActivity(intent);
                                            edOTP.setText("");
                                        }
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(LoginActivity.this, PinActivity.class);
                                        startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                                    }
                                }).show();
                                progress.dismiss();
                                break;
                            }
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
            } else {
                if (result == 0) {
                    CommonActivity.createAlertDialog(mActivity,
                            getString(R.string.errorNetworkAccess), getString(R.string.app_name)).show();
                } else {
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

    @SuppressLint("MissingPermission")
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_ACTIVITY_FOR_RESULT_PIN) {
            if (resultCode != 12) {
                finish();
            }
        }
    }
}
