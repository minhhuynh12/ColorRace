package com.example.metfone.colorracemetfone.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.MainActivity;

public class LoginActivity extends Activity implements View.OnClickListener{
    Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnLogin:
                Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                startActivity(intent);
                break;
        }
    }


}
