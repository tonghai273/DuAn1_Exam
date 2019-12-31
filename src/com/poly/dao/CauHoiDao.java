/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.connect.Util;
import com.poly.model.CauHoi;
import com.poly.model.TaiKhoan;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tong Duy Hai
 */
public class CauHoiDao {

    public static List<CauHoi> getAll() {
        String sql = "select * from CauHoi";
        List<CauHoi> ls = new ArrayList<>();
        try {
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CauHoi ch = new CauHoi();
                ch.setMacauhoi(rs.getString(1));
                ch.setCauhoi(rs.getString(2));
                ch.setMamon(rs.getString(3));
                ch.setMucdo(rs.getString(4));
                ch.setDapan1(rs.getString(5));
                ch.setDapan2(rs.getString(6));
                ch.setDapan3(rs.getString(7));
                ch.setDapan4(rs.getString(8));
                ls.add(ch);
            }
            rs.close();
            return ls;
        } catch (Exception e) {
            return null;
        } finally {
            Util.disCon();
        }
    }

    public static void save(CauHoi ch) {
        try {
            String sql = "insert into CauHoi(CauHoi,MaMon,MucDo,DapAn1,DapAn2,DapAn3,DapAn4) values(?,?,?,?,?,?,?)";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ch.getCauhoi());
            ps.setString(2, ch.getMamon());
            ps.setString(3, ch.getMucdo());
            ps.setString(4, ch.getDapan1());
            ps.setString(5, ch.getDapan2());
            ps.setString(6, ch.getDapan3());
            ps.setString(7, ch.getDapan4());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(CauHoi ch) {
        String sql = "update CauHoi set MaMon =?, MucDo = ?, CauHoi = ?, DapAn1 = ?,DapAn2 = ?, DapAn3 = ?,DapAn4 = ?,DapAnDung = ? where MaCH =?";
        try {
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareCall(sql);

            ps.setString(1, ch.getMamon());
            ps.setString(2, ch.getMucdo());
            ps.setString(3, ch.getCauhoi());
            ps.setString(4, ch.getDapan1());
            ps.setString(5, ch.getDapan2());
            ps.setString(6, ch.getDapan3());
            ps.setString(7, ch.getDapan4());
            ps.setString(8, ch.getDapandung());
            ps.setString(9, ch.getMacauhoi());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Util.disCon();
        }
    }

    public static void delete(CauHoi ch) {
        String sql = "delete CauHoi where MaCH = ?";
        try {
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ps.setString(1, ch.getMacauhoi());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.disCon();
        }
    }

    public static CauHoi getCauHoi(String maCauHoi) throws IOException, FileNotFoundException, ClassNotFoundException {
        try {
            String sql = "select * from CauHoi where MaCH ='" + maCauHoi + "'";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            CauHoi q = new CauHoi();
            if (rs.next()) {
                q.setMacauhoi(maCauHoi);
                q.setCauhoi(rs.getString(2));
                q.setMamon(rs.getString(3));
                q.setMucdo(rs.getString(4));
                q.setDapan1(rs.getString(5));
                q.setDapan2(rs.getString(6));
                q.setDapan3(rs.getString(7));
                q.setDapan4(rs.getString(8));
            }

            return q;
        } catch (SQLException e) {

            return null;
        }
    }

//    Bình
//    private static CauHoi getCauHoi(ResultSet rs) throws SQLException{
//        String maCauHoi = rs.getString(1);
//        String maMon = rs.getString(2);
//        String mucDo = rs.getString(3);
//        String cauhoi = rs.getString(4);
//        String dapAn1 = rs.getString(5);
//        String dapAn2 = rs.getString(6);
//        String dapAn3 = rs.getString(7);
//        String dapAn4 = rs.getString(8);
//        String dapAnDung = rs.getString(9);
//        
//        CauHoi ch = new CauHoi();
//        ch.setMacauhoi(maCauHoi);
//        ch.setMucdo(mucDo);
//        ch.setMamon(maMon);
//        ch.setCauhoi(cauhoi);
//        ch.setDapan1(dapAn1);
//        ch.setDapan2(dapAn2);
//        ch.setDapan3(dapAn3);
//        ch.setDapan4(dapAn4);
//        ch.setDapandung(dapAnDung);
//         return ch;
//    }
//    
//    public static ArrayList <CauHoi> getAll() throws IOException, FileNotFoundException, ClassNotFoundException {
//        String sql = "Select * from CauHoi";
//
//        try {
//            Connection con = Util.getConnection();
//            PreparedStatement ps = con.prepareCall(sql);
//            ResultSet rs = ps.executeQuery();
//            ArrayList <CauHoi> ch = new ArrayList<>();
//            while(rs.next()){
//                ch.add(getCauHoi(rs));
//            }
//            rs.close();
//            return ch;
//        } catch (SQLException e) {
//        }
//        finally{
//            Util.disCon();
//        }
//     return null;
//        
//    }
//    public static void Save(CauHoi ch) throws IOException, FileNotFoundException, ClassNotFoundException {
//        String sql = "insert into CauHoi (MaMon,MucDo,Question,DapAn1,DapAn2,DapAn3,DapAn4,DapAnDung) values (?,?,?,?,?,?,?,?)";
//        try {
//            Connection con = Util.getConnection();
//            PreparedStatement ps = con.prepareCall(sql);
//            ps.setString(1, ch.getMamon());
//            ps.setString(2, ch.getMucdo());
//            ps.setString(3, ch.getCauhoi());
//            ps.setString(4, ch.getDapan1());
//            ps.setString(5, ch.getDapan2());
//            ps.setString(6, ch.getDapan3());
//            ps.setString(7, ch.getDapan4());
//            ps.setString(8, ch.getDapandung());
//
//            ps.execute();
//
//        } catch (SQLException e) {
//        } finally {
//            Util.disCon();
//        }
//    }
//
//    public static void Update(CauHoi ch) throws IOException, FileNotFoundException, ClassNotFoundException {
//        String sql = "update CauHoi set MaMon =?, MucDo = ?, Question = ?, DapAn1 = ?,DapAn2 = ?, DapAn3 = ?,DapAn4 = ?,DapAnDung = ? where MaCH =?";
//        try {
//            Connection con = Util.getConnection();
//            PreparedStatement ps = con.prepareCall(sql);
//
//            ps.setString(1, ch.getMamon());
//            ps.setString(2, ch.getMucdo());
//            ps.setString(3, ch.getCauhoi());
//            ps.setString(4, ch.getDapan1());
//            ps.setString(5, ch.getDapan2());
//            ps.setString(6, ch.getDapan3());
//            ps.setString(7, ch.getDapan4());
//            ps.setString(8, ch.getDapandung());
//            ps.setString(9, ch.getMacauhoi());
//
//            ps.executeUpdate();
//
//        } catch (Exception e) {
//            System.out.println(e);
//        } finally {
//            Util.disCon();
//        }
//
//    }
//
//    public static void Delete(CauHoi ch) throws IOException, FileNotFoundException, ClassNotFoundException {
//        String sql = "delete CauHoi where MaCH = ?";
//        Connection con = Util.getConnection();
//        try {
//            PreparedStatement ps = con.prepareCall(sql);
//
//            ps.setString(1, ch.getMacauhoi());
//
//            ps.executeUpdate();
//        } catch (Exception e) {
//        } finally {
//            Util.disCon();
//        }
//    }
//
//    public static String getMaMonHoc(String monHoc) throws IOException, FileNotFoundException, ClassNotFoundException {
//        try {
//            String sql = "select MaMon from MonHoc where TenMon like N'%" + monHoc + "'";
//            Connection con = Util.getConnection();
//            PreparedStatement ps = con.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            String maMon = "";
//            if (rs.next()) {
//                maMon = rs.getString(1);
//                return maMon;
//            }
//        } catch (SQLException e) {
//            return "";
//        }
//        return "";
//    }
}
