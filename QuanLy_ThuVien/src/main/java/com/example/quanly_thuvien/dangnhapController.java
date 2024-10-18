package com.example.quanly_thuvien;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import database.databaseConnection;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class dangnhapController {
    @FXML
    private Button dangnhap;
    @FXML
    private TextField tentaikhoan;
    @FXML
    private PasswordField tenmatkhau;
    @FXML
    private Button thoatcontrol;


    @FXML
    void dangnhapControl() throws SQLException, IOException {

        databaseConnection connect = new databaseConnection();
        Connection connecdb = connect.getConnection();

        String kiemtrataikhoan = "SELECT tentaikhoan, matkhau, vaitro FROM nguoidung WHERE tentaikhoan = '" + tentaikhoan.getText() + "' and matkhau = '" + tenmatkhau.getText() + "'";

        try {
            Statement statement = connecdb.createStatement();
            ResultSet queryResult = statement.executeQuery(kiemtrataikhoan);
            if (queryResult.next()) {
                if (queryResult.getString(3).equals("admin")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("giaodienchinh_thuthu.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) dangnhap.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if (queryResult.getString(3).equals("thành viên")) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("giaodienchinh_thanhvien.fxml"));
                    Parent root = loader.load();
                    thanhvienController controller = loader.getController();
                    controller.thongtintaikhoan(tentaikhoan.getText(), tenmatkhau.getText());
                    Stage stage = (Stage) dangnhap.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    controller.hienthithongtin();
                    stage.show();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi đăng nhập");
                alert.setHeaderText(null);
                alert.setContentText("Tài khoản hoặc mật khẩu không đúng!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void thoatcontrolaction() {
        Stage stage = (Stage) thoatcontrol.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void dangkytaikhoanController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dangky.fxml"));
        Parent root = loader.load();
        Stage dangkytaikhoan = new Stage();
        Scene scene = new Scene(root);
        dangkytaikhoan.setScene(scene);
        dangkytaikhoan.show();
    }
}