/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.dao;

/**
 *
 * @author NGUYEN HOANG DONG
 */
import CH.model.TaiKhoan;
import java.sql.*;

public class TaiKhoanDAO {

    public TaiKhoan login(String user, String pass){
        TaiKhoan tk = null;
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE TaiKhoan=? AND MatKhau=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                tk = new TaiKhoan(rs.getString("TaiKhoan"),
                                  rs.getString("MatKhau"),
                                  rs.getString("VaiTro"));
            }
            rs.close();
            pst.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return tk;
    }
}

