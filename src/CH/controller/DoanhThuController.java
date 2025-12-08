/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.controller;

/**
 *
 * @author NGUYEN HOANG DONG
 */
import CH.view.DoanhThuView;
import CH.dao.DoanhThuDAO;
import CH.model.DoanhThu;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DoanhThuController {

    private DoanhThuView view;
    private DoanhThuDAO dao;

    public DoanhThuController(DoanhThuView view) {
        this.view = view;
        this.dao = new DoanhThuDAO();

        loadNgay(); // mặc định

        view.getBtnNgay().addActionListener(e -> loadNgay());
        view.getBtnThang().addActionListener(e -> loadThang());
        view.getBtnNam().addActionListener(e -> loadNam());
    }

    private void loadNgay(){ loadData(dao.thongKeTheoNgay()); }
    private void loadThang(){ loadData(dao.thongKeTheoThang()); }
    private void loadNam(){ loadData(dao.thongKeTheoNam()); }

    private void loadData(List<DoanhThu> list){
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,###"); //dùng để định dạng số
        double total = 0;
        for(DoanhThu d : list){
            model.addRow(new Object[]{d.getThoiGian(), df.format(d.getTongTien())});
            total += d.getTongTien();
        }
        view.getLblTong().setText("Tổng doanh thu: " + df.format(total) + " VNĐ");
    }
}

