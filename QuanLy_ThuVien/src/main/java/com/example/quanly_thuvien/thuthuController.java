package com.example.quanly_thuvien;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import database.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lop.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class thuthuController implements Initializable {

    @FXML
    private TextField cn_masach;

    @FXML
    private TextField cn_namxuatban;

    @FXML
    private TextField cn_tinhtrang;

    @FXML
    private TextField cn_soluong;

    @FXML
    private TextField cn_tensach;

    @FXML
    private TextField cn_tentacgia;

    @FXML
    private TextField cn_theloai;


    @FXML
    private TableView<Sach> Sachlist;

    @FXML
    private Button lammoi;

    @FXML
    private Button timkiemsach;

    @FXML
    private TextField timkiemtensach;

    @FXML
    private Button tt_dangxuat;

    @FXML
    private Button tt_danhsachmuon;

    @FXML
    private TableColumn<Sach, String> tt_masach;

    @FXML
    private TableColumn<Sach, Integer> tt_namxb;

    @FXML
    private Button tt_quanlysach;

    @FXML
    private TableColumn<Sach, Integer> tt_soluong;

    @FXML
    private TableColumn<Sach, String> tt_tacgia;

    @FXML
    private TableColumn<Sach, String> tt_tensach;

    @FXML
    private TableColumn<Sach, String> tt_theloai;

    @FXML
    private TableColumn<Sach, String> tt_tinhtrang;

    private ObservableList<Sach> ds_sach;

    @FXML
    public ComboBox<String> tt_combobox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Có thể mượn", "Đã có người mượn");
        ds_sach = FXCollections.observableArrayList();
        try {
            tt_combobox.setItems(list);
            docdulieusach();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Gán dữ liệu cho các cột trong bảng
        tt_masach.setCellValueFactory(new PropertyValueFactory<>("maSach"));
        tt_theloai.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        tt_tensach.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tt_tacgia.setCellValueFactory(new PropertyValueFactory<>("tenTacGia"));
        tt_namxb.setCellValueFactory(new PropertyValueFactory<>("namXuatBan"));
        tt_soluong.setCellValueFactory(new PropertyValueFactory<>("soLuongCon"));
        tt_tinhtrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        Sachlist.setItems(ds_sach);
    }

    public void docdulieusach() {
        thuvienDAO dulieucuasach = new thuvienDAO();
        try {
            List<Sach> danhSachSach = dulieucuasach.getAllsach();
            ds_sach = FXCollections.observableArrayList(danhSachSach);
            Sachlist.setItems(ds_sach);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void capnhatlaidulieu() {
        ds_sach.clear();
        try {
            docdulieusach();
            Sachlist.setItems(ds_sach);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void timKiemSach() {
        boolean datimthay = false;
        databaseConnection connect = new databaseConnection();
        Connection connecdb = connect.getConnection();

        String timsach = "SELECT * FROM sach WHERE tenSach LIKE ? ";

        try {
            PreparedStatement statement = connecdb.prepareStatement(timsach);
            statement.setString(1, timkiemtensach.getText());

            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                ds_sach.clear();
                String maSach = queryResult.getString("maSach");
                String theLoai = queryResult.getString("theLoai");
                String tenSach = queryResult.getString("tenSach");
                String tenTacGia = queryResult.getString("tenTacGia");
                int namXuatBan = queryResult.getInt("namXuatBan");
                int soLuongCon = queryResult.getInt("soLuongCon");
                String tinhTrang = queryResult.getString("tinhTrang");
                Sach sach = new Sach(maSach, theLoai, tenSach, tenTacGia, namXuatBan, soLuongCon, tinhTrang);
                ds_sach.add(sach);
                datimthay = true;
                break;
            }
            if (datimthay) {
                Sachlist.setItems(ds_sach);
            } else {
                capnhatlaidulieu();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy sách!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tt_danhsachmuonController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("thuthu_danhsachmuon.fxml"));
        Parent root = loader.load();
        Stage stage2 = new Stage();
        Scene scene = new Scene(root);
        stage2.setScene(scene);
        stage2.show();
    }

    public void tt_dangxuatController() throws IOException {
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

    // cn
    public void themsach(){
        String masach = cn_masach.getText();
        String theloai = cn_theloai.getText();
        String soluong = cn_soluong.getText();
        String tensach = cn_tensach.getText();
        String tentacgia = cn_tentacgia.getText();
        String namxuatban = cn_namxuatban.getText();
        String tinhtrang = tt_combobox.getValue();

        if(masach.isEmpty() || theloai.isEmpty() || soluong.isEmpty() || tensach.isEmpty() ||
                tentacgia.isEmpty() || namxuatban.isEmpty() || tinhtrang.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }

        databaseConnection connect = new databaseConnection();
        Connection connecdb = connect.getConnection();

        String tim = "SELECT tenSach, theLoai, tenTacGia, namXuatBan FROM sach WHERE maSach LIKE ?";
        boolean sachdatontai = false;
        try {
            PreparedStatement statement = connecdb.prepareStatement(tim);
            statement.setString(1, masach);
            ResultSet queryResult = statement.executeQuery();
            if(queryResult.next()){
                if(queryResult.getString(1).equals(tensach) && queryResult.getString(2).equals(theloai)
                        && queryResult.getString(3).equals(tentacgia) && queryResult.getString(4).equals(namxuatban)){
                    sachdatontai = true;
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Mã bị trùng.");
                    alert.showAndWait();
                    return;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            int namXB = Integer.parseInt(namxuatban);
            LocalDate nam = LocalDate.now();
            if(namXB > nam.getYear() || namXB < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("sai năm xuất bản");
                alert.showAndWait();
                return;
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("năm xuất bản phải là một số hợp lệ.");
            alert.showAndWait();
            return;
        }

        try{
            int intsoluong = Integer.parseInt(soluong);
            if(intsoluong < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("số lượng không thể âm.");
                alert.showAndWait();
                return;
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Số lượng phải là một số hợp lệ.");
            alert.showAndWait();
            return;
        }

        if (sachdatontai) {
            try {
                int soLuong = Integer.parseInt(soluong);
                String capnhatSoLuong = "UPDATE sach SET soLuongCon = soLuongCon + ? WHERE maSach = ?";
                try {
                    PreparedStatement capnhat = connecdb.prepareStatement(capnhatSoLuong);
                    capnhat.setInt(1, soLuong);
                    capnhat.setString(2, masach);
                    capnhat.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Sách đã tồn tại." + "\n"
                            + "Cập nhật số lượng thành công");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            String themSach = "INSERT INTO sach (maSach, theLoai, tenSach, tenTacGia, namXuatBan, soLuongCon) VALUES (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement insertStatement = connecdb.prepareStatement(themSach);
                // Đặt các giá trị cho sách mới
                insertStatement.setString(1, masach);
                insertStatement.setString(2, theloai);
                insertStatement.setString(3, tensach);
                insertStatement.setString(4, tentacgia);
                insertStatement.setInt(5, Integer.parseInt(namxuatban));
                insertStatement.setInt(6, Integer.parseInt(soluong));
                insertStatement.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Thêm sách thành công.");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void suasach(){
        String masach = cn_masach.getText();
        String theloai = cn_theloai.getText();
        String soluong = cn_soluong.getText();
        String tensach = cn_tensach.getText();
        String tentacgia = cn_tentacgia.getText();
        String namxuatban = cn_namxuatban.getText();
        String tinhtrang = tt_combobox.getValue();

        if(masach.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Mã sách trống");
            alert.showAndWait();
            return;
        }
        databaseConnection connect = new databaseConnection();
        Connection connecdb = connect.getConnection();

        String tim = "SELECT * FROM sach WHERE maSach LIKE ? ";
        boolean sachdatontai = false;
        try {
            PreparedStatement statement = connecdb.prepareStatement(tim);
            statement.setString(1, masach);
            ResultSet queryResult = statement.executeQuery();
            if(queryResult.next()){
                sachdatontai = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        if(!sachdatontai){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("không tìm thấy dữ liệu.");
            alert.showAndWait();
            return;
        }

        String suaSach1 = "UPDATE sach SET theLoai = ? WHERE maSach = ?";
        String suaSach2 = "UPDATE sach SET tenSach = ? WHERE maSach = ?";
        String suaSach3 = "UPDATE sach SET tenTacGia = ? WHERE maSach = ?";
        String suaSach4 = "UPDATE sach SET namXuatBan = ? WHERE maSach = ?";
        String suaSach5 = "UPDATE sach SET soLuongCon = ? WHERE maSach = ?";
        String suaSach6 = "UPDATE sach SET tinhTrang = ? WHERE maSach = ?";
        int capnhat = 0;
        try {
            PreparedStatement capnhat1 = connecdb.prepareStatement(suaSach1);
            PreparedStatement capnhat2 = connecdb.prepareStatement(suaSach2);
            PreparedStatement capnhat3 = connecdb.prepareStatement(suaSach3);
            PreparedStatement capnhat4 = connecdb.prepareStatement(suaSach4);
            PreparedStatement capnhat5 = connecdb.prepareStatement(suaSach5);
            PreparedStatement capnhat6 = connecdb.prepareStatement(suaSach6);

            if (!theloai.isEmpty()) {
                capnhat1.setString(1, theloai);
                capnhat1.setString(2, masach);
                capnhat1.executeUpdate();
                capnhat++;
            }
            if (!tensach.isEmpty()) {
                capnhat2.setString(1, tensach);
                capnhat2.setString(2, masach);
                capnhat2.executeUpdate();
                capnhat++;
            }
            if (!tentacgia.isEmpty()) {
                capnhat3.setString(1, tentacgia);
                capnhat3.setString(2, masach);
                capnhat3.executeUpdate();
                capnhat++;
            }
            if (!namxuatban.isEmpty()) {
                capnhat4.setString(1, namxuatban);
                capnhat4.setString(2, masach);
                capnhat4.executeUpdate();
                capnhat++;
            }
            if (!soluong.isEmpty()) {
                capnhat5.setString(1, soluong);
                capnhat5.setString(2, masach);
                capnhat5.executeUpdate();
                capnhat++;
            }
            if (!tinhtrang.isEmpty()) {
                capnhat6.setString(1, tinhtrang);
                capnhat6.setString(2, masach);
                capnhat6.executeUpdate();
                capnhat++;
            }

        }catch (Exception  e){
            e.printStackTrace();
        }
        if(capnhat > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật thành công.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật thất bại");
            alert.showAndWait();
        }
    }

    public void xoasach(){
        String masach = cn_masach.getText();

        databaseConnection connect = new databaseConnection();
        Connection connecdb = connect.getConnection();

        String tim = "SELECT * FROM sach WHERE maSach = ? ";
        boolean sachdatontai = false;
        try {
            PreparedStatement statement = connecdb.prepareStatement(tim);
            statement.setString(1, masach);
            ResultSet queryResult = statement.executeQuery();
            if(queryResult.next()){
                sachdatontai = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        if(!sachdatontai){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("không tìm thấy dữ liệu.");
            alert.showAndWait();
            return;
        }

        String kiemtra = "SELECT COUNT(*) AS soluongnguoimuon FROM muon_tra WHERE maSach = ?";
        try {
            PreparedStatement Statement = connecdb.prepareStatement(kiemtra);
            Statement.setString(1, masach);
            ResultSet resultSet = Statement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Không thể xóa sách này vì vẫn còn người đang mượn.");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String xoaSach = "DELETE FROM sach WHERE maSach = ?";

        try{
            PreparedStatement xoa = connecdb.prepareStatement(xoaSach);
            xoa.setString(1, masach);
            int thanhcong = xoa.executeUpdate();

            if(thanhcong > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("xóa sách thành công.");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("xóa sách thất bại");
                alert.showAndWait();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}



