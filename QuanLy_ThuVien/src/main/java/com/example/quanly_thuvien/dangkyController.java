package com.example.quanly_thuvien;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import database.databaseConnection;
import lop.*;

import java.sql.*;

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
        String tennguoidung = dangky_tennguoidung.getText();
        String tentaikhoan = dangky_tentaikhoa.getText();
        String matkhau = dangky_matkhau.getText();
        String gioitinh = dangky_gioitinh.getText();
        String email = dangky_email.getText();
        String diachi = dangky_diachi.getText();
        String manguoidung = dangky_mathanhvien.getText();
        String vaitro = "thành viên";
        // Kiểm tra dữ liệu đầu vào
        if (tentaikhoan.isEmpty() || matkhau.isEmpty() || manguoidung.isEmpty() || gioitinh.isEmpty() || email.isEmpty() || diachi.isEmpty() || tennguoidung.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }

        databaseConnection connect = new databaseConnection();
        Connection connecdb = connect.getConnection();
        String kiemtrataikhoan = "SELECT tentaikhoan, tennguoidung, manguoidung FROM nguoidung WHERE tentaikhoan = '" + tentaikhoan + "'";

        try {
            Statement statement = connecdb.createStatement();
            ResultSet queryResult = statement.executeQuery(kiemtrataikhoan);
            if (queryResult.next()) {
                if (queryResult.getString(1).equals(tentaikhoan)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Tài khoản đã có người sử dung.");
                    alert.showAndWait();
                } else if (queryResult.getString(2).equals(tennguoidung)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Tên người dùng đã có người sử dụng.");
                    alert.showAndWait();
                } else if (queryResult.getString(3).equals(manguoidung)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Mã thành viên đã có người sử dụng.");
                    alert.showAndWait();
                }
            } else {
                nguoidung thanhvienmoi = new nguoidung(tentaikhoan, matkhau, manguoidung, tennguoidung, vaitro, gioitinh, email, diachi);


                String themnguoidung = "INSERT INTO nguoidung (tentaikhoan,matkhau,manguoidung,tennguoidung,vaitro,gioitinh,email,diachi) VALUES (?,?,?,?,?,?,?,?)";
                Connection connection = connect.getConnection();
                PreparedStatement them = connection.prepareStatement(themnguoidung);
                them.setString(1, thanhvienmoi.getTaiKhoan());
                them.setString(2, thanhvienmoi.getMatKhau());
                them.setString(3, thanhvienmoi.getManguoidung());
                them.setString(4, thanhvienmoi.getTenNguoiDung());
                them.setString(5, thanhvienmoi.getVaitro());
                them.setString(6, thanhvienmoi.getGioiTinh());
                them.setString(7, thanhvienmoi.getEmail());
                them.setString(8, thanhvienmoi.getDiaChi());

                int dbthaydoi = them.executeUpdate();

                if (dbthaydoi > 0) {
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setTitle(null);
                    infoAlert.setHeaderText(null);
                    infoAlert.setContentText("Đăng ký tài khoản thành công!");
                    infoAlert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void dangky_thoatController(){
        Stage stage = (Stage) dangky_thoat.getScene().getWindow();
        stage.close();
    }
}
