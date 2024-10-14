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
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
public class thanhvienController implements Initializable {

    @FXML
    private Label tv_ten;
    @FXML
    private Label tv_maso;
    @FXML
    private Label tv_thongbao;
    @FXML
    private TextField timkiemtensach;
    @FXML
    private Button tv_dangxuat;
    @FXML
    private TextField mathanhvien;
    @FXML
    private TextField tv_masachText;
    @FXML
    private TextField tv_tacgiaText;
    @FXML
    private TextField tv_tensachText;
    @FXML
    private TextField tv_theloaiText;
    @FXML
    private TextField tv_soluongText;
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

    private String taikhoan;
    private String matkhau;
    private String TENNGUOIDUNG;
    private String MATHANHVIEN;
    public void laytaikhoandangnhap(String taikhoan, String matkhau) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }
    public String getTaiKhoan() {
        return taikhoan;
    }
    public String getMatKhau() {
        return matkhau;
    }

    public void hienthitaikhoan(){
        boolean thongtin = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("taikhoan.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dulieu = line.split(",");
                if (dulieu.length == 8) {
                    String TAIKHOAN = dulieu[0];
                    String MATKHAU = dulieu[1];
                    TENNGUOIDUNG = dulieu[3];
                    MATHANHVIEN = dulieu[7];
                    if (TAIKHOAN.equals(getTaiKhoan()) && MATKHAU.equals(getMatKhau())) {
                        thongtin = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(thongtin){
            tv_ten.setText(TENNGUOIDUNG);
            tv_maso.setText(MATHANHVIEN);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ds_sach = FXCollections.observableArrayList();  // Khởi tạo ObservableList trống
        try {
            docdulieusach();// Đọc dữ liệu từ file
        } catch (IOException e) {
            e.printStackTrace();
        }
        masach.setCellValueFactory(new PropertyValueFactory<>("maSach"));
        theloai.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        tensach.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tacgia.setCellValueFactory(new PropertyValueFactory<>("tenTacGia"));
        tinhtrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        Sachlist.setItems(ds_sach);
    }

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
                        ds_sach.add(sach);  // Thêm sách vào danh sách ObservableList
                    }
                }
            }
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

    @FXML
    public void timKiemSach() {
        String tenSachCanTim = timkiemtensach.getText(); // Lấy tên sách từ TextField
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
            if (datimthay) {
                Sachlist.setItems(sach_tim);
            } else {
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
    public void muonSach() {
        String maSachMuon = tv_masachText.getText();
        String tenSachMuon = tv_tensachText.getText();
        String theLoaiMuon = tv_theloaiText.getText();
        String tenTacGiaMuon = tv_tacgiaText.getText();
        String mathanhvienmuon = mathanhvien.getText();
        String soluongmuon = tv_soluongText.getText();

        if(mathanhvienmuon.isEmpty() || maSachMuon.isEmpty() || tenSachMuon.isEmpty()
                || tenTacGiaMuon.isEmpty() || theLoaiMuon.isEmpty() || soluongmuon.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }

        boolean nguoimuon = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("taikhoan.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dulieu = line.split(",");
                if (dulieu.length == 8) {
                    String TAIKHOAN = dulieu[0];
                    String MATKHAU = dulieu[1];
                    String MATHANHVIENMUON = dulieu[7];
                    if (TAIKHOAN.equals(getTaiKhoan()) && MATKHAU.equals(getMatKhau()) && MATHANHVIENMUON.equals(mathanhvienmuon)) {
                        nguoimuon = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!nguoimuon){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Mã thành viên không đúng.");
            alert.showAndWait();
            return;
        }

        int soLuong;
        try {
            soLuong = Integer.parseInt(soluongmuon);
        } catch (NumberFormatException e) {
            tv_thongbao.setText("Số lượng phải là một số nguyên hợp lệ.");
            return;
        }

        int sosachdamuon = 0;
        boolean damuon = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("danhsachmuonsach.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] thongTinMuon = line.split(",");
                if (thongTinMuon.length == 6) {
                    String maThanhVien = thongTinMuon[0];
                    int soLuongdamuon = Integer.parseInt(thongTinMuon[5]);
                    if (maThanhVien.equals(mathanhvienmuon)) {
                        damuon = true;
                        sosachdamuon += soLuongdamuon;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(sosachdamuon + soLuong > 5) {
            if(damuon){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("số lượng còn mượn là: " + sosachdamuon +"\n" +
                                        "Không thể mượn quá 5 quyển");
                alert.showAndWait();
                return;
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Không thể mượn quá 5 quyển.");
                alert.showAndWait();
                return;
            }
        }
        List<String> danhSachCapNhat = new ArrayList<>();
        List<String> danhsachmuoncapnhat = new ArrayList<>();
        boolean daMuonThanhCong = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("danhsachsach.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] thongTin = line.split(",");
                if (thongTin.length == 6) {
                    String maSach = thongTin[0];
                    String theLoai = thongTin[1];
                    String tenSach = thongTin[2];
                    String tenTacGia = thongTin[3];
                    int soLuongCon = Integer.parseInt(thongTin[4]);
                    String tinhTrang = thongTin[5];
                    if (maSach.equals(maSachMuon) && tenSach.equals(tenSachMuon)
                            && theLoai.equals(theLoaiMuon) && tenTacGia.equals(tenTacGiaMuon)) {
                        if (soLuongCon == 0) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(null);
                            alert.setHeaderText(null);
                            alert.setContentText("Đã hết sách.");
                            alert.showAndWait();
                            return;
                        } else if (soLuongCon >= soLuong) {
                            soLuongCon -= soLuong;
                            if (soLuongCon == 0) {
                                tinhTrang = "Hết sách";
                            }
                            daMuonThanhCong = true;
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(null);
                            alert.setHeaderText(null);
                            alert.setContentText("Không đủ sách để mượn.");
                            alert.showAndWait();
                            return;
                        }
                    }
                    danhSachCapNhat.add(maSach + "," + theLoai + "," + tenSach + "," + tenTacGia + "," + soLuongCon + "," + tinhTrang);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (daMuonThanhCong) {
            boolean dacapnhat = false;
            try (BufferedReader reader = new BufferedReader(new FileReader("danhsachmuonsach.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] thongTin = line.split(",");
                    if (thongTin.length == 6) {
                        String Mathanhvien = thongTin[0];
                        String Masach = thongTin[1];
                        String Theloai = thongTin[2];
                        String Tensach = thongTin[3];
                        String Tentacgia = thongTin[4];
                        int soluong = Integer.parseInt(thongTin[5]);
                        if (Mathanhvien.equals(mathanhvienmuon) && Masach.equals(maSachMuon)
                                && Theloai.equals(theLoaiMuon) && Tensach.equals(tenSachMuon) && Tentacgia.equals(tenTacGiaMuon)) {
                            soluong += soLuong;
                            danhsachmuoncapnhat.add(Mathanhvien + "," + Masach + "," + Theloai + "," + Tensach + "," + Tentacgia + "," + soluong);
                            dacapnhat = true;
                        } else {
                            danhsachmuoncapnhat.add(line);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!dacapnhat){
                danhsachmuoncapnhat.add(mathanhvienmuon + "," + maSachMuon + "," + theLoaiMuon + "," + tenSachMuon + "," + tenTacGiaMuon + "," + soLuong);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("danhsachmuonsach.txt", false))) {
            for (String sach : danhsachmuoncapnhat) {
                writer.write(sach);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (daMuonThanhCong) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("danhsachsach.txt", false))) {
                for (String sach : danhSachCapNhat) {
                    writer.write(sach);
                    writer.newLine();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Mượn sách thành công.");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Mượn sách không thành công.");
            alert.showAndWait();
        }
    }

    @FXML
    public void traSach() {
        String maSachTra = tv_masachText.getText();
        String tenSachTra = tv_tensachText.getText();
        String theLoaiTra = tv_theloaiText.getText();
        String tenTacGiaTra = tv_tacgiaText.getText();
        String mathanhvientra = mathanhvien.getText();
        String soluongtra = tv_soluongText.getText();

        if(mathanhvientra.isEmpty() || maSachTra.isEmpty() || tenSachTra.isEmpty()
                || tenTacGiaTra.isEmpty() || theLoaiTra.isEmpty() || soluongtra.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }

        int soLuongTra;
        try {
            soLuongTra = Integer.parseInt(soluongtra);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Số lượng phải là số nguyên hợp lệ");
            alert.showAndWait();
            return;
        }

        if(soLuongTra > 5){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Không thể trả quá 5 quyển.");
            alert.showAndWait();
        }
        List<String> danhSachCapNhat = new ArrayList<>();
        List<String> danhSachMuonTraCapNhat = new ArrayList<>();
        boolean daTraThanhCong = false;
        boolean tonTaiThongTinMuon = false;
        // Kiểm tra thông tin trong danh sách mượn
        try (BufferedReader reader = new BufferedReader(new FileReader("danhsachmuonsach.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] thongTinMuon = line.split(",");
                if (thongTinMuon.length == 6) {
                    String maThanhVien = thongTinMuon[0];
                    String maSachMuon = thongTinMuon[1];
                    String theLoaiMuon = thongTinMuon[2];
                    String tenSachMuon = thongTinMuon[3];
                    String tentacgiasachmuon = thongTinMuon[4];
                    int soLuongDaMuon = Integer.parseInt(thongTinMuon[5]);
                    // Kiểm tra xem có tồn tại thông tin thành viên và sách đã mượn không
                    if (maThanhVien.equals(mathanhvientra) && maSachMuon.equals(maSachTra) &&
                            theLoaiMuon.equals(theLoaiTra) && tenSachMuon.equals(tenSachTra)
                            && tentacgiasachmuon.equals(tenTacGiaTra)) {
                        if(soLuongDaMuon < soLuongTra){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(null);
                            alert.setHeaderText(null);
                            alert.setContentText("Vượt quá số lượng đã mượn.");
                            alert.showAndWait();
                            return;
                        }else{
                            tonTaiThongTinMuon = true;
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Nếu không tồn tại thông tin mượn của thành viên, không cho phép trả sách
        if (!tonTaiThongTinMuon) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Không có thông tin mượn sách này.");
            alert.showAndWait();
            return;
        }
        // Đọc và cập nhật thông tin sách trong file danhsachsach.txt.txt
        try (BufferedReader reader = new BufferedReader(new FileReader("danhsachsach.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] thongTin = line.split(",");
                if (thongTin.length == 6) {
                    String maSach = thongTin[0];
                    String theLoai = thongTin[1];
                    String tenSach = thongTin[2];
                    String tenTacGia = thongTin[3];
                    int soLuongCon = Integer.parseInt(thongTin[4]);
                    String tinhTrang = thongTin[5];
                    // Kiểm tra xem sách có khớp với thông tin trả không
                    if (maSach.equals(maSachTra) && tenSach.equals(tenSachTra)
                            && theLoai.equals(theLoaiTra) && tenTacGia.equals(tenTacGiaTra)) {
                        soLuongCon += soLuongTra;
                        tinhTrang = "có thể mượn";
                        daTraThanhCong = true; // Đánh dấu trả sách thành công
                    }
                    // Cập nhật dòng sách đã chỉnh sửa và thêm vào danh sách cập nhật
                    danhSachCapNhat.add(maSach + "," + theLoai + "," + tenSach + "," + tenTacGia + "," + soLuongCon + "," + tinhTrang);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Nếu trả sách thành công, ghi lại toàn bộ danh sách sách đã cập nhật vào file
        if (daTraThanhCong) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("danhsachsach.txt", false))) {
                for (String sach : danhSachCapNhat) {
                    writer.write(sach);
                    writer.newLine(); // Ghi mỗi sách trên 1 dòng
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Trả sách thành công.");
                alert.showAndWait();
            }catch (IOException e) {
                e.printStackTrace();
            }
            // Cập nhật danh sách mượn trả
            try (BufferedReader reader = new BufferedReader(new FileReader("danhsachmuonsach.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] thongTinMuon = line.split(",");
                    if (thongTinMuon.length == 6) {
                        String mathanhvien = thongTinMuon[0];
                        String masachdamuon = thongTinMuon[1];
                        String theloaisachdamuon = thongTinMuon[2];
                        String tensachdamuon = thongTinMuon[3];
                        String tacgiasachdamuon = thongTinMuon[4];
                        int soluongdamuon = Integer.parseInt(thongTinMuon[5]);
                        // Kiểm tra điều kiện có khớp với thông tin trả sách không
                        if (mathanhvien.equals(mathanhvientra) && masachdamuon.equals(maSachTra) && tensachdamuon.equals(tenSachTra)
                                && theloaisachdamuon.equals((theLoaiTra)) && tacgiasachdamuon.equals(tenTacGiaTra)) {
                            soluongdamuon -= soLuongTra;
                            if(soluongdamuon != 0){
                                danhSachMuonTraCapNhat.add(mathanhvien + "," + masachdamuon + "," + theloaisachdamuon + "," + tensachdamuon + "," + tacgiasachdamuon + "," + soluongdamuon);
                            }
                        }else {
                            danhSachMuonTraCapNhat.add(line);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Ghi lại danh sách đã cập nhật vào file danh sách mượn trả
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("danhsachmuonsach.txt", false))) {
                for (String tra : danhSachMuonTraCapNhat) {
                    writer.write(tra);
                    writer.newLine(); // Ghi mỗi dòng vào file
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Trả sách không thành công.");
            alert.showAndWait();
        }
    }

    @FXML
    public void tv_danhsachmuonController()  throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("thanhvien_danhsachmuon.fxml"));
        Parent root = loader.load();
        thanhvien_danhsachmuonController controller2 = loader.getController(); // Lấy controller
        controller2.laytaikhoandangnhap2(taikhoan, matkhau);
        Stage tv_stage = new Stage();
        Scene scene = new Scene(root);
        tv_stage.setScene(scene);
        tv_stage.show();
        controller2.tv_hienthidanhsachmuon();
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
