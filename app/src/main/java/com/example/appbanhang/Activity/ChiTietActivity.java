package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Model.GioHang;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.R;
import com.example.appbanhang.Utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.function.ToLongBiFunction;

public class ChiTietActivity extends AppCompatActivity {
    TextView ten, gia, mota;
    Button add;
    ImageView anh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge notificationBadge;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionBar();
        initData();
        initControl();
    }

    private void initControl() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (Utils.mangGiohang.size() > 0) {
            boolean flag = false;
            int sl = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.mangGiohang.size(); i++) {
                if (Utils.mangGiohang.get(i).getIdsp() == sanPhamMoi.getId()) {
                    Utils.mangGiohang.get(i).setSl(sl + Utils.mangGiohang.get(i).getSl());
                    long gia = Long.parseLong(sanPhamMoi.getGiasp()) * Utils.mangGiohang.get(i).getSl();
                    Utils.mangGiohang.get(i).setGiasp(gia);
                    flag = true;
                }
                if (flag = true) {
                    long gia = Long.parseLong(sanPhamMoi.getGiasp()) * sl;
                    GioHang gioHang = new GioHang();
                    gioHang.setGiasp(gia);
                    gioHang.setSl(sl);
                    gioHang.setIdsp(sanPhamMoi.getId());
                    gioHang.setTensp(sanPhamMoi.getTensp());
                    gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                    Utils.mangGiohang.add(gioHang);
                }
            }
        } else {
            int sl = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getGiasp()) * sl;
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSl(sl);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            Utils.mangGiohang.add(gioHang);
        }
        int total = 0;
        for(int i=0;i<Utils.mangGiohang.size();i++){
            total += total + Utils.mangGiohang.get(i).getSl();
        }
        notificationBadge.setText(String.valueOf(total));
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        ten.setText(sanPhamMoi.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        gia.setText("Price: " + decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()) + "Đ"));
        mota.setText(sanPhamMoi.getMota());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(anh);
        Integer[] so = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initView() {
        ten = findViewById(R.id.tv_tensp);
        gia = findViewById(R.id.tv_giasp);
        mota = findViewById(R.id.tv_mieutaChitiet);
        add = findViewById(R.id.bt_add_gioHang);
        anh = findViewById(R.id.imgchitiet);
        spinner = findViewById(R.id.spinner);
        toolbar = findViewById(R.id.toolbar);
        notificationBadge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.frame_chiTiet);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(i);
            }
        });
        if (Utils.mangGiohang != null) {
            int total = 0;
            for(int i=0;i<Utils.mangGiohang.size();i++){
                total += total + Utils.mangGiohang.get(i).getSl();
            }
            notificationBadge.setText(String.valueOf(total));
        }
    }

    private void ActionBar() {
        // getSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.mangGiohang != null) {
            int total = 0;
            for(int i=0;i<Utils.mangGiohang.size();i++){
                total += total + Utils.mangGiohang.get(i).getSl();
            }
            notificationBadge.setText(String.valueOf(total));
        }
    }
}