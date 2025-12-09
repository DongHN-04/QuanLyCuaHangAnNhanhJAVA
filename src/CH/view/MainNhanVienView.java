/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.view;

/**
 *
 * @author NGUYEN HOANG DONG
 */
import CH.model.KhachHang;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class MainNhanVienView extends JFrame {

    private final Color SIDEBAR_COLOR = new Color(0, 91, 110);
    private final Color ACCENT_RED = new Color(255, 77, 77);

    private CardLayout cardLayout;
    private JPanel pnlContent;
    private Map<String, JButton> menuButtons = new HashMap<>();

    // Các View Con
    private KhachHangView khachHangView;
    private HoaDonView hoaDonView;
    private DatMonView datMonView;

    public MainNhanVienView() {
        setTitle("Hệ Thống Quản Lý Cửa Hàng Đồ Ăn Nhanh");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Header
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(10, 20, 10, 20));
        pnlHeader.add(new JLabel("Hệ Thống Quản Lý Cửa Hàng Đồ Ăn Nhanh") {{
            setFont(new Font("Segoe UI", Font.BOLD, 18));
        }}, BorderLayout.WEST);
        
        JPanel pnlUser = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlUser.setBackground(Color.WHITE);
        pnlUser.add(new JLabel("Xin chào, Nhân Viên"));
        /*JButton btnLogout = new JButton("Đăng Xuất");
        btnLogout.setBackground(ACCENT_RED); 
        btnLogout.setForeground(Color.BLACK); 
        btnLogout.setFocusPainted(false);
        pnlUser.add(btnLogout);*/
        pnlHeader.add(pnlUser, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // 2. Card Layout
        cardLayout = new CardLayout();
        pnlContent = new JPanel(cardLayout);
        
        // --- KHỞI TẠO CÁC VIEW CON ---
        hoaDonView = new HoaDonView();
        datMonView = new DatMonView();
        khachHangView = new KhachHangView();
        
        // --- ADD VÀO CARDLAYOUT ---
        pnlContent.add(createTrangChuPanel(), "Trang chủ"); 
        pnlContent.add(datMonView, "Đặt Món");      
        pnlContent.add(hoaDonView, "Hóa đơn");
        pnlContent.add(khachHangView, "Khách Hàng");

        add(pnlContent, BorderLayout.CENTER);

        // 3. Sidebar
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setPreferredSize(new Dimension(220, 0));
        pnlSidebar.setBackground(SIDEBAR_COLOR);
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));

        JLabel lblAdmin = new JLabel("NHÂN VIÊN");
        lblAdmin.setForeground(Color.WHITE);
        lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 30))); pnlSidebar.add(lblAdmin); pnlSidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        // [QUAN TRỌNG] Tên ở đây phải khớp 100% với tên add ở trên
        String[] menuItems = {"Trang chủ", "Khách Hàng", "Đặt Món", "Hóa đơn", "Thoát"};

        for (String item : menuItems) {
            JButton btnMenu = createMenuButton(item);
            btnMenu.addActionListener(e -> {
                if (item.equals("Thoát")) System.exit(0);
                else {
                    cardLayout.show(pnlContent, item);
                    updateActiveButton(item);
                }
            });
            menuButtons.put(item, btnMenu);
            pnlSidebar.add(btnMenu); pnlSidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        add(pnlSidebar, BorderLayout.WEST);
        
        cardLayout.show(pnlContent, "Trang chủ"); 
        updateActiveButton("Trang chủ");
    }

    // Getters
    public HoaDonView getHoaDonView(){ return hoaDonView; }
    public DatMonView getDatMonView(){ return datMonView; }
    public KhachHangView getkhachHangView() { return khachHangView; }
    // [Sửa getter]
    
    // Helpers
    private JPanel createTrangChuPanel() {
        JPanel pnl = new JPanel(new GridBagLayout()); pnl.setBackground(Color.WHITE);
        JLabel lbl = new JLabel("Trang Chủ"); lbl.setFont(new Font("Segoe UI", Font.BOLD, 30));
        pnl.add(lbl); return pnl;
    }
    private JPanel createPlaceholderPanel(String title) {
        JPanel pnl = new JPanel(new GridBagLayout()); pnl.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(title); lbl.setFont(new Font("Segoe UI", Font.BOLD, 24)); lbl.setForeground(Color.GRAY);
        pnl.add(lbl); return pnl;
    }
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 40)); btn.setBackground(SIDEBAR_COLOR);
        btn.setForeground(Color.WHITE); btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBorderPainted(false); btn.setFocusPainted(false); btn.setContentAreaFilled(true); 
        btn.setOpaque(true); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT); btn.setBorder(new EmptyBorder(0, 40, 0, 0));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(0, 77, 77)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(SIDEBAR_COLOR); }
        });
        return btn;
    }
    private void updateActiveButton(String activeName) {
        for (Map.Entry<String, JButton> entry : menuButtons.entrySet()) {
            JButton btn = entry.getValue();
            if (entry.getKey().equals(activeName)) {
                btn.setForeground(ACCENT_RED); btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            } else {
                btn.setForeground(Color.WHITE); btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            }
            btn.setBackground(SIDEBAR_COLOR);
        }
    }
}
