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
public class MonHoc {
    private String mamon;
    private String tenmon;

    public MonHoc() {
    }

    public MonHoc(String mamon, String tenmon) {
        this.mamon = mamon;
        this.tenmon = tenmon;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }
    
}
