package CH.controller;

import CH.dao.KhoDAO;
import CH.dao.ThucDonDAO;
import CH.model.MonAn;
import CH.view.ThucDonView;
import javax.swing.*;

public class ThucDonController {
    private ThucDonView view;
    private ThucDonDAO dao;
    private DatMonController datMonController;
    private KhoController khoController;

    public ThucDonController(ThucDonView view, DatMonController datMonController, KhoController khoController) {
        this.view = view;
        this.dao = new ThucDonDAO();
        this.datMonController = datMonController;
        this.khoController = khoController;
        loadData();

        view.addThemListener(e -> {
            MonAn m = view.getMonAnInfo();

            if (m.getTenMon().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nhập tên món!");
                return;
            }
            
            // Kiểm tra trùng tên 
            boolean trungTen = dao.getAll().stream()
                    .anyMatch(mon -> mon.getTenMon().equalsIgnoreCase(m.getTenMon()));

            if (trungTen) {
                JOptionPane.showMessageDialog(
                        view,
                        "Món \"" + m.getTenMon() + "\" đã tồn tại!\nVui lòng nhập tên khác.",
                        "Trùng dữ liệu",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            m.setMaMon(dao.getNewID());

            if (dao.add(m)) {
                KhoDAO kdao = new KhoDAO();
                // Nếu chưa có trong kho thì thêm mới, mặc định số lượng = 0
                if (!kdao.exists(m.getMaMon())) {
                    kdao.insert(m.getMaMon(), 0);
                }

                reload(); // load lại bảng Thực đơn
                JOptionPane.showMessageDialog(view, "Thêm món thành công!");
            }
        });


        view.addSuaListener(e -> {
            if (view.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn món muốn sửa!");
                return;
            }
            if(dao.update(view.getMonAnInfo())) { reload(); JOptionPane.showMessageDialog(view, "Sửa thành công!"); }
        });

        view.addXoaListener(e -> {
            if (view.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn món muốn xóa!");
                return;
            }
            String ma = view.getTable().getValueAt(view.getSelectedRow(), 0).toString();
            String ten = view.getTable().getValueAt(view.getSelectedRow(), 1).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc muốn xóa món \"" + ten + "\"?\n(Dữ liệu kho cũng sẽ bị xóa)",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                KhoDAO khoDao = new KhoDAO();
                // XÓA TRONG KHO TRƯỚC
                khoDao.deleteByMaMon(ma);

                // XÓA THỰC ĐƠN
                if (dao.delete(ma)) {
                    reload();
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                }
            }
        });
        
        view.addResetListener(e -> view.clearForm());

        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting() && view.getSelectedRow() >= 0) {
                int r = view.getSelectedRow();
                String ma = view.getTable().getValueAt(r, 0).toString();
                String ten = view.getTable().getValueAt(r, 1).toString();
                String giaStr = view.getTable().getValueAt(r, 2).toString().replace(",", "");
                String dvt = view.getTable().getValueAt(r, 3).toString();
                view.fillForm(new MonAn(ma, ten, Double.parseDouble(giaStr), dvt));
            }
        });
        
    }

    public void loadData() {
        view.clearTable();
        for(MonAn m : dao.getAll()) view.addRow(m);
    }
    private void reload() { 
        loadData(); 
        view.clearForm();
        if (datMonController != null ){
            datMonController.loadMenu();
        }
        if (khoController != null){
            khoController.loadData();
        }
    }
}