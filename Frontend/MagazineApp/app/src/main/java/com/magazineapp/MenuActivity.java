package com.magazineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.magazineapp.main.BaseActivity;

public class MenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        initViews();

        txtRegister.setOnClickListener(view -> NextActivity(Register.class));

        txtLogin.setOnClickListener(view -> NextActivity(Login.class));

    }

    private TextView txtRegister;
    private TextView txtLogin;

    public void initViews() {
        txtRegister = findViewById(R.id.txtRegister);
        txtLogin = findViewById(R.id.txtLogin);
    }

}