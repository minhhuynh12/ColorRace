package com.example.metfone.colorracemetfone.ui.Pin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.Chart.ChartActivity;
import com.example.metfone.colorracemetfone.ui.CreateQrCode.CreateQrCodeActivity;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.MainActivity;
import com.example.metfone.colorracemetfone.ui.SignData.SignDataActivity;
import com.example.metfone.colorracemetfone.ui.SignData.model.SignDataItem;
import com.example.metfone.colorracemetfone.ui.confirm.ConfirmActivity;
import com.example.metfone.colorracemetfone.ui.login.LoginActivity;
import com.example.metfone.colorracemetfone.ui.login.model.CheckOTPItem;
import com.example.metfone.colorracemetfone.ui.login.model.GetOtpItem;
import com.example.metfone.colorracemetfone.util.DBHelper;
import com.example.metfone.colorracemetfone.util.DbQrCode;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;
import com.example.metfone.colorracemetfone.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class PinConfirmActivity extends AppCompatActivity {
    public static final String TAG = "PinLockView";
    private SharePreferenceUtils sharedPreferences;
    private DBHelper dbHelper;
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private String strPin;
    private int totalIsdn;
    private Activity mActivity;
    private String EVENT_DATE_TIME;
    private String permission;
    public List<CheckOTPItem> itemCheckOTP;
    private String confirmPin;
    private String isdn;
    private String status;
    private String roleName;
    public static int START_ACTIVITY_FOR_RESULT_PIN = 94;
    private TextView profile_name;
    private DbQrCode dbQrCode;
    private String qrCode;
    private String sysDate;


    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);
            if (pin.equals(strPin)){
                confirmPin = pin;
                sharedPreferences.putPinCode(pin);

                String otp = sharedPreferences.getOTP();
                String device_id = sharedPreferences.getDeviceID();


                if (!Utils.hasConnection(PinConfirmActivity.this)) {
                    if (!"".equals(EVENT_DATE_TIME) || !"".equals(sysDate)) {
                        if (Utils.compareDatetimeEvent(EVENT_DATE_TIME, sysDate)) {
                            sharedPreferences.putFlagQrCode("1");
                            Intent intent = new Intent(PinConfirmActivity.this , CreateQrCodeActivity.class);
                            startActivityForResult(intent , START_ACTIVITY_FOR_RESULT_PIN);
                        } else {
                            sharedPreferences.putFlagQrCode("0");
                            new CallWSAsyncTask().execute("1", isdn, otp , confirmPin , device_id);

                        }
                    } else {
                        Toast.makeText(PinConfirmActivity.this, PinConfirmActivity.this.getResources().getString(R.string.no_netword), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    sharedPreferences.putFlagQrCode("0");
                    new CallWSAsyncTask().execute("1", isdn, otp , confirmPin , device_id);
                }

            }else {
                Toast.makeText(PinConfirmActivity.this , PinConfirmActivity.this.getResources().getString(R.string.confirm_pin_code_wrong) , Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);

        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        mActivity = this;
        dbQrCode = new DbQrCode(this);
        Intent intent = getIntent();
        strPin = intent.getStringExtra("pin");

        sharedPreferences = new SharePreferenceUtils(this);
        roleName = sharedPreferences.getRoleName();
        status = sharedPreferences.getStatus();
        permission = sharedPreferences.getPermission();
        totalIsdn = sharedPreferences.getTotalIsdn();
        isdn = sharedPreferences.getISDN_LOGIN();
        qrCode = sharedPreferences.getQrCode();
        EVENT_DATE_TIME = sharedPreferences.getTimeNightRace();
        sysDate = sharedPreferences.getSystemDate();

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        profile_name = findViewById(R.id.profile_name);
        profile_name.setText(this.getResources().getString(R.string.confirm_pin));

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
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
                            req.addParam("pinCode", params[3]);
                            req.addParam("deviceId", params[4]);
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "setUpPinCode", "pincodeConfirm", "getlist", mActivity);
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
//                            itemCheckOTP = req.parseXMLToListObject("return", CheckOTPItem.class);
                            sharedPreferences.putOTP(confirmPin);
                            if ("CUSTOMER".equals(roleName)){
                                if ("0".equals(status)) {
                                    Intent intent = new Intent(PinConfirmActivity.this, ConfirmActivity.class);
                                    startActivityForResult(intent , START_ACTIVITY_FOR_RESULT_PIN);
                                } else {
                                    dbQrCode.insertQrCode(isdn, qrCode);
                                    Intent intent = new Intent(PinConfirmActivity.this, MainActivity.class);
                                    startActivityForResult(intent , START_ACTIVITY_FOR_RESULT_PIN);
                                }
                            }else {
                                if ("STAFF_SYNC".equals(permission)) {
                                    dbHelper = new DBHelper(PinConfirmActivity.this);
                                    int size = dbHelper.checkSize();

                                    boolean flag = dbHelper.checkSignData();
                                    if (totalIsdn > size) {
                                        Intent intent = new Intent(PinConfirmActivity.this, SignDataActivity.class);
                                        startActivityForResult(intent , START_ACTIVITY_FOR_RESULT_PIN);
                                    }else {
                                        if (flag) {
                                            Intent intentChart = new Intent(PinConfirmActivity.this, ChartActivity.class);
                                            startActivityForResult(intentChart , START_ACTIVITY_FOR_RESULT_PIN);

                                        }else {
                                            Intent intent = new Intent(PinConfirmActivity.this, SignDataActivity.class);
                                            startActivityForResult(intent , START_ACTIVITY_FOR_RESULT_PIN);

                                        }
                                    }
                                }else {
                                    Intent intent = new Intent(PinConfirmActivity.this, ChartActivity.class);
                                    startActivityForResult(intent , START_ACTIVITY_FOR_RESULT_PIN);
                                }
                            }

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
                        CommonActivity.createAlertDialog(mActivity,
                                getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
//                        if (items != null){
//                            CommonActivity.createAlertDialog(mActivity, items.get(0).getMessageEn(), getString(R.string.app_name)).show();
//                        }else {
//                            CommonActivity.createAlertDialog(mActivity, getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
//                        }
                    }
                }
            } else {
                if (result == 0) {// Do khong co mang
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_ACTIVITY_FOR_RESULT_PIN){
            if (resultCode == 12){
                Intent returnIntent = new Intent();
                setResult(12,returnIntent);
                finish();
            }
            finish();
        }
    }
}
