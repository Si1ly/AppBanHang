package com.example.appbanhang.Utils;

import com.example.appbanhang.Model.GioHang;
import com.example.appbanhang.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //Them dia chi localhost
    public static final String BASE_URL="http://172.31.28.130/banhang/";
    public static List<GioHang> mangGiohang;
    public static List<GioHang> mangMuahang = new ArrayList<>();
    public static User currentUser = new User();

    public static String statusOrder(int status){
        String result ="";
        switch (status) {
            case 0:
                result = " Đơn hàng đang đợi xác nhận";
                break;
            case 1:
                result = " Đơn hàng xác nhận thành công";
                break;
            case 2:
                result = " Giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = " Đơn đã hủy";
                break;

            default:
                result = "";
                break;
        }
        return result;
    }

}
