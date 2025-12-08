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
import java.util.*;

public class KhoDAO {
    
    public List<Kho> getAll() {
        List<Kho> list = new ArrayList<>();
        try {
            Connection cons = DBConnection.getConnection();
            ResultSet rs = cons.createStatement().executeQuery("SELECT * FROM Kho");
            while (rs.next()) {
                list.add(new Kho(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
            }
            cons.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean add(Kho k) {
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "INSERT INTO Kho VALUES (?,?,?,?)";
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setString(1, k.getMaNL());
            ps.setString(2, k.getTenNL());
            ps.setInt(3, k.getSoLuong());
            ps.setString(4, k.getDonViTinh());
            int row = ps.executeUpdate();
            cons.close();
            return row > 0;
        } catch (Exception e) { return false; }
    }

    public boolean update(Kho k) {
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "UPDATE Kho SET TenNL=?, SoLuong=?, DonViTinh=? WHERE MaNL=?";
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setString(1, k.getTenNL());
            ps.setInt(2, k.getSoLuong());
            ps.setString(3, k.getDonViTinh());
            ps.setString(4, k.getMaNL());
            int row = ps.executeUpdate();
            cons.close();
            return row > 0;
        } catch (Exception e) { return false; }
    }

    public boolean delete(String ma) {
        try {
            Connection cons = DBConnection.getConnection();
            PreparedStatement ps = cons.prepareStatement("DELETE FROM Kho WHERE MaNL=?");
            ps.setString(1, ma);
            int row = ps.executeUpdate();
            cons.close();
            return row > 0;
        } catch (Exception e) { return false; }
    }

    // Mã tự tạo NL01 - NL99
    public String getNewID() {
        String id = "NL01";
        try {
            Connection cons = DBConnection.getConnection();
            ResultSet rs = cons.createStatement().executeQuery(
                    "SELECT MaNL FROM Kho ORDER BY length(MaNL) DESC, MaNL DESC LIMIT 1");
            if (rs.next()) {
                String last = rs.getString(1).substring(2);
                int num = Integer.parseInt(last) + 1;
                id = "NL" + (num < 10 ? "0" + num : num);
            }
            cons.close();
        } catch (Exception e) {}
        return id;
    }
}

