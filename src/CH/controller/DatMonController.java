    package CH.controller;

    import CH.dao.*;
    import CH.model.*;
    import CH.view.*;
    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseAdapter; 
    import java.awt.event.MouseEvent;   
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;

    public class DatMonController {
        private DatMonView view;
        private ThucDonDAO menuDao;
        private HoaDonDAO hoaDonDao;
        private KhoDAO khoDao;
        private double currentTotal = 0;
        private HoaDonController hoaDonController;
        private KhachHangController khachHangController;
        private KhoController khoController;


        public DatMonController(DatMonView view, HoaDonController hoaDonController,KhachHangController khachHangController, KhoController khoController) {
            this.view = view;
            this.menuDao = new ThucDonDAO();
            this.hoaDonDao = new HoaDonDAO();
            this.khoDao = new KhoDAO();
            
            this.khachHangController = khachHangController;
            this.hoaDonController = hoaDonController;
            this.khoController = khoController;

            loadMenu();

            // 1. Sự kiện nút "Thêm vào giỏ >>"
            view.addThemListener(e -> themVaoGio());

            // 2. Sự kiện Click đúp chuột vào bảng Menu -> Tự động thêm vào giỏ
            view.getTableMenu().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Nếu click 2 lần liên tiếp
                        themVaoGio(); // Gọi hàm thêm món
                    }
                }
            });

            // Các sự kiện khác giữ nguyên
            view.addXoaListener(e -> xoaKhoiGio());
            view.addThanhToanListener(e -> moPopupThanhToan());
            view.getModelGioHang().addTableModelListener(e -> {
                // Chỉ xử lý khi cột bị thay đổi là cột Số Lượng (index = 1)
                if (e.getColumn() == 1) {
                    int row = e.getFirstRow();
                    tinhLaiTienMotDong(row);
                }
            });
        }

        public void loadMenu() {
            DefaultTableModel model = (DefaultTableModel) view.getTableMenu().getModel();
            model.setRowCount(0);

            List<MonAn> listMon = menuDao.getAll();
            List<Kho> listKho = khoDao.getAll();

            for (MonAn m : listMon) {

                // tìm tồn kho theo mã món
                Kho kho = listKho.stream()
                        .filter(k -> k.getMaMon().equals(m.getMaMon()))
                        .findFirst()
                        .orElse(null);

                int soLuongTon = (kho != null) ? kho.getSoLuong() : 0;

                String trangThai = soLuongTon > 0 ? "Còn món" : "Hết món";

                model.addRow(new Object[]{
                    m.getMaMon(),
                    m.getTenMon(),
                    String.format("%,.0f", m.getDonGia()),
                    m.getDonViTinh(),
                    trangThai
                });
            }
        }

        private void themVaoGio() {
            int row = view.getTableMenu().getSelectedRow();
            
            String trangThai = view.getTableMenu().getValueAt(row, 4).toString();
            if (trangThai.equalsIgnoreCase("Hết món")) {
                JOptionPane.showMessageDialog(
                        view,
                        "Món này đã hết!\nVui lòng chọn món khác.",
                        "Hết hàng",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            if (row >= 0) {
                String ten = view.getTableMenu().getValueAt(row, 1).toString();
                // Xử lý giá tiền (bỏ dấu phẩy, chấm để tính toán)
                String giaStr = view.getTableMenu().getValueAt(row, 2).toString().replace(",", "").replace(".", "");
                double gia = 0;
                try {
                    gia = Double.parseDouble(giaStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                view.addMonToGio(ten, gia);
                updateTongTien();
            } else {
                // Chỉ hiện thông báo nếu bấm nút mà chưa chọn dòng nào
                JOptionPane.showMessageDialog(view, "Vui lòng chọn món từ thực đơn!");
            }
        }

        private void xoaKhoiGio() {
            int row = view.getTableGioHang().getSelectedRow();
            if (row >= 0) {
                ((DefaultTableModel)view.getTableGioHang().getModel()).removeRow(row);
                updateTongTien();
            } else {
                JOptionPane.showMessageDialog(view, "Chọn món trong giỏ để xóa!");
            }
        }

        private void updateTongTien() {
            currentTotal = 0;
            DefaultTableModel model = view.getModelGioHang();
            for (int i = 0; i < model.getRowCount(); i++) {
                // Lấy cột thành tiền (index 3)
                String tienStr = model.getValueAt(i, 3).toString().replace(",", "").replace(".", "");
                currentTotal += Double.parseDouble(tienStr);
            }
            view.setTongTien(currentTotal);
        }

        private void moPopupThanhToan() {
            if (currentTotal == 0) {
                JOptionPane.showMessageDialog(view, "Giỏ hàng đang trống!");
                return;
            }

            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(view);
            XacNhanThanhToanDialog dialog = new XacNhanThanhToanDialog(parent, view.getModelGioHang(), currentTotal);

            dialog.addXacNhanListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    luuHoaDonVaoDB(dialog.getTenKhach());
                    dialog.dispose();
                }
            });

            dialog.setVisible(true);
        }

        private void luuHoaDonVaoDB(String tenKhach) {
            try {
                // TỰ ĐỘNG LIÊN KẾT KHÁCH HÀNG ---
                KhachHangDAO khDao = new KhachHangDAO();
                boolean khachDaTonTai = false;

                for (KhachHang kh : khDao.getAll()) {
                    if (kh.getTenKH().equalsIgnoreCase(tenKhach)) {
                        khachDaTonTai = true;
                        break;
                    }
                }

                if (!khachDaTonTai && !tenKhach.equalsIgnoreCase("Khách vãng lai") && !tenKhach.trim().isEmpty()) {
                    KhachHang newKH = new KhachHang();
                    newKH.setMaKH(khDao.getNewID());
                    newKH.setTenKH(tenKhach);
                    newKH.setTheLoai("Vãng lai");
                    newKH.setGioiTinh("Chưa rõ");
                    newKH.setSoDienThoai("");
                    newKH.setDiaChi("");
                    khDao.add(newKH);

                    //  Gọi hàm load lại dữ liệu bên Controller Khách hàng ngay sau khi add thành công
                    if (khachHangController != null) {
                        khachHangController.loadDataToView();
                    }
                }
                // ---------------------------------------------

                // 1. Lưu Hóa Đơn
                String maHD = hoaDonDao.getNewID();
                String ngayLap = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                HoaDon hd = new HoaDon(maHD, "Admin", tenKhach, ngayLap, currentTotal);
                hoaDonDao.add(hd);

                // 2. Lưu Chi Tiết
                java.sql.Connection conn = DBConnection.getConnection();
                String sql = "INSERT INTO ChiTietHoaDon(MaHD, TenMon, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
                java.sql.PreparedStatement ps = conn.prepareStatement(sql);
                DefaultTableModel model = view.getModelGioHang();

                for (int i = 0; i < model.getRowCount(); i++) {
                    ps.setString(1, maHD);
                    ps.setString(2, model.getValueAt(i, 0).toString());
                    ps.setInt(3, Integer.parseInt(model.getValueAt(i, 1).toString()));
                    ps.setDouble(4, Double.parseDouble(model.getValueAt(i, 2).toString().replace(",", "")));
                    ps.executeUpdate();
                }
                conn.close();
                
                //  trừ kho
                for (int i = 0; i < model.getRowCount(); i++) {
                    String tenMon = model.getValueAt(i, 0).toString();
                    int soLuongBan = Integer.parseInt(model.getValueAt(i, 1).toString());

                    // Lấy mã món
                    String maMon = menuDao.getAll().stream()
                            .filter(m -> m.getTenMon().equalsIgnoreCase(tenMon))
                            .findFirst().get().getMaMon();

                    // Lấy tồn kho
                    Kho kho = khoDao.getAll().stream()
                            .filter(k -> k.getMaMon().equals(maMon))
                            .findFirst().orElse(null);

                    if (kho == null || kho.getSoLuong() < soLuongBan) {
                        JOptionPane.showMessageDialog(view,
                                "Không đủ kho cho món: " + tenMon);
                        return;
                    }

                    // Trừ kho
                    khoDao.updateSoLuong(maMon, kho.getSoLuong() - soLuongBan);
                    //LOAD BẢNG KHO NGAY LẬP TỨC
                    if (khoController != null) {
                        khoController.loadData();
                    }
                }

                JOptionPane.showMessageDialog(view, "Thanh toán thành công! Khách hàng ");
                view.getModelGioHang().setRowCount(0);
                updateTongTien();
                loadMenu();

                if (hoaDonController != null) hoaDonController.loadData();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Lỗi thanh toán: " + ex.getMessage());
            }
        }
        // Hàm tính lại tiền khi số lượng thay đổi
        private void tinhLaiTienMotDong(int row) {
            if (row < 0) return;
            try {
                DefaultTableModel model = view.getModelGioHang();

                // 1. Lấy số lượng mới vừa nhập
                Object slObj = model.getValueAt(row, 1);
                int soLuongMoi = Integer.parseInt(slObj.toString());

                // Kiểm tra nếu nhập số âm hoặc 0 -> Reset về 1 hoặc xóa (ở đây mình reset về 1)
                if (soLuongMoi <= 0) {
                    soLuongMoi = 1;
                    model.setValueAt(1, row, 1); // Cập nhật lại số 1 lên bảng
                    JOptionPane.showMessageDialog(view, "Số lượng phải lớn hơn 0!");
                }

                // 2. Lấy đơn giá 
                String giaStr = model.getValueAt(row, 2).toString().replace(",", "").replace(".", "");
                double donGia = Double.parseDouble(giaStr);

                // 3. Tính thành tiền mới
                double thanhTienMoi = soLuongMoi * donGia;

                // 4. Cập nhật cột Thành tiền (index 3)
                model.setValueAt(String.format("%,.0f", thanhTienMoi), row, 3);

                updateTongTien();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Vui lòng chỉ nhập số nguyên!");

            }
        }
    }