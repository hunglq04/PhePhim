package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Phim implements Serializable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Ten")
    @Expose
    private String ten;
    @SerializedName("DaoDien")
    @Expose
    private String daoDien;
    @SerializedName("DienVien")
    @Expose
    private String dienVien;
    @SerializedName("ThoiLuong")
    @Expose
    private String thoiLuong;
    @SerializedName("Ngay")
    @Expose
    private String ngay;
    @SerializedName("Diem")
    @Expose
    private float diem;
    @SerializedName("Poster")
    @Expose
    private String poster;
    @SerializedName("TheLoai")
    @Expose
    private String theLoai;
    @SerializedName("MoTa")
    @Expose
    private String moTa;
    @SerializedName("Banner")
    @Expose
    private String banner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDaoDien() {
        return daoDien;
    }

    public void setDaoDien(String daoDien) {
        this.daoDien = daoDien;
    }

    public String getDienVien() {
        return dienVien;
    }

    public void setDienVien(String dienVien) {
        this.dienVien = dienVien;
    }

    public String getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(String thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

}