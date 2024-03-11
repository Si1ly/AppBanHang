package com.example.appbanhang.Retrofit;

import android.database.Observable;

import com.example.appbanhang.Model.LoaiSp;
import com.example.appbanhang.Model.LoaiSpModel;
import com.example.appbanhang.Model.User;
import com.example.appbanhang.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Call<List<LoaiSp>> getLoaiSp();

    @POST("dangki.php")
    Observable<User> dangKi();

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

}
