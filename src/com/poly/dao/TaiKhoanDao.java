/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.connect.Util;
import com.poly.model.TaiKhoan;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tong Duy Hai
 */
public class TaiKhoanDao {

    public static List<TaiKhoan> getAll() {
        String sql = "select * from TaiKhoan";
        List<TaiKhoan> ls = new ArrayList<>();
        try {
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setUsername(rs.getString(1));
                tk.setPassword(rs.getString(2));
                tk.setName(rs.getString(3));
                tk.setRole(rs.getString(4));
                ls.add(tk);
            }
            rs.close();
            return ls;
        } catch (Exception e) {
            return null;
        } finally {
            Util.disCon();
        }
    }

    public static void save(TaiKhoan tk) {
        try {
            String sql = "insert into TaiKhoan(TenTaiKhoan,MatKhau,Ten,VaiTro) values (?,?,?,?)";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tk.getUsername());
            ps.setString(2, tk.getPassword());
            ps.setString(3, tk.getName());
            ps.setString(4, tk.getRole());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(TaiKhoan tk) {
        try {
            String sql = "update TaiKhoan set MatKhau=?,Ten=?,VaiTro=? where TenTaiKhoan = ? ";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tk.getPassword());
            ps.setString(2, tk.getName());
            ps.setString(3, tk.getRole());
            ps.setString(4, tk.getUsername());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(TaiKhoan tk) {
        try {
            String sql = "delete from TaiKhoan where TenTaiKhoan = ? ";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tk.getUsername());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteTKKQ(String tk) {
        try {
            String sql = "delete from KetQua where TenTaiKhoan = ? ";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tk);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<TaiKhoan> checkLog(String userName, String pass) {
        String sql = "select * from TaiKhoan where TenTaiKhoan = '" + userName + "' and MatKhau = '" + pass + "'";
        List<TaiKhoan> ls = new ArrayList<>();
        try {
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setUsername(rs.getString(1));
                tk.setPassword(rs.getString(2));
                tk.setName(rs.getString(3));
                tk.setRole(rs.getString(4));
                ls.add(tk);
            }
            rs.close();
            return ls;
        } catch (Exception e) {
            return null;
        } finally {
            Util.disCon();
        }
    }

    public static TaiKhoan getUser(String userName) throws IOException, FileNotFoundException, ClassNotFoundException {
        try {
            String sql = "select * from TaiKhoan where TenTaiKhoan ='" + userName + "'";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            TaiKhoan user = new TaiKhoan();
            while (rs.next()) {
                user.setUsername(rs.getString(1));
                user.setPassword(rs.getString(2));
                user.setName(rs.getString(3));
                user.setRole(rs.getString(4));
            }
            return user;

        } catch (SQLException e) {
            return null;

        }
    }

}
