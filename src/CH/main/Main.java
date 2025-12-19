package CH.main;

import CH.controller.*;
import CH.dao.DBConnection;
import CH.view.LoginView;
import CH.view.MainAdminView;
import CH.view.TrangChuView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        
        DBConnection.initializeDatabase();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e){ e.printStackTrace(); }
                
                LoginView login = new LoginView();
                new LoginController(login);
                login.setVisible(true);
                
                /*MainAdminView mainView = new MainAdminView();
                
                new NhanVienController(mainView.getNhanVienView());
                new KhachHangController(mainView.getKhachHangView());
                
                // Khởi tạo HoaDonController trước và lưu vào biến
                HoaDonController hoaDonCtrl = new HoaDonController(mainView.getHoaDonView());
                
                // Truyền biến hoaDonCtrl vào DatMonController
                new DatMonController(mainView.getDatMonView(), hoaDonCtrl);
                
                new ThucDonController(mainView.getThucDonView());
                
                new KhoController(mainView.getKhoView());
                
                new DoanhThuController(mainView.getDoanhThuView());*/
            }
        });
    }
}   