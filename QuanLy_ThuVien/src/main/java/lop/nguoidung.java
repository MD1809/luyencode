package lop;

public class nguoidung {
    private String taikhoan;
    private String matkhau;
    private String manguoidung;
    private String tennguoidung;
    private String vaiTro;
    private String gioiTinh;
    private String email;
    private String diaChi;

    // Constructor
    public nguoidung(String taikhoan, String matkhau, String manguoidung,
                     String tennguoidung,  String vaiTro, String gioiTinh, String email, String diaChi) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.manguoidung = manguoidung;
        this.tennguoidung = tennguoidung;
        this.vaiTro = vaiTro;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.diaChi = diaChi;
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
    public String getVaitro() {
        return vaiTro;
    }
    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
    public String getTaiKhoan(){
        return taikhoan;
    }
    public void setTaikhoan(String taikhoan){
        this.taikhoan = taikhoan;
    }
    public String getMatKhau(){
        return matkhau;
    }
    public void setMatKhau(String matkhau){
        this.matkhau = matkhau;
    }
    // Getter và Setter cho ten
    public String getTenNguoiDung(){
        return tennguoidung;
    }
    public void setTenNguoiDung(String tennguoidung){
        this.tennguoidung = tennguoidung;
    }
    public  String getManguoidung(){
        return manguoidung;
    }
    public void setManguoidung(){
        this.manguoidung = manguoidung;
    }
}
