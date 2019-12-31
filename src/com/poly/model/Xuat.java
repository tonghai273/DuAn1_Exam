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
public class Xuat {

    private String maDe;
    private String mon;
    private int so1;
    private int so2;
    private int so3;
    private int so4;
    private int so5;
    private String TB;

    public Xuat() {
    }

    public Xuat(String maDe, String mon, int so1, int so2, int so3, int so4, int so5, String TB) {
        this.maDe = maDe;
        this.mon = mon;
        this.so1 = so1;
        this.so2 = so2;
        this.so3 = so3;
        this.so4 = so4;
        this.so5 = so5;
        this.TB = TB;
    }

    public String getMaDe() {
        return maDe;
    }

    public void setMaDe(String maDe) {
        this.maDe = maDe;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public int getSo1() {
        return so1;
    }

    public void setSo1(int so1) {
        this.so1 = so1;
    }

    public int getSo2() {
        return so2;
    }

    public void setSo2(int so2) {
        this.so2 = so2;
    }

    public int getSo3() {
        return so3;
    }

    public void setSo3(int so3) {
        this.so3 = so3;
    }

    public int getSo4() {
        return so4;
    }

    public void setSo4(int so4) {
        this.so4 = so4;
    }

    public int getSo5() {
        return so5;
    }

    public void setSo5(int so5) {
        this.so5 = so5;
    }

    public String getTB() {
        return TB;
    }

    public void setTB(String TB) {
        this.TB = TB;
    }

  

}
