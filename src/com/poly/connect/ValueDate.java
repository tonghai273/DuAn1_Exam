/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.connect;

import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Tong Duy Hai
 */
public class ValueDate {

    public static boolean checkPass(String x1) {
        if (x1.length() < 5) {
            JOptionPane.showMessageDialog(null, "Mật khẩu cần ít nhất 5 kí tự");
            return false;
        }
        return true;
    }

    public static boolean checkTaiKhoan(String s) {
        if (s.length() < 0) {
            JOptionPane.showMessageDialog(null, "Không để trống tài khoản");
            return false;
        }
        return true;
    }

    public static boolean checkTrung(String key, String data, String check) throws IOException, FileNotFoundException, ClassNotFoundException, Exception {
        String sql = "select " + key + " from " + data;
        try {
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            int i = 0;

            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase(check)) {
                    JOptionPane.showMessageDialog(null, check + " đã tồn tại!");
                    i++;
                    break;
                }
            }

            rs.close();
            return i == 0;

        } catch (HeadlessException | SQLException e) {
            throw new Exception(e);
        } finally {
            Util.disCon();
        }
    }
    public static boolean checkNullDangNhap(String user, String pass){
        if (user.equals("") || pass.equals("")){
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin đăng nhập !");
            return false;
        }
        else{
            return true;
        }
    }
    
}
