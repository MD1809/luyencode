package com.example.quanly_thuvien;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
public class dangkyController {
    @FXML
    private TextField dangky_mathanhvien;
    @FXML
    private TextField dangky_diachi;
    @FXML
    private TextField dangky_email;
    @FXML
    private TextField dangky_gioitinh;
    @FXML
    private TextField dangky_matkhau;
    @FXML
    private TextField dangky_tennguoidung;
    @FXML
    private TextField dangky_tentaikhoa;
    @FXML
    private Button dangky_thoat;
    @FXML
    private Label dangky_thongbao;
    @FXML
    private void dangky_thanhvienController() {
        // Lấy dữ liệu từ các trường TextField
        String tenNguoiDung = dangky_tennguoidung.getText();
        String tenTaiKhoan = dangky_tentaikhoa.getText();
        String matKhau = dangky_matkhau.getText();
        String gioiTinh = dangky_gioitinh.getText();
        String email = dangky_email.getText();
        String diaChi = dangky_diachi.getText();
        String maThanhVien = dangky_mathanhvien.getText();
        String vaitro = "thành viên";
        // Kiểm tra dữ liệu đầu vào
        if (tenNguoiDung.isEmpty() || tenTaiKhoan.isEmpty() || matKhau.isEmpty() || gioiTinh.isEmpty() || email.isEmpty() || diaChi.isEmpty() || maThanhVien.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }
        boolean mathanhviendatontai = false;
        boolean taikhoandatontai = false;
        boolean tennguoidungdatontai = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("taikhoan.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dulieu = line.split(",");
                if (dulieu.length == 8) {
                    String taikhoan = dulieu[0];
                    String tennguoidung = dulieu[3];
                    String mathanhvien = dulieu[7];
                    if (maThanhVien.equals(mathanhvien)) {
                        mathanhviendatontai = true;
                    }
                    if(tenTaiKhoan.equalsIgnoreCase(taikhoan)){
                        taikhoandatontai = true;
                    }
                    if(tenNguoiDung.equalsIgnoreCase(tennguoidung)){
                        tennguoidungdatontai = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mathanhviendatontai){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Mã thành viên đã có người sử dụng.");
            alert.showAndWait();
            return;
        }
        if(taikhoandatontai){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản đã có người sử dung.");
            alert.showAndWait();
            return;
        }
        if(tennguoidungdatontai){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Tên người dùng đã có người sử dụng.");
            alert.showAndWait();
            return;
        }
        // Lưu thông tin tài khoản vào file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("taikhoan.txt", true))) {
            writer.write(tenTaiKhoan + "," + matKhau + "," + vaitro + "," + tenNguoiDung + "," + gioiTinh + "," + email + "," + diaChi + "," + maThanhVien);
            writer.newLine();
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle(null);
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Đăng ký tài khoản thành công!");
            infoAlert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void dangky_thoatController(){
        Stage stage = (Stage) dangky_thoat.getScene().getWindow();
        stage.close();
    }
}
