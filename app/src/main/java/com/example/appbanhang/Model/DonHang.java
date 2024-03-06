package com.example.appbanhang.Model;

import java.util.List;

public class DonHang {
    int id;
    int idUser;
    String diaChi;
    String soDienthoai;
    String tongTien;
    List<Item> itemList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienthoai() {
        return soDienthoai;
    }

    public void setSoDienthoai(String soDienthoai) {
        this.soDienthoai = soDienthoai;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
