/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.connect.Util;
import com.poly.dao.CauHoiDao;
import com.poly.dao.MonHocDao;
import com.poly.model.CauHoi;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.DataBindingException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author Tong Duy Hai
 */
public class QuanLyCauHoi1 extends javax.swing.JInternalFrame {

    ArrayList<CauHoi> qs = new ArrayList<>();
    int index;
    DefaultTableModel model;

    /**
     * Creates new form QuanLyCauHoi
     */
    public QuanLyCauHoi1() {
            initComponents();
            LoadingData();
            addCBBMon();

    }

    private void LoadingData() {
        qs = (ArrayList<CauHoi>) CauHoiDao.getAll();
        model = (DefaultTableModel) tblQuestion.getModel();
        model.setRowCount(0);
        for (CauHoi ch : qs) {
            model.addRow(new Object[]{ch.getMacauhoi(), ch.getMucdo(), ch.getMamon(), ch.getCauhoi(), ch.getDapan1(), ch.getDapan2(), ch.getDapan3(), ch.getDapan4(), ch.getDapandung()});
        }
    }

    //validate cau hoi
    private boolean checkNull() {
        if (txtAreaQuestion.getText().equals("") || txtDapAnA.getText().equals("") || txtDapAnB.getText().equals("") || txtDapAnC.getText().equals("") || txtDapAnD.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đủ thông tin!");
            return false;
        } else {
            return true;
        }
    }

    private void clear() {
        txtAreaQuestion.setText("");
        txtDapAnA.setText("");
        txtDapAnB.setText("");
        txtDapAnC.setText("");
        txtDapAnD.setText("");
        cbbLeve.setSelectedIndex(0);
        cbbMon.setSelectedIndex(0);
//        cbbDapAnDung.setSelectedIndex(0);
        tblQuestion.clearSelection();
    }

    private void ShowInfor() {
        CauHoi ch = qs.get(index);
        int selectRow = tblQuestion.getSelectedRow();
        String cbbMucdo = model.getValueAt(selectRow, 1).toString();
        for (int i = 0; i < this.cbbLeve.getItemCount(); i++) {
            if (this.cbbLeve.getItemAt(i).toString().equalsIgnoreCase(cbbMucdo)) {
                cbbLeve.setSelectedIndex(i);
            }
        }
        String cbbMon = model.getValueAt(selectRow, 2).toString();
        if(cbbMon.equals("toan")){
            this.cbbMon.setSelectedIndex(2);
        }
        if(cbbMon.equals("ly")){
            this.cbbMon.setSelectedIndex(1);
        }
        if(cbbMon.equals("hoa")){
            this.cbbMon.setSelectedIndex(0);
        }
//        for (int i = 0; i < this.cbbMon.getItemCount(); i++) {
//            if (this.cbbMon.getItemAt(i).toString().equalsIgnoreCase(cbbMon)) {
//                this.cbbMon.setSelectedIndex(i);
//            }
//        }
        txtAreaQuestion.setText(ch.getCauhoi());
        txtDapAnA.setText(ch.getDapan1());
        txtDapAnB.setText(ch.getDapan2());
        txtDapAnC.setText(ch.getDapan3());
        txtDapAnD.setText(ch.getDapan4());
    }

    private void addCBBMon() {
        cbbMon.removeAllItems();
        try {
            String sql = "select TenMon from MonHoc";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String TenMon = rs.getString("TenMon");
                cbbMon.addItem(TenMon);
            }
        } catch (Exception e) {

        }
    }

    private boolean themCauHoi() {
        try {
            if (checkNull() == true) {
                CauHoi ch = new CauHoi();
                ch.setMucdo(cbbLeve.getSelectedItem().toString());
                String tenMon = cbbMon.getSelectedItem().toString();
                String maMon = MonHocDao.getMaMonHoc(tenMon);
                ch.setMamon(maMon);
                ch.setCauhoi(txtAreaQuestion.getText());
                ch.setDapan1(txtDapAnA.getText());
                ch.setDapan2(txtDapAnB.getText());
                ch.setDapan3(txtDapAnC.getText());
                ch.setDapan4(txtDapAnD.getText());
//                ch.setDapandung(cbbDapAnDung.getSelectedItem().toString());

                CauHoiDao.save(ch);
                addCBBMon();
                LoadingData();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean update() {
        index = tblQuestion.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn câu hỏi để sửa!");
            return false;
        } else {
            try {
                if (checkNull() == true) {
                    CauHoi ch = new CauHoi();
                    int selectRow = tblQuestion.getSelectedRow();
                    String MaCH = tblQuestion.getValueAt(selectRow, 0).toString();
                    String tenMon = cbbMon.getSelectedItem().toString();
                    String maMon = MonHocDao.getMaMonHoc(tenMon);
                    ch.setMamon(maMon);
                    ch.setCauhoi(txtAreaQuestion.getText());
                    ch.setDapan1(txtDapAnA.getText());
                    ch.setDapan2(txtDapAnB.getText());
                    ch.setDapan3(txtDapAnC.getText());
                    ch.setDapan4(txtDapAnD.getText());
//                    ch.setDapandung(cbbDapAnDung.getSelectedItem().toString());
                    ch.setMacauhoi(MaCH);
                    ch.setMucdo(cbbLeve.getSelectedItem().toString());
                    CauHoiDao.update(ch);
                    addCBBMon();
                    LoadingData();
                    Util.changeLog(LoGin.userr, "Sửa câu hỏi", tblQuestion.getValueAt(selectRow, 0).toString());
                    clear();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    private boolean xoa() {
       
        index = tblQuestion.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn câu hỏi để xóa !");
            return false;
        }
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa câu hỏi này ?", "Xoa", JOptionPane.YES_NO_OPTION);
        if(chon == JOptionPane.YES_OPTION){
            try {
                CauHoi ch = qs.get(index);
                int selectRow = tblQuestion.getSelectedRow();
                String MaCH = tblQuestion.getValueAt(selectRow, 0).toString();
                CauHoiDao.delete(ch);
                LoadingData();
                Util.changeLog(LoGin.userr, "Xóa câu hỏi", MaCH);
                clear();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
        
    }

    private void layTuFile() {
        ArrayList<CauHoi> array = new ArrayList<CauHoi>();
        JFileChooser fileChooser = new JFileChooser();
        File folder = new File("/DeThi");
        if (!folder.exists()) {
            folder.mkdir();
        }
        fileChooser.setCurrentDirectory(folder); // quét lại danh sách file của nó từ thư mục hiện tại
        int rs = fileChooser.showOpenDialog(this);
        if (rs == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                XWPFDocument document = new XWPFDocument(new FileInputStream(file));
                XWPFWordExtractor extract = new XWPFWordExtractor(document);
                String content = extract.getText().split("<begin>")[1].split("<end>")[0];
//                String content = extract.getText();
                System.out.println(content);
                String[] list = content.split("<t>");
                System.out.println("=======");
                for (int i = 0; i < list.length; i++) {
                    String[] q_a = list[i].split(" ");

                    CauHoi question = new CauHoi();
                    question.setCauhoi(q_a[0]);
                    int len = q_a.length;
                    if (len == 5) {
                        for (int j = 0; j < len; j++) {
                            if (q_a[j].contains("*")) {
                                question.setDapan1(q_a[j].substring(q_a[j].indexOf("*") + 1));
                                question.setDapan2(q_a[(j + 1) % 4]);
                                question.setDapan3(q_a[(j + 2) % 4]);
                                question.setDapan4(q_a[(j + 3) % 4]);
                                String tenMon = cbbMon.getSelectedItem().toString();
                                String maMon = MonHocDao.getMaMonHoc(tenMon);
                                question.setMamon(maMon);
                                question.setMucdo(cbbLeve.getSelectedItem().toString());
                                CauHoiDao.save(question);
                                addCBBMon();
                                LoadingData();
                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
        btnAddQuestion = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbbLeve = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbbMon = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaQuestion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDapAnA = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDapAnC = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDapAnB = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtDapAnD = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblQuestion = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("ÔN THI ĐẠI HỌC KHỐI A");

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        btnAddQuestion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Add.png"))); // NOI18N
        btnAddQuestion.setText("Thêm câu hỏi");
        btnAddQuestion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddQuestion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddQuestion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddQuestionMouseClicked(evt);
            }
        });
        btnAddQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddQuestionActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/ic_add_test.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/ic_delete_test.png"))); // NOI18N
        btnDelete.setText("Xóa ");
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/ic_fix_test.png"))); // NOI18N
        btnUpdate.setText("Cập nhật");
        btnUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUpdate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setText("Mức độ");

        cbbLeve.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dễ", "Trung Bình", "Khó" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("Môn học");

        cbbMon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1" }));

        txtAreaQuestion.setColumns(20);
        txtAreaQuestion.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtAreaQuestion.setRows(5);
        jScrollPane2.setViewportView(txtAreaQuestion);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("Câu hỏi");

        txtDapAnA.setColumns(20);
        txtDapAnA.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtDapAnA.setRows(5);
        jScrollPane3.setViewportView(txtDapAnA);

        txtDapAnC.setColumns(20);
        txtDapAnC.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtDapAnC.setRows(5);
        jScrollPane4.setViewportView(txtDapAnC);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Đáp án");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("B");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("A");

        txtDapAnB.setColumns(20);
        txtDapAnB.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtDapAnB.setRows(5);
        jScrollPane5.setViewportView(txtDapAnB);

        txtDapAnD.setColumns(20);
        txtDapAnD.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtDapAnD.setRows(5);
        jScrollPane6.setViewportView(txtDapAnD);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("C");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setText("D");

        jPanel5.setBackground(new java.awt.Color(255, 204, 204));

        tblQuestion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã câu hỏi", "Mức độ", "Môn học", "Câu hỏi", "Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQuestion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQuestionMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblQuestion);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7)
                .addGap(28, 28, 28))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel3.setText("*Hãy để đáp án đúng vào vị trí đầu tiên");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbLeve, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbMon, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(470, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3)
                                    .addComponent(jScrollPane5)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbLeve, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbMon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(btnAddQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 20, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(621, 621, 621))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddQuestionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddQuestionMouseClicked

    }//GEN-LAST:event_btnAddQuestionMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (themCauHoi() == true) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            Util.changeLog(LoGin.userr, "Thêm câu hỏi", "");
        } else {
            JOptionPane.showMessageDialog(this, "Thất bại !");
        }
        clear();
        btnAddQuestion.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddQuestionActionPerformed
        clear();
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }//GEN-LAST:event_btnAddQuestionActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        if (xoa() == true) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        }
        clear();
        btnAddQuestion.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblQuestionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuestionMouseClicked
        index = tblQuestion.getSelectedRow();
        ShowInfor();
        if (evt.getClickCount() == 2 && index != 1) {
            tblQuestion.setSelectionMode(0);
        }
        btnAddQuestion.setEnabled(true);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);
    }//GEN-LAST:event_tblQuestionMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        if (update() == true) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");

        } else {
            JOptionPane.showMessageDialog(this, "Thất bại !");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddQuestion;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbbLeve;
    private javax.swing.JComboBox<String> cbbMon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable tblQuestion;
    private javax.swing.JTextArea txtAreaQuestion;
    private javax.swing.JTextArea txtDapAnA;
    private javax.swing.JTextArea txtDapAnB;
    private javax.swing.JTextArea txtDapAnC;
    private javax.swing.JTextArea txtDapAnD;
    // End of variables declaration//GEN-END:variables
}
