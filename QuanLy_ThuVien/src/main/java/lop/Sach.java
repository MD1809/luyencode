package lop;

public class Sach {
    private String maSach;
    private String theLoai;
    private String tenSach;
    private String tenTacGia;
    private Integer namXuatBan;
    private Integer soLuongCon;
    private String tinhTrang;
    // Constructor
    public Sach(String maSach, String theLoai, String tenSach, String tenTacGia, Integer namXuatBan, Integer soLuongCon, String tinhTrang) {
        this.maSach = maSach;
        this.theLoai = theLoai;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.namXuatBan = namXuatBan;
        this.soLuongCon = soLuongCon;
        this.tinhTrang = tinhTrang;
    }
    public Sach() {
    }
    // Getter và Setter cho maSach
    public String getMaSach() {
        return maSach;
    }
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }
    // Getter và Setter cho theLoai
    public String getTheLoai() {
        return theLoai;
    }
    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }
    // Getter và Setter cho tenSach
    public String getTenSach() {
        return tenSach;
    }
    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
    // Getter và Setter cho tenTacGia
    public String getTenTacGia() {
        return tenTacGia;
    }
    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }
    // Getter và Setter cho soLuongCon
    public Integer getSoLuongCon() {
        return soLuongCon;
    }
    public void setSoLuongCon(Integer soLuongCon) {
        this.soLuongCon = soLuongCon;
    }
    // Getter và Setter cho tinhTrang
    public String getTinhTrang() {
        return tinhTrang;
    }
    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Integer getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(Integer namXuatBan) {
        this.namXuatBan = namXuatBan;
    }
}