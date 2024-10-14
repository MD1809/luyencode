package com.example.quanly_thuvien;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class thuthu_danhsachmuonController {
    @FXML
    private Button tt_thoatdanhsachmuon;
    @FXML
    private TableView<muon_tra> tt_danhsachmuon;
    @FXML
    private TableColumn<muon_tra, String> tt_dsm_masach;
    @FXML
    private TableColumn<muon_tra, String> tt_dsm_mathanhvien;
    @FXML
    private TableColumn<muon_tra, String> tt_dsm_tensach;
    @FXML
    private TableColumn<muon_tra, String> tt_dsm_soluong;
    @FXML
    public void initialize() {
        // Đặt tiêu đề cho các cột
        tt_dsm_mathanhvien.setCellValueFactory(new PropertyValueFactory<>("maThanhVien"));
        tt_dsm_masach.setCellValueFactory(new PropertyValueFactory<>("maSach"));
        tt_dsm_tensach.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tt_dsm_soluong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tt_hienThiDanhSachMuon();
    }
    @FXML
    public void tt_hienThiDanhSachMuon() {
        List<muon_tra> danhSachMuon = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("danhsachmuonsach.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] thongTin = line.split(",");
                if (thongTin.length == 6) {
                    String maThanhVien = thongTin[0];
                    String maSach = thongTin[1];
                    String theLoai = thongTin[2];
                    String tenSach = thongTin[3];
                    String tenTacGia = thongTin[4];
                    int soLuong = Integer.parseInt(thongTin[5]);
                    danhSachMuon.add(new muon_tra(maThanhVien, maSach, theLoai, tenSach, tenTacGia, soLuong));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Clear the table and add new data
        tt_danhsachmuon.getItems().clear();
        tt_danhsachmuon.getItems().addAll(danhSachMuon);
    }
    public void tt_thoatdanhsachmuonController() {
        Stage stagethoat = (Stage) tt_thoatdanhsachmuon.getScene().getWindow();
        stagethoat.close();
    }
}
