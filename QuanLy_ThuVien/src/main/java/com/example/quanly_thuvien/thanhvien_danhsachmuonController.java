package com.example.quanly_thuvien;


import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;
import lop.*;
import database.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class thanhvien_danhsachmuonController {
    @FXML
    private Button tv_dsm_dong;
    @FXML
    private TextField tv_dsm_giahan;
    @FXML
    private TableView<muon_tra> tv_danhsachmuon;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_maid;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_masach;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_theloai;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_tensach;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_tentacgia;
    @FXML
    private TableColumn<muon_tra, Integer> tv_dsm_namxb;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_ngaymuon;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_ngaytra;

    private String taikhoan;
    private String matkhau;

    public void thongtinmanguoidung(String taikhoan, String matkhau) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }
    public String getTaiKhoan() {
        return taikhoan;
    }
    public String getMatKhau() {
        return matkhau;
    }

    @FXML
    public void initialize() {
        // Đặt tiêu đề cho các cột
        tv_dsm_maid.setCellValueFactory(new PropertyValueFactory<>("maID"));
        tv_dsm_masach.setCellValueFactory(new PropertyValueFactory<>("maSach"));
        tv_dsm_theloai.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        tv_dsm_tensach.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tv_dsm_tentacgia.setCellValueFactory(new PropertyValueFactory<>("tenTacGia"));
        tv_dsm_namxb.setCellValueFactory(new PropertyValueFactory<>("namXuatBan"));
        tv_dsm_ngaymuon.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getNgayMuon();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return new SimpleStringProperty(date.format(formatter));
        });
        tv_dsm_ngaytra.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getNgayTra();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return new SimpleStringProperty(date.format(formatter));
        });
        tv_hienthidanhsachmuon();
    }

    @FXML
    public void tv_hienthidanhsachmuon(){
        ObservableList<muon_tra> danhsachmuon = FXCollections.observableArrayList();

        try (Connection connection = databaseConnection.getConnection()) {
            String laydulieu = "select mt.maID, mt.manguoidung, mt.maSach, s.theLoai, s.tenSach, s.tenTacGia, s.namXuatBan, mt.ngayMuon, mt.ngayTra from muon_tra mt " +
                    "inner join sach s on mt.masach = s.masach " +
                    "inner join nguoidung n on mt.manguoidung = n.manguoidung " +
                    "where n.tentaikhoan = ?";
            PreparedStatement statement = connection.prepareStatement(laydulieu);
            statement.setString(1, getTaiKhoan());

            ResultSet queryResult = statement.executeQuery();

            while(queryResult.next()){
                int maID = queryResult.getInt("maID");
                String manguoidung = queryResult.getString("manguoidung");
                String maSach = queryResult.getString("maSach");
                String theLoai = queryResult.getString("theLoai");
                String tenSach = queryResult.getString("tenSach");
                String tenTacGia = queryResult.getString("tenTacGia");
                int namXuatBan = queryResult.getInt("namXuatBan");
                LocalDate ngayMuon = queryResult.getDate("ngayMuon").toLocalDate();
                LocalDate ngayTra = queryResult.getDate("ngayTra").toLocalDate();

                muon_tra muontra = new muon_tra(maID, manguoidung, maSach, theLoai, tenSach, tenTacGia, namXuatBan, ngayMuon, ngayTra);
                danhsachmuon.add(muontra);
            }
            tv_danhsachmuon.setItems(danhsachmuon);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void giaHanThoiGianTraSach() {
        String mamuon = tv_dsm_giahan.getText();
        try (Connection connection = databaseConnection.getConnection()) {
            // Lấy ngày trả hiện tại từ cơ sở dữ liệu
            String layNgayTra = "SELECT mt.ngayTra, mt.manguoidung FROM muon_tra mt" +
                    " INNER JOIN nguoidung n ON mt.manguoidung = n.manguoidung" +
                    " where maID = ? AND n.tentaikhoan = ?";
            PreparedStatement layNgayTraStatement = connection.prepareStatement(layNgayTra);
            layNgayTraStatement.setString(1, mamuon);
            layNgayTraStatement.setString(2, taikhoan);

            ResultSet resultSet = layNgayTraStatement.executeQuery();
            if (resultSet.next()) {
                LocalDate ngayTraHienTai = resultSet.getDate("ngayTra").toLocalDate();

                // Tính toán ngày trả mới sau khi gia hạn
                LocalDate ngayTraMoi = ngayTraHienTai.plusDays(15);

                // Cập nhật lại ngày trả mới vào cơ sở dữ liệu
                String giaHanQuery = "UPDATE muon_tra SET ngayTra = ? WHERE maID = ?";
                PreparedStatement giaHanStatement = connection.prepareStatement(giaHanQuery);

                // Thiết lập giá trị cho các cột
                giaHanStatement.setDate(1, Date.valueOf(ngayTraMoi)); // Ngày trả mới
                giaHanStatement.setString(2, mamuon);

                // Thực hiện lệnh UPDATE
                int rowsUpdated = giaHanStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Gia hạn thời gian trả sách thành công.");
                    alert.showAndWait();
                    initialize();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Không thể gia hạn");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy thông tin mượn sách.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Có lỗi xảy ra khi gia hạn thời gian trả sách.");
            alert.showAndWait();
        }
    }


    // Đóng cửa sổ danh sách mượn
    public void tv_thoatdanhsachmuonController() {
        Stage stagethoat = (Stage) tv_dsm_dong.getScene().getWindow();
        stagethoat.close();
    }
}
