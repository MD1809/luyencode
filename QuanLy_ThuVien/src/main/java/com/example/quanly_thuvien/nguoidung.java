package com.example.quanly_thuvien;

public class nguoidung {
    private String ten;
    private String gioiTinh;
    private String email;
    private String diaChi;
    private String vaiTro;
    // Constructor
    public nguoidung(String ten, String gioiTinh, String email, String diaChi, String vaiTro) {
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.diaChi = diaChi;
        this.vaiTro = vaiTro;
    }
    // Getter và Setter cho ten
    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }
    // Getter và Setter cho gioiTinh
    public String getGioiTinh() {
        return gioiTinh;
    }
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    // Getter và Setter cho email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    // Getter và Setter cho diaChi
    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    // Getter và Setter cho vaiTro
    public String getVaiTro() {
        return vaiTro;
    }
    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
}
