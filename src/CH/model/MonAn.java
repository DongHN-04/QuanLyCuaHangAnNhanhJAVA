package CH.model;

public class MonAn {
    private String maMon, tenMon, donViTinh;
    private double donGia;

    public MonAn(String maMon, String tenMon, double donGia, String donViTinh) {
        this.maMon = maMon; this.tenMon = tenMon; this.donGia = donGia; this.donViTinh = donViTinh;
    }
    // Getters 
    public String getMaMon() { return maMon; }
    public String getTenMon() { return tenMon; }
    public double getDonGia() { return donGia; }
    public String getDonViTinh() { return donViTinh; }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    // Chuyển đổi sang mảng Object để hiển thị lên JTable
    public Object[] toObjectArray() {
        return new Object[]{maMon, tenMon, String.format("%,.0f", donGia), donViTinh};
    }
}