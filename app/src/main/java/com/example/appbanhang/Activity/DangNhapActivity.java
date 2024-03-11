package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.Retrofit.ApiBanHang;
import com.example.appbanhang.Retrofit.RetrofitClient;
import com.example.appbanhang.Utils.Utils;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class DangNhapActivity extends AppCompatActivity {
    TextView dangKi;
    EditText edt_email_login,edt_pass_login;
    AppCompatButton button;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();
    }

    private void initControl() {
        dangKi.setOnClickListener(task->{
            Intent i = new Intent(DangNhapActivity.this,DangKiActivity.class);
            startActivity(i);
        });
        button.setOnClickListener(task1->{
            String email = edt_email_login.getText().toString().trim();
            String pass = edt_pass_login.getText().toString().trim();
            //Api
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        dangKi = findViewById(R.id.lnkRegister);
        edt_email_login = findViewById(R.id.edtloginEmail);
        edt_pass_login = findViewById(R.id.edtloginPwd);
        button = findViewById(R.id.btnlogin);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}