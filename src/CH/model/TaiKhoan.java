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
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;

    public TaiKhoan(String tenDangNhap, String matKhau, String vaiTro){
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public String getTenDangNhap(){ return tenDangNhap; }
    public String getMatKhau(){ return matKhau; }
    public String getVaiTro(){ return vaiTro; }
}

