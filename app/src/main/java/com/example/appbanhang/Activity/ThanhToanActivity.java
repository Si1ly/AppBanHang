package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.Model.CreateOrder;
import com.example.appbanhang.Model.NotiRespone;
import com.example.appbanhang.Model.NotiSendData;
import com.example.appbanhang.R;
import com.example.appbanhang.Retrofit.ApiBanHang;
import com.example.appbanhang.Retrofit.ApiPushNotification;
import com.example.appbanhang.Retrofit.RetrofitClient;
import com.example.appbanhang.Retrofit.RetrofitClientNoti;
import com.example.appbanhang.Utils.Utils;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tongTien,email,sdt;
    EditText diaChi;
    AppCompatButton bt_datHang,bt_thanhtoanZalo;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long sum;
    int total;
    int iddonhang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);


        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2554, Environment.SANDBOX);

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
                                    messageModel -> {
                                        pushNotifytoUser();
                                        Utils.mangMuahang.clear();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                            ));
                }
            }
        });
        bt_thanhtoanZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diachi = diaChi.getText().toString().trim();
                if (TextUtils.isEmpty(diachi)) {
                    Toast.makeText(getApplicationContext()  , "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else {
                    String email = Utils.currentUser.getEmail();
                    String sdt = Utils.currentUser.getMobile();
                    int id = Utils.currentUser.getId();
                    compositeDisposable.add(apiBanHang.createOrder(email, sdt, String.valueOf(sum), id, diachi, total, new Gson().toJson(Utils.mangMuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        pushNotifytoUser();
                                        Utils.mangMuahang.clear();

                                        iddonhang = Integer.parseInt(messageModel.getIddonhang());
                                        requestZalo();

                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    }
                            ));
                }
            }
        });
    }

    private void requestZalo() {
            CreateOrder orderApi = new CreateOrder();

            try {
                JSONObject data = orderApi.createOrder("100000");
                String code = data.getString("return_code");


                if (code.equals("1")) {
                    String token = data.getString("zp_trans_token");

                    ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
                        @Override
                        public void onPaymentSucceeded(String s, String s1, String s2) {
                            compositeDisposable.add(apiBanHang.Updatemomo(iddonhang,token)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            messagerModel -> {
                                                if (messagerModel.isSuccess()){
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    Toast.makeText(getApplicationContext(), "Xin may dau", Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            },
                                            throwable -> {
                                                Log.d("kdkjasgdkas",throwable.getMessage());
                                            }
                                    ));
                        }

                        @Override
                        public void onPaymentCanceled(String s, String s1) {

                        }

                        @Override
                        public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void pushNotifytoUser() {
        String token = "c-X6eFdBQaqjecBi2vORWP:APA91bGT1JV_bGI2rcs0xMcYoZVRhxo5I1vawVq5Y_h86b4tlK8QyhUwmjq3ROrxVVCENpYqpyoT0YMa4OLhmRhTpkO1J6hwewn2N618ynYcjuTGROT2GaWNR4pK9VXrj_KNNJ8s8Blk";
        Map<String,String> data = new HashMap<>();
        data.put("title","Thông báo");
        data.put("body","Bạn có đơn hàng mới");
        NotiSendData notiSendData = new NotiSendData(token,data);
        ApiPushNotification apiPushNotification = RetrofitClientNoti.getInstance().create(ApiPushNotification.class);
        apiPushNotification.sendNotification(notiSendData).enqueue(new Callback<NotiRespone>() {
            @Override
            public void onResponse(Call<NotiRespone> call, Response<NotiRespone> response) {
                if (response.isSuccessful()){
                }
            }

            @Override
            public void onFailure(Call<NotiRespone> call, Throwable t) {
                Log.d("asdasdasas", t.getMessage());
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
        bt_thanhtoanZalo = findViewById(R.id.bt_thanhtoanZaloPay_tt);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}