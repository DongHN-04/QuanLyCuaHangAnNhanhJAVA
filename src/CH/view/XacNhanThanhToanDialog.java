package CH.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Dialog xác nhận thanh toán
 * Hiển thị thông tin khách hàng, danh sách món đã chọn và tổng tiền
 * Chỉ đảm nhiệm hiển thị (MVC - View)
 */
public class XacNhanThanhToanDialog extends JDialog {

    // ===== KHAI BÁO COMPONENT =====
    private JTextField txtTenKhach;          // Tên khách hàng
    private JLabel lblTongTienFinal;         // Hiển thị tổng tiền
    private JButton btnXacNhanIn;            // Nút xác nhận & in hóa đơn

    /**
     * Constructor tạo dialog xác nhận thanh toán
     *
     * @param parent       Frame cha
     * @param modelGioHang Model giỏ hàng hiện tại
     * @param tongTien     Tổng tiền thanh toán
     */
    public XacNhanThanhToanDialog(JFrame parent,
                                  DefaultTableModel modelGioHang,
                                  double tongTien) {

        super(parent, "Xác nhận Thanh Toán", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        /* ===================== HEADER ===================== */
        JPanel pnlHead = new JPanel(new GridLayout(2, 1));
        pnlHead.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnlHead.add(new JLabel("Tên khách hàng:"));
        txtTenKhach = new JTextField("Khách vãng lai");
        pnlHead.add(txtTenKhach);

        add(pnlHead, BorderLayout.NORTH);

        /* ===================== TABLE REVIEW GIỎ HÀNG ===================== */
        JTable tblReview = new JTable();
        DefaultTableModel modelReview = new DefaultTableModel();

        // Copy tiêu đề cột từ giỏ hàng
        for (int i = 0; i < modelGioHang.getColumnCount(); i++) {
            modelReview.addColumn(modelGioHang.getColumnName(i));
        }

        // Copy dữ liệu giỏ hàng sang bảng xem lại
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            Object[] row = new Object[modelGioHang.getColumnCount()];
            for (int j = 0; j < modelGioHang.getColumnCount(); j++) {
                row[j] = modelGioHang.getValueAt(i, j);
            }
            modelReview.addRow(row);
        }

        tblReview.setModel(modelReview);
        add(new JScrollPane(tblReview), BorderLayout.CENTER);

        /* ===================== FOOTER ===================== */
        JPanel pnlFoot = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlFoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Label tổng tiền
        lblTongTienFinal = new JLabel(
                "Tổng cộng: " + String.format("%,.0f VNĐ", tongTien),
                SwingConstants.CENTER
        );
        lblTongTienFinal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongTienFinal.setForeground(Color.RED);

        // Nút xác nhận & in hóa đơn
        btnXacNhanIn = new JButton("XÁC NHẬN & IN");
        btnXacNhanIn.setBackground(new Color(0, 100, 0));
        btnXacNhanIn.setForeground(Color.RED);
        btnXacNhanIn.setFont(new Font("Arial", Font.BOLD, 14));
        btnXacNhanIn.setPreferredSize(new Dimension(100, 40));

        pnlFoot.add(lblTongTienFinal);
        pnlFoot.add(btnXacNhanIn);

        add(pnlFoot, BorderLayout.SOUTH);
    }

    /* ===================== GETTER & LISTENER ===================== */

    /**
     * Lấy tên khách hàng nhập trong dialog
     */
    public String getTenKhach() {
        return txtTenKhach.getText();
    }

    /**
     * Gắn sự kiện xác nhận thanh toán cho Controller
     */
    public void addXacNhanListener(ActionListener al) {
        btnXacNhanIn.addActionListener(al);
    }
}
