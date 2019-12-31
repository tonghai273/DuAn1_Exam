/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.connect.Util;
import com.poly.dao.DeThiDao;
import com.poly.dao.MonHocDao;
import com.poly.model.TaiKhoan;
import com.poly.model.Xuat;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Tong Duy Hai
 */
public class ThongKe1 extends javax.swing.JInternalFrame {

    static double diem;
    static String dx;
    static ArrayList<Xuat> list;

    /**
     * Creates new form ThongKe
     */
    public ThongKe1() {
        initComponents();
        cbbMon.removeAllItems();
        loadCbbMon();
        cbbMon.setSelectedIndex(0);
    }

    private void loadCbbMon() {
        try {
            String sql = "select TenMon from MonHoc";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> ls = new ArrayList<>();

            while (rs.next()) {
                ls.add(rs.getString(1));
            }

            rs.close();
            for (String s : ls) {
                cbbMon.addItem(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object[] getBangDiem(String maMon, String maDe) {
        String sql = " exec sp_BangDiem ?,? ";

        try {
            Connection con = Util.getConnection();

            PreparedStatement ps = con.prepareCall(sql);
            ps.setString(1, maMon);
            ps.setString(2, maDe);
            ResultSet rs = ps.executeQuery();

            int dem1 = 0, dem2 = 0, dem3 = 0, dem4 = 0, dem5 = 0;
            while (rs.next()) {
//                System.out.println(rs.getString(1)+rs.getString(2)+rs.getDouble(3));
                if (rs.getDouble(3) < 3) {
                    dem5++;
                } else if (rs.getDouble(3) < 5) {
                    dem4++;
                } else if (rs.getDouble(3) < 8) {
                    dem3++;
                } else if (rs.getDouble(3) < 10) {
                    dem2++;
                } else if (rs.getDouble(3) == 10) {
                    dem1++;
                }

            }
            Object[] ls = new Object[]{maDe, dem1, dem2, dem3, dem4, dem5};
            rs.close();
            return ls;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            Util.disCon();
        }
    }

    private static ArrayList<String> getListDeThi(String maMon) {
        ArrayList<String> array = new ArrayList<>();
        try {
            String sql = "select distinct KetQua.MaDT from KetQua join DeThi on DeThi.MaDT = KetQua.MaDT where MaMon='" + maMon + "'";

            Connection con = Util.getConnection();
            PreparedStatement pss = con.prepareStatement(sql);
            ResultSet rs = pss.executeQuery();
            while (rs.next()) {
                array.add(rs.getString(1));
            }

            return array;

        } catch (Exception e) {

            return null;
        }

    }

    private void diemTrungBinh(String maMon) {
        try {
            String sql = "select avg (KetQua.Diem) as TB from KetQua join DeThi on DeThi.MaDT = KetQua.MaDT where MaMon= '" + maMon + "'";
            Connection con = Util.getConnection();
            PreparedStatement pss = con.prepareStatement(sql);
            ResultSet rs = pss.executeQuery();
            if (rs.next()) {
                diem = rs.getDouble("TB");
                DecimalFormat df = new DecimalFormat("#.##");
                dx = df.format(diem);
                txtDiemTB.setText(dx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void xuat() throws FileNotFoundException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employees sheet");

        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);

        // 
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Mã đề thi");
        cell.setCellStyle(style);
        // 
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Môn học");
        cell.setCellStyle(style);
        // 
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Điểm 10");
        cell.setCellStyle(style);
        // 
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("8 đến 10");
        cell.setCellStyle(style);
        // 
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("5 đến 8");
        cell.setCellStyle(style);
        //
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("3 đến 5");
        cell.setCellStyle(style);
        //        
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Dưới 3");
        cell.setCellStyle(style);

        for (Xuat emp : list) {
            rownum++;
            row = sheet.createRow(rownum);
            // EmpNo (A)
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(emp.getMaDe());
            
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(emp.getMon());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(emp.getSo1());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(emp.getSo2());

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(emp.getSo3());

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(emp.getSo4());

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(emp.getSo5());
        }
        File file = new File("thongke.xls");
        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File("thongke.xls"));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbbMon = new javax.swing.JComboBox<>();
        txtDiemTB = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDiem = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();

        setClosable(true);
        setTitle("ÔN THI ĐẠI HỌC KHỐI A");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(238, 112, 82));
        jLabel1.setText("Chọn môn");

        cbbMon.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        cbbMon.setForeground(new java.awt.Color(238, 112, 82));
        cbbMon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toán", "Vật lý", "Hóa học" }));
        cbbMon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbMonItemStateChanged(evt);
            }
        });
        cbbMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMonActionPerformed(evt);
            }
        });

        txtDiemTB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiemTB.setForeground(new java.awt.Color(238, 112, 82));
        txtDiemTB.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(238, 112, 82));
        jLabel3.setText("Điểm trung bình");

        tblDiem.setForeground(new java.awt.Color(238, 112, 82));
        tblDiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã đề thi", "Điểm 10", "8 đến < 10", "5 đến < 8", "3 đến < 5", "Dưới 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDiem);

        jButton1.setText("Xuất file");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbbMon, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jButton1))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbMonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbMonItemStateChanged
        // TODO add your handling code here:
//        bangDiem();
    }//GEN-LAST:event_cbbMonItemStateChanged
    private void bangDiem() {
        try {
            String monHoc = cbbMon.getSelectedItem().toString();
            System.out.println(monHoc);
            String maMon = MonHocDao.getMaMonHoc(monHoc);
            System.out.println(maMon);
            diemTrungBinh(maMon);
            ArrayList<String> maDe = getListDeThi(maMon);
            ArrayList<Object[]> ls = new ArrayList<>();
            for (String s : maDe) {
                ls.add(getBangDiem(maMon, s));
            }
            DefaultTableModel model = (DefaultTableModel) tblDiem.getModel();
            model.setRowCount(0);
            for (int i = 0; i < ls.size(); i++) {
                model.addRow(ls.get(i));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void cbbMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMonActionPerformed
        // TODO add your handling code here:
        bangDiem();
    }//GEN-LAST:event_cbbMonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất thống kê ra Excel ?", "Xuất", JOptionPane.YES_NO_OPTION);
        if(chon== JOptionPane.YES_OPTION){
        try {
            String monHoc = cbbMon.getSelectedItem().toString();
            System.out.println(monHoc);
            String maMon = MonHocDao.getMaMonHoc(monHoc);
            ArrayList<String> maDe = getListDeThi(maMon);
            list = new ArrayList<>();
            for (String s : maDe) {
                String sql = " exec sp_BangDiem ?,? ";
                Connection con = Util.getConnection();
                PreparedStatement ps = con.prepareCall(sql);
                ps.setString(1, maMon);
                ps.setString(2, s);
                ResultSet rs = ps.executeQuery();

                int dem1 = 0, dem2 = 0, dem3 = 0, dem4 = 0, dem5 = 0;
                while (rs.next()) {
                    if (rs.getDouble(3) < 3) {
                        dem5++;
                    } else if (rs.getDouble(3) < 5) {
                        dem4++;
                    } else if (rs.getDouble(3) < 8) {
                        dem3++;
                    } else if (rs.getDouble(3) < 10) {
                        dem2++;
                    } else if (rs.getDouble(3) == 10) {
                        dem1++;
                    }
                }
                Xuat vec = new Xuat(s, monHoc, dem1, dem2, dem3, dem4, dem5, dx);
                list.add(vec);
            }
            xuat();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbbMon;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDiem;
    private javax.swing.JLabel txtDiemTB;
    // End of variables declaration//GEN-END:variables
}
