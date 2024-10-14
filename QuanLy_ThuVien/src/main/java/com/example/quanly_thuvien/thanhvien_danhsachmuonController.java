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
public class thanhvien_danhsachmuonController {
    @FXML
    private Button tv_dsm_dong;
    @FXML
    private TableView<muon_tra> tv_danhsachmuon;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_mathanhvien;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_masach;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_theloai;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_tensach;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_tentacgia;
    @FXML
    private TableColumn<muon_tra, String> tv_dsm_soluong;
    private String taikhoandn;
    private String matkhaudn;
    // Nhận tài khoản và mật khẩu từ lớp điều khiển khác
    public void laytaikhoandangnhap2(String taikhoan, String matkhau){
        this.taikhoandn = taikhoan;
        this.matkhaudn = matkhau;
    }
    public String getTaiKhoanDN(){
        return taikhoandn;
    }
    public String getMatKhauDN(){
        return matkhaudn;
    }
    @FXML
    public void initialize() {
        // Đặt tiêu đề cho các cột
        tv_dsm_mathanhvien.setCellValueFactory(new PropertyValueFactory<>("maThanhVien"));
        tv_dsm_masach.setCellValueFactory(new PropertyValueFactory<>("maSach"));
        tv_dsm_theloai.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        tv_dsm_tensach.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tv_dsm_tentacgia.setCellValueFactory(new PropertyValueFactory<>("tenTacGia"));
        tv_dsm_soluong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tv_hienthidanhsachmuon();
    }
    // Kiểm tra tài khoản đăng nhập
    public boolean kiemTraTaiKhoan(String taikhoan, String matkhau, String mathanhvien) {
        boolean dung = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("taikhoan.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dulieu = line.split(",");
                if (dulieu.length == 8) {
                    String TAIKHOAN = dulieu[0];
                    String MATKHAU = dulieu[1];
                    String MATHANHVIEN = dulieu[7];
                    if (TAIKHOAN.equals(getTaiKhoanDN()) && MATKHAU.equals(getMatKhauDN()) && MATHANHVIEN.equals(mathanhvien)) {
                        dung = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dung;
    }
    // Hiển thị danh sách mượn sách
    @FXML
    public void tv_hienthidanhsachmuon(){
        List<muon_tra> danhSachMuon = new ArrayList<>();
        try (BufferedReader docdulieu = new BufferedReader(new FileReader("danhsachmuonsach.txt"))) {
            String line;
            while ((line = docdulieu.readLine()) != null) {
                String[] thongTin = line.split(",");
                if (thongTin.length == 6) {
                    String maThanhVien = thongTin[0];
                    String maSach = thongTin[1];
                    String theLoai = thongTin[2];
                    String tenSach = thongTin[3];
                    String tenTacGia = thongTin[4];
                    int soLuong = Integer.parseInt(thongTin[5]);
                    if(kiemTraTaiKhoan(taikhoandn, matkhaudn, maThanhVien)){
                        danhSachMuon.add(new muon_tra(maThanhVien, maSach, theLoai, tenSach, tenTacGia, soLuong));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Clear the table and add new data
        tv_danhsachmuon.getItems().clear();
        tv_danhsachmuon.getItems().addAll(danhSachMuon);
    }
    // Đóng cửa sổ danh sách mượn
    public void tv_thoatdanhsachmuonController() {
        Stage stagethoat = (Stage) tv_dsm_dong.getScene().getWindow();
        stagethoat.close();
    }
}
