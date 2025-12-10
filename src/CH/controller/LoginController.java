/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.controller;

/**
 *
 * @author NGUYEN HOANG DONG
 */
import CH.dao.TaiKhoanDAO;
import CH.model.TaiKhoan;
import CH.view.LoginView;
import CH.view.MainAdminView;
import CH.view.MainNhanVienView;
import javax.swing.*;

public class LoginController {

    private LoginView view;
    private TaiKhoanDAO dao;

    public LoginController(LoginView view){
        this.view = view;
        this.dao = new TaiKhoanDAO();

        view.getBtnLogin().addActionListener(e -> login());
    }

    private void login(){
        String user = view.getUser();
        String pass = view.getPass();
        TaiKhoan tk = dao.login(user, pass);

        if(tk == null){
            JOptionPane.showMessageDialog(view, "Sai tài khoản hoặc mật khẩu!");
            return;
        }

        JOptionPane.showMessageDialog(view, 
            "Đăng nhập thành công! Vai trò: " + tk.getVaiTro());

        view.dispose(); // đóng form login

        // ===============================================================
        //                          PHÂN QUYỀN
        // ===============================================================
        
        // --- TRƯỜNG HỢP 1: ADMIN ---
        if (tk.getVaiTro().equalsIgnoreCase("Admin")) {
            MainAdminView mainAdminView = new MainAdminView();

            new NhanVienController(mainAdminView.getNhanVienView());
            new KhachHangController(mainAdminView.getKhachHangView());
            
            // 1. Khởi tạo HoaDonController
            HoaDonController hoaDonCtrl = new HoaDonController(mainAdminView.getHoaDonView());
            
            // 2. Khởi tạo DatMonController VÀ LƯU VÀO BIẾN
            DatMonController datMonCtrl = new DatMonController(mainAdminView.getDatMonView(), hoaDonCtrl);
            
            // 3.Truyền datMonCtrl vào ThucDonController
            new ThucDonController(mainAdminView.getThucDonView(), datMonCtrl);
            
            new KhoController(mainAdminView.getKhoView());
            new DoanhThuController(mainAdminView.getDoanhThuView());
            
            mainAdminView.setVisible(true);
            
        } 
        // --- TRƯỜNG HỢP 2: NHÂN VIÊN ---
        else {
            MainNhanVienView mainNhanVienView = new MainNhanVienView();
            
            // 1. Khởi tạo HoaDonController
            HoaDonController hoaDonCtrl = new HoaDonController(mainNhanVienView.getHoaDonView());
            
            // 2. Khởi tạo DatMonController VÀ LƯU VÀO BIẾN
            DatMonController datMonCtrl = new DatMonController(mainNhanVienView.getDatMonView(), hoaDonCtrl);
            
            // 3. Truyền datMonCtrl vào ThucDonController
            new ThucDonController(mainNhanVienView.getThucDonView(), datMonCtrl);
            
            new KhachHangController(mainNhanVienView.getKhachHangView());
            
            mainNhanVienView.setVisible(true);
        }
    }
}