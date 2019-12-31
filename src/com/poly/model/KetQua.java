/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.model;

/**
 *
 * @author Tong Duy Hai
 */
public class KetQua {

    private int maketqua;
    private String tentaikhoan;
    private String madethi;
    private double diem;

    public KetQua() {
    }

    public KetQua(int maketqua, String tentaikhoan, String madethi, double diem) {
        this.maketqua = maketqua;
        this.tentaikhoan = tentaikhoan;
        this.madethi = madethi;
        this.diem = diem;
    }

    public int getMaketqua() {
        return maketqua;
    }

    public void setMaketqua(int maketqua) {
        this.maketqua = maketqua;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMadethi() {
        return madethi;
    }

    public void setMadethi(String madethi) {
        this.madethi = madethi;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }

}
