package CH.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * View đăng nhập hệ thống
 * Chỉ xử lý giao diện, không xử lý logic đăng nhập
 */
public class LoginView extends JFrame {

    // ===== KHAI BÁO COMPONENT =====
    private JTextField txtUser;        // Ô nhập tên đăng nhập
    private JPasswordField txtPass;    // Ô nhập mật khẩu
    private JButton btnLogin;          // Nút đăng nhập
    private JCheckBox chkShowPass;     // Checkbox hiển thị mật khẩu

    public LoginView() {
        // ===== THIẾT LẬP CỬA SỔ =====
        setTitle("Đăng nhập");
        setSize(420, 260);
        setLocationRelativeTo(null);   // Canh giữa màn hình
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== KHAI BÁO FONT =====
        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 13);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontButton = new Font("Segoe UI", Font.BOLD, 14);

        // ===== PANEL CHÍNH =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ CỬA HÀNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(0, 123, 255));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // ===== FORM ĐĂNG NHẬP =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label + ô nhập username
        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(fontLabel);

        txtUser = new JTextField();
        txtUser.setFont(fontInput);

        // Label + ô nhập password
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(fontLabel);

        txtPass = new JPasswordField();
        txtPass.setFont(fontInput);

        // Checkbox hiển thị / ẩn mật khẩu
        chkShowPass = new JCheckBox("Hiển thị mật khẩu");
        chkShowPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkShowPass.setBackground(new Color(245, 247, 250));

        // Xử lý hiện / ẩn mật khẩu
        chkShowPass.addActionListener(e -> {
            if (chkShowPass.isSelected()) {
                txtPass.setEchoChar((char) 0); // Hiển thị chữ thường
            } else {
                txtPass.setEchoChar('●');     // Ẩn bằng dấu chấm
            }
        });

        // ===== BỐ TRÍ FORM =====
        // Dòng 1: Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUser, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUser, gbc);

        // Dòng 2: Password
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblPass, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPass, gbc);

        // Dòng 3: Checkbox
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(chkShowPass, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ===== NÚT ĐĂNG NHẬP =====
        btnLogin = new JButton("ĐĂNG NHẬP") {

            // Vẽ nút bo tròn
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }

            // Không vẽ viền nút
            @Override
            protected void paintBorder(Graphics g) {
            }
        };

        btnLogin.setFont(fontButton);
        btnLogin.setBackground(new Color(0, 123, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setOpaque(false);
        btnLogin.setPreferredSize(new Dimension(160, 38));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover cho nút đăng nhập
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(0, 105, 217));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(new Color(0, 123, 255));
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(245, 247, 250));
        btnPanel.add(btnLogin);

        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Nhấn Enter = Đăng nhập
        getRootPane().setDefaultButton(btnLogin);
    }

    // ===== GETTER PHỤC VỤ CONTROLLER =====

    public String getUser() {
        return txtUser.getText().trim();
    }

    public String getPass() {
        return new String(txtPass.getPassword());
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }
}
