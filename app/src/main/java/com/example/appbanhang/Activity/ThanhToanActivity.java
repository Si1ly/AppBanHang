package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.appbanhang.R;
import com.example.appbanhang.Utils.Utils;

import java.text.DecimalFormat;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tongTien,email,sdt;
    EditText diaChi;
    AppCompatButton bt_datHang;
    long sum;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        initView();
        initControl();
        countItem();
    }

    private void countItem() {
        total = 0;
        for(int i=0;i<Utils.mangMuahang.size();i++){
            total += total + Utils.mangMuahang.get(i).getSl();
        }
    }

    private void initControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        sum = getIntent().getLongExtra("tongtien",0);
        tongTien.setText(decimalFormat.format(sum));
        email.setText(Utils.currentUser.getEmail());
        sdt.setText(Utils.currentUser.getMobile());
        bt_datHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diachi = diaChi.getText().toString().trim();
                if(TextUtils.isEmpty(diachi)){
                    Toast.makeText(ThanhToanActivity.this, "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    //post Data
                }
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar_Thanhtoan);
        tongTien = findViewById(R.id.tongTien_tt);
        email = findViewById(R.id.email_tt);
        sdt = findViewById(R.id.sdt_tt);
        diaChi = findViewById(R.id.diaChi_tt);
        bt_datHang = findViewById(R.id.bt_Dathang_tt);
    }
}