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
public class DeThi {

    private String madethi;
    private String mamon;
    private String danhsachmacauhoi;
    private int thoigian;
    private String danhsachdapan;

    public DeThi() {
    }

    public DeThi(String madethi, String mamon, String danhsachmacauhoi, int thoigian, String danhsachdapan) {
        this.madethi = madethi;
        this.mamon = mamon;
        this.danhsachmacauhoi = danhsachmacauhoi;
        this.thoigian = thoigian;
        this.danhsachdapan = danhsachdapan;
    }

    public String getMadethi() {
        return madethi;
    }

    public void setMadethi(String madethi) {
        this.madethi = madethi;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getDanhsachmacauhoi() {
        return danhsachmacauhoi;
    }

    public void setDanhsachmacauhoi(String danhsachmacauhoi) {
        this.danhsachmacauhoi = danhsachmacauhoi;
    }

    public int getThoigian() {
        return thoigian;
    }

    public void setThoigian(int thoigian) {
        this.thoigian = thoigian;
    }

    public String getDanhsachdapan() {
        return danhsachdapan;
    }

    public void setDanhsachdapan(String danhsachdapan) {
        this.danhsachdapan = danhsachdapan;
    }

}
