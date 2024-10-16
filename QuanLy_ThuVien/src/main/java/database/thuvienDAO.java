package database;

import lop.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class thuvienDAO {

    public List<Sach> getAllsach() {
        List<Sach> dulieusach = new ArrayList<>();
        String query = "SELECT * FROM sach";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Sach sach = new Sach(resultSet.getString("maSach"),
                        resultSet.getString("theLoai"),
                        resultSet.getString("tenSach"),
                        resultSet.getString("tenTacGia"),
                        resultSet.getInt("namXuatBan"),
                        resultSet.getInt("soLuongCon"),
                        resultSet.getString("tinhTrang"));
                dulieusach.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dulieusach;
    }

    public List<nguoidung> getAllnguoidung() {
        List<nguoidung> dulieunguoidung = new ArrayList<>();
        String query = "SELECT * FROM nguoidung";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                nguoidung nguoidung = new nguoidung(
                        resultSet.getString("taikhoan"),
                        resultSet.getString("matkhau"),
                        resultSet.getString("manguoidung"),
                        resultSet.getString("tennguoidung"),
                        resultSet.getString("vaiTro"),
                        resultSet.getString("gioiTinh"),
                        resultSet.getString("email"),
                        resultSet.getString("diaChi")
                );
                dulieunguoidung.add(nguoidung);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dulieunguoidung;
    }

    public List<muon_tra> getthongtinmuontra() {
        List<muon_tra> dulieu = new ArrayList<>();
        String query = "select mt.maID, mt.manguoidung, s.maSach, s.theLoai, s.tenSach, s.tenTacGia, s.namXuatBan, mt.ngayMuon, mt.ngayTra from muon_tra mt " +
                "inner join sach s on mt.masach = s.masach;";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                muon_tra dulieunguoimuon = new muon_tra(
                        resultSet.getInt("maID"),
                        resultSet.getString("manguoidung"),
                        resultSet.getString("maSach"),
                        resultSet.getString("theLoai"),
                        resultSet.getString("tenSach"),
                        resultSet.getString("tentacgia"),
                        resultSet.getInt("namxuatban"),
                        resultSet.getDate("ngayMuon").toLocalDate(),
                        resultSet.getDate("ngayTra").toLocalDate()
                );
                dulieu.add(dulieunguoimuon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dulieu;
    }
}
