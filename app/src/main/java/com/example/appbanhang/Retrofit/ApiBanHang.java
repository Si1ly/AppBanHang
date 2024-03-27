package com.example.appbanhang.Retrofit;

import com.example.appbanhang.Model.DonHangModel;
import com.example.appbanhang.Model.LoaiSp;

import com.example.appbanhang.Model.LoaiSpModel;
import com.example.appbanhang.Model.SanPhamMoiModel;
import com.example.appbanhang.Model.User;
import com.example.appbanhang.Model.UserModel;

import java.util.List;
import io.reactivex.rxjava3.core.*;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {

    @GET("getloaisanpham.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Call<SanPhamMoiModel> getSpmoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );


    @POST("dangky.php")
    @FormUrlEncoded
    Observable<UserModel> dangky(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> createOrder(
            @Field("email") String email,
            @Field("sodienthoai") String sodienthoai,
            @Field("tongtien") String tongtien,
            @Field("iduser") int iduser,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
        @Field("iduser") int iduser
    );
}
