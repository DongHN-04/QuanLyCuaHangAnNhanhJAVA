package CH.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import CH.model.KhachHang; 

public class KhachHangView extends JPanel {
    private final Color TEAL_COLOR = new Color(0, 77, 77);
    private JTextField txtMaKH, txtTenKH, txtTheLoai, txtSDT, txtDiaChi, txtTimKiem;
    private JRadioButton rdoNam, rdoNu;
    private ButtonGroup btnGroupGender;
    private JTable tableKhachHang;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnSua, btnXoa, btnReset, btnTimKiemBtn;

    public KhachHangView() {
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        JPanel pnlFormContainer = new JPanel();
        pnlFormContainer.setLayout(new BoxLayout(pnlFormContainer, BoxLayout.Y_AXIS));
        pnlFormContainer.setBackground(TEAL_COLOR);
        pnlFormContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblFormTitle = new JLabel("Thông tin Khách hàng");
        lblFormTitle.setForeground(Color.WHITE);
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblFormTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlFormContainer.add(lblFormTitle);
        pnlFormContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(TEAL_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(pnlForm, gbc, 0, 0, "Mã khách hàng", txtMaKH = new JTextField(15));
        txtMaKH.setEditable(false);
        txtMaKH.setBackground(new Color(230, 230, 230));
        txtMaKH.setText("Tự động sinh");
        
        addFormRow(pnlForm, gbc, 2, 0, "Tên khách hàng", txtTenKH = new JTextField(15));
        
        addFormRow(pnlForm, gbc, 0, 1, "Thể loại", txtTheLoai = new JTextField(15));

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblGender = new JLabel("Giới tính");
        lblGender.setForeground(Color.WHITE);
        pnlForm.add(lblGender, gbc);

        JPanel pnlGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlGender.setBackground(TEAL_COLOR);
        rdoNam = new JRadioButton("Nam"); rdoNam.setBackground(TEAL_COLOR); rdoNam.setForeground(Color.WHITE);
        rdoNu = new JRadioButton("Nữ"); rdoNu.setBackground(TEAL_COLOR); rdoNu.setForeground(Color.WHITE);
        btnGroupGender = new ButtonGroup(); btnGroupGender.add(rdoNam); btnGroupGender.add(rdoNu);
        pnlGender.add(rdoNam); pnlGender.add(rdoNu);
        
        gbc.gridx = 3; gbc.weightx = 1.0;
        pnlForm.add(pnlGender, gbc);

        addFormRow(pnlForm, gbc, 0, 2, "Số điện thoại", txtSDT = new JTextField(15));
        addFormRow(pnlForm, gbc, 2, 2, "Địa chỉ", txtDiaChi = new JTextField(15));

        pnlFormContainer.add(pnlForm);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButtons.setBackground(TEAL_COLOR);
        btnThem = createStyledButton("Thêm"); btnSua = createStyledButton("Sửa"); btnXoa = createStyledButton("Xóa");
        btnReset = createStyledButton("RESET");
        pnlButtons.add(btnThem); pnlButtons.add(btnSua); pnlButtons.add(btnXoa); pnlButtons.add(btnReset);
        pnlFormContainer.add(pnlButtons);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSearch.setBackground(TEAL_COLOR);
        txtTimKiem = new JTextField(15);
        btnTimKiemBtn = createStyledButton("Tìm kiếm");
        JLabel lblSearch = new JLabel("Tên KH: "); lblSearch.setForeground(Color.WHITE);
        pnlSearch.add(lblSearch); pnlSearch.add(txtTimKiem); pnlSearch.add(btnTimKiemBtn);
        pnlFormContainer.add(pnlSearch);

        add(pnlFormContainer, BorderLayout.NORTH);

        String[] columnNames = {"Mã KH", "Tên khách hàng", "Thể loại", "Giới tính", "SĐT", "Địa chỉ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableKhachHang = new JTable(tableModel);
        add(new JScrollPane(tableKhachHang), BorderLayout.CENTER);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int x, int y, String labelText, Component field) {
        gbc.gridx = x; gbc.gridy = y; gbc.weightx = 0;
        JLabel label = new JLabel(labelText); label.setForeground(Color.WHITE);
        panel.add(label, gbc);
        gbc.gridx = x + 1; gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE); btn.setPreferredSize(new Dimension(100, 30));
        return btn;
    }

    public KhachHang getKhachHangInfo() {
        String gt = rdoNam.isSelected() ? "Nam" : "Nữ";
        // Truyền null cho email vì đã bỏ
        return new KhachHang(txtMaKH.getText(), txtTenKH.getText(), txtTheLoai.getText(), gt, txtSDT.getText(), txtDiaChi.getText());
    }

    public void fillForm(KhachHang kh) {
        txtMaKH.setText(kh.getMaKH());
        txtTenKH.setText(kh.getTenKH());
        txtTheLoai.setText(kh.getTheLoai());
        txtSDT.setText(kh.getSoDienThoai());
        txtDiaChi.setText(kh.getDiaChi());
        if ("Nam".equals(kh.getGioiTinh())) rdoNam.setSelected(true); else rdoNu.setSelected(true);
    }

    public void clearForm() {
        txtMaKH.setText("Tự động sinh"); txtTenKH.setText(""); txtTheLoai.setText("");
        txtSDT.setText(""); txtDiaChi.setText(""); btnGroupGender.clearSelection();
    }
    
    public JTable getTable() { return tableKhachHang; }
    public void clearTable() { tableModel.setRowCount(0); }
    public void addRowToTable(KhachHang kh) { tableModel.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getTheLoai(), kh.getGioiTinh(), kh.getSoDienThoai(), kh.getDiaChi()}); }
    public int getSelectedRow() { return tableKhachHang.getSelectedRow(); }
    public void addTableSelectionListener(javax.swing.event.ListSelectionListener l) { tableKhachHang.getSelectionModel().addListSelectionListener(l); }
    public void addThemListener(ActionListener al) { btnThem.addActionListener(al); }
    public void addSuaListener(ActionListener al) { btnSua.addActionListener(al); }
    public void addXoaListener(ActionListener al) { btnXoa.addActionListener(al); }
    public void addResetListener(ActionListener al) { btnReset.addActionListener(al); }
}