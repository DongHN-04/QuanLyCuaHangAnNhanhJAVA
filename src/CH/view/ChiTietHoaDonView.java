package CH.view;

import CH.model.ChiTietHoaDon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * View hiển thị chi tiết hóa đơn
 * Được mở dưới dạng JDialog (popup)
 * Chỉ chịu trách nhiệm hiển thị dữ liệu
 */
public class ChiTietHoaDonView extends JDialog {

    // Bảng hiển thị danh sách món ăn trong hóa đơn
    private JTable tableChiTiet;
    private DefaultTableModel tableModel;

    // Các label hiển thị thông tin chung
    private JLabel lblMaHD, lblKhachHang, lblTongTien;

    /**
     * Constructor
     * @param parent Frame cha (HoaDonView)
     */
    public ChiTietHoaDonView(JFrame parent) {
        super(parent, "Chi tiết hóa đơn", true); // true = modal
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        /* ===================== HEADER ===================== */
        // Khu vực tiêu đề và mã hóa đơn
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("Chi tiết hóa đơn", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 77, 77));

        lblMaHD = new JLabel("Mã hóa đơn: ...");
        lblMaHD.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        pnlHeader.add(lblTitle, BorderLayout.CENTER);
        pnlHeader.add(lblMaHD, BorderLayout.SOUTH);
        add(pnlHeader, BorderLayout.NORTH);

        /* ===================== TABLE ===================== */
        // Bảng chi tiết món ăn
        String[] columns = {"Tên món ăn", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columns, 0);
        tableChiTiet = new JTable(tableModel);

        tableChiTiet.setRowHeight(30);
        tableChiTiet.getTableHeader().setBackground(new Color(230, 230, 230));
        tableChiTiet.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tableChiTiet);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        /* ===================== FOOTER ===================== */
        // Khu vực hiển thị khách hàng và tổng tiền
        JPanel pnlFooter = new JPanel(new GridLayout(1, 2));
        pnlFooter.setBackground(Color.WHITE);
        pnlFooter.setBorder(new EmptyBorder(15, 20, 15, 20));

        lblKhachHang = new JLabel("Khách hàng: ...");
        lblTongTien = new JLabel("Tổng tiền: ...", SwingConstants.RIGHT);
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 14));

        pnlFooter.add(lblKhachHang);
        pnlFooter.add(lblTongTien);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    /**
     * Phương thức để Controller gọi và đổ dữ liệu vào View
     * @param maHD mã hóa đơn
     * @param tenKH tên khách hàng
     * @param tongTien tổng tiền (từ hóa đơn)
     * @param listChiTiet danh sách chi tiết hóa đơn
     */
    public void setDetails(String maHD, String tenKH, double tongTien,
                           List<ChiTietHoaDon> listChiTiet) {

        // Hiển thị thông tin chung
        lblMaHD.setText("Mã hóa đơn: " + maHD);
        lblKhachHang.setText("Khách hàng: " + tenKH);

        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Tính lại tổng tiền từ danh sách chi tiết
        double tongTienTinhToan = 0;

        for (ChiTietHoaDon item : listChiTiet) {
            Object[] rowData = item.toObjectArray();
            tableModel.addRow(rowData);

            try {
                // Lấy cột thành tiền (index 3) và loại bỏ ký tự phân cách
                String thanhTienStr = rowData[3].toString()
                        .replace(",", "")
                        .replace(".", "");

                tongTienTinhToan += Double.parseDouble(thanhTienStr);
            } catch (Exception e) {
                // Bỏ qua nếu lỗi định dạng
            }
        }

        // Hiển thị tổng tiền cuối cùng
        lblTongTien.setText(
                "Tổng tiền: " + String.format("%,.0f VNĐ", tongTienTinhToan)
        );
    }
}
