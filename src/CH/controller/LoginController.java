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
            
            mainAdminView.getTrangChuView()
                    .setUserInfo(tk.getTaiKhoan(), tk.getVaiTro());

            new NhanVienController(mainAdminView.getNhanVienView());
            
            // 1. Khởi tạo HoaDonController
            HoaDonController hoaDonCtrl = new HoaDonController(mainAdminView.getHoaDonView());
            
            // 2. Khởi tạo KhachHangController và lưu vào biến
            KhachHangController khachHangCtrl = new KhachHangController(mainAdminView.getKhachHangView());
            
            // 3. Tạo KhoController VÀ LƯU VÀO BIẾN
            KhoController khoCtrl = new KhoController(mainAdminView.getKhoView(), null);
            
            // 4. Khởi tạo DatMonController VÀ LƯU VÀO BIẾN
            DatMonController datMonCtrl = new DatMonController(mainAdminView.getDatMonView(), hoaDonCtrl, khachHangCtrl, khoCtrl);
            
            // 6. GÁN NGƯỢC datMonCtrl cho khoCtrl
            khoCtrl.setDatMonController(datMonCtrl);
                               
            // 5.Truyền datMonCtrl vào ThucDonController
            new ThucDonController(mainAdminView.getThucDonView(), datMonCtrl, khoCtrl);
            

            new DoanhThuController(mainAdminView.getDoanhThuView());
            
            mainAdminView.setVisible(true);
            
        } 
        // --- TRƯỜNG HỢP 2: NHÂN VIÊN ---
        else {
            MainNhanVienView mainNhanVienView = new MainNhanVienView();
            
            // SET USER CHO TRANG CHỦ
            mainNhanVienView.getTrangChuView()
                    .setUserInfo(tk.getTaiKhoan(), tk.getVaiTro());
            
            // 1. Khởi tạo HoaDonController
            HoaDonController hoaDonCtrl = new HoaDonController(mainNhanVienView.getHoaDonView());
            // 2. Khởi tạo KhachHangController và lưu vào biến
            KhachHangController khachHangCtrl = new KhachHangController(mainNhanVienView.getKhachHangView());
                      
            // 3. Khởi tạo DatMonController VÀ LƯU VÀO BIẾN
            DatMonController datMonCtrl = new DatMonController(mainNhanVienView.getDatMonView(), hoaDonCtrl, khachHangCtrl, null);
            
            // 4. Truyền datMonCtrl vào ThucDonController
            new ThucDonController(mainNhanVienView.getThucDonView(), datMonCtrl, null);
                 
            mainNhanVienView.setVisible(true);
        }
    }
}