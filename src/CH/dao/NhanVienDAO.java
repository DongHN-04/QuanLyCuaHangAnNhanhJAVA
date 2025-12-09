package CH.dao;

import CH.model.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    // Lấy toàn bộ danh sách nhân viên
    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "SELECT * FROM NhanVien";
            PreparedStatement ps = cons.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setTaiKhoan(rs.getString("TaiKhoan"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setVaiTro(rs.getString("VaiTro"));
                list.add(nv);
            }
            ps.close();
            cons.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public NhanVien getByID(String maNV) {
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setTaiKhoan(rs.getString("TaiKhoan"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setVaiTro(rs.getString("VaiTro"));
                rs.close();
                ps.close();
                cons.close();
                return nv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Thêm nhân viên
    public boolean add(NhanVien nv) {
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "INSERT INTO NhanVien(MaNV, TenNV, NgaySinh, GioiTinh, ChucVu, SoDienThoai, DiaChi, TaiKhoan, MatKhau, VaiTro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getTenNV());
            ps.setString(3, nv.getNgaySinh());
            ps.setString(4, nv.getGioiTinh());
            ps.setString(5, nv.getChucVu());
            ps.setString(6, nv.getSoDienThoai());
            ps.setString(7, nv.getDiaChi());
            ps.setString(8, nv.getTaiKhoan());
            ps.setString(9, nv.getMatKhau());
            ps.setString(10, nv.getVaiTro());
            
            int row = ps.executeUpdate();
            ps.close();
            cons.close();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa nhân viên
    public boolean update(NhanVien nv) {
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "UPDATE NhanVien SET TenNV=?, NgaySinh=?, GioiTinh=?, ChucVu=?, SoDienThoai=?, DiaChi=?, TaiKhoan=?, MatKhau=?, VaiTro=? WHERE MaNV=?";
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getNgaySinh());
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getChucVu());
            ps.setString(5, nv.getSoDienThoai());
            ps.setString(6, nv.getDiaChi());
            ps.setString(7, nv.getTaiKhoan());
            ps.setString(8, nv.getMatKhau());
            ps.setString(9, nv.getVaiTro());
            ps.setString(10, nv.getMaNV());

            int row = ps.executeUpdate();
            ps.close();
            cons.close();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa nhân viên
    public boolean delete(String maNV) {
        try {
            Connection cons = DBConnection.getConnection();
            String sql = "DELETE FROM NhanVien WHERE MaNV=?";
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setString(1, maNV);
            
            int row = ps.executeUpdate();
            ps.close();
            cons.close();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // Hàm sinh mã tự động
    public String getNewID() {
        String newID = "NV001"; // Mặc định nếu bảng trống
        try {
            Connection cons = DBConnection.getConnection();
            // Lấy mã nhân viên cuối cùng (Sắp xếp giảm dần và lấy 1 cái đầu tiên)
            String sql = "SELECT MaNV FROM NhanVien ORDER BY MaNV DESC LIMIT 1";
            PreparedStatement ps = cons.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String lastID = rs.getString("MaNV"); // Ví dụ: NV005
                // Cắt chuỗi để lấy phần số: "005" -> 5
                String prefix = lastID.substring(0, 2); // "NV"
                String numberPart = lastID.substring(2); // "005"
                
                int number = Integer.parseInt(numberPart);
                number++; // Tăng lên 1 -> 6
                
                // Format lại thành chuỗi 3 chữ số (ví dụ 6 -> "006")
                newID = prefix + String.format("%03d", number);
            }
            ps.close();
            cons.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newID;
    }
}