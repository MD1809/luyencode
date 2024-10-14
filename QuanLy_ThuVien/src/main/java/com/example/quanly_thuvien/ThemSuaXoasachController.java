package com.example.quanly_thuvien;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class ThemSuaXoasachController {
    @FXML
    private TextField masachText;
    @FXML
    private TextField soluongText;
    @FXML
    private TextField tacgiaText;
    @FXML
    private TextField tensachText;
    @FXML
    private TextField theloaiText;
    @FXML
    private TextField tinhtrangText;
    @FXML
    private Label thongbao;
    // đọc dữ liệu từ Text
    public Sach docsach() {
        Sach newsach = new Sach();
        newsach.setMaSach(masachText.getText());
        newsach.setTheLoai(theloaiText.getText());
        newsach.setTenSach(tensachText.getText());
        newsach.setTenTacGia(tacgiaText.getText());
        newsach.setSoLuongCon(Integer.parseInt(soluongText.getText()));
        newsach.setTinhTrang(tinhtrangText.getText());
        return newsach;
    }
    private boolean kiemTraDuLieu() {
        if (masachText.getText().isEmpty() || theloaiText.getText().isEmpty() || tensachText.getText().isEmpty() ||
                tacgiaText.getText().isEmpty() || soluongText.getText().isEmpty() || tinhtrangText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    public void themsach() {
        if (!kiemTraDuLieu()) {
            return;
        }
        Sach sach = docsach();
        List<String> danhSachCapNhat = new ArrayList<>();
        boolean dacosach = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("danhsachsach.txt"))) {
            String doc;
            while ((doc = reader.readLine()) != null) {
                String[] thongTin = doc.split(",");
                if (thongTin.length == 6) {
                    String maSach = thongTin[0];
                    String theLoai = thongTin[1];
                    String tenSach = thongTin[2];
                    String tenTacGia = thongTin[3];
                    int soLuongCon = Integer.parseInt(thongTin[4]);
                    String tinhTrang = thongTin[5];
                    if (maSach.equals(masachText.getText()) && theLoai.equals(theloaiText.getText())
                        && tenSach.equals(tensachText.getText()) && tenTacGia.equals(tacgiaText.getText())) {
                        soLuongCon += sach.getSoLuongCon();
                        tinhTrang = ( soLuongCon > 0) ? "có thể mượn" : "Hết sách";
                        danhSachCapNhat.add(maSach + "," + theLoai + "," + tenSach + "," + tenTacGia + "," + soLuongCon + "," + tinhTrang);
                        dacosach = true;
                    }else{
                        danhSachCapNhat.add(doc);
                    }
                }
            }
            if (dacosach) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("danhsachsach.txt"))) {
                    for (String sachCapNhat : danhSachCapNhat) {
                        writer.write(sachCapNhat);
                        writer.newLine();
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Sách đã tồn tại." + "\n"
                                            + "Cập nhật số lượng thành công");
                    alert.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try (FileWriter writer = new FileWriter("danhsachsach.txt", true)) {
                    writer.write(sach.getMaSach() + "," + sach.getTheLoai() + "," + sach.getTenSach() + ","
                            + sach.getTenTacGia() + "," + sach.getSoLuongCon() + "," + sach.getTinhTrang() + "\n");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Thêm sách thành công.");
                    alert.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void suaSach() {

        if (!kiemTraDuLieu()) {
            return;
        }

        Sach sachCanSua = docsach();
        StringBuilder newContent = new StringBuilder();
        boolean daTonTai = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("danhsachsach.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] thongTin = line.split(",");
                if (thongTin.length == 6) {
                    String maSach = thongTin[0];
                    if (maSach.equals(sachCanSua.getMaSach())) {
                        line = sachCanSua.getMaSach() + "," + sachCanSua.getTheLoai() + "," +
                                sachCanSua.getTenSach() + "," + sachCanSua.getTenTacGia() + "," +
                                sachCanSua.getSoLuongCon() + "," + sachCanSua.getTinhTrang();
                        daTonTai = true;
                    }
                }
                newContent.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter writer = new FileWriter("danhsachsach.txt")) {
            writer.write(newContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!daTonTai) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Sách không tồn tại trong hệ thống");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật thành công.");
            alert.showAndWait();
        }
    }
    public void xoaSach() {
        String masachcanxoa = masachText.getText();
        String soluongsachcanxoa = soluongText.getText();
        String theloaicanxoa = theloaiText.getText();
        String tensachcanxoa = tensachText.getText();
        String tentacgiacanxoa = tacgiaText.getText();

        if (!kiemTraDuLieu()) {
            return;
        }

        List<String> danhsachsaukhixoa = new ArrayList<>();
        boolean datimthaysachcanxoa = false;
        // Đọc file và kiểm tra từng dòng
        try (BufferedReader reader = new BufferedReader(new FileReader("danhsachsach.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] thongTin = line.split(",");
                if (thongTin.length == 6) {
                    String maSach = thongTin[0];
                    String theLoai = thongTin[1];
                    String tenSach = thongTin[2];
                    String tenTacGia = thongTin[3];
                    int soLuongCon = Integer.parseInt(thongTin[4]);
                    if (maSach.equals(masachcanxoa) && theLoai.equals(theloaicanxoa) && tenSach.equals(tensachcanxoa) && tenTacGia.equals(tentacgiacanxoa)) {
                        if(soLuongCon < Integer.parseInt(soluongsachcanxoa)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(null);
                            alert.setHeaderText(null);
                            alert.setContentText("Số lượng không đủ để xóa.");
                            alert.showAndWait();
                            return;
                        }else if(Integer.parseInt(soluongsachcanxoa) < soLuongCon){
                            int soLuongMoi = soLuongCon - Integer.parseInt(soluongsachcanxoa);
                            String tinhTrangMoi = (soLuongMoi > 0) ? "có thể mượn" : "Hết sách";
                            danhsachsaukhixoa.add(maSach + "," + theLoai + "," + tenSach + "," + tenTacGia + "," + soLuongMoi + "," + tinhTrangMoi);
                            datimthaysachcanxoa = true;
                        }else{
                            datimthaysachcanxoa = true;
                        }
                    }else{
                        danhsachsaukhixoa.add(line);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (datimthaysachcanxoa) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("danhsachsach.txt"))) {
                for (String sachconlai : danhsachsaukhixoa) {
                    writer.write(sachconlai);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Xóa sách thành công.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Sách không tồn tại trong hệ thống");
            alert.showAndWait();
        }
    }
}