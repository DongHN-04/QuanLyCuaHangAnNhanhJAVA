package CH.view;

import CH.controller.KhachHangController;
import CH.controller.TrangChuController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class MainNhanVienView extends JFrame {

    private final Color SIDEBAR_COLOR = new Color(0, 91, 110);
    private final Color HOVER_COLOR = new Color(0, 110, 130);
    private final Color ACCENT_RED = new Color(255, 77, 77);

    private CardLayout cardLayout;
    private JPanel pnlContent;
    private Map<String, JButton> menuButtons = new HashMap<>();

    // View con
    private TrangChuView trangChuView;
    private KhachHangView khachHangView;
    private HoaDonView hoaDonView;
    private DatMonView datMonView;
    private ThucDonView thucDonView;

    public MainNhanVienView() {
        setTitle("Hệ Thống Quản Lý Cửa Hàng Đồ Ăn Nhanh");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initHeader();
        initContent();
        initSidebar();

        cardLayout.show(pnlContent, "Trang chủ");
        updateActiveButton("Trang chủ");
    }

    // ================= HEADER =================
    private void initHeader() {
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblTitle = new JLabel("Hệ Thống Quản Lý Cửa Hàng Đồ Ăn Nhanh");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        add(pnlHeader, BorderLayout.NORTH);
    }

    // ================= CONTENT =================
    private void initContent() {
        cardLayout = new CardLayout();
        pnlContent = new JPanel(cardLayout);

        trangChuView = new TrangChuView();
        khachHangView = new KhachHangView();
        datMonView = new DatMonView();
        thucDonView = new ThucDonView();
        hoaDonView = new HoaDonView();

        new TrangChuController(trangChuView);
        new KhachHangController(khachHangView);
        

        pnlContent.add(trangChuView, "Trang chủ");
        pnlContent.add(khachHangView, "Khách Hàng");
        pnlContent.add(datMonView, "Đặt Món");
        pnlContent.add(thucDonView, "Thực Đơn");
        pnlContent.add(hoaDonView, "Hóa đơn");

        add(pnlContent, BorderLayout.CENTER);
    }

    // ================= SIDEBAR =================
    private void initSidebar() {
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setPreferredSize(new Dimension(220, 0));
        pnlSidebar.setBackground(SIDEBAR_COLOR);
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));

        JLabel lblRole = new JLabel("NHÂN VIÊN");
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblRole.setForeground(Color.WHITE);
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        pnlSidebar.add(lblRole);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        String[] menuItems = {
            "Trang chủ",
            "Khách Hàng",
            "Đặt Món",
            "Thực Đơn",
            "Hóa đơn",
            "Thoát"
        };

        for (String item : menuItems) {
            JButton btn = createMenuButton(item);
            menuButtons.put(item, btn);
            pnlSidebar.add(btn);
            pnlSidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        add(pnlSidebar, BorderLayout.WEST);
    }

    // ================= MENU BUTTON =================
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(220, 45));
        btn.setBackground(SIDEBAR_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);
        btn.setBorder(new EmptyBorder(0, 25, 0, 0));

        String iconPath = switch (text) {
            case "Trang chủ"  -> "/CH/icons/house.png";
            case "Khách Hàng" -> "/CH/icons/rating.png";
            case "Đặt Món"    -> "/CH/icons/fast-food.png";
            case "Thực Đơn"   -> "/CH/icons/menu.png";
            case "Hóa đơn"    -> "/CH/icons/invoice.png";
            case "Thoát"      -> "/CH/icons/exit.png";
            default -> null;
        };

        if (iconPath != null) {
            ImageIcon icon = createResizedIcon(iconPath);
            if (icon != null) btn.setIcon(icon);
        }

        btn.addActionListener(e -> {
            if ("Thoát".equals(text)) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có muốn thoát không?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) System.exit(0);
            } else {
                cardLayout.show(pnlContent, text);
                updateActiveButton(text);
            }
        });

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(SIDEBAR_COLOR);
            }
        });

        return btn;
    }

    private void updateActiveButton(String active) {
        for (var entry : menuButtons.entrySet()) {
            JButton btn = entry.getValue();
            if (entry.getKey().equals(active)) {
                btn.setForeground(ACCENT_RED);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            } else {
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            }
        }
    }

    private ImageIcon createResizedIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("Không tìm thấy icon: " + path);
            return null;
        }
        Image img = new ImageIcon(url)
                .getImage()
                .getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // ================= GETTERS =================
    public TrangChuView getTrangChuView() { return trangChuView; }
    public DatMonView getDatMonView() { return datMonView; }
    public HoaDonView getHoaDonView() { return hoaDonView; }
    public KhachHangView getKhachHangView() { return khachHangView; }
    public ThucDonView getThucDonView() { return thucDonView; }
}
