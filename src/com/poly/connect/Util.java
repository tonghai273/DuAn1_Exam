/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.connect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Tong Duy Hai
 */
public class Util {

    public static Connection con;

    public static Connection getConnection() throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            FileInputStream fis = new FileInputStream("‪Connect.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<String> list = (ArrayList<String>) ois.readObject();

            String url = "jdbc:sqlserver://Localhost\\SQLEXPRESS:1433;databaseName=" + list.get(0) + "";
            con = DriverManager.getConnection(url, list.get(1), list.get(2));

            fis.close();
            ois.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void disCon() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized Connection TestCon(String data, String user, String pass) {
        try {
            String url = "jdbc:sqlserver://Localhost\\SQLEXPRESS:1433;databaseName=" + data + "";
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void changeLog(String manv, String action, String id) {
        try {
            File file = new File("changelog.txt");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
            if (file.exists()) {
                bw.newLine();
                bw.write(getTime() + " " + getDate() + " --User: " + manv + " --action: " + action + " --action-id: " + id);
            } else {
                bw.write("Changelog----");
                bw.newLine();
                bw.write(getTime() + " " + getDate() + " --User: " + manv + " --action: " + action + " --action-id: " + id);
            }
            bw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static String getTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa");
        String time = sdf.format(d);

        return time;
    }

    public static String getDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        return strDate;
    }
}
