package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Adapter.LoaiSpAdapter;
import com.example.appbanhang.Model.LoaiSp;
import com.example.appbanhang.R;
import com.example.appbanhang.Retrofit.ApiBanHang;
import com.example.appbanhang.Retrofit.RetrofitClient;
import com.example.appbanhang.Utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangLoaiSp;
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        anhxa();
        ActionBar();
        ActionviewLipper();
        if(isConnected(this)){
            Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show();
            ActionviewLipper();
            getLoaiSanPham();
        }else{
            Toast.makeText(this, "Khong co Internet", Toast.LENGTH_LONG).show();
        }
    }

    private void getLoaiSanPham() {
        apiBanHang.getLoaiSp().enqueue(new Callback<List<LoaiSp>>() {
            @Override
            public void onResponse(Call<List<LoaiSp>> call, Response<List<LoaiSp>> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    mangLoaiSp = response.body();
                    loaiSpAdapter = new LoaiSpAdapter(mangLoaiSp,getApplicationContext());
                    listViewmanhinhchinh.setAdapter(loaiSpAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<LoaiSp>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        apiBanHang.getLoaiSp().cancel();
        super.onDestroy();
    }

    private void ActionviewLipper() {
        List<String>mangquangcao =new ArrayList<>();
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        for (int i=0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in_right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out_right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in_right);
        viewFlipper.setOutAnimation(slide_out_right);
    }

    private void ActionBar() {
       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }



    private void anhxa(){
        toolbar =(Toolbar) findViewById(R.id.Toolbarmanhinhchinh);
        viewFlipper =(ViewFlipper) findViewById(R.id.ViewFlipper);
        recyclerViewmanhinhchinh =(RecyclerView) findViewById(R.id.recyclewview);
        navigationView =(NavigationView) findViewById(R.id.navigationview);
        listViewmanhinhchinh=(ListView) findViewById(R.id.listviewanhinhchinh);
        drawerLayout =(DrawerLayout) findViewById(R.id.DrawerLayout);
        mangLoaiSp = new ArrayList<>();
        loaiSpAdapter = new LoaiSpAdapter(mangLoaiSp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaiSpAdapter);
    }
    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi != null && wifi.isConnected() || mobile != null && mobile.isConnected()){
            return true;
        }else{
            return false;
        }
    }
}