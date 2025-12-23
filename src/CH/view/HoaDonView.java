package CH.view;

import CH.model.HoaDon;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HoaDonView extends JPanel {

    /* ===================== HẰNG SỐ GIAO DIỆN ===================== */
    // Màu chủ đạo của giao diện
    private static final Color MAIN_COLOR = new Color(0, 77, 77);

    // Font tiêu đề
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);

    // Font nhãn (label)
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    // Font cho nút bấm
    private static final Font BTN_FONT = new Font("Segoe UI", Font.BOLD, 12);

    /* ===================== KHAI BÁO COMPONENT ===================== */
    // Ô nhập liệu
    private JTextField txtMaHD, txtNhanVien, txtKhachHang;

    // Ô chọn ngày lập hóa đơn
    private JDateChooser dcNgayLap;

    // Bảng hiển thị danh sách hóa đơn
    private JTable tblHoaDon;
    private DefaultTableModel tblModel;

    // Các nút chức năng
    private JButton btnThem, btnSua, btnXoa, btnReset, btnXemChiTiet;

    /* ===================== HÀM KHỞI TẠO ===================== */
    public HoaDonView() {
        setLayout(new BorderLayout());
        initUI();
    }

    /* ===================== KHỞI TẠO GIAO DIỆN ===================== */
    private void initUI() {
        add(createTopPanel(), BorderLayout.NORTH);   // Khu vực form nhập
        add(createTablePanel(), BorderLayout.CENTER); // Khu vực bảng
    }

    /* ===================== PANEL PHÍA TRÊN ===================== */
    private JPanel createTopPanel() {
        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
        pnlTop.setBackground(MAIN_COLOR);
        pnlTop.setBorder(new EmptyBorder(10, 20, 10, 20));

        pnlTop.add(createTitle());          // Tiêu đề
        pnlTop.add(Box.createVerticalStrut(15));
        pnlTop.add(createFormArea());       // Khu vực nhập liệu

        return pnlTop;
    }

    // Tạo tiêu đề giao diện
    private JLabel createTitle() {
        JLabel lbl = new JLabel("Thông tin hóa đơn");
        lbl.setFont(TITLE_FONT);
        lbl.setForeground(Color.WHITE);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    /* ===================== KHU VỰC FORM ===================== */
    private JPanel createFormArea() {
        JPanel pnlFormArea = new JPanel(new BorderLayout());
        pnlFormArea.setBackground(MAIN_COLOR);

        pnlFormArea.add(createLeftPanel(), BorderLayout.CENTER); // Form + nút CRUD
        pnlFormArea.add(createRightPanel(), BorderLayout.EAST);  // Nút xem chi tiết

        return pnlFormArea;
    }

    /* ===================== BÊN TRÁI ===================== */
    private JPanel createLeftPanel() {
        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setBackground(MAIN_COLOR);

        pnlLeft.add(createInputPanel(), BorderLayout.CENTER); // Các ô nhập
        pnlLeft.add(createCRUDPanel(), BorderLayout.SOUTH);   // Nút thêm/sửa/xóa

        return pnlLeft;
    }

    // Panel chứa các ô nhập liệu
    private JPanel createInputPanel() {
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBackground(MAIN_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Khởi tạo ô nhập
        txtMaHD = createTextField(false, "Tự động sinh");
        txtNhanVien = createTextField(true, "");
        txtKhachHang = createTextField(true, "");

        dcNgayLap = new JDateChooser();
        dcNgayLap.setDateFormatString("dd/MM/yyyy");
        dcNgayLap.setPreferredSize(new Dimension(150, 25));

        // Thêm từng dòng vào form
        addInput(pnlInput, gbc, 0, 0, "Mã hóa đơn", txtMaHD);
        addInput(pnlInput, gbc, 0, 1, "Nhân viên lập", txtNhanVien);
        addInput(pnlInput, gbc, 0, 2, "Khách hàng", txtKhachHang);
        addInput(pnlInput, gbc, 0, 3, "Ngày lập", dcNgayLap);

        return pnlInput;
    }

    // Panel chứa các nút CRUD
    private JPanel createCRUDPanel() {
        JPanel pnlCRUD = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlCRUD.setBackground(MAIN_COLOR);

        btnThem = createButton("Thêm");
        btnSua = createButton("Sửa");
        btnXoa = createButton("Xóa");
        btnReset = createButton("Reset");

        pnlCRUD.add(btnThem);
        pnlCRUD.add(btnSua);
        pnlCRUD.add(btnXoa);
        pnlCRUD.add(btnReset);

        return pnlCRUD;
    }

    /* ===================== BÊN PHẢI ===================== */
    private JPanel createRightPanel() {
        JPanel pnlRight = new JPanel(new GridBagLayout());
        pnlRight.setBackground(MAIN_COLOR);

        // Nút xem chi tiết hóa đơn
        btnXemChiTiet = new JButton("<html><center>Xem chi tiết<br>hóa đơn</center></html>");
        btnXemChiTiet.setPreferredSize(new Dimension(120, 60));
        btnXemChiTiet.setFont(BTN_FONT);
        btnXemChiTiet.setBackground(Color.WHITE);
        btnXemChiTiet.setForeground(MAIN_COLOR);
        btnXemChiTiet.setFocusPainted(false);
        btnXemChiTiet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnlRight.add(btnXemChiTiet);
        return pnlRight;
    }

    /* ===================== BẢNG HÓA ĐƠN ===================== */
    private JScrollPane createTablePanel() {
        String[] columns = {"Mã hóa đơn", "Nhân viên lập", "Tên khách hàng", "Ngày lập", "Tổng tiền"};
        tblModel = new DefaultTableModel(columns, 0);

        tblHoaDon = new JTable(tblModel);
        tblHoaDon.setRowHeight(30);
        tblHoaDon.getTableHeader().setFont(BTN_FONT);
        tblHoaDon.getTableHeader().setBackground(new Color(230, 230, 230));

        return new JScrollPane(tblHoaDon);
    }

    /* ===================== HÀM HỖ TRỢ ===================== */
    // Tạo JTextField có thể chỉnh sửa hoặc không
    private JTextField createTextField(boolean editable, String text) {
        JTextField txt = new JTextField(15);
        txt.setEditable(editable);
        txt.setText(text);
        if (!editable) txt.setBackground(new Color(230, 230, 230));
        return txt;
    }

    // Tạo nút bấm chuẩn giao diện
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(BTN_FONT);
        btn.setPreferredSize(new Dimension(80, 30));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Thêm 1 dòng gồm label + component vào form
    private void addInput(JPanel p, GridBagConstraints gbc, int x, int y, String label, Component comp) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0;

        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(Color.WHITE);
        p.add(lbl, gbc);

        gbc.gridx = x + 1;
        gbc.weightx = 1;
        p.add(comp, gbc);
    }

    /* ===================== XỬ LÝ DỮ LIỆU ===================== */
    // Lấy dữ liệu từ form để tạo đối tượng Hóa Đơn
    public HoaDon getHoaDonInfo() {
        String ngayLap = "";
        if (dcNgayLap.getDate() != null) {
            ngayLap = new SimpleDateFormat("dd/MM/yyyy").format(dcNgayLap.getDate());
        }
        return new HoaDon(
                txtMaHD.getText(),
                txtNhanVien.getText(),
                txtKhachHang.getText(),
                ngayLap,
                0
        );
    }

    // Đổ dữ liệu hóa đơn lên form khi click bảng
    public void fillForm(HoaDon hd) {
        txtMaHD.setText(hd.getMaHD());
        txtNhanVien.setText(hd.getTenNV());
        txtKhachHang.setText(hd.getTenKH());

        try {
            Date d = new SimpleDateFormat("dd/MM/yyyy").parse(hd.getNgayLap());
            dcNgayLap.setDate(d);
        } catch (Exception e) {
            dcNgayLap.setDate(null);
        }
    }

    // Xóa trắng form nhập
    public void clearForm() {
        txtMaHD.setText("Tự động sinh");
        txtNhanVien.setText("");
        txtKhachHang.setText("");
        dcNgayLap.setDate(null);
    }

    /* ===================== XỬ LÝ BẢNG ===================== */
    public void addRow(HoaDon hd) {
        tblModel.addRow(hd.toObjectArray());
    }

    public void clearTable() {
        tblModel.setRowCount(0);
    }

    public int getSelectedRow() {
        return tblHoaDon.getSelectedRow();
    }

    public JTable getTable() {
        return tblHoaDon;
    }

    /* ===================== GẮN SỰ KIỆN ===================== */
    public void addThemListener(ActionListener l) { btnThem.addActionListener(l); }
    public void addSuaListener(ActionListener l) { btnSua.addActionListener(l); }
    public void addXoaListener(ActionListener l) { btnXoa.addActionListener(l); }
    public void addResetListener(ActionListener l) { btnReset.addActionListener(l); }
    public void addXemChiTietListener(ActionListener l) { btnXemChiTiet.addActionListener(l); }
}
