package CH.main;

import CH.controller.*;
import CH.dao.DBConnection;
import CH.view.LoginView;
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

            }
        });
    }
}   