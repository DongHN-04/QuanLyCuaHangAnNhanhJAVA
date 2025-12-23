package CH.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import CH.model.MonAn;

/**
 * View quản lý Thực đơn
 * Chỉ đảm nhiệm hiển thị giao diện (MVC - View)
 */
public class ThucDonView extends JPanel {

    // ===== KHAI BÁO COMPONENT =====
    private JTextField txtMaMon, txtTenMon, txtDonGia, txtDVT;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnReset;

    public ThucDonView() {
        setLayout(new BorderLayout());

        /* ===================== FORM NHẬP LIỆU ===================== */
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(new Color(0, 77, 77));
        pnlForm.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã món (tự động sinh)
        addInput(pnlForm, gbc, 0, 0, "Mã món", txtMaMon = new JTextField(15));
        txtMaMon.setEditable(false);
        txtMaMon.setText("Tự động");

        // Tên món
        addInput(pnlForm, gbc, 0, 1, "Tên món", txtTenMon = new JTextField(15));

        // Đơn giá
        addInput(pnlForm, gbc, 0, 2, "Đơn giá", txtDonGia = new JTextField(15));

        // Đơn vị tính
        addInput(pnlForm, gbc, 0, 3, "Đơn vị tính", txtDVT = new JTextField(15));

        /* ===================== BUTTONS ===================== */
        JPanel pnlBtn = new JPanel();
        pnlBtn.setBackground(new Color(0, 77, 77));

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnReset = new JButton("Reset");

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnReset);

        // Gộp form + button
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlForm, BorderLayout.CENTER);
        pnlNorth.add(pnlBtn, BorderLayout.SOUTH);

        add(pnlNorth, BorderLayout.NORTH);

        /* ===================== TABLE DANH SÁCH MÓN ===================== */
        String[] cols = {"Mã món", "Tên món", "Đơn giá", "Đơn vị tính"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(25);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Hàm hỗ trợ thêm 1 dòng nhập liệu (Label + Component)
     */
    private void addInput(JPanel p, GridBagConstraints gbc,
                          int x, int y, String lbl, Component cmp) {
        gbc.gridx = x;
        gbc.gridy = y;

        JLabel l = new JLabel(lbl);
        l.setForeground(Color.WHITE);
        p.add(l, gbc);

        gbc.gridx = x + 1;
        p.add(cmp, gbc);
    }

    /**
     * Lấy dữ liệu từ form và tạo đối tượng Món ăn
     */
    public MonAn getMonAnInfo() {
        double gia = 0;
        try {
            gia = Double.parseDouble(txtDonGia.getText());
        } catch (Exception e) {
            // Nếu nhập sai thì mặc định = 0
        }
        return new MonAn(
                txtMaMon.getText(),
                txtTenMon.getText(),
                gia,
                txtDVT.getText()
        );
    }

    /**
     * Đổ dữ liệu món ăn lên form khi chọn trong bảng
     */
    public void fillForm(MonAn m) {
        txtMaMon.setText(m.getMaMon());
        txtTenMon.setText(m.getTenMon());
        txtDonGia.setText(String.format("%.0f", m.getDonGia()));
        txtDVT.setText(m.getDonViTinh());
    }

    /**
     * Xóa trắng form nhập liệu
     */
    public void clearForm() {
        txtMaMon.setText("Tự động");
        txtTenMon.setText("");
        txtDonGia.setText("");
        txtDVT.setText("");
    }

    /* ===================== GETTER & LISTENER ===================== */

    // Thêm 1 dòng vào bảng
    public void addRow(MonAn m) {
        model.addRow(m.toObjectArray());
    }

    // Xóa toàn bộ dữ liệu bảng
    public void clearTable() {
        model.setRowCount(0);
    }

    // Lấy dòng đang được chọn
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public JTable getTable() {
        return table;
    }

    // Listener cho Controller
    public void addThemListener(ActionListener al) {
        btnThem.addActionListener(al);
    }

    public void addSuaListener(ActionListener al) {
        btnSua.addActionListener(al);
    }

    public void addXoaListener(ActionListener al) {
        btnXoa.addActionListener(al);
    }

    public void addResetListener(ActionListener al) {
        btnReset.addActionListener(al);
    }
}
