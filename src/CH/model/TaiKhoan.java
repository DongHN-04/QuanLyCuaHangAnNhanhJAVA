/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.model;

/**
 *
 * @author NGUYEN HOANG DONG
 */
public class TaiKhoan {
    private String taiKhoan;
    private String matKhau;
    private String vaiTro;

    public TaiKhoan(String taiKhoan, String matKhau, String vaiTro) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public String getTaiKhoan() { return taiKhoan; }
    public String getMatKhau() { return matKhau; }
    public String getVaiTro() { return vaiTro; }
}

