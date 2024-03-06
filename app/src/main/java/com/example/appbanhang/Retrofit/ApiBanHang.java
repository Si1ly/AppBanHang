package com.example.appbanhang.Retrofit;

import android.database.Observable;

import com.example.appbanhang.Model.LoaiSp;
import com.example.appbanhang.Model.LoaiSpModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Call<List<LoaiSp>> getLoaiSp();

}
