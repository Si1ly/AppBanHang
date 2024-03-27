package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.Model.EventBus.TinhTongEvent;
import com.example.appbanhang.R;
import com.example.appbanhang.Retrofit.ApiBanHang;
import com.example.appbanhang.Retrofit.RetrofitClient;
import com.example.appbanhang.Utils.Utils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tongTien,email,sdt;
    EditText diaChi;
    AppCompatButton bt_datHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
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
            total += Utils.mangMuahang.get(i).getSl();
        }
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        sum = getIntent().getLongExtra("tongtien", 0);

        tongTien.setText(decimalFormat.format(sum));
        email.setText(Utils.currentUser.getEmail());
        sdt.setText(Utils.currentUser.getMobile());
        bt_datHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diachi = diaChi.getText().toString().trim();
                if (TextUtils.isEmpty(diachi)) {
                    Toast.makeText(ThanhToanActivity.this, "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else {
                    String email = Utils.currentUser.getEmail();
                    String sdt = Utils.currentUser.getMobile();
                    int id = Utils.currentUser.getId();
                    compositeDisposable.add(apiBanHang.createOrder(email, sdt, String.valueOf(sum), id, diachi, total, new Gson().toJson(Utils.mangMuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        Log.d("asdasdawqe", email+sdt+sum+id+diachi+total+new Gson().toJson(Utils.mangMuahang));
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }


                            ));
                }
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toolbar_Thanhtoan);
        tongTien = findViewById(R.id.tongTien_tt);
        email = findViewById(R.id.email_tt);
        sdt = findViewById(R.id.sdt_tt);
        diaChi = findViewById(R.id.diaChi_tt);
        bt_datHang = findViewById(R.id.bt_Dathang_tt);


    }
}