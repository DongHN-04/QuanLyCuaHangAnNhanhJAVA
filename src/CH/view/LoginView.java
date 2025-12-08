/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.view;

/**
 *
 * @author NGUYEN HOANG DONG
 */
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginView(){
        setTitle("Đăng nhập");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Tên đăng nhập:"));
        txtUser = new JTextField();
        panel.add(txtUser);

        panel.add(new JLabel("Mật khẩu:"));
        txtPass = new JPasswordField();
        panel.add(txtPass);

        btnLogin = new JButton("Đăng nhập");
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);
    }

    public String getUser(){ return txtUser.getText(); }
    public String getPass(){ return new String(txtPass.getPassword()); }
    public JButton getBtnLogin(){ return btnLogin; }
}
