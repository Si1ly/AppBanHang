package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.appbanhang.Adapter.GioHangAdapter;
import com.example.appbanhang.Model.EventBus.TinhTongEvent;
import com.example.appbanhang.Model.GioHang;
import com.example.appbanhang.R;
import com.example.appbanhang.Utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {

    TextView gioHangTrong,sum_Sp;
    Toolbar toolbar;
    RecyclerView recyclerViewGioHang;
    Button buy;
    GioHangAdapter gioHangAdapter;
//    List<GioHang> gioHangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        sum();
    }

    private void sum() {
        long sum = 0;
        for(int i=0;i<Utils.mangGiohang.size();i++){
            sum = sum + (Utils.mangGiohang.get(i).getGiasp()*Utils.mangGiohang.get(i).getSl());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        sum_Sp.setText(decimalFormat.format(sum));
    }

    private void initControl() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerViewGioHang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewGioHang.setLayoutManager(layoutManager);

        if (Utils.mangGiohang.isEmpty() == true) {
            gioHangTrong.setVisibility(View.VISIBLE);
        }else{
            gioHangAdapter = new GioHangAdapter(getApplicationContext(),Utils.mangGiohang);
            recyclerViewGioHang.setAdapter(gioHangAdapter);
        }
    }

    private void initView() {
        gioHangTrong = findViewById(R.id.gioHang_tvTrong);
        sum_Sp = findViewById(R.id.gioHang_tongGia);
        toolbar = findViewById(R.id.toolbar_GioHang);
        recyclerViewGioHang = findViewById(R.id.recycleView_gioHang);
        buy = findViewById(R.id.gioHang_MuaHang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if(event != null){
            sum();
        }
    }

}