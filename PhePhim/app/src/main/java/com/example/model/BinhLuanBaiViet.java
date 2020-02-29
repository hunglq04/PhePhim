package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BinhLuanBaiViet {

    @SerializedName("MaBaiViet")
    @Expose
    private String maBaiViet;
    @SerializedName("User")
    @Expose
    private String user;
    @SerializedName("Avatar")
    @Expose
    private String avatar;
    @SerializedName("NoiDung")
    @Expose
    private String noiDung;
    @SerializedName("Ngay")
    @Expose
    private String ngay;
    @SerializedName("DiemBL")
    @Expose
    private int diemBL;

    public String getMaBaiViet() {
        return maBaiViet;
    }

    public void setMaBaiViet(String maBaiViet) {
        this.maBaiViet = maBaiViet;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getDiemBL() {
        return diemBL;
    }

    public void setDiemBL(int diemBL) {
        this.diemBL = diemBL;
    }

}