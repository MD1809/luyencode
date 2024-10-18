package com.example.quanly_thuvien;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
public class dangnhapController {
    @FXML
    private Button dangnhap;
    @FXML
    private TextField tenvaitro;
    @FXML
    private TextField tentaikhoan;
    @FXML
    private PasswordField tenmatkhau;
    @FXML
    private Button thoatcontrol;

    private boolean kiemtra(String taikhoan, String matkhau, String vaitro) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("taikhoan.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(taikhoan) && parts[1].equalsIgnoreCase(matkhau) && parts[2].equals(vaitro)) {
                    return true;
                }
            }
        }
        return false;
    }
    @FXML
    void dangnhapcontrol() throws IOException {
        String vaitro = tenvaitro.getText();
        String taikhoan = tentaikhoan.getText();
        String matkhau = tenmatkhau.getText();
        if(kiemtra(taikhoan, matkhau, vaitro)){
            if(Objects.equals(vaitro, "admin")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("giaodienchinh_thuthu.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) dangnhap.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else if(Objects.equals(vaitro, "thành viên")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("giaodienchinh_thanhvien.fxml"));
                Parent root = loader.load();
                thanhvienController controller = loader.getController();
                controller.laytaikhoandangnhap(taikhoan, matkhau);
                Stage stage = (Stage) dangnhap.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi đăng nhập");
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản hoặc mật khẩu không đúng!");
            alert.showAndWait();
        }
    }
    @FXML
    void thoatcontrolaction() {
        Stage stage = (Stage) thoatcontrol.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void dangkytaikhoanController() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dangky.fxml"));
        Parent root = loader.load();
        Stage dangkytaikhoan = new Stage();
        Scene scene = new Scene(root);
        dangkytaikhoan.setScene(scene);
        dangkytaikhoan.show();
    }
}