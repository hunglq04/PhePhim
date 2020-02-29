package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaiViet implements Serializable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("TieuDe")
    @Expose
    private String tieuDe;
    @SerializedName("NoiDung")
    @Expose
    private String noiDung;
    @SerializedName("Ngay")
    @Expose
    private String ngay;
    @SerializedName("Diem")
    @Expose
    private int diem;
    @SerializedName("Anh")
    @Expose
    private String anh;
    @SerializedName("TacGia")
    @Expose
    private String tacGia;
    @SerializedName("Avatar")
    @Expose
    private String avatar;
    @SerializedName("TheLoai")
    @Expose
    private String theLoai;

    public BaiViet(String id, String tieuDe, String noiDung, String ngay, int diem, String anh, String tacGia, String avatar, String theLoai) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ngay = ngay;
        this.diem = diem;
        this.anh = anh;
        this.tacGia = tacGia;
        this.avatar = avatar;
        this.theLoai = theLoai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
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

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

}