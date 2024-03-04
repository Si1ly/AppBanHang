package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.appbanhang.R;

public class GioHang extends AppCompatActivity {

    TextView gioHangTrong,Sum;
    Toolbar toolbar;
    RecyclerView recyclerViewGioHang;
    Button buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
    }

    private void initView() {
        gioHangTrong = findViewById(R.id.gioHang_tvTrong);
        Sum = findViewById(R.id.gioHang_tongGia);
        toolbar = findViewById(R.id.toolbar_GioHang);
        recyclerViewGioHang = findViewById(R.id.recycleView_gioHang);
        buy = findViewById(R.id.gioHang_MuaHang);
    }
}