package lop;

import java.time.LocalDate;

public class muon_tra{
    private int maid;
    private String manguoidung;
    private String masach;
    private String theloai;
    private String tensach;
    private String tentacgia;
    private int namxuatban;
    private LocalDate ngaymuon;
    private LocalDate ngaytra;
    // Constructor
    public muon_tra(int maid, String manguoidung, String masach, String theloai, String tensach,
                    String tentacgia, int namxuatban, LocalDate ngaymuon, LocalDate ngaytra) {
        this.maid = maid;
        this.manguoidung = manguoidung;
        this.masach = masach;
        this.theloai = theloai;
        this.tensach = tensach;
        this.tentacgia = tentacgia;
        this.namxuatban = namxuatban;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
    }
    // Getter and Setter methods
    public int getMaID() {
        return maid;
    }

    public void setMaID(int maid) {
        this.maid = maid;
    }

    public String getManguoidung() {
        return manguoidung;
    }

    public void setManguoidung(String manguoidung) {
        this.manguoidung = manguoidung;
    }

    public String getMaSach() {
        return masach;
    }

    public void setMaSach(String masach) {
        this.masach = masach;
    }

    public String getTheLoai() {
        return theloai;
    }

    public void setTheLoai(String theloai) {
        this.theloai = theloai;
    }

    public String getTenSach() {
        return tensach;
    }

    public void setTenSach(String tensach) {
        this.tensach = tensach;
    }

    public String getTenTacGia() {
        return tentacgia;
    }

    public void setTenTacGia(String tentacgia) {
        this.tentacgia = tentacgia;
    }

    public int getNamXuatBan() {
        return namxuatban;
    }

    public void setNamXuatBan(int namxuatban) {
        this.namxuatban = namxuatban;
    }

    public LocalDate getNgayMuon() {
        return ngaymuon;
    }

    public void setNgayMuon(LocalDate ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public LocalDate getNgayTra() {
        return ngaytra;
    }

    public void setNgayTra(LocalDate ngaytra) {
        this.ngaytra = ngaytra;
    }
}
