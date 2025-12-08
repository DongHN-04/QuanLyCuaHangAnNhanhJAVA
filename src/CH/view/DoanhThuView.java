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
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DoanhThuView extends JPanel {

    private JTable tblDoanhThu;
    private JButton btnNgay, btnThang, btnNam;
    private JLabel lblTongDoanhThu;

    public DoanhThuView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TOP BUTTONS
        JPanel pnlTop = new JPanel(new FlowLayout());
        btnNgay = new JButton("Theo Ngày");
        btnThang = new JButton("Theo Tháng");
        btnNam = new JButton("Theo Năm");
        pnlTop.add(btnNgay); pnlTop.add(btnThang); pnlTop.add(btnNam);

        // TABLE
        tblDoanhThu = new JTable(new DefaultTableModel(
                new Object[]{"Thời gian", "Doanh thu"}, 0
        ));
        JScrollPane sp = new JScrollPane(tblDoanhThu);

        // BOTTOM TOTAL
        JPanel pnlBottom = new JPanel(new FlowLayout());
        lblTongDoanhThu = new JLabel("Tổng doanh thu: 0 VNĐ");
        lblTongDoanhThu.setFont(new Font("Segoe UI", Font.BOLD, 15));
        pnlBottom.add(lblTongDoanhThu);

        // ADD
        add(pnlTop, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    // Getter
    public JTable getTable(){ return tblDoanhThu; }
    public JButton getBtnNgay(){ return btnNgay; }
    public JButton getBtnThang(){ return btnThang; }
    public JButton getBtnNam(){ return btnNam; }
    public JLabel getLblTong(){ return lblTongDoanhThu; }
}

