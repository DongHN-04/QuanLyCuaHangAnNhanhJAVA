/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.controller;

/**
 *
 * @author NGUYEN HOANG DONG
 */
import CH.dao.KhoDAO;
import CH.view.KhoView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import CH.model.Kho;

public class KhoController {
    private KhoView view;
    private KhoDAO dao;
    private DatMonController datMonController;
   
    public KhoController(KhoView view, DatMonController datMonController) {
        this.view = view;
        this.dao = new KhoDAO();
        this.datMonController = datMonController;
        loadData();
        addEvents();
    }

    public void loadData() {
        view.clearTable();
        dao.getAll().forEach(k -> view.addRow(k));
    }

    private void addEvents() {

        // Sự kiện click bảng
        view.addTableClickListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = view.getSelectedRow();
                if(row >= 0) {
                    String ma = (String) view.getTable().getValueAt(row, 0);
                    String ten = (String) view.getTable().getValueAt(row, 1);
                    int sl = (int) view.getTable().getValueAt(row, 2);
                    view.fillForm(new Kho(ma, ten, sl));
                }
            }
        });

        // Nút nhập kho (tăng thêm số lượng)
        view.addNhapKhoListener(e -> {
            Kho k = view.getKhoInfo();
            if (k.getMaMon() == null || k.getMaMon().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn món trong kho!");
                return;
            }
            if(k.getSoLuong() <= 0) {
                JOptionPane.showMessageDialog(view, "Số lượng phải >0");
                return;
            }
            if (dao.updateSoLuong(k.getMaMon(), k.getSoLuong())) {
                loadData();
                if (datMonController != null) {
                    datMonController.loadMenu(); // CẬP NHẬT MENU NGAY
                }
                JOptionPane.showMessageDialog(view, "Nhập kho thành công!");
            }
        });

        // Nút reset
        view.addResetListener(e -> view.clearForm());
    }
    
    public void setDatMonController(DatMonController datMonController) {
        this.datMonController = datMonController;
    }
}


