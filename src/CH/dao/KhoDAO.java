/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.dao;

/**
 *
 * @author NGUYEN HOANG DONG
 */
import CH.model.Kho;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhoDAO {

    public List<Kho> getAll() {
        List<Kho> list = new ArrayList<>();
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "SELECT t.MaMon, t.TenMon, k.SoLuong "
                    + "FROM Kho k JOIN ThucDon t ON k.MaMon = t.MaMon";
            PreparedStatement ps = cons.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Kho(
                        rs.getString("MaMon"),
                        rs.getString("TenMon"),
                        rs.getInt("SoLuong")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateSoLuong(String maMon, int soLuong) {
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "UPDATE Kho SET SoLuong=? WHERE MaMon=?";
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setInt(1, soLuong);
            ps.setString(2, maMon);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean insert(String maMon, int soLuong) {
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "INSERT INTO Kho(MaMon, SoLuong) VALUES(?, ?)";
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setString(1, maMon);
            ps.setInt(2, soLuong);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean exists(String maMon) {
    try {
        Connection cons = DBConnection.getConnection();
        String sql = "SELECT MaMon FROM Kho WHERE MaMon = ?";
        PreparedStatement ps = cons.prepareStatement(sql);
        ps.setString(1, maMon);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // có tồn tại => true
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
