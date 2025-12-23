package CH.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import CH.model.MonAn;

/**
 * View đặt món (bán hàng)
 * Hiển thị danh sách thực đơn và giỏ hàng
 * Chỉ xử lý giao diện, không xử lý nghiệp vụ
 */
public class DatMonView extends JPanel {

    // Bảng thực đơn và bảng giỏ hàng
    private JTable tableMenu, tableGioHang;

    // Model dữ liệu cho hai bảng
    private DefaultTableModel modelMenu, modelGioHang;

    // Nút chức năng
    private JButton btnXoaMon, btnThanhToan;

    // Label hiển thị tổng tiền
    private JLabel lblTongTien;

    /**
     * Constructor khởi tạo giao diện đặt món
     */
    public DatMonView() {
        // Chia giao diện làm 2 cột: Thực đơn | Giỏ hàng
        setLayout(new GridLayout(1, 2, 10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        /* ===================== BÊN TRÁI: THỰC ĐƠN ===================== */
        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setBorder(new TitledBorder("THỰC ĐƠN"));

        // Các cột của bảng thực đơn
        String[] colMenu = {"Mã", "Tên món", "Đơn giá", "ĐVT", "Trạng thái"};

        // Model bảng thực đơn (không cho sửa)
        modelMenu = new DefaultTableModel(colMenu, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableMenu = new JTable(modelMenu);
        tableMenu.setRowHeight(30);
        pnlLeft.add(new JScrollPane(tableMenu), BorderLayout.CENTER);

        /* ===================== BÊN PHẢI: GIỎ HÀNG ===================== */
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBorder(new TitledBorder("GIỎ HÀNG ĐANG CHỌN"));

        // Các cột của bảng giỏ hàng
        String[] colGio = {"Tên món", "SL", "Đơn giá", "Thành tiền"};

        // Model bảng giỏ hàng
        modelGioHang = new DefaultTableModel(colGio, 0) {

            // Chỉ cho phép chỉnh sửa cột Số lượng
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }

            // Khai báo kiểu dữ liệu cho từng cột
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Integer.class; // Cột số lượng
                return String.class;
            }
        };

        tableGioHang = new JTable(modelGioHang);
        tableGioHang.setRowHeight(30);
        pnlRight.add(new JScrollPane(tableGioHang), BorderLayout.CENTER);

        /* ===================== FOOTER GIỎ HÀNG ===================== */
        // Khu vực hiển thị tổng tiền và nút thanh toán
        JPanel pnlFooter = new JPanel(new GridLayout(2, 1));

        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ", SwingConstants.RIGHT);
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongTien.setForeground(Color.RED);
        lblTongTien.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXoaMon = new JButton("Xóa món");

        btnThanhToan = new JButton("THANH TOÁN & IN HÓA ĐƠN");
        btnThanhToan.setBackground(new Color(255, 77, 77));
        btnThanhToan.setForeground(Color.BLACK);
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 12));

        pnlBtns.add(btnXoaMon);
        pnlBtns.add(btnThanhToan);

        pnlFooter.add(lblTongTien);
        pnlFooter.add(pnlBtns);
        pnlRight.add(pnlFooter, BorderLayout.SOUTH);

        /* ===================== ADD VÀO PANEL CHÍNH ===================== */
        add(pnlLeft);
        add(pnlRight);
    }

    /* ===================== XỬ LÝ DỮ LIỆU ===================== */

    /**
     * Thêm một món ăn vào bảng thực đơn
     */
    public void addMonToMenu(MonAn m) {
        modelMenu.addRow(m.toObjectArray());
    }

    /**
     * Thêm món ăn vào giỏ hàng
     * Nếu món đã tồn tại thì tăng số lượng
     */
    public void addMonToGio(String ten, double gia) {

        // Kiểm tra xem món đã có trong giỏ chưa
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            if (modelGioHang.getValueAt(i, 0).equals(ten)) {

                int slCu = Integer.parseInt(modelGioHang.getValueAt(i, 1).toString());
                int slMoi = slCu + 1;

                modelGioHang.setValueAt(slMoi, i, 1);
                modelGioHang.setValueAt(
                        String.format("%,.0f", slMoi * gia), i, 3
                );
                return;
            }
        }

        // Nếu chưa có thì thêm dòng mới
        modelGioHang.addRow(new Object[]{
                ten,
                1,
                String.format("%,.0f", gia),
                String.format("%,.0f", gia)
        });
    }

    /* ===================== GETTER ===================== */
    public DefaultTableModel getModelGioHang() { return modelGioHang; }
    public JTable getTableMenu() { return tableMenu; }
    public JTable getTableGioHang() { return tableGioHang; }

    /**
     * Cập nhật tổng tiền hiển thị
     */
    public void setTongTien(double tien) {
        lblTongTien.setText(
                "Tổng tiền: " + String.format("%,.0f VNĐ", tien)
        );
    }

    /* ===================== GẮN SỰ KIỆN ===================== */
    public void addXoaListener(ActionListener al) {
        btnXoaMon.addActionListener(al);
    }

    public void addThanhToanListener(ActionListener al) {
        btnThanhToan.addActionListener(al);
    }
}
