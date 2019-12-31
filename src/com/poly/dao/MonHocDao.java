/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.connect.Util;
import com.poly.model.MonHoc;
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
public class MonHocDao{

    public List<MonHoc> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void save(MonHoc t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(MonHoc t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void delete(MonHoc t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static String getMaMonHoc(String monHoc) throws IOException, FileNotFoundException, ClassNotFoundException {
        try {
            String sql = "select MaMon from MonHoc where TenMon like N'%" + monHoc + "'";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String maMon = "";
            if (rs.next()) {
                maMon = rs.getString(1);
                return maMon;
            }
        } catch (SQLException e) {
            return "";
        }
        return "";
    }

    
}
