package com.example.quanly_thuvien;

public class muon_tra {
    private String maThanhVien; // Mã thành viên mượn sách
    private String maSach;       // Mã sách được mượn
    private String tenSach;
    private String tenTacGia;
    private String theLoai;
    private int soLuong;
    // Constructor
    public muon_tra(String maThanhVien, String maSach, String theLoai, String tenSach, String tenTacGia, int soLuong) {
        this.maThanhVien = maThanhVien;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.theLoai = theLoai;
        this.soLuong = soLuong;
    }
    // Getter và Setter cho maThanhVien
    public String getMaThanhVien() {
        return maThanhVien;
    }
    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
    }
    // Getter và Setter cho maSach
    public String getMaSach() {
        return maSach;
    }
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }
    // Getter và Setter cho tenSach
    public String getTenSach() {
        return tenSach;
    }
    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
    public String getTenTacGia() {
        return tenTacGia;
    }
    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }
    public String getTheLoai() {
        return theLoai;
    }
    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }
    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
