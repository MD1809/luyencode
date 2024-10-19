package com.example.quanly_thuvien;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import database.*;
import lop.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class thuthu_danhsachmuonController {
    @FXML
    private Button tt_dsm_dong;

    @FXML
    private TableColumn<muon_tra, Integer> tt_dsm_ma;

    @FXML
    private TableColumn<muon_tra, String> tt_dsm_masach;

    @FXML
    private TableColumn<muon_tra, String> tt_dsm_manguoidung;

    @FXML
    private TableColumn<muon_tra, Integer> tt_dsm_namxb;

    @FXML
    private TableColumn<muon_tra, String> tt_dsm_ngaymuon;

    @FXML
    private TableColumn<muon_tra, String> tt_dsm_ngaytra;

    @FXML
    private TableColumn<muon_tra, String> tt_dsm_tensach;

    @FXML
    private TableColumn<muon_tra, String> tt_dsm_tentacgia;

    @FXML
    private TableColumn<muon_tra, String> tt_dsm_theloai;

    @FXML
    private TableView<muon_tra> tt_danhsachmuon;



    @FXML
    public void initialize() {
        // Đặt tiêu đề cho các cột
        tt_dsm_ma.setCellValueFactory(new PropertyValueFactory<>("maID"));
        tt_dsm_manguoidung.setCellValueFactory(new PropertyValueFactory<>("manguoidung"));
        tt_dsm_masach.setCellValueFactory(new PropertyValueFactory<>("maSach"));
        tt_dsm_theloai.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        tt_dsm_tensach.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tt_dsm_tentacgia.setCellValueFactory(new PropertyValueFactory<>("tenTacGia"));
        tt_dsm_namxb.setCellValueFactory(new PropertyValueFactory<>("namXuatBan"));
        //tt_dsm_ngaymuon.setCellValueFactory(new PropertyValueFactory<>("ngayMuon"));
        tt_dsm_ngaymuon.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getNgayMuon();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return new SimpleStringProperty(date.format(formatter));
        });
        //tt_dsm_ngaytra.setCellValueFactory(new PropertyValueFactory<>("ngayTra"));
        tt_dsm_ngaytra.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getNgayTra();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return new SimpleStringProperty(date.format(formatter));
        });

        tt_hienthidanhsachmuon();
    }
    @FXML
    public void tt_hienthidanhsachmuon() {
        List<muon_tra> danhsachmuon = new ArrayList<>();
        thuvienDAO dulieu = new thuvienDAO();
        try{
            danhsachmuon = dulieu.getthongtinmuontra();
        } catch(Exception e){
            e.printStackTrace();
        }
        // Clear the table and add new data
        tt_danhsachmuon.getItems().clear();
        tt_danhsachmuon.getItems().addAll(danhsachmuon);
    }

    @FXML
    public void tt_thoatdanhsachmuonController() {
        Stage stagethoat = (Stage) tt_dsm_dong.getScene().getWindow();
        stagethoat.close();
    }
}
