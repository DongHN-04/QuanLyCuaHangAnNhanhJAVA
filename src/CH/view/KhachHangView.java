package CH.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import CH.model.KhachHang;

/**
 * View quản lý khách hàng
 * Chịu trách nhiệm hiển thị form nhập liệu, bảng danh sách khách hàng
 * Không xử lý nghiệp vụ (logic nằm ở Controller)
 */
public class KhachHangView extends JPanel {

    // Màu chủ đạo của giao diện
    private final Color TEAL_COLOR = new Color(0, 77, 77);

    // Các ô nhập liệu
    private JTextField txtMaKH, txtTenKH, txtTheLoai, txtSDT, txtDiaChi, txtTimKiem;

    // Radio button chọn giới tính
    private JRadioButton rdoNam, rdoNu;
    private ButtonGroup btnGroupGender;

    // Bảng hiển thị khách hàng
    private JTable tableKhachHang;
    private DefaultTableModel tableModel;

    // Các nút chức năng
    private JButton btnThem, btnSua, btnXoa, btnReset, btnTimKiemBtn;

    /**
     * Constructor khởi tạo giao diện
     */
    public KhachHangView() {
        setLayout(new BorderLayout());
        initUI();
    }

    /* ===================== KHỞI TẠO GIAO DIỆN ===================== */
    private void initUI() {

        /* ===================== FORM NHẬP ===================== */
        JPanel pnlFormContainer = new JPanel();
        pnlFormContainer.setLayout(new BoxLayout(pnlFormContainer, BoxLayout.Y_AXIS));
        pnlFormContainer.setBackground(TEAL_COLOR);
        pnlFormContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Tiêu đề form
        JLabel lblFormTitle = new JLabel("Thông tin Khách hàng");
        lblFormTitle.setForeground(Color.WHITE);
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblFormTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlFormContainer.add(lblFormTitle);
        pnlFormContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel chứa các dòng nhập liệu
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(TEAL_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã khách hàng (tự động sinh)
        addFormRow(pnlForm, gbc, 0, 0, "Mã khách hàng", txtMaKH = new JTextField(15));
        txtMaKH.setEditable(false);
        txtMaKH.setBackground(new Color(230, 230, 230));
        txtMaKH.setText("Tự động sinh");

        // Tên khách hàng
        addFormRow(pnlForm, gbc, 2, 0, "Tên khách hàng", txtTenKH = new JTextField(15));

        // Thể loại khách hàng
        addFormRow(pnlForm, gbc, 0, 1, "Thể loại", txtTheLoai = new JTextField(15));

        // Giới tính
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;

        JLabel lblGender = new JLabel("Giới tính");
        lblGender.setForeground(Color.WHITE);
        pnlForm.add(lblGender, gbc);

        JPanel pnlGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlGender.setBackground(TEAL_COLOR);

        rdoNam = new JRadioButton("Nam");
        rdoNam.setBackground(TEAL_COLOR);
        rdoNam.setForeground(Color.WHITE);

        rdoNu = new JRadioButton("Nữ");
        rdoNu.setBackground(TEAL_COLOR);
        rdoNu.setForeground(Color.WHITE);

        btnGroupGender = new ButtonGroup();
        btnGroupGender.add(rdoNam);
        btnGroupGender.add(rdoNu);

        pnlGender.add(rdoNam);
        pnlGender.add(rdoNu);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        pnlForm.add(pnlGender, gbc);

        // Số điện thoại & địa chỉ
        addFormRow(pnlForm, gbc, 0, 2, "Số điện thoại", txtSDT = new JTextField(15));
        addFormRow(pnlForm, gbc, 2, 2, "Địa chỉ", txtDiaChi = new JTextField(15));

        pnlFormContainer.add(pnlForm);

        /* ===================== CÁC NÚT CHỨC NĂNG ===================== */
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
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

        /* ===================== TÌM KIẾM ===================== */
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSearch.setBackground(TEAL_COLOR);

        txtTimKiem = new JTextField(15);
        btnTimKiemBtn = createStyledButton("Tìm kiếm");

        JLabel lblSearch = new JLabel("Tên KH / Mã KH:");
        lblSearch.setForeground(Color.WHITE);

        pnlSearch.add(lblSearch);
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiemBtn);

        pnlFormContainer.add(pnlSearch);

        add(pnlFormContainer, BorderLayout.NORTH);

        /* ===================== BẢNG KHÁCH HÀNG ===================== */
        String[] columnNames = {
                "Mã KH", "Tên khách hàng", "Thể loại",
                "Giới tính", "SĐT", "Địa chỉ"
        };

        tableModel = new DefaultTableModel(columnNames, 0);
        tableKhachHang = new JTable(tableModel);

        add(new JScrollPane(tableKhachHang), BorderLayout.CENTER);
    }

    /* ===================== HÀM HỖ TRỢ ===================== */
    // Thêm một dòng gồm label + field vào form
    private void addFormRow(JPanel panel, GridBagConstraints gbc,
                            int x, int y, String labelText, Component field) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0;

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);

        gbc.gridx = x + 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    // Tạo nút bấm theo style chung
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE);
        btn.setPreferredSize(new Dimension(100, 30));
        return btn;
    }

    /* ===================== XỬ LÝ DỮ LIỆU ===================== */
    // Lấy dữ liệu từ form để tạo đối tượng Khách Hàng
    public KhachHang getKhachHangInfo() {
        String gioiTinh = rdoNam.isSelected() ? "Nam" : "Nữ";
        return new KhachHang(
                txtMaKH.getText(),
                txtTenKH.getText(),
                txtTheLoai.getText(),
                gioiTinh,
                txtSDT.getText(),
                txtDiaChi.getText()
        );
    }

    // Đổ dữ liệu khách hàng lên form khi click bảng
    public void fillForm(KhachHang kh) {
        txtMaKH.setText(kh.getMaKH());
        txtTenKH.setText(kh.getTenKH());
        txtTheLoai.setText(kh.getTheLoai());
        txtSDT.setText(kh.getSoDienThoai());
        txtDiaChi.setText(kh.getDiaChi());

        if ("Nam".equals(kh.getGioiTinh()))
            rdoNam.setSelected(true);
        else
            rdoNu.setSelected(true);
    }

    // Xóa trắng form
    public void clearForm() {
        txtMaKH.setText("Tự động sinh");
        txtTenKH.setText("");
        txtTheLoai.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        btnGroupGender.clearSelection();
    }

    /* ===================== BẢNG & TÌM KIẾM ===================== */
    public JTextField getTxtSearch() { return txtTimKiem; }

    public JTable getTable() { return tableKhachHang; }

    public void clearTable() { tableModel.setRowCount(0); }

    public void addRowToTable(KhachHang kh) {
        tableModel.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getTheLoai(),
                kh.getGioiTinh(),
                kh.getSoDienThoai(),
                kh.getDiaChi()
        });
    }

    public int getSelectedRow() { return tableKhachHang.getSelectedRow(); }

    public void addTableSelectionListener(
            javax.swing.event.ListSelectionListener l) {
        tableKhachHang.getSelectionModel().addListSelectionListener(l);
    }

    /* ===================== GẮN SỰ KIỆN ===================== */
    public void addThemListener(ActionListener al) { btnThem.addActionListener(al); }
    public void addSuaListener(ActionListener al) { btnSua.addActionListener(al); }
    public void addXoaListener(ActionListener al) { btnXoa.addActionListener(al); }
    public void addResetListener(ActionListener al) { btnReset.addActionListener(al); }
    public void addTimKiemListener(ActionListener al) { btnTimKiemBtn.addActionListener(al); }
}
