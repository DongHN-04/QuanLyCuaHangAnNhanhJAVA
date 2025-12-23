package CH.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import CH.model.NhanVien;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * View quản lý Nhân Viên
 * Chỉ xử lý giao diện hiển thị (MVC - View)
 */
public class NhanVienView extends JPanel {

    // ===== MÀU CHỦ ĐẠO =====
    private final Color TEAL_COLOR = new Color(0, 77, 77);

    // ===== KHAI BÁO COMPONENT =====
    private JTextField txtMaNV, txtTenNV, txtChucVu, txtSDT, txtDiaChi, txtTimKiem, txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboVaiTro;
    private JDateChooser txtNgaySinh;
    private JRadioButton rdoNam, rdoNu;
    private ButtonGroup btnGroupGender;
    private JTable tableNhanVien;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnSua, btnXoa, btnReset, btnTimKiem;

    public NhanVienView() {
        setLayout(new BorderLayout());
        initUI();
    }

    /**
     * Khởi tạo giao diện chính
     */
    private void initUI() {

        // ===== PANEL FORM TỔNG =====
        JPanel pnlFormContainer = new JPanel();
        pnlFormContainer.setLayout(new BoxLayout(pnlFormContainer, BoxLayout.Y_AXIS));
        pnlFormContainer.setBackground(TEAL_COLOR);
        pnlFormContainer.setBorder(new EmptyBorder(15, 25, 15, 25));

        // ===== TIÊU ĐỀ FORM =====
        JLabel lblFormTitle = new JLabel("Thông tin nhân viên");
        lblFormTitle.setForeground(Color.WHITE);
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblFormTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlFormContainer.add(lblFormTitle);
        pnlFormContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        // ===== FORM NHẬP LIỆU =====
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(TEAL_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kích thước chung cho các ô nhập
        Dimension fieldSize = new Dimension(180, 28);

        // ===== DÒNG 1: MÃ NV + TÊN NV =====
        addFormRow(pnlForm, gbc, 0, 0, "Mã nhân viên", txtMaNV = new JTextField(), fieldSize);
        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new Color(230, 230, 230));
        txtMaNV.setText("Tự động sinh");

        addFormRow(pnlForm, gbc, 0, 1, "Tên nhân viên", txtTenNV = new JTextField(), fieldSize);

        // ===== DÒNG 2: NGÀY SINH + GIỚI TÍNH =====
        addFormRow(pnlForm, gbc, 1, 0, "Ngày sinh", txtNgaySinh = new JDateChooser(), fieldSize);
        txtNgaySinh.setDateFormatString("dd/MM/yyyy");

        // Panel chọn giới tính
        JPanel pnlGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 13, 3));
        pnlGender.setBackground(TEAL_COLOR);
        pnlGender.setBorder(new LineBorder(Color.WHITE, 1));

        rdoNam = new JRadioButton("Nam");
        rdoNu = new JRadioButton("Nữ");
        rdoNam.setForeground(Color.WHITE);
        rdoNu.setForeground(Color.WHITE);
        rdoNam.setBackground(TEAL_COLOR);
        rdoNu.setBackground(TEAL_COLOR);

        btnGroupGender = new ButtonGroup();
        btnGroupGender.add(rdoNam);
        btnGroupGender.add(rdoNu);

        pnlGender.add(rdoNam);
        pnlGender.add(rdoNu);

        addFormRow(pnlForm, gbc, 1, 1, "Giới tính", pnlGender, fieldSize);

        // ===== DÒNG 3: CHỨC VỤ + SĐT =====
        addFormRow(pnlForm, gbc, 2, 0, "Chức vụ", txtChucVu = new JTextField(), fieldSize);
        addFormRow(pnlForm, gbc, 2, 1, "Số điện thoại", txtSDT = new JTextField(), fieldSize);

        // ===== DÒNG 4: TÀI KHOẢN + ĐỊA CHỈ =====
        addFormRow(pnlForm, gbc, 3, 0, "Tài khoản", txtTaiKhoan = new JTextField(), fieldSize);
        addFormRow(pnlForm, gbc, 3, 1, "Địa chỉ", txtDiaChi = new JTextField(), fieldSize);

        // ===== DÒNG 5: MẬT KHẨU + VAI TRÒ =====
        addFormRow(pnlForm, gbc, 4, 0, "Mật khẩu", txtMatKhau = new JPasswordField(), fieldSize);
        cboVaiTro = new JComboBox<>(new String[]{"Admin", "Nhân viên"});
        addFormRow(pnlForm, gbc, 4, 1, "Vai trò", cboVaiTro, fieldSize);

        pnlFormContainer.add(pnlForm);

        // ===== CÁC NÚT CHỨC NĂNG =====
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlButtons.setBackground(TEAL_COLOR);

        btnThem = createStyledButton("Thêm");
        btnSua = createStyledButton("Sửa");
        btnXoa = createStyledButton("Xóa");
        btnReset = createStyledButton("RESET");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnReset);

        pnlFormContainer.add(pnlButtons);

        // ===== TÌM KIẾM =====
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlSearch.setBackground(TEAL_COLOR);
        JLabel lblSearch = new JLabel("Tên NV:");
        lblSearch.setForeground(Color.WHITE);

        txtTimKiem = new JTextField(15);
        btnTimKiem = createStyledButton("Tìm kiếm");

        pnlSearch.add(lblSearch);
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);

        pnlFormContainer.add(pnlSearch);

        add(pnlFormContainer, BorderLayout.NORTH);

        // ===== BẢNG DANH SÁCH NHÂN VIÊN =====
        String[] columnNames = {
                "Mã NV", "Tên NV", "Ngày sinh", "Giới tính",
                "Chức vụ", "SĐT", "Địa chỉ", "Tài khoản", "Vai trò"
        };

        tableModel = new DefaultTableModel(columnNames, 0);
        tableNhanVien = new JTable(tableModel);
        tableNhanVien.setRowHeight(25);
        tableNhanVien.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableNhanVien.getTableHeader().setBackground(new Color(220, 220, 220));

        add(new JScrollPane(tableNhanVien), BorderLayout.CENTER);
    }

    /**
     * Hàm thêm 1 dòng vào form (Label + Field)
     */
    private void addFormRow(JPanel panel, GridBagConstraints gbc,
                            int row, int col, String label,
                            Component field, Dimension size) {

        gbc.gridx = col * 2;
        gbc.gridy = row;
        gbc.weightx = 0;

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        lbl.setPreferredSize(new Dimension(120, 25));
        panel.add(lbl, gbc);

        gbc.gridx = col * 2 + 1;
        gbc.weightx = 1;
        field.setPreferredSize(size);
        panel.add(field, gbc);
    }

    /**
     * Tạo nút giao diện thống nhất
     */
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(80, 30));
        btn.setFocusPainted(false);
        return btn;
    }

    /**
     * Lấy dữ liệu từ form và tạo đối tượng NhanVien
     */
    public NhanVien getNhanVienInfo() {
        String gt = rdoNam.isSelected() ? "Nam" : "Nữ";

        String strNgaySinh = "";
        if (txtNgaySinh.getDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            strNgaySinh = sdf.format(txtNgaySinh.getDate());
        }

        return new NhanVien(
                txtMaNV.getText(),
                txtTenNV.getText(),
                strNgaySinh,
                gt,
                txtChucVu.getText(),
                txtSDT.getText(),
                txtDiaChi.getText(),
                txtTaiKhoan.getText(),
                String.valueOf(txtMatKhau.getPassword()),
                cboVaiTro.getSelectedItem().toString()
        );
    }

    /**
     * Đổ dữ liệu nhân viên lên form
     */
    public void fillForm(NhanVien nv) {
        txtMaNV.setText(nv.getMaNV());
        txtTenNV.setText(nv.getTenNV());

        try {
            if (nv.getNgaySinh() != null && !nv.getNgaySinh().isEmpty()) {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(nv.getNgaySinh());
                txtNgaySinh.setDate(date);
            } else {
                txtNgaySinh.setDate(null);
            }
        } catch (Exception e) {
            txtNgaySinh.setDate(null);
        }

        txtChucVu.setText(nv.getChucVu());
        txtSDT.setText(nv.getSoDienThoai());
        txtDiaChi.setText(nv.getDiaChi());
        txtTaiKhoan.setText(nv.getTaiKhoan());
        txtMatKhau.setText(nv.getMatKhau());
        cboVaiTro.setSelectedItem(nv.getVaiTro());

        if ("Nam".equals(nv.getGioiTinh())) rdoNam.setSelected(true);
        else rdoNu.setSelected(true);
    }

    /**
     * Xóa trắng form
     */
    public void clearForm() {
        txtMaNV.setText("Tự động sinh");
        txtTenNV.setText("");
        txtNgaySinh.setDate(null);
        txtChucVu.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtTaiKhoan.setText("");
        txtMatKhau.setText("");
        cboVaiTro.setSelectedIndex(0);
        btnGroupGender.clearSelection();
    }

    // ===== GETTER & LISTENER CHO CONTROLLER =====
    public JTable getTable() { return tableNhanVien; }
    public void clearTable() { tableModel.setRowCount(0); }
    public void addRowToTable(NhanVien nv) { tableModel.addRow(nv.toObjectArray()); }
    public int getSelectedRow() { return tableNhanVien.getSelectedRow(); }
    public void removeRowInTable(int row) { tableModel.removeRow(row); }
    public JTextField getTxtTimKiem() { return txtTimKiem; }

    public void addTableSelectionListener(javax.swing.event.ListSelectionListener l) {
        tableNhanVien.getSelectionModel().addListSelectionListener(l);
    }
    public void addThemListener(ActionListener al) { btnThem.addActionListener(al); }
    public void addSuaListener(ActionListener al) { btnSua.addActionListener(al); }
    public void addXoaListener(ActionListener al) { btnXoa.addActionListener(al); }
    public void addResetListener(ActionListener al) { btnReset.addActionListener(al); }
    public void addTimKiemListener(ActionListener al) { btnTimKiem.addActionListener(al); }
}
