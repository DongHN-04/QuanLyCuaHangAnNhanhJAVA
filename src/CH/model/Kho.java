/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.model;

/**
 *
 * @author NGUYEN HOANG DONG
 */
public class Kho {
    private String maNL;
    private String tenNL;
    private int soLuong;
    private String donViTinh;

    public Kho() {}

    public Kho(String maNL, String tenNL, int soLuong, String donViTinh) {
        this.maNL = maNL;
        this.tenNL = tenNL;
        this.soLuong = soLuong;
        this.donViTinh = donViTinh;
    }

    public String getMaNL() { return maNL; }
    public String getTenNL() { return tenNL; }
    public int getSoLuong() { return soLuong; }
    public String getDonViTinh() { return donViTinh; }

    public void setMaNL(String maNL) { this.maNL = maNL; }
    public void setTenNL(String tenNL) { this.tenNL = tenNL; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }

    public Object[] toObjectArray() {
        return new Object[]{maNL, tenNL, soLuong, donViTinh};
    }
}

