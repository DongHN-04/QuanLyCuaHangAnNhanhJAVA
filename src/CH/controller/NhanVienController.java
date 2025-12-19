package CH.controller;

import CH.dao.NhanVienDAO;
import CH.model.NhanVien;
import CH.view.NhanVienView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.regex.Pattern;   

public class NhanVienController {
    
    private NhanVienView view;
    private NhanVienDAO nhanVienDAO;

    public NhanVienController(NhanVienView view) {
        this.view = view;
        this.nhanVienDAO = new NhanVienDAO();

        // Load dữ liệu ban đầu
        loadDataToView();

        // Gán sự kiện cho các nút
        view.addThemListener(new AddListener());
        view.addSuaListener(new EditListener());
        view.addXoaListener(new DeleteListener());
        view.addResetListener(e -> {
            view.clearForm();
            view.getNhanVienInfo().setMaNV("Tự động sinh"); 
        });
        
        // Sự kiện click vào bảng
        view.addTableSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = view.getSelectedRow();
                if (row >= 0) {
                    try {
                        String maNV = view.getTable().getValueAt(row, 0).toString(); 

                        // Lấy thông tin đầy đủ từ DB
                        NhanVien nv = nhanVienDAO.getByID(maNV);

                        // Đổ lên form
                        view.fillForm(nv);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        view.addTimKiemListener(e -> searchNhanVien());

        view.getTxtTimKiem().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                searchNhanVien();
            }
        });

    } 

    private void loadDataToView() {
        view.clearTable();
        List<NhanVien> list = nhanVienDAO.getAll();
        for (NhanVien nv : list) {
            view.addRowToTable(nv);
        }
    }

    private boolean validateForm(NhanVien nv) {
        // 1. Kiểm tra rỗng
        if (nv.getTenNV().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên nhân viên không được để trống!");
            return false;
        }
        if (nv.getNgaySinh() == null || nv.getNgaySinh().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày sinh!");
            return false;
        }
        if (nv.getSoDienThoai().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Số điện thoại không được để trống!");
            return false;
        }

        // 2. Validate Số điện thoại (Phải là số, 10 ký tự, bắt đầu bằng số 0)
        String phoneRegex = "^0\\d{9}$";
        if (!Pattern.matches(phoneRegex, nv.getSoDienThoai())) {
            JOptionPane.showMessageDialog(view, "Số điện thoại không hợp lệ (Phải là 10 số, bắt đầu bằng 0)!");
            return false;
        }


        return true; 
    }

    // --- INNER CLASSES (LISTENERS) ---

    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NhanVien nv = view.getNhanVienInfo();

            // Validate trước khi xử lý
            if (!validateForm(nv)) {
                return; 
            }

            // Tự động sinh mã
            String newID = nhanVienDAO.getNewID();
            nv.setMaNV(newID);

            if (nhanVienDAO.add(nv)) {
                JOptionPane.showMessageDialog(view, "Thêm thành công!");
                loadDataToView();
                view.clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!");
            }
        }
    }

    class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn nhân viên để sửa!");
                return;
            }

            NhanVien nv = view.getNhanVienInfo();
            
            // Validate khi sửa
            if (!validateForm(nv)) {
                return;
            }

            if (nhanVienDAO.update(nv)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToView();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
            }
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getSelectedRow();
            if (row >= 0) {
                String maNV = view.getTable().getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa nhân viên " + maNV + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (nhanVienDAO.delete(maNV)) {
                        JOptionPane.showMessageDialog(view, "Xóa thành công!");
                        loadDataToView();
                        view.clearForm();
                    } else {
                        JOptionPane.showMessageDialog(view, "Xóa thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn nhân viên để xóa!");
            }
        }
    }
    private void searchNhanVien() {
        String keyword = view.getTxtTimKiem().getText().trim();

        List<NhanVien> list = nhanVienDAO.search(keyword);

        view.clearTable();
        for (NhanVien nv : list) {
            view.addRowToTable(nv);
        }
    }

}