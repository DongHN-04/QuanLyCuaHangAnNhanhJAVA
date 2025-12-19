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

public class NhanVienView extends JPanel {

    // Colors
    private final Color TEAL_COLOR = new Color(0, 77, 77);
    
    // Components
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
   
    private void initUI() {
        // ===== PANEL FORM TỔNG =====
        JPanel pnlFormContainer = new JPanel();
        pnlFormContainer.setLayout(new BoxLayout(pnlFormContainer, BoxLayout.Y_AXIS));
        pnlFormContainer.setBackground(TEAL_COLOR);
        pnlFormContainer.setBorder(new EmptyBorder(15, 25, 15, 25));

        // ===== TITLE =====
        JLabel lblFormTitle = new JLabel("Thông tin nhân viên");
        lblFormTitle.setForeground(Color.WHITE);
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblFormTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlFormContainer.add(lblFormTitle);
        pnlFormContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        // ===== FORM GRID =====
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(TEAL_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kích thước chung
        Dimension fieldSize = new Dimension(180, 28);

        // ==== Dòng 1: Mã NV + Tên NV ====
        addFormRow(pnlForm, gbc, 0, 0, "Mã nhân viên", txtMaNV = new JTextField(), fieldSize);
        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new Color(230, 230, 230));
        txtMaNV.setText("Tự động sinh");

        addFormRow(pnlForm, gbc, 0, 1, "Tên nhân viên", txtTenNV = new JTextField(), fieldSize);

        // ==== Dòng 2: Ngày sinh + Giới tính ====
        addFormRow(pnlForm, gbc, 1, 0, "Ngày sinh", txtNgaySinh = new JDateChooser(), fieldSize);
        txtNgaySinh.setDateFormatString("dd/MM/yyyy");

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

        // ==== Dòng 3: Chức vụ + SĐT ====
        addFormRow(pnlForm, gbc, 2, 0, "Chức vụ", txtChucVu = new JTextField(), fieldSize);
        addFormRow(pnlForm, gbc, 2, 1, "Số điện thoại", txtSDT = new JTextField(), fieldSize);

        // ==== Dòng 4: Địa chỉ + Tài khoản ====
        addFormRow(pnlForm, gbc, 3, 0, "Tài khoản", txtTaiKhoan = new JTextField(), fieldSize);
        addFormRow(pnlForm, gbc, 3, 1, "Địa chỉ", txtDiaChi = new JTextField(), fieldSize);

        // ==== Dòng 5: Mật khẩu + Vai trò ====
        addFormRow(pnlForm, gbc, 4, 0, "Mật khẩu", txtMatKhau = new JPasswordField(), fieldSize);
        cboVaiTro = new JComboBox<>(new String[]{"Admin", "Nhân viên"});
        addFormRow(pnlForm, gbc, 4, 1, "Vai trò", cboVaiTro, fieldSize);

        pnlFormContainer.add(pnlForm);

        // ===== BUTTONS =====
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

        // ===== SEARCH =====
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

        // ===== TABLE =====
        String[] columnNames = {"Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Giới tính", "Chức vụ", "SĐT", "Địa chỉ", "Tài khoản", "Vai trò"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableNhanVien = new JTable(tableModel);
        tableNhanVien.setRowHeight(25);
        tableNhanVien.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableNhanVien.getTableHeader().setBackground(new Color(220, 220, 220));
        JScrollPane scrollPane = new JScrollPane(tableNhanVien);
        add(scrollPane, BorderLayout.CENTER);
    }


    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, int col, String label, Component field, Dimension size) {
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

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE); btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(80, 30)); btn.setFocusPainted(false);
        return btn;
    }

// [SỬA] Cập nhật hàm lấy dữ liệu từ Form
    public NhanVien getNhanVienInfo() {
        String gt = rdoNam.isSelected() ? "Nam" : "Nữ";
        
        String strNgaySinh = "";
        if (txtNgaySinh.getDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            strNgaySinh = sdf.format(txtNgaySinh.getDate());
        }

        return new NhanVien(txtMaNV.getText(), txtTenNV.getText(), strNgaySinh, gt, txtChucVu.getText(), txtSDT.getText(), txtDiaChi.getText(), 
        txtTaiKhoan.getText(), String.valueOf(txtMatKhau.getPassword()), cboVaiTro.getSelectedItem().toString());
    }

    public void fillForm(NhanVien nv) {
            txtMaNV.setText(nv.getMaNV());
            txtTenNV.setText(nv.getTenNV());

            // Chuyển chuỗi String từ Model thành Date để hiển thị lên JDateChooser
            try {
                if (nv.getNgaySinh() != null && !nv.getNgaySinh().isEmpty()) {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(nv.getNgaySinh());
                    txtNgaySinh.setDate(date);
                } else {
                    txtNgaySinh.setDate(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                txtNgaySinh.setDate(null);
            }

            txtChucVu.setText(nv.getChucVu());
            txtSDT.setText(nv.getSoDienThoai());
            txtDiaChi.setText(nv.getDiaChi());
            txtTaiKhoan.setText(nv.getTaiKhoan());
            txtMatKhau.setText(nv.getMatKhau());
            cboVaiTro.setSelectedItem(nv.getVaiTro());
            if (nv.getGioiTinh() != null && nv.getGioiTinh().equals("Nam")) rdoNam.setSelected(true); else rdoNu.setSelected(true);
    }

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
    
    public JTable getTable() { 
        return tableNhanVien; 
    }
    public void clearTable() { 
        tableModel.setRowCount(0); 
    }
    public void addRowToTable(NhanVien nv) { 
        tableModel.addRow(nv.toObjectArray()); 
    }
    public int getSelectedRow() { 
        return tableNhanVien.getSelectedRow(); 
    }
    public void updateRowInTable(NhanVien nv, int row) {
        tableModel.setValueAt(nv.getMaNV(), row, 0);
        tableModel.setValueAt(nv.getTenNV(), row, 1);
        tableModel.setValueAt(nv.getNgaySinh(), row, 2);
        tableModel.setValueAt(nv.getGioiTinh(), row, 3);
        tableModel.setValueAt(nv.getChucVu(), row, 4);
        tableModel.setValueAt(nv.getSoDienThoai(), row, 5);
        tableModel.setValueAt(nv.getDiaChi(), row, 6);
        tableModel.setValueAt(nv.getTaiKhoan(), row, 7);
        tableModel.setValueAt(nv.getVaiTro(), row, 8);
    }
    public void removeRowInTable(int row) { tableModel.removeRow(row); }
    public void addTableSelectionListener(javax.swing.event.ListSelectionListener listener) { tableNhanVien.getSelectionModel().addListSelectionListener(listener); }
    public void addThemListener(ActionListener al) { btnThem.addActionListener(al); }
    public void addSuaListener(ActionListener al) { btnSua.addActionListener(al); }
    public void addXoaListener(ActionListener al) { btnXoa.addActionListener(al); }
    public void addResetListener(ActionListener al) { btnReset.addActionListener(al); }
    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public void addTimKiemListener(ActionListener al) {
        btnTimKiem.addActionListener(al);
    }

    public Object getValueAt(int row, int i) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}