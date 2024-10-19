package com.example.quanly_thuvien;

import database.databaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class doimatkhauController {
    @FXML
    private Button dmk_thoat;
    @FXML
    private Label dmk_thongbao;
    @FXML
    private TextField mk_cuText;
    @FXML
    private TextField mk_moiText;

    private String taikhoandn;
    private String matkhaudn;

    public void taikhoanmatkhau(String taikhoan, String matkhau) {
        this.taikhoandn = taikhoan;
        this.matkhaudn = matkhau;
    }
    public String getTaiKhoan() {
        return taikhoandn;
    }
    public String getMatKhau() {
        return matkhaudn;
    }

    @FXML
    public void doimatkhau() {
        if (mk_cuText.getText().isEmpty() || mk_moiText.getText().isEmpty()) {
            dmk_thongbao.setText("Điền đầy đủ thông tin.");
            return;
        }

        databaseConnection connect = new databaseConnection();
        Connection connecdb = connect.getConnection();

        String matkhau = "SELECT matkhau FROM nguoidung WHERE tentaikhoan = ? AND matkhau = ?";
        String capnhatmatkhau = "UPDATE nguoidung SET matkhau = ? WHERE tentaikhoan = ? AND matkhau = ?";

        try {
            PreparedStatement preparedStatement = connecdb.prepareStatement(matkhau);
            preparedStatement.setString(1, taikhoandn);
            preparedStatement.setString(2, matkhaudn);

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                if (!queryResult.getString("matkhau").equals(mk_cuText.getText())) {
                    dmk_thongbao.setText("Sai mật khẩu.");
                    return;
                } else {
                    PreparedStatement preparedStatement1 = connecdb.prepareStatement(capnhatmatkhau);
                    preparedStatement1.setString(1, mk_moiText.getText());
                    preparedStatement1.setString(2, taikhoandn);
                    preparedStatement1.setString(3, matkhaudn);

                    int rowsAffected = preparedStatement1.executeUpdate();

                    if (rowsAffected > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Đổi mật khẩu thành công.");
                        alert.showAndWait();
                    } else {
                        dmk_thongbao.setText("Không thể cập nhật mật khẩu.");
                    }

                    preparedStatement1.close();
                }
            } else {
                dmk_thongbao.setText("Sai mật khẩu.");
            }

            queryResult.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connecdb != null) {
                    connecdb.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void thoat_dmk(){
        Stage stage = (Stage) dmk_thoat.getScene().getWindow();
        stage.close();
    }

}
