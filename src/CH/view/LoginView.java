package CH.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JCheckBox chkShowPass;

    public LoginView() {
        setTitle("Đăng nhập");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== FONT =====
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

        // ===== FORM =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(fontLabel);

        txtUser = new JTextField();
        txtUser.setFont(fontInput);

        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(fontLabel);

        txtPass = new JPasswordField();
        txtPass.setFont(fontInput);

        chkShowPass = new JCheckBox("Hiển thị mật khẩu");
        chkShowPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkShowPass.setBackground(new Color(245, 247, 250));

        chkShowPass.addActionListener(e -> {
            if (chkShowPass.isSelected()) {
                txtPass.setEchoChar((char) 0);
            } else {
                txtPass.setEchoChar('●');
            }
        });

        // Dòng 1
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUser, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUser, gbc);

        // Dòng 2
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblPass, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPass, gbc);

        // Dòng 3
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(chkShowPass, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ===== NÚT ĐĂNG NHẬP =====
        btnLogin = new JButton("ĐĂNG NHẬP") {
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

            @Override
            protected void paintBorder(Graphics g) {
                // Không vẽ viền
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

        // Hover effect
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

        getRootPane().setDefaultButton(btnLogin);
    }

    // ===== GETTER =====
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
