package com.example.quanly_thuvien;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lop.*;
import database.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.sql.Date;
import java.time.LocalDate;

public class thanhvienController implements Initializable {

    @FXML
    private TextField timkiemtensach;
    @FXML
    private Button tv_dangxuat;
    @FXML
    private TextField mathanhvien;
    @FXML
    private TextField tv_masachText;
    @FXML
    private Label tennguoidung;
    @FXML
    private Label manguoidung;
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
    private TableColumn<Sach, String> tinhtrang;

    private ObservableList<Sach> ds_sach;

    private String taikhoandn;
    private String matkhaudn;

    public void thongtintaikhoan(String taikhoan, String matkhau) {
        this.taikhoandn = taikhoan;
        this.matkhaudn = matkhau;
    }
    public String getTaiKhoan() {
        return taikhoandn;
    }
    public String getMatKhau() {
        return matkhaudn;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ds_sach = FXCollections.observableArrayList();
        try {
            danhsachsach();
        } catch (IOException e) {
            e.printStackTrace();
        }

        masach.setCellValueFactory(new PropertyValueFactory<>("maSach"));
        theloai.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        tensach.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tacgia.setCellValueFactory(new PropertyValueFactory<>("tenTacGia"));
        tinhtrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));

        Sachlist.setItems(ds_sach);
        xulinhanhmuonsach();
    }

    public void hienthithongtin(){
        databaseConnection connect = new databaseConnection();
        Connection connecdb = connect.getConnection();
        String thongtin = "select tennguoidung, manguoidung from nguoidung where tentaikhoan LIKE ? ";
        try{
            PreparedStatement statement = connecdb.prepareStatement(thongtin);
            statement.setString(1, taikhoandn);
            ResultSet queryResult = statement.executeQuery();
            if(queryResult.next()){
                tennguoidung.setText(queryResult.getString(1));
                manguoidung.setText("Mã số: " + queryResult.getString(2));
            }
        } catch(Exception e){
            e.printStackTrace();

        }
    }

    public void danhsachsach() throws IOException{
        thuvienDAO dulieucuasach = new thuvienDAO();
        try{
            List<Sach> danhSachSach = dulieucuasach.getAllsach();
            ds_sach = FXCollections.observableArrayList(danhSachSach);
            Sachlist.setItems(ds_sach);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void capnhatlaidulieu() {
        ds_sach.clear();
        try {
            danhsachsach();
            Sachlist.setItems(ds_sach);
        } catch (IOException e) {
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

    @FXML
    public void xulinhanhmuonsach(){

        Sachlist.setOnMouseClicked(event -> {
            Sach thongtin = Sachlist.getSelectionModel().getSelectedItem();
            if (thongtin != null) {
                tv_masachText.setText(thongtin.getMaSach());
            }
        });
    }

    public boolean sachcon(String ma){
        boolean cosach = false;
        databaseConnection connect = new databaseConnection();
        Connection connecdb = connect.getConnection();
        String soluong = "SELECT soLuongCon, tinhTrang FROM sach WHERE maSach LIKE ? ";
        try{
            PreparedStatement statement = connecdb.prepareStatement(soluong);
            statement.setString(1, ma);
            ResultSet queryResult = statement.executeQuery();

            if (queryResult.next()) {
                int soLuongCon = queryResult.getInt("soLuongCon");
                if (soLuongCon > 0 && queryResult.getString("tinhTrang").equals("Có thể mượn")) {
                    cosach = true;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return cosach;
    }

    @FXML
    public void muonSach() {

        String maSachMuon = tv_masachText.getText();
        String mathanhvienmuon = mathanhvien.getText();

        thuvienDAO dulieucuasach = new thuvienDAO();
        List<Sach> dulieusach = dulieucuasach.getAllsach();

        boolean tontaisach = false;
        for (Sach sach : dulieusach) {
            if (sach.getMaSach().equals(maSachMuon)) {
                tontaisach = true;
                break;
            }
        }

        if (mathanhvienmuon.isEmpty() || maSachMuon.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }else if(!tontaisach){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("không có dữ liệu sách.");
            alert.showAndWait();
            return;
        }else if (!sachcon(maSachMuon)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Đã có người mượn");
            alert.showAndWait();
            return;
        }

        boolean nguoimuon = false;
        try (Connection connection = databaseConnection.getConnection()) {
            String kiemtramaso = "SELECT manguoidung FROM nguoidung WHERE tentaikhoan = ? ";
            PreparedStatement statement = connection.prepareStatement(kiemtramaso);
            statement.setString(1, taikhoandn);
            ResultSet queryResult = statement.executeQuery();

            if (queryResult.next() && queryResult.getString("manguoidung").equals(mathanhvienmuon)) {
                nguoimuon = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!nguoimuon) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Mã thành viên không đúng.");
            alert.showAndWait();
            return;
        }

        // Kiểm tra sách
        boolean sachmuonmuon = false;
        Sach sachMuon = null;
        for (Sach sach : dulieusach) {
            if (sach.getMaSach().equals(maSachMuon)) {
                sachmuonmuon = true;
                sachMuon = sach;
                break;
            }
        }

        if (sachmuonmuon && sachMuon != null) {
            try (Connection connection = databaseConnection.getConnection()) {
                String muonsach = "INSERT INTO muon_tra (maNguoiDung, maSach, ngayMuon, ngayTra) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(muonsach);

                // Thiết lập giá trị cho các cột
                statement.setString(1, mathanhvienmuon);
                statement.setString(2, sachMuon.getMaSach());;

                // Ngày mượn là ngày hiện tại
                Date ngayMuon = Date.valueOf(LocalDate.now());
                statement.setDate(3, ngayMuon);

                Date ngayTra = Date.valueOf(LocalDate.now().plusDays(15));
                statement.setDate(4, ngayTra);

                // Thực hiện lệnh INSERT
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Mượn sách thành công.");
                    alert.showAndWait();
                    initialize(null,null);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("không tìm thấy sách." + "\n"
                    + "Mượn sách không thành công");
            alert.showAndWait();
        }
    }

    @FXML
    public void tv_danhsachmuonController()  throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("thanhvien_danhsachmuon.fxml"));
        Parent root = loader.load();
        thanhvien_danhsachmuonController controller = loader.getController();
        controller.thongtinmanguoidung(taikhoandn, matkhaudn);
        Stage tv_stage = new Stage();
        Scene scene = new Scene(root);
        tv_stage.setScene(scene);
        tv_stage.show();
        controller.tv_hienthidanhsachmuon();
    }

    @FXML
    public void doimatkhau() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("doimatkhau.fxml"));
        Parent root = loader.load();
        doimatkhauController dmk = loader.getController();
        dmk.taikhoanmatkhau(taikhoandn, matkhaudn);
        Stage thaydoimatkhau = new Stage();
        Scene scene = new Scene(root);
        thaydoimatkhau.setScene(scene);
        thaydoimatkhau.show();
    }

    public void tv_dangxuatController() throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất không?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dangnhap.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tv_dangxuat.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}