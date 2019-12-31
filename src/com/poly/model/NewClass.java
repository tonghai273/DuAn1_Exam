/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.model;

import static java.lang.Integer.min;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tong Duy Hai
 */
public class NewClass {

//    public static double calculateArea(double alpha, double r) {
//        double dienTichCungTron = (3.14 * r * r * alpha) / 180;
//        double goc = Math.toRadians(alpha);
//        double dientich = (Math.sin(2 * goc) * r * r) / 2;
//        double kq = dienTichCungTron - dientich;
//        System.out.println(kq);
//        double kqq = Math.round(kq * 100.0) / 100.0;
//        System.out.println(kqq);
//        return kqq;
//    }
//
//    public static long calculateDeterminant(int size, int[][] matrixB) {
//        int temp = 1;
//        while (temp < size) {
//            for (int j = 0; j < temp; j++) {
//                for (int k = size - 1; k >= 0; k--) {
//                    matrixB[temp][k] = matrixB[temp][k] - ((matrixB[temp][j] * matrixB[j][k]) / matrixB[j][j]);
//                }
//            }
//            temp++;
//        }
//        int det = 1;
//        for (int i = 0; i < size; i++) {
//
//            det *= matrixB[i][i];
//        }
//
//        return det;
//    }
    String censorText(String s1, String s2) {
        String arr[] = s1.split(" ");
        for (int i = 0; i < arr.length; i++) {
            String s = s2.replace(arr[i], replaceStr(arr[i]));
            s2 = s;
        }
        return s2;

    }

    String replaceStr(String s) {
        String res = s.charAt(0) + "";
        for (int i = 1; i < s.length(); i++) {
            res += "*";
        }
        return res;

    }

    long[] findPM25MaskDistribution(long n, long k) {
        long max = n / k + 1;
        long min = max - 1;

        long kq[] = {min, max};
        return kq;

    }

//nhân 2 ma trận
    int[][] multiplyMatrix(int n, int m, int[][] a, int p, int[][] b) {
        int[][] c = new int[n][p];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                c[i][j] = 0;
                for (int k = 0; k < m; k++) {
                    c[i][j] = c[i][j] + a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public boolean validate(final String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static double computeIntersectedAreaOfCircles(int x1, int y1, double r1, int x2, int y2, double r2) {
        double d = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double abc = Math.acos(((d * d) + (r2 * r2) - (r1 * r1)) / (2 * d * r2));
        double cab = Math.acos(((d * d) + (r1 * r1) - (r2 * r2)) / (2 * d * r1));
        System.out.println(abc);
        System.out.println(cab);

        double a = 2 * cab;
        System.out.println(Math.sin(a));
        double b = 2 * abc;

        double dientich = (((a * r1 * r1) / 2) - (((r1 * r1) * Math.sin(a)) / 2) + (((b * r2 * r2) / 2)) - ((r2 * r2) * Math.sin(b)));
        System.out.println(dientich);
        return dientich;

    }

    String isEvenNumber(String s) {

        String ra = "";
        try {
            int sa = Integer.parseInt(s);
            if (s.length() > 8) {
                ra = s + " is out of range to check";
            } else {
                if (sa < 0) {
                    ra += s + " is an invalid integer number";
                }
                if (sa % 2 == 0) {
                    ra += s + " is even";
                }
                if (sa % 2 != 0) {
                    ra += s + " is not even";
                } else {

                }

            }
        } catch (Exception e) {
            ra += s + "is an invalid integer number";
        }

        return ra;

    }

    String numberToText(String num) {
        String out = "";
        try {
            int sa = Integer.parseInt(num);

        } catch (Exception e) {
            out += "Khong hop le";

        }
        return null;

    }

    int[] robotMaintenance(int n, int[] arr) {
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {

            }
        }
        return null;

    }

    String orderInteger(String[] arrInt) {

        String out = "";
        for (int i = 0; i < arrInt.length; i++) {
            double chuoi = Double.parseDouble(arrInt[i]);
            double tron = Math.round(chuoi * 10) / 10;
        }
        return null;

    }


    static int[] addX(int n, int arr[], int x) {
        int i;

        int newarr[] = new int[n + 1];
        for (i = 0; i < n; i++) {
            newarr[i] = arr[i];
        }

        newarr[n] = x;

        return newarr;
    }
    
    
    
   

    public static void main(String[] args) {
        computeIntersectedAreaOfCircles(1, 1, 8, 9, 3, 2);
    }

//    public static void main(String[] args) {
//        int m1, n1; // số dòng và số cột của ma trận A
//        int m2, n2; // số dòng và số cột của ma trận B
//        char choose;
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Nhập vào số dòng của ma trận A: ");
//        m1 = scanner.nextInt();
//        System.out.println("Nhập vào số cột của ma trận A: ");
//        n1 = scanner.nextInt();
//
//        System.out.println("Nhập vào số dòng của ma trận B: ");
//        m2 = scanner.nextInt();
//        System.out.println("Nhập vào số cột của ma trận B: ");
//        n2 = scanner.nextInt();
//
//        int[][] A = new int[m1][n1];
//        int[][] B = new int[m2][n2];
//
//        // ma trận tích C = A * B
//        // sẽ có số dòng là số dòng của ma trận A
//        // và số cột là số cột của ma trận B
//        int C[][] = new int[m1][n2];
//
//        while (m1 > 0 && n1 > 0 && m2 > 0 && n2 > 0) {
//            if (n1 != m2) {
//                System.out.println("Để nhân hai ma trận thì "
//                        + "số cột của ma trận A phải bằng số dòng của ma trận B");
//                System.out.println("Nhập lại số cột của ma trận A: ");
//                n1 = scanner.nextInt();
//                System.out.println("Nhập vào số dòng của ma trận B: ");
//                m2 = scanner.nextInt();
//            } else {
//                // nhập giá trị của các phần tử cho 2 ma trận A
//                System.out.println("Nhập vào các phần tử của ma trận A: ");
//                for (int i = 0; i < m1; i++) {
//                    for (int j = 0; j < n1; j++) {
//                        System.out.print("A[" + i + "][" + j + "] = ");
//                        A[i][j] = scanner.nextInt();
//                    }
//                }
//
//                // nhập giá trị của các phần tử cho 2 ma trận B
//                System.out.println("Nhập vào các phần tử của ma trận A: ");
//                for (int i = 0; i < m2; i++) {
//                    for (int j = 0; j < n2; j++) {
//                        System.out.print("B[" + i + "][" + j + "] = ");
//                        B[i][j] = scanner.nextInt();
//                    }
//                }
//
//                // hiển thị 2 ma trận vừa nhập
//                System.out.println("Ma trận A: ");
//                for (int i = 0; i < m1; i++) {
//                    for (int j = 0; j < n1; j++) {
//                        System.out.print(A[i][j] + "\t");
//                    }
//                    System.out.println("\n");
//                }
//
//                System.out.println("Ma trận B: ");
//                for (int i = 0; i < m2; i++) {
//                    for (int j = 0; j < n2; j++) {
//                        System.out.print(B[i][j] + "\t");
//                    }
//                    System.out.println("\n");
//                }
//
//                // tính và in ra ma trận C = A * B
//                for (int i = 0; i < m1; i++) {
//                    for (int j = 0; j < n2; j++) {
//                        C[i][j] = 0;
//                        for (int k = 0; k < n1; k++) {
//                            C[i][j] = C[i][j] + A[i][k] * B[k][j];
//                        }
//                    }
//                }
//
//                // hiển thị ma trận tích C
//                System.out.println("Ma trận tích C: ");
//                for (int i = 0; i < m1; i++) {
//                    for (int j = 0; j < n2; j++) {
//                        System.out.print(C[i][j] + "\t");
//                    }
//                    System.out.println("\n");
//                }
//
//                // tìm ma trận D là ma trận chuyển vị của ma trận C
//                // ma trận D là ma trận chuyển vị của ma trận C
//                // thì các dòng của ma trận C sẽ trở thành
//                // các cột của ma trận D và ngược lại
//                // ví dụ: ma trận C có số dòng m1 = 3 và số cột n2 = 4 thì
//                // ma trận D sẽ có số dòng n2 = 4 và số cột m1 = 3
//                int D[][] = new int[n2][m1];
//                for (int i = 0; i < m1; i++) {
//                    for (int j = 0; j < n2; j++) {
//                        D[j][i] = C[i][j];
//                    }
//                }
//
//                // hiển thị ma trận D
//                System.out.println("Ma trận chuyển vị của ma trận C là: ");
//                for (int i = 0; i < n2; i++) {
//                    for (int j = 0; j < m1; j++) {
//                        System.out.print(D[i][j] + "\t");
//                    }
//                    System.out.println("\n");
//                }
//
//                System.out.println("Bạn có muốn tiếp tục không? Bấm y để tiếp tục, n để thoát!");
//                choose = scanner.next().charAt(0);
//                if (choose == 'y') {
//                    continue;
//                } else {
//                    System.out.println("Goodbye!");
//                    break;
//                }
//            }
//
//        }
//    }
}
