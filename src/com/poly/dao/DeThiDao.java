/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.UI.VaoThi;
import com.poly.connect.Util;
import com.poly.model.CauHoi;
import com.poly.model.DeThi;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author Tong Duy Hai
 */
public class DeThiDao {

    public static List<DeThi> getAll() {
        List<DeThi> list = new ArrayList<>();
        try {
            String sql = "select * from DeThi";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DeThi deThi = new DeThi();

                deThi.setMadethi(rs.getString(1));
                deThi.setMamon(rs.getString(2));
                deThi.setDanhsachmacauhoi(rs.getString(3).trim());
                deThi.setThoigian(rs.getInt(4));
                deThi.setDanhsachdapan(rs.getString(5).trim());
                list.add(deThi);

            }
            rs.close();
            return list;

        } catch (Exception e) {
            return null;
        } finally {
            Util.disCon();
        }
    }

    public static void save(DeThi t) {
        try {
            String sql = "insert into DeThi values (?,?,?,?,?)";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getMadethi());
            ps.setString(2, t.getMamon());
            ps.setString(3, t.getDanhsachmacauhoi());
            ps.setInt(4, t.getThoigian());
            ps.setString(5, t.getDanhsachdapan());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(DeThi t) {
        try {
            String sql = "update DeThi set MaDT = ?, MaMon = ?, DSMaCH = ?, ThoiGian = ?, DSDapAn = ? ";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getMadethi());
            ps.setString(2, t.getMamon());
            ps.setString(3, t.getDanhsachmacauhoi());
            ps.setInt(4, t.getThoigian());
            ps.setString(5, t.getDanhsachdapan());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String t) {
        try {
            String sql = "delete from DeThi where MaDT = '" + t + "'";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DeThi getDeThi(String MaDT) {
        try {
            String sql = "select * from DeThi where MaDT = '" + MaDT + "'";
            Connection con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DeThi deThi = new DeThi();
            while (rs.next()) {
                deThi.setMadethi(MaDT);
                deThi.setMamon(rs.getString(2));
                deThi.setDanhsachmacauhoi(rs.getString(3).trim());
                deThi.setThoigian(rs.getInt(4));
                deThi.setDanhsachdapan(rs.getString(5).trim());

            }
            return deThi;

        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<String> getListDeThi(String maMon) {
        ArrayList<String> array = new ArrayList<>();
        try {
            String sql = "select MaDT from DeThi where MaMon='" + maMon + "'";

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

    public static String getMaMon(String tenMon) {
        String maMon = null;
        String sql = "select maMon from MonHoc where tenMon=N'" + tenMon + "'";
        try {
            Connection con = Util.getConnection();
            PreparedStatement pss = con.prepareStatement(sql);
            ResultSet rs = pss.executeQuery();

            while (rs.next()) {
                maMon = rs.getString(1);

            }
            return maMon;
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<Object[]> getTenMon() {
        ArrayList<Object[]> arr = new ArrayList<>();
        String sql = "select TenMon,MaDT from MonHoc join DeThi on DeThi.MaMon = MonHoc.MaMon ";
        try {
            Connection con = Util.getConnection();
            PreparedStatement pss = con.prepareStatement(sql);
            ResultSet rs = pss.executeQuery();

            while (rs.next()) {
                arr.add(new Object[]{rs.getString(1), rs.getString(2)});

            }
            return arr;
        } catch (Exception e) {
            return null;
        }
    }

    public static void write(DeThi deThi, String tenMon) {
        FileOutputStream out = null;
        try {
            String[] dsCauHoi = deThi.getDanhsachmacauhoi().split(";");
            String[] dsDapAn = deThi.getDanhsachdapan().split(";");

//         Được sử dụng để đọc ghi các file MS-Word với định dạng .docx 
//         Create Blank document
            XWPFDocument document = new XWPFDocument();
//          định dạng đề thi
            createDocFormat(deThi, tenMon, document);
            out = new FileOutputStream(new File("demo.docx"));
            for (int i = 0; i < dsCauHoi.length; i++) {
                CauHoi ch = new CauHoi();
                ch.setCauhoi(CauHoiDao.getCauHoi(dsCauHoi[i]).getCauhoi());
                String da = dsDapAn[i];
                String a = da.substring(0, 1);
                String b = da.substring(1, 2);
                String c = da.substring(2, 3);
                String d = da.substring(3);

                ch.setDapan1(VaoThi.getIndexDapAn(Integer.parseInt(a), dsCauHoi[i]));
                ch.setDapan2(VaoThi.getIndexDapAn(Integer.parseInt(b), dsCauHoi[i]));
                ch.setDapan3(VaoThi.getIndexDapAn(Integer.parseInt(c), dsCauHoi[i]));
                ch.setDapan4(VaoThi.getIndexDapAn(Integer.parseInt(d), dsCauHoi[i]));
                writeQuestion(ch, document, i);
            }
            document.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File("demo.docx"));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void createDocFormat(DeThi d, String tenMon, XWPFDocument document) {

//        XWPFParagraph: Được dùng để tạo đoạn paragraph trong tài liệu word. (Tùy chỉnh căn lề, border)
        XWPFParagraph de = document.createParagraph();
//        XWPFRun: Được sử dụng thể thêm text vào paragraph (Tùy chỉnh font, size…)
        XWPFRun deRun = de.createRun();
        deRun.setBold(true);
        deRun.setText("ĐỀ THI TRẮC NGHIỆM");
        deRun.setFontSize(15);
        deRun.setFontFamily("Times New Roman");
        deRun.addBreak();
        XWPFRun mon = de.createRun();
        mon.setBold(true);
        mon.setText("Môn học: " + tenMon);
        mon.setFontSize(15);
        mon.setFontFamily("Times New Roman");
        mon.addBreak();
        XWPFRun time = de.createRun();
        time.setItalic(true);
        time.setText("Thời gian làm bài : " + (d.getThoigian() / 60) + " phút");
        time.setFontSize(15);
        time.setFontFamily("Times New Roman");
        time.addBreak();
        XWPFRun maDe = de.createRun();
        maDe.setBold(true);
        maDe.setText("Mã đề: " + d.getMadethi());
        maDe.setFontSize(15);
        maDe.setFontFamily("Times New Roman");
        maDe.addBreak();

//      Định dạng vào giữa
        de.setAlignment(ParagraphAlignment.CENTER);

        XWPFParagraph info = document.createParagraph();
        XWPFRun school = info.createRun();
        school.setBold(true);
        school.setFontSize(13);
        school.setFontFamily("Times New Roman");
        school.setText("Trường:. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .Lớp: . . . . . . . . . . . . . . . . . . . . . . . . ");
        school.addBreak();
        school.addBreak();

        XWPFRun ten = info.createRun();
        ten.setBold(true);
        ten.setFontSize(13);
        ten.setFontFamily("Times New Roman");
        ten.setText("Họ và tên:. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . Mã học sinh:. . . . . . . . . . . . . . . . . . . . ");
        ten.addBreak();

        info.setAlignment(ParagraphAlignment.LEFT);

    }

    public static void writeQuestion(CauHoi q, XWPFDocument document, int index) {

        XWPFParagraph question = document.createParagraph();
        XWPFRun q2 = question.createRun();
        q2.setBold(true);
        q2.setText("Câu " + (index + 1) + ". ");
        q2.setFontFamily("Times New Roman");
        q2.setColor("0000CC");
        XWPFRun questionRun = question.createRun();   //Nối một đoạn mới
        questionRun.setText(q.getCauhoi());
        questionRun.setFontSize(13);
        questionRun.setFontFamily("Times New Roman");

        XWPFParagraph answ1 = document.createParagraph();
        XWPFRun a1Run = answ1.createRun();
        a1Run.setBold(true);
        a1Run.setText("        A. ");
        a1Run.setFontFamily("Times New Roman");
        XWPFRun answ1Run = answ1.createRun();
        answ1Run.setText(q.getDapan1());
        answ1Run.setFontSize(13);
        answ1Run.setFontFamily("Times New Roman");

        XWPFParagraph answ2 = document.createParagraph();
        XWPFRun a2Run = answ2.createRun();
        a2Run.setBold(true);
        a2Run.setText("        B. ");
        a2Run.setFontFamily("Times New Roman");
        XWPFRun answ2Run = answ2.createRun();
        answ2Run.setText(q.getDapan2());
        answ2Run.setFontSize(13);
        answ2Run.setFontFamily("Times New Roman");

        XWPFParagraph answ3 = document.createParagraph();
        XWPFRun a3Run = answ3.createRun();
        a3Run.setBold(true);
        a3Run.setText("        C. ");
        a3Run.setFontFamily("Times New Roman");
        XWPFRun answ3Run = answ3.createRun();
        answ3Run.setText(q.getDapan3());
        answ3Run.setFontSize(13);
        answ3Run.setFontFamily("Times New Roman");

        XWPFParagraph answ4 = document.createParagraph();
        XWPFRun a4Run = answ4.createRun();
        a4Run.setBold(true);
        a4Run.setText("        D. ");
        a4Run.setFontFamily("Times New Roman");
        XWPFRun answ4Run = answ4.createRun();
        answ4Run.setText(q.getDapan4());
        answ4Run.setFontSize(13);
        answ4Run.setFontFamily("Times New Roman");

    }
}
