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
    private String maMon;
    private String tenMon;
    private int soLuong;

    public Kho(String maMon, String tenMon, int soLuong) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soLuong = soLuong;
    }

    public String getMaMon() { return maMon; }
    public String getTenMon() { return tenMon; }
    public int getSoLuong() { return soLuong; }

    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public Object[] toObjectArray() {
        return new Object[]{maMon, tenMon, soLuong};
    }
}


