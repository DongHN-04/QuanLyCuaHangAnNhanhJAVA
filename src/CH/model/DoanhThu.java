/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.model;

/**
 *
 * @author NGUYEN HOANG DONG
 */
public class DoanhThu {
    private String thoiGian;
    private double tongTien;

    public DoanhThu(String thoiGian, double tongTien) {
        this.thoiGian = thoiGian;
        this.tongTien = tongTien;
    }

    public String getThoiGian() { return thoiGian; }
    public double getTongTien() { return tongTien; }
}

