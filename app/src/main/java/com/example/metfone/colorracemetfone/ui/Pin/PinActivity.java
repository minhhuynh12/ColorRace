package com.example.metfone.colorracemetfone.ui.Pin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.Chart.ChartActivity;
import com.example.metfone.colorracemetfone.ui.CreateQrCode.CreateQrCodeActivity;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.MainActivity;
import com.example.metfone.colorracemetfone.ui.SignData.SignDataActivity;
import com.example.metfone.colorracemetfone.ui.confirm.ConfirmActivity;
import com.example.metfone.colorracemetfone.ui.login.LoginActivity;
import com.example.metfone.colorracemetfone.util.DBHelper;
import com.example.metfone.colorracemetfone.util.DbQrCode;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;
import com.example.metfone.colorracemetfone.util.Utils;

public class PinActivity extends AppCompatActivity {
    public static final String TAG = "PinLockView";

    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private SharePreferenceUtils sharePreferenceUtils;
    private String pinShare = "";
    private String status;
    private String roleName;
    private String permission;
    private int totalIsdn;
    private DBHelper dbHelper;
    private TextView profile_name;
    public static int START_ACTIVITY_FOR_RESULT_PIN = 94;
    private DbQrCode dbQrCode;
    private String isdn;
    private String qrCode;
    private String EVENT_DATE_TIME;
    private String sysDate;
    private boolean flaw = false;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {

            Log.d(TAG, "Pin complete: " + pin);
            if ("".equals(pinShare)) {
                Intent intent = new Intent(PinActivity.this, PinConfirmActivity.class);
                intent.putExtra("pin", pin);
                startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
            } else {

                if (pinShare.equals(pin)) {
                    if ("CUSTOMER".equals(roleName)) {
                        if ("0".equals(status)) {
                            Intent intent = new Intent(PinActivity.this, ConfirmActivity.class);
                            startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                        } else {
                            dbQrCode.insertQrCode(isdn, qrCode);

                            if (!Utils.hasConnection(PinActivity.this)) {
                                if (!"".equals(EVENT_DATE_TIME) || !"".equals(sysDate)) {
                                    if (Utils.compareDatetimeEvent(EVENT_DATE_TIME, sysDate)) {
                                        sharePreferenceUtils.putFlagQrCode("1");
                                        Intent intent = new Intent(PinActivity.this, CreateQrCodeActivity.class);
                                        startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                                    } else {
                                        sharePreferenceUtils.putFlagQrCode("0");
                                        Intent intent = new Intent(PinActivity.this, MainActivity.class);
                                        startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                                    }
                                } else {
                                    Toast.makeText(PinActivity.this, PinActivity.this.getResources().getString(R.string.no_netword), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                sharePreferenceUtils.putFlagQrCode("0");
                                Intent intent = new Intent(PinActivity.this, MainActivity.class);
                                startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                            }
                        }

                    } else {
                        if ("STAFF_SYNC".equals(permission)) {
                            dbHelper = new DBHelper(PinActivity.this);
                            int size = dbHelper.checkSize();

                            boolean flag = dbHelper.checkSignData();
                            if (totalIsdn > size) {
                                Intent intent = new Intent(PinActivity.this, SignDataActivity.class);
                                startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                            } else {
                                if (flag) {
                                    Intent intentChart = new Intent(PinActivity.this, ChartActivity.class);
                                    startActivityForResult(intentChart, START_ACTIVITY_FOR_RESULT_PIN);

                                } else {
                                    Intent intent = new Intent(PinActivity.this, SignDataActivity.class);
                                    startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);

                                }
                            }
                        } else {
                            Intent intent = new Intent(PinActivity.this, ChartActivity.class);
                            startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                        }
                    }
                } else {

                    Toast.makeText(PinActivity.this, PinActivity.this.getResources().getString(R.string.wrong_pin_code), Toast.LENGTH_SHORT).show();
                }
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
        dbQrCode = new DbQrCode(this);
        sharePreferenceUtils = new SharePreferenceUtils(this);
        pinShare = sharePreferenceUtils.getPinCode();
        roleName = sharePreferenceUtils.getRoleName();
        status = sharePreferenceUtils.getStatus();
        permission = sharePreferenceUtils.getPermission();
        totalIsdn = sharePreferenceUtils.getTotalIsdn();
        isdn = sharePreferenceUtils.getISDN_LOGIN();
        qrCode = sharePreferenceUtils.getQrCode();
        EVENT_DATE_TIME = sharePreferenceUtils.getTimeNightRace();
        sysDate = sharePreferenceUtils.getSystemDate();

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        profile_name = findViewById(R.id.profile_name);
        profile_name.setText(this.getResources().getString(R.string.set_up_pin_code));


        String pin = "";
        pin = sharePreferenceUtils.getPinCode();

        if ("".equals(pin)){
            mPinLockView.setVisibiliteCancel(new PinLockView.CallBackVisibility() {
                @Override
                public void callVisibiliti(View view) {
                    LinearLayout llCancel = view.findViewById(R.id.llCancel);
                    llCancel.setVisibility(View.VISIBLE);

                }
            });
        }else {
            mPinLockView.setVisibiliteCancel(new PinLockView.CallBackVisibility() {
                @Override
                public void callVisibiliti(View view) {
                    LinearLayout llCancel = view.findViewById(R.id.llCancel);
                    llCancel.setVisibility(View.GONE);

                }
            });
        }



            mPinLockView.setFinsh(new PinLockView.CallBackFinish() {
                @Override
                public void callFinish() {
                    if (!flaw) {
                        flaw = true;
                        sharePreferenceUtils.putPinCode("");



                        if ("CUSTOMER".equals(roleName)) {
                            if ("0".equals(status)) {
                                Intent intent = new Intent(PinActivity.this, ConfirmActivity.class);
                                startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                            } else {
                                dbQrCode.insertQrCode(isdn, qrCode);

                                if (!Utils.hasConnection(PinActivity.this)) {
                                    if (!"".equals(EVENT_DATE_TIME) || !"".equals(sysDate)) {
                                        if (Utils.compareDatetimeEvent(EVENT_DATE_TIME, sysDate)) {
                                            sharePreferenceUtils.putFlagQrCode("1");
                                            Intent intent = new Intent(PinActivity.this, CreateQrCodeActivity.class);
                                            startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                                        } else {
                                            sharePreferenceUtils.putFlagQrCode("0");
                                            Intent intent = new Intent(PinActivity.this, MainActivity.class);
                                            startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                                        }
                                    } else {
                                        Toast.makeText(PinActivity.this, PinActivity.this.getResources().getString(R.string.no_netword), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    sharePreferenceUtils.putFlagQrCode("0");
                                    Intent intent = new Intent(PinActivity.this, MainActivity.class);
                                    startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                                }
                            }

                        } else {
                            if ("STAFF_SYNC".equals(permission)) {
                                dbHelper = new DBHelper(PinActivity.this);
                                int size = dbHelper.checkSize();

                                boolean flag = dbHelper.checkSignData();
                                if (totalIsdn > size) {
                                    Intent intent = new Intent(PinActivity.this, SignDataActivity.class);
                                    startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                                } else {
                                    if (flag) {
                                        Intent intentChart = new Intent(PinActivity.this, ChartActivity.class);
                                        startActivityForResult(intentChart, START_ACTIVITY_FOR_RESULT_PIN);

                                    } else {
                                        Intent intent = new Intent(PinActivity.this, SignDataActivity.class);
                                        startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);

                                    }
                                }
                            } else {
                                Intent intent = new Intent(PinActivity.this, ChartActivity.class);
                                startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
                            }
                        }
                    }
                }
            });


        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_ACTIVITY_FOR_RESULT_PIN) {
            if (resultCode == 12) {
                Intent returnIntent = new Intent();
                setResult(12, returnIntent);
                finish();
            } else if (resultCode == 13) {
                restartActivity();
            } else {
                finish();
            }

        }
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
