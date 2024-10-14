package com.example.quanly_thuvien;

public class thanhvien extends nguoidung {
    private String maThanhVien;
    // Constructor
    public thanhvien(String ten, String gioiTinh, String email, String diaChi, String vaiTro, String maThanhVien) {
        super(ten, gioiTinh, email, diaChi, vaiTro);  // Gọi constructor của lớp cha NguoiDung
        this.maThanhVien = maThanhVien;
    }
    // Getter và Setter cho maThanhVien
    public String getMaThanhVien() {
        return maThanhVien;
    }
    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
    }
}