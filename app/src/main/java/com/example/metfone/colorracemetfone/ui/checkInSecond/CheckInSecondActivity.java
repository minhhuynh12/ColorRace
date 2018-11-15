package com.example.metfone.colorracemetfone.ui.checkInSecond;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.checkInSecond.model.CheckInSecondItem;
import com.example.metfone.colorracemetfone.ui.showResultQrCode.model.ContactItem;
import com.example.metfone.colorracemetfone.util.DBCheckInSecond;
import com.example.metfone.colorracemetfone.util.DBHelper;

public class CheckInSecondActivity extends AppCompatActivity {

    private String isdn;
    private String datetime;
    private EditText edCustomer;
    private EditText edTicketType;
    private EditText edCheckIN;
    private String data;
    private String ticketType;
    private Button btnGivingGifts;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_second);
        Intent intent = getIntent();
        datetime = intent.getStringExtra("datetime");
        data = intent.getStringExtra("data");
        String[] separated = data.split(";");
        init();

        for (int i = 0; i < separated.length; i++) {
            String[] arrStr = separated[i].split("=");
            if ("isdn ".equals(arrStr[0])) {
                if (arrStr.length > 1) {
                    isdn = arrStr[1];
                } else {
                    isdn = "";
                }
            } else if ("ticket_type".equals(arrStr[0])) {
                if (arrStr.length > 1) {
                    ticketType = arrStr[1];
                } else {
                    ticketType = "";
                }
            }
        }

        edCustomer.setText(isdn);
        edTicketType.setText(ticketType);
        edCheckIN.setText(datetime);

        btnGivingGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init(){
        edCustomer = findViewById(R.id.edCustomer);
        edTicketType = findViewById(R.id.edTicketType);
        edCheckIN = findViewById(R.id.edCheckIN);
        btnGivingGifts = findViewById(R.id.btnGivingGifts);
    }

}
