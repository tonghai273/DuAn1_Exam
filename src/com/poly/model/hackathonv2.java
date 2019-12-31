/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.model;

import java.sql.Array;
import java.util.List;

/**
 *
 * @author Tong Duy Hai
 */
public class hackathonv2 {

    static String checkDivisible(int n) {
        int soDu, tong = 0, sdb = n;
        String kq = "";
        while (n > 0) {
            soDu = n % 10;
            n = n / 10;
            tong += soDu;
        }
        while (sdb > 0) {
            soDu = sdb % 10;
            sdb = sdb / 10;
            if (tong % soDu == 0) {
                kq = "YES";
            } else {
                kq = "NO";
                break;
            }
        }
        return kq;
    }

    String[] collectTopRewardableStudents(int n, String[] arrRollnos, double[] arrGrades) {

        int dem = 0;
        double max = arrGrades[0];
        for (int i = 0; i < n; i++) {
            if (max < arrGrades[i]) {
                max = arrGrades[i];
            }
        }
        for (int i = 0; i < n; i++) {
            if (arrGrades[i] == max) {
                dem++;

            }
        }
        int a[] = new int[n];

        String b[] = new String[dem];
        int j = 0;
//        for (int j = 0; j < dem; j++) {
        for (int i = 0; i < n; i++) {
            if (arrGrades[i] == max) {
                b[j] = arrRollnos[i];
                j++;
            }
        }

        return b;
    }

    static String compareADNGenomes(String strFirstGen, String strSecondGen) {
        String kq = "", aCon = "", bCon = "";
        int index = 0;
        if (strFirstGen.length() == 0 || strSecondGen.length() == 0) {
            return kq = "NO";
        }
        if (strFirstGen.length() == strSecondGen.length()) {
            for (int i = 0; i < strFirstGen.length(); i++) {
                if (strFirstGen.charAt(i) != strSecondGen.charAt(i)) {
                    index++;
                    aCon += strFirstGen.charAt(i);
                    bCon += strSecondGen.charAt(i);
                }
            }
            String ab = "";
            if (index != 2) {
                kq = "NO";
            } else {
                for (int i = bCon.length() - 1; i >= 0; i--) {
                    ab += bCon.charAt(i);
                }
                for (int i = 0; i < aCon.length(); i++) {
                    if (aCon.charAt(i) != ab.charAt(i)) {
                        kq = "NO";
                        break;
                    } else {
                        kq = "YES";
                    }
                }
            }
        } else {
            kq = "NO";
        }
        return kq;
    }

    static int[][] arrangeNumberPlates(int n, int[] arrNumberPlates) {
        int chan[] = new int[n], le[] = new int[n];
        int in = 0, jn = 0;
        for (int i = 0; i < arrNumberPlates.length; i++) {
            if (arrNumberPlates[i] % 2 == 0) {
                chan[in] = arrNumberPlates[i];
                in++;
            } else {
                le[jn] = arrNumberPlates[i];
                jn++;
            }
        }

        for (int i = 0; i < chan.length - 1; i++) {
            for (int j = i + 1; j < chan.length; j++) {
                if (chan[i] < chan[j]) {
                    int l1 = chan[i];
                    chan[i] = chan[j];
                    chan[j] = l1;
                }
            }
        }
        for (int i : chan) {
            System.out.println(i);
        }
        for (int i = 0; i < le.length - 1; i++) {
            for (int j = i + 1; j < le.length; j++) {
                if (le[i] > le[j]) {
                    int l1 = le[i];
                    le[i] = le[j];
                    le[j] = l1;
                }
            }
        }
        for (int i : le) {
            System.out.println(i);
        }
        int m[][] = new int[n][n];
        m[0] = chan;
        m[1] = le;
        return m;
    }
    
    static String convertBinaryToUnsignedInteger(String strBinary)
{
    String kq="";
    String decimal = "";
    boolean kt = true;
    for(int i =0;i<strBinary.length();i++){
        if(strBinary.indexOf(i)>0){
kt = false;
            break;
        }
        
        if(strBinary.charAt(i)!='1' || strBinary.charAt(i)!='0'){
            kt = false;
            break;
        }
        else{
            kt = true;
        }
    }
    if(kt == false){
        return "Khong hop le";
    }
    else{
        int decimall = Integer.parseInt(strBinary, 2);
        System.out.println("Decimal: " + decimal);
        return String.valueOf(decimall);
    }
    

}

    public static void main(String[] args) {
//        String a = compareADNGenomes("ab", "bd");
//        System.out.println(a);
//        int decimall = Integer.parseInt("11000111", 2);
//        System.out.println("Decimal: " + decimall);
     String a= convertBinaryToUnsignedInteger("001100");
        System.out.println(a);
    }
}
