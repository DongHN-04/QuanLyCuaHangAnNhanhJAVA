/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.dao;

/**
 *
 * @author NGUYEN HOANG DONG
 */
import CH.model.DoanhThu;
import java.sql.*;
import java.util.*;

public class DoanhThuDAO {

    public List<DoanhThu> thongKeTheoNgay() {
        List<DoanhThu> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT NgayLap, SUM(TongTien) AS Tong "
                    + "FROM HoaDon GROUP BY NgayLap "
                    + "ORDER BY STR_TO_DATE(NgayLap,'%d/%m/%Y') DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new DoanhThu(rs.getString("NgayLap"), rs.getDouble("Tong")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DoanhThu> thongKeTheoThang() {
        List<DoanhThu> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT DATE_FORMAT(STR_TO_DATE(NgayLap,'%d/%m/%Y'),'%m-%Y') AS ThoiGian, "
                    + "SUM(TongTien) AS Tong "
                    + "FROM HoaDon GROUP BY ThoiGian "
                    + "ORDER BY STR_TO_DATE(CONCAT('01-',ThoiGian),'%d-%m-%Y') DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new DoanhThu(rs.getString("ThoiGian"), rs.getDouble("Tong")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DoanhThu> thongKeTheoNam() {
        List<DoanhThu> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT DATE_FORMAT(STR_TO_DATE(NgayLap,'%d/%m/%Y'),'%Y') AS ThoiGian, "
                    + "SUM(TongTien) AS Tong "
                    + "FROM HoaDon GROUP BY ThoiGian ORDER BY ThoiGian DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new DoanhThu(rs.getString("ThoiGian"), rs.getDouble("Tong")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
