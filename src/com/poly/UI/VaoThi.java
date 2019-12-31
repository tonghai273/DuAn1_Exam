/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.connect.Util;
import com.poly.dao.CauHoiDao;
import com.poly.dao.DeThiDao;
import com.poly.model.DeThi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.Timer;

/**
 *
 * @author Tong Duy Hai
 */
public class VaoThi extends javax.swing.JFrame {

    private Timer timmer;
    private int seconds;
    private long time = 1200;
    private int page = 0;
    private int trang = 1;
    private int tongPage;
    private int[] dsChon;
    private int[] dapAn;
    private String dsCauHoi;
    private String dsDapAn;
    public String maDe;
    public String name;
    public String user;
    private double diem;

    /**
     * Creates new form VaoThi
     */
    public VaoThi() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        btnNopbai.setEnabled(true);
        try {
            setDeThiInfo();
            setTimeProgress();
            timmer.start();
            int len = 15;
            tongPage = len / 5;
            dsChon = new int[len];
            for (int i = 0; i < len; i++) {
                dsChon[i] = -1;
            }
            if (page == 0) {
                btnPre.setEnabled(false);
            }
            set(page);
            pageNumb.setText("Page " + trang + "/3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDeThiInfo() throws IOException, FileNotFoundException, ClassNotFoundException {
        maDe = ChonMon.maDe;
        name = LoGin.name;
        user = LoGin.userr;
        lblTen.setText(name);
        lblTaiKhoan.setText(user);
        lblMonHoc.setText(ChonMon.monthi);
    }

    public String[] getDsCauHoi(String maDe) throws IOException, FileNotFoundException, ClassNotFoundException {
        DeThi rs = DeThiDao.getDeThi(maDe);
        dsCauHoi = rs.getDanhsachmacauhoi();
        System.out.println("Danh sách câu hỏi đây");
        System.out.println(dsCauHoi);
        String[] ds = dsCauHoi.split(";");
        return ds;
    }

    public String[] getDsDapAn(String maDe) throws IOException, FileNotFoundException, ClassNotFoundException {
        DeThi rs = DeThiDao.getDeThi(maDe);
        dsDapAn = rs.getDanhsachdapan();
        System.err.println("ds da: " + dsDapAn);
        String[] ds = dsDapAn.split(";");
        System.err.println("danh sách đáp án đây: " + ds);
        dapAn = new int[ds.length];
        System.err.println("đáp án đúng");
        for (int i = 0; i < ds.length; i++) {
            dapAn[i] = ds[i].indexOf("1");
            System.err.println(dapAn[i]);
        }
        return ds;

    }

//    set thời gian làm bài
    private void setTimeProgress() {
        timebar.setMaximum((int) time);
        long temp = time;
        timebar.setMinimum(0);
        timmer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (timebar.getValue() < temp) {

                    int h = (int) (time / 3600);
                    int m = (int) ((time % 3600) / 60);
                    int s = (int) ((time % 3600) % 60);
                    if (h > 0) {
                        if (m > 0) {
                            timebar.setString(h + " : " + m + " : " + s);
                        }
                    } else {
                        if (m > 0) {
                            timebar.setString(m + " : " + s);
                        } else {
                            timebar.setString("00 : " + s);
                        }
                    }
                    time--;
                    seconds++;
                    timebar.setValue(seconds);
                } else {
                    timmer.stop();
                    int show = JOptionPane.showConfirmDialog(null, "Bạn đã hết thời gian làm bài!", "Time up", 2);
                    if (show == 0) {
                        chamDiem();
                    }
                }
            }
        });
    }

//    đẩy câu hỏi và đáp án lên form
    public void set(int page) {
        try {
            String[] dsCH = getDsCauHoi(maDe);
            String[] dsDA = getDsDapAn(maDe);
            int index = page * 5;
            setQuestionContent(dsCH[index].trim(), index + 1, dsDA[index].trim(), questionID, questionContent, answ1, answ2, answ3, answ4);
            setQuestionContent(dsCH[index + 1].trim(), index + 2, dsDA[index + 1].trim(), questionID1, questionContent1, answ5, answ6, answ7, answ8);
            setQuestionContent(dsCH[index + 2].trim(), index + 3, dsDA[index + 2].trim(), questionID2, questionContent2, answ9, answ10, answ11, answ12);
            setQuestionContent(dsCH[index + 3].trim(), index + 4, dsDA[index + 3].trim(), questionID3, questionContent3, answ13, answ14, answ15, answ16);
            setQuestionContent(dsCH[index + 4].trim(), index + 5, dsDA[index + 4].trim(), questionID4, questionContent4, answ17, answ18, answ19, answ20);
            if (dsChon[index] == -1) {
                buttonGroup1.clearSelection();
            }
            if (dsChon[index + 1] == -1) {
                buttonGroup2.clearSelection();
            }
            if (dsChon[index + 2] == -1) {
                buttonGroup3.clearSelection();
            }
            if (dsChon[index + 3] == -1) {
                buttonGroup4.clearSelection();
            }
            if (dsChon[index + 4] == -1) {
                buttonGroup5.clearSelection();
            }
// ghi nhớ những đáp án đã chọn      
            if (dsChon[index] == 0) {
                answ1.setSelected(true);
            }
            if (dsChon[index] == 1) {
                answ2.setSelected(true);
            }
            if (dsChon[index] == 2) {
                answ3.setSelected(true);
            }
            if (dsChon[index] == 3) {
                answ4.setSelected(true);
            }
            if (dsChon[index + 1] == 0) {
                answ5.setSelected(true);
            }
            if (dsChon[index + 1] == 1) {
                answ6.setSelected(true);
            }
            if (dsChon[index + 1] == 2) {
                answ7.setSelected(true);
            }
            if (dsChon[index + 1] == 3) {
                answ8.setSelected(true);
            }
            if (dsChon[index + 2] == 0) {
                answ9.setSelected(true);
            }

            if (dsChon[index + 2] == 1) {
                answ10.setSelected(true);
            }

            if (dsChon[index + 2] == 2) {
                answ11.setSelected(true);
            }

            if (dsChon[index + 2] == 3) {
                answ12.setSelected(true);
            }

            if (dsChon[index + 3] == 0) {
                answ13.setSelected(true);
            }

            if (dsChon[index + 3] == 1) {
                answ14.setSelected(true);
            }

            if (dsChon[index + 3] == 2) {
                answ15.setSelected(true);
            }

            if (dsChon[index + 3] == 3) {
                answ16.setSelected(true);
            }

            if (dsChon[index + 4] == 0) {
                answ17.setSelected(true);
            }
            if (dsChon[index + 4] == 1) {
                answ18.setSelected(true);
            }
            if (dsChon[index + 4] == 2) {
                answ19.setSelected(true);
            }
            if (dsChon[index + 4] == 3) {
                answ20.setSelected(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // phương thức để đẩy câu hỏi lên
    public void setQuestionContent(String maCauHoi, int index, String dsDapAn, JLabel questionID,
            JLabel questionContent, JRadioButton answ1,
            JRadioButton answ2, JRadioButton answ3, JRadioButton answ4) {
        try {
            System.err.println("Mã câu hỏi: " + maCauHoi);
            String content = CauHoiDao.getCauHoi(maCauHoi).getCauhoi();
            System.err.println("Câu hỏi: ");
            System.err.println(content);
            questionID.setText("Câu : " + Integer.toString(index));
            questionContent.setText(content);

            String a = dsDapAn.substring(0, 1);
            String b = dsDapAn.substring(1, 2);
            String c = dsDapAn.substring(2, 3);
            String d = dsDapAn.substring(3);

            System.err.println("Thứ tự đáp án");
            System.err.println(a);
            System.err.println(b);
            System.err.println(c);
            System.err.println(d);

            System.err.println("Các đáp án: ");

            answ1.setText("A :  " + getIndexDapAn(Integer.parseInt(a), maCauHoi));
//            System.out.println(getIndexDapAn(Integer.parseInt(a), maCauHoi));
            answ2.setText("B :  " + getIndexDapAn(Integer.parseInt(b), maCauHoi));
//            System.out.println(getIndexDapAn(Integer.parseInt(b), maCauHoi));
            answ3.setText("C :  " + getIndexDapAn(Integer.parseInt(c), maCauHoi));
//            System.out.println(getIndexDapAn(Integer.parseInt(c), maCauHoi));
            answ4.setText("D :  " + getIndexDapAn(Integer.parseInt(d), maCauHoi));
//            System.out.println(getIndexDapAn(Integer.parseInt(d), maCauHoi));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIndexDapAn(int index, String maCauHoi) {
        try {
            if (index == 1) {
                String da = CauHoiDao.getCauHoi(maCauHoi).getDapan1();
                return da;
            }
            if (index == 2) {
                String da = CauHoiDao.getCauHoi(maCauHoi).getDapan2();
                return da;
            }
            if (index == 3) {
                String da = CauHoiDao.getCauHoi(maCauHoi).getDapan3();
                return da;
            }
            if (index == 4) {
                String da = CauHoiDao.getCauHoi(maCauHoi).getDapan4();
                return da;
            }
        } catch (Exception e) {

        }

        return null;
    }

    private void chamDiem() {
        double temp = 0.67;
        System.out.println("cong " + temp);
        for (int i = 0; i < dapAn.length; i++) {
            System.out.println("Dap an da chon " + dsChon[i]);
            System.out.println("Dap an " + dapAn[i]);
            if (dsChon[i] == dapAn[i]) {
                diem += temp;
                System.out.println(diem);
            }
        }
        System.out.println("diem " + diem);
        DecimalFormat df = new DecimalFormat("#.##");
        String dx = df.format(diem);
        diem = Double.parseDouble(dx);
        System.out.println("Diem dat dc: " + diem);

        JOptionPane.showMessageDialog(this, "Bạn đạt được : " + diem + " điểm");

        btnNopbai.setEnabled(false);
        optionDialog();
    }

    private void optionDialog() {
        String[] options = {"Tiếp tục", "Xem đáp án"};
        int x = JOptionPane.showOptionDialog(null, null, "Click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (x == 0) {
            luuKetQua(user, maDe, diem);
            VaoThi.this.setVisible(false);
            VaoThi.this.dispose();
            ChonMon hsHome = new ChonMon();
            hsHome.setLocationRelativeTo(null);
            hsHome.setVisible(true);
        }
        if (x == 1) {
            luuKetQua(user, maDe, diem);
            showDapAn(page, Color.red);
        }
    }

    private void showDapAn(int page, Color c) {
        int index = page * 5;
        if (dapAn[index] == 0) {
            answ1.setForeground(c);
        }
        if (dapAn[index] == 1) {
            answ2.setForeground(c);
        }
        if (dapAn[index] == 2) {
            answ3.setForeground(c);
        }
        if (dapAn[index] == 3) {
            answ4.setForeground(c);
        }
        if (dapAn[index + 1] == 0) {
            answ5.setForeground(c);
        }
        if (dapAn[index + 1] == 1) {
            answ6.setForeground(c);
        }
        if (dapAn[index + 1] == 2) {
            answ7.setForeground(c);
        }
        if (dapAn[index + 1] == 3) {
            answ8.setForeground(c);
        }
        if (dapAn[index + 2] == 0) {
            answ9.setForeground(c);
        }
        if (dapAn[index + 2] == 1) {
            answ10.setForeground(c);
        }
        if (dapAn[index + 2] == 2) {
            answ11.setForeground(c);
        }
        if (dapAn[index + 2] == 3) {
            answ12.setForeground(c);
        }
        if (dapAn[index + 3] == 0) {
            answ13.setForeground(c);
        }
        if (dapAn[index + 3] == 1) {
            answ14.setForeground(c);
        }
        if (dapAn[index + 3] == 2) {
            answ15.setForeground(c);
        }
        if (dapAn[index + 3] == 3) {
            answ16.setForeground(c);
        }
        if (dapAn[index + 4] == 0) {
            answ17.setForeground(c);
        }
        if (dapAn[index + 4] == 1) {
            answ18.setForeground(c);
        }
        if (dapAn[index + 4] == 2) {
            answ19.setForeground(c);
        }
        if (dapAn[index + 4] == 3) {
            answ20.setForeground(c);
        }
    }

    private void luuKetQua(String taikhoan, String madethi, double diem) {
        try {
            String sql = "insert into KetQua(TenTaiKhoan,MaDT,Diem) values(?,?,?)";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, taikhoan);
            ps.setString(2, madethi);
            ps.setDouble(3, diem);
            ps.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblMonHoc = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnNopbai = new javax.swing.JButton();
        timebar = new javax.swing.JProgressBar();
        jLabel5 = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTaiKhoan = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        answ1 = new javax.swing.JRadioButton();
        answ2 = new javax.swing.JRadioButton();
        answ3 = new javax.swing.JRadioButton();
        answ4 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        questionID = new javax.swing.JLabel();
        questionContent = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        answ5 = new javax.swing.JRadioButton();
        answ6 = new javax.swing.JRadioButton();
        answ7 = new javax.swing.JRadioButton();
        answ8 = new javax.swing.JRadioButton();
        jPanel10 = new javax.swing.JPanel();
        questionID1 = new javax.swing.JLabel();
        questionContent1 = new javax.swing.JLabel();
        panel3 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        answ9 = new javax.swing.JRadioButton();
        answ10 = new javax.swing.JRadioButton();
        answ11 = new javax.swing.JRadioButton();
        answ12 = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        questionID2 = new javax.swing.JLabel();
        questionContent2 = new javax.swing.JLabel();
        panel4 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        answ13 = new javax.swing.JRadioButton();
        answ14 = new javax.swing.JRadioButton();
        answ15 = new javax.swing.JRadioButton();
        answ16 = new javax.swing.JRadioButton();
        jPanel16 = new javax.swing.JPanel();
        questionID3 = new javax.swing.JLabel();
        questionContent3 = new javax.swing.JLabel();
        panel5 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        answ17 = new javax.swing.JRadioButton();
        answ18 = new javax.swing.JRadioButton();
        answ19 = new javax.swing.JRadioButton();
        answ20 = new javax.swing.JRadioButton();
        jPanel31 = new javax.swing.JPanel();
        questionID4 = new javax.swing.JLabel();
        questionContent4 = new javax.swing.JLabel();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        pageNumb = new javax.swing.JLabel();
        btnThoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ÔN THI ĐẠI HỌC KHỐI A");

        jPanel4.setBackground(new java.awt.Color(238, 112, 82));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Alarm.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Thời gian");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Môn học");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Tổng số câu");

        lblMonHoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMonHoc.setText("Toán");

        lblTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTime.setText("20 phút");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("15");

        btnNopbai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnNopbai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/ic_complete.png"))); // NOI18N
        btnNopbai.setText("Nộp bài");
        btnNopbai.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNopbai.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNopbai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNopbaiActionPerformed(evt);
            }
        });

        timebar.setBackground(new java.awt.Color(0, 204, 51));
        timebar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        timebar.setForeground(new java.awt.Color(0, 51, 204));
        timebar.setMaximum(0);
        timebar.setToolTipText("");
        timebar.setString("");
        timebar.setStringPainted(true);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tài khoản");

        lblTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTen.setText("jLabel6");
        lblTen.setVerifyInputWhenFocusTarget(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Họ tên");

        lblTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTaiKhoan.setText("jLabel10");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblTen, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(36, 36, 36)
                .addComponent(btnNopbai, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(timebar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(96, 96, 96))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timebar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTen, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNopbai, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        panel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        panel1.setAutoscrolls(true);

        buttonGroup1.add(answ1);
        answ1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ1ItemStateChanged(evt);
            }
        });
        answ1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                answ1MouseClicked(evt);
            }
        });
        answ1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(answ2);
        answ2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ2ItemStateChanged(evt);
            }
        });
        answ2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(answ3);
        answ3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ3ItemStateChanged(evt);
            }
        });
        answ3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(answ4);
        answ4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ4ItemStateChanged(evt);
            }
        });
        answ4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(answ2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answ3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answ4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answ1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(answ1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        questionID.setText("Câu 1:");

        questionContent.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(questionContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(questionContent, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addComponent(questionID))
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        buttonGroup2.add(answ5);
        answ5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ5ItemStateChanged(evt);
            }
        });
        answ5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ5ActionPerformed(evt);
            }
        });

        buttonGroup2.add(answ6);
        answ6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ6ItemStateChanged(evt);
            }
        });
        answ6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ6ActionPerformed(evt);
            }
        });

        buttonGroup2.add(answ7);
        answ7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ7ItemStateChanged(evt);
            }
        });
        answ7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ7ActionPerformed(evt);
            }
        });

        buttonGroup2.add(answ8);
        answ8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ8ItemStateChanged(evt);
            }
        });
        answ8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(answ6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answ7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answ8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answ5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(answ5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ8)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        questionID1.setText("Câu 1:");

        questionContent1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionID1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(questionContent1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(questionContent1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addComponent(questionID1))
        );

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        buttonGroup3.add(answ9);
        answ9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ9ItemStateChanged(evt);
            }
        });
        answ9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ9ActionPerformed(evt);
            }
        });

        buttonGroup3.add(answ10);
        answ10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ10ItemStateChanged(evt);
            }
        });
        answ10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ10ActionPerformed(evt);
            }
        });

        buttonGroup3.add(answ11);
        answ11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ11ItemStateChanged(evt);
            }
        });
        answ11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ11ActionPerformed(evt);
            }
        });

        buttonGroup3.add(answ12);
        answ12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ12ItemStateChanged(evt);
            }
        });
        answ12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(answ9)
                    .addComponent(answ10)
                    .addComponent(answ11)
                    .addComponent(answ12))
                .addGap(0, 1135, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(answ9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        questionID2.setText("Câu 1:");

        questionContent2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionID2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(questionContent2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(questionContent2, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addComponent(questionID2))
        );

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        buttonGroup4.add(answ13);
        answ13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ13ItemStateChanged(evt);
            }
        });
        answ13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ13ActionPerformed(evt);
            }
        });

        buttonGroup4.add(answ14);
        answ14.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ14ItemStateChanged(evt);
            }
        });
        answ14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ14ActionPerformed(evt);
            }
        });

        buttonGroup4.add(answ15);
        answ15.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ15ItemStateChanged(evt);
            }
        });
        answ15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ15ActionPerformed(evt);
            }
        });

        buttonGroup4.add(answ16);
        answ16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ16ItemStateChanged(evt);
            }
        });
        answ16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(answ13)
                    .addComponent(answ14)
                    .addComponent(answ15)
                    .addComponent(answ16))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(answ13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        questionID3.setText("Câu 1:");

        questionContent3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionID3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(questionContent3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(questionContent3, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addComponent(questionID3))
        );

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        buttonGroup5.add(answ17);
        answ17.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ17ItemStateChanged(evt);
            }
        });
        answ17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ17ActionPerformed(evt);
            }
        });

        buttonGroup5.add(answ18);
        answ18.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ18ItemStateChanged(evt);
            }
        });
        answ18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ18ActionPerformed(evt);
            }
        });

        buttonGroup5.add(answ19);
        answ19.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ19ItemStateChanged(evt);
            }
        });
        answ19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ19ActionPerformed(evt);
            }
        });

        buttonGroup5.add(answ20);
        answ20.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                answ20ItemStateChanged(evt);
            }
        });
        answ20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answ20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(answ17)
                    .addComponent(answ18)
                    .addComponent(answ19)
                    .addComponent(answ20))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(answ17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answ20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        questionID4.setText("Câu 1:");

        questionContent4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionID4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(questionContent4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(questionContent4, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addComponent(questionID4))
        );

        javax.swing.GroupLayout panel5Layout = new javax.swing.GroupLayout(panel5);
        panel5.setLayout(panel5Layout);
        panel5Layout.setHorizontalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel5Layout.setVerticalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel5);

        btnPre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/ic_previous.png"))); // NOI18N
        btnPre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPreMouseClicked(evt);
            }
        });
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/ic_next.png"))); // NOI18N
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNextMouseClicked(evt);
            }
        });
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        pageNumb.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        pageNumb.setForeground(new java.awt.Color(255, 0, 51));
        pageNumb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pageNumb.setText("Page 1");

        btnThoat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/ic_back.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnThoat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pageNumb, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(274, 274, 274)
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 952, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNext, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pageNumb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void answ1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ1ItemStateChanged
        // TODO add your handling code here:
        if (answ1.isSelected()) {
            dsChon[page * 5] = 0;
        }
    }//GEN-LAST:event_answ1ItemStateChanged

    private void answ1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_answ1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_answ1MouseClicked

    private void answ1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ1ActionPerformed

    private void answ2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ2ItemStateChanged
        // TODO add your handling code here:
        if (answ2.isSelected()) {
            dsChon[page * 5] = 1;
        }
    }//GEN-LAST:event_answ2ItemStateChanged

    private void answ2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ2ActionPerformed

    private void answ3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_answ3ItemStateChanged

    private void answ3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ3ActionPerformed
        // TODO add your handling code here:
        if (answ3.isSelected()) {
            dsChon[page * 5] = 2;
        }
    }//GEN-LAST:event_answ3ActionPerformed

    private void answ4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ4ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_answ4ItemStateChanged

    private void answ4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ4ActionPerformed
        // TODO add your handling code here:
        if (answ4.isSelected()) {
            dsChon[page * 5] = 3;
        }
    }//GEN-LAST:event_answ4ActionPerformed

    private void answ5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ5ItemStateChanged
        // TODO add your handling code here:
        if (answ5.isSelected()) {
            dsChon[page * 5 + 1] = 0;
        }
    }//GEN-LAST:event_answ5ItemStateChanged

    private void answ5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ5ActionPerformed

    private void answ6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ6ItemStateChanged
        // TODO add your handling code here:
        if (answ6.isSelected()) {
            dsChon[page * 5 + 1] = 1;
        }
    }//GEN-LAST:event_answ6ItemStateChanged

    private void answ6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ6ActionPerformed

    private void answ7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ7ItemStateChanged
        // TODO add your handling code here:
        if (answ7.isSelected()) {
            dsChon[page * 5 + 1] = 2;
        }
    }//GEN-LAST:event_answ7ItemStateChanged

    private void answ7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ7ActionPerformed

    private void answ8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ8ItemStateChanged
        // TODO add your handling code here:
        if (answ8.isSelected()) {
            dsChon[page * 5 + 1] = 3;
        }
    }//GEN-LAST:event_answ8ItemStateChanged

    private void answ8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ8ActionPerformed

    private void answ9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ9ItemStateChanged
        // TODO add your handling code here:
        if (answ9.isSelected()) {
            dsChon[page * 5 + 2] = 0;
        }
    }//GEN-LAST:event_answ9ItemStateChanged

    private void answ9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ9ActionPerformed

    private void answ10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ10ItemStateChanged
        // TODO add your handling code here:
        if (answ10.isSelected()) {
            dsChon[page * 5 + 2] = 1;
        }
    }//GEN-LAST:event_answ10ItemStateChanged

    private void answ10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ10ActionPerformed

    private void answ11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ11ItemStateChanged
        // TODO add your handling code here:
        if (answ11.isSelected()) {
            dsChon[page * 5 + 2] = 2;
        }
    }//GEN-LAST:event_answ11ItemStateChanged

    private void answ11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ11ActionPerformed

    private void answ12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ12ItemStateChanged
        // TODO add your handling code here:
        if (answ12.isSelected()) {
            dsChon[page * 5 + 2] = 3;
        }
    }//GEN-LAST:event_answ12ItemStateChanged

    private void answ12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ12ActionPerformed

    private void answ13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ13ItemStateChanged
        // TODO add your handling code here:
        if (answ13.isSelected()) {
            dsChon[page * 5 + 3] = 0;
        }
    }//GEN-LAST:event_answ13ItemStateChanged

    private void answ13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ13ActionPerformed

    private void answ14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ14ItemStateChanged
        // TODO add your handling code here:
        if (answ14.isSelected()) {
            dsChon[page * 5 + 3] = 1;
        }
    }//GEN-LAST:event_answ14ItemStateChanged

    private void answ14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ14ActionPerformed

    private void answ15ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ15ItemStateChanged
        // TODO add your handling code here:
        if (answ15.isSelected()) {
            dsChon[page * 5 + 3] = 2;
        }
    }//GEN-LAST:event_answ15ItemStateChanged

    private void answ15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ15ActionPerformed

    private void answ16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ16ItemStateChanged
        // TODO add your handling code here:
        if (answ16.isSelected()) {
            dsChon[page * 5 + 3] = 3;
        }
    }//GEN-LAST:event_answ16ItemStateChanged

    private void answ16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ16ActionPerformed

    private void answ17ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ17ItemStateChanged
        // TODO add your handling code here:
        if (answ17.isSelected()) {
            dsChon[page * 5 + 4] = 0;
        }
    }//GEN-LAST:event_answ17ItemStateChanged

    private void answ17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ17ActionPerformed

    private void answ18ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ18ItemStateChanged
        // TODO add your handling code here:
        if (answ18.isSelected()) {
            dsChon[page * 5 + 4] = 1;
        }
    }//GEN-LAST:event_answ18ItemStateChanged

    private void answ18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ18ActionPerformed

    private void answ19ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ19ItemStateChanged
        // TODO add your handling code here:
        if (answ19.isSelected()) {
            dsChon[page * 5 + 4] = 2;
        }
    }//GEN-LAST:event_answ19ItemStateChanged

    private void answ19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ19ActionPerformed

    private void answ20ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_answ20ItemStateChanged
        // TODO add your handling code here:
        if (answ20.isSelected()) {
            dsChon[page * 5 + 4] = 3;
        }
    }//GEN-LAST:event_answ20ItemStateChanged

    private void answ20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answ20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answ20ActionPerformed

    private void btnPreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPreMouseClicked

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        // TODO add your handling code here:
        if (btnPre.isEnabled()) {
            btnNext.setEnabled(true);
            trang--;
            page--;
            pageNumb.setText("Page " + trang + "/3");
            set(page);
            if (trang == 1) {
                btnPre.setEnabled(false);
                btnNext.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNextMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        if (btnNext.isEnabled()) {
            btnPre.setEnabled(true);
            trang++;
            page++;
            pageNumb.setText("Page " + trang + "/3");
            set(page);
            if (trang == 3) {
                btnPre.setEnabled(true);
                btnNext.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnNopbaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNopbaiActionPerformed
        // TODO add your handling code here:
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn nộp bài ?", "Nộp bài", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            timmer.stop();
            chamDiem();
        }
    }//GEN-LAST:event_btnNopbaiActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát ?", "Thoát", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            VaoThi.this.setVisible(false);
            VaoThi.this.dispose();
            ChonMon hsHome = new ChonMon();
            hsHome.setLocationRelativeTo(null);
            hsHome.setVisible(true);
        }

    }//GEN-LAST:event_btnThoatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VaoThi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VaoThi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VaoThi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VaoThi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VaoThi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton answ1;
    private javax.swing.JRadioButton answ10;
    private javax.swing.JRadioButton answ11;
    private javax.swing.JRadioButton answ12;
    private javax.swing.JRadioButton answ13;
    private javax.swing.JRadioButton answ14;
    private javax.swing.JRadioButton answ15;
    private javax.swing.JRadioButton answ16;
    private javax.swing.JRadioButton answ17;
    private javax.swing.JRadioButton answ18;
    private javax.swing.JRadioButton answ19;
    private javax.swing.JRadioButton answ2;
    private javax.swing.JRadioButton answ20;
    private javax.swing.JRadioButton answ3;
    private javax.swing.JRadioButton answ4;
    private javax.swing.JRadioButton answ5;
    private javax.swing.JRadioButton answ6;
    private javax.swing.JRadioButton answ7;
    private javax.swing.JRadioButton answ8;
    private javax.swing.JRadioButton answ9;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNopbai;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnThoat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMonHoc;
    private javax.swing.JLabel lblTaiKhoan;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel pageNumb;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel panel5;
    private javax.swing.JLabel questionContent;
    private javax.swing.JLabel questionContent1;
    private javax.swing.JLabel questionContent2;
    private javax.swing.JLabel questionContent3;
    private javax.swing.JLabel questionContent4;
    private javax.swing.JLabel questionID;
    private javax.swing.JLabel questionID1;
    private javax.swing.JLabel questionID2;
    private javax.swing.JLabel questionID3;
    private javax.swing.JLabel questionID4;
    private javax.swing.JProgressBar timebar;
    // End of variables declaration//GEN-END:variables
}
