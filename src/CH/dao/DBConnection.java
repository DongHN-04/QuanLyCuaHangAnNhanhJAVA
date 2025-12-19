package CH.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    // ===== LẤY CẤU HÌNH TỪ ENV (ƯU TIÊN) =====
    private static final String HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String PORT = System.getenv().getOrDefault("DB_PORT", "3306");
    private static final String DB_NAME = System.getenv().getOrDefault("DB_NAME", "quanlycuahangannhanh");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASS = System.getenv().getOrDefault("DB_PASS", "070704");

    // URL kết nối server
    private static final String SERVER_URL =
            "jdbc:mysql://" + HOST + ":" + PORT +
            "/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh";

    // URL kết nối database
    private static final String DB_URL = SERVER_URL + "&useUnicode=true&characterEncoding=utf8";

    /**
     * Lấy kết nối thao tác dữ liệu
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh" +
                    "&useUnicode=true&characterEncoding=utf8",
                    USER,
                    PASS
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Khởi tạo Database + Bảng
     * GỌI 1 LẦN DUY NHẤT KHI CHẠY MAIN
     */
    public static void initializeDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. Kết nối server MySQL
            Connection serverConn = DriverManager.getConnection(SERVER_URL, USER, PASS);
            Statement stmt = serverConn.createStatement();

            // 2. Tạo database nếu chưa tồn tại
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME +
                    " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
            System.out.println("Da Kiem Tra Database: " + DB_NAME);

            stmt.close();
            serverConn.close();

            // 3. Kết nối vào DB
            Connection dbConn = getConnection();
            Statement dbStmt = dbConn.createStatement();

            // === NHANVIEN ===
            dbStmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS NhanVien (" +
                    "MaNV VARCHAR(20) PRIMARY KEY," +
                    "TenNV VARCHAR(100) NOT NULL," +
                    "NgaySinh VARCHAR(20)," +
                    "GioiTinh VARCHAR(10)," +
                    "ChucVu VARCHAR(50)," +
                    "SoDienThoai VARCHAR(15)," +
                    "DiaChi VARCHAR(255)," +
                    "TaiKhoan VARCHAR(50) UNIQUE NOT NULL," +
                    "MatKhau VARCHAR(100) NOT NULL," +
                    "VaiTro VARCHAR(20) NOT NULL" +
                    ")"
            );

            // Admin mặc định
            dbStmt.executeUpdate(
                    "INSERT INTO NhanVien " +
                    "(MaNV, TenNV, NgaySinh, GioiTinh, ChucVu, SoDienThoai, DiaChi, TaiKhoan, MatKhau, VaiTro) " +
                    "SELECT 'ADMIN01','Quản trị hệ thống','1990-01-01','Nam','Admin','0123456789','Hệ thống','admin','admin123','ADMIN' " +
                    "WHERE NOT EXISTS (SELECT 1 FROM NhanVien WHERE TaiKhoan='admin')"
            );

            // === KHACHHANG ===
            dbStmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS KhachHang (" +
                    "MaKH VARCHAR(20) PRIMARY KEY," +
                    "TenKH VARCHAR(100)," +
                    "TheLoai VARCHAR(20)," +
                    "GioiTinh VARCHAR(10)," +
                    "SoDienThoai VARCHAR(15)," +
                    "DiaChi VARCHAR(255)" +
                    ")"
            );

            // === THUCDON ===
            dbStmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS ThucDon (" +
                    "MaMon VARCHAR(20) PRIMARY KEY," +
                    "TenMon VARCHAR(100)," +
                    "DonGia DOUBLE," +
                    "DonViTinh VARCHAR(20)" +
                    ")"
            );

            // === HOADON ===
            dbStmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS HoaDon (" +
                    "MaHD VARCHAR(20) PRIMARY KEY," +
                    "TenNV VARCHAR(100)," +
                    "TenKH VARCHAR(100)," +
                    "NgayLap VARCHAR(20)," +
                    "TongTien DOUBLE" +
                    ")"
            );

            // === CHITIETHOADON ===
            dbStmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS ChiTietHoaDon (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY," +
                    "MaHD VARCHAR(20)," +
                    "TenMon VARCHAR(100)," +
                    "SoLuong INT," +
                    "DonGia DOUBLE," +
                    "FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD) ON DELETE CASCADE" +
                    ")"
            );

            // === KHO ===
            dbStmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Kho (" +
                    "MaMon VARCHAR(20) PRIMARY KEY," +
                    "SoLuong INT DEFAULT 0," +
                    "FOREIGN KEY (MaMon) REFERENCES ThucDon(MaMon)" +
                    ")"
            );

            dbStmt.close();
            dbConn.close();

            System.out.println("Khoi tao Database & Bang thanh cong!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Loi khoi tao Database!");
        }
    }
}
