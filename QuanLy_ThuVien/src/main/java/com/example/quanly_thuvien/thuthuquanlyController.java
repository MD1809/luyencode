package com.example.quanly_thuvien;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
public class thuthuquanlyController implements Initializable {
    @FXML
    private Button tt_dangxuat;
    @FXML
    private TextField timkiemtensach;
    @FXML
    private TableView<Sach> Sachlist;
    @FXML
    private TableColumn<Sach, String> masach;
    @FXML
    private TableColumn<Sach, String> theloai;
    @FXML
    private TableColumn<Sach, String> tensach;
    @FXML
    private TableColumn<Sach, String> tacgia;
    @FXML
    private TableColumn<Sach, String> soluong;
    @FXML
    private TableColumn<Sach, String> tinhtrang;
    private ObservableList<Sach> ds_sach;
    void docdulieusach() throws IOException {
        try (BufferedReader list_sach = new BufferedReader(new FileReader("danhsachsach.txt"))) {
            {
                String line;
                while ((line = list_sach.readLine()) != null) {
                    String[] thongTin = line.split(",");
                    if (thongTin.length == 6) {
                        String maSach = thongTin[0];
                        String theLoai = thongTin[1];
                        String tenSach = thongTin[2];
                        String tenTacGia = thongTin[3];
                        int soLuongCon = Integer.parseInt(thongTin[4]);
                        String tinhTrang = thongTin[5];
                        Sach sach = new Sach(maSach, theLoai, tenSach, tenTacGia, soLuongCon, tinhTrang);
                        ds_sach.add(sach);
                    }
                }
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ds_sach = FXCollections.observableArrayList();
        try {
            docdulieusach();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Gán dữ liệu cho các cột trong bảng
        masach.setCellValueFactory(new PropertyValueFactory<>("maSach"));
        theloai.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        tensach.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tacgia.setCellValueFactory(new PropertyValueFactory<>("tenTacGia"));
        soluong.setCellValueFactory(new PropertyValueFactory<>("soLuongCon"));
        tinhtrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        Sachlist.setItems(ds_sach);
    }
    @FXML
    public void quanlysachcontroller() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemSuaXoasach.fxml"));
        Parent root = loader.load();
        Stage stage1 = new Stage();
        Scene scene = new Scene(root);
        stage1.setScene(scene);
        stage1.show();
    }
    @FXML
    public void timKiemSach() {
        String tenSachCanTim = timkiemtensach.getText();
        ObservableList<Sach> sach_tim = FXCollections.observableArrayList();
        boolean datimthay = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("danhsachsach.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] thongTin = line.split(",");
                if (thongTin.length == 6 && thongTin[2].equalsIgnoreCase(tenSachCanTim)) {
                    String maSach = thongTin[0];
                    String theLoai = thongTin[1];
                    String tenSach = thongTin[2];
                    String tenTacGia = thongTin[3];
                    int soLuongCon = Integer.parseInt(thongTin[4]);
                    String tinhTrang = thongTin[5];
                    Sach sach = new Sach(maSach, theLoai, tenSach, tenTacGia, soLuongCon, tinhTrang);
                    sach_tim.add(sach);
                    datimthay = true;
                    break;
                }
            }
            if(datimthay){
                Sachlist.setItems(sach_tim);
            }else{
                capnhatlaidulieu();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy sách!");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void capnhatlaidulieu() {
        ds_sach.clear();
        try {
            docdulieusach();
            Sachlist.setItems(ds_sach);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void tt_danhsachmuonController() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("thuthu_danhsachmuon.fxml"));
        Parent root = loader.load();
        Stage stage2 = new Stage();
        Scene scene = new Scene(root);
        stage2.setScene(scene);
        stage2.show();
    }
    public void tt_dangxuatController() throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất không?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dangnhap.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tt_dangxuat.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
