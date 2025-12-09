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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import CH.model.Kho;

public class KhoView extends JPanel {

    private JTextField txtMaMon, txtTenMon, txtSoLuong;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnNhapKho, btnCapNhat, btnReset;

    public KhoView() {
        setLayout(new BorderLayout());
        Color TEAL = new Color(0, 77, 77);

        // ===== FORM =====
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(TEAL);
        pnlForm.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Inputs
        addInput(pnlForm, gbc, 0, 0, "Mã món", txtMaMon = new JTextField(15));
        txtMaMon.setEditable(false);

        addInput(pnlForm, gbc, 0, 1, "Tên món", txtTenMon = new JTextField(15));
        txtTenMon.setEditable(false);

        addInput(pnlForm, gbc, 0, 2, "Số lượng", txtSoLuong = new JTextField(15));

        // ===== BUTTONS =====
        JPanel pnlBtn = new JPanel();
        pnlBtn.setBackground(TEAL);

        btnNhapKho = new JButton("Nhập thêm");
        btnReset = new JButton("Reset");

        pnlBtn.add(btnNhapKho); 
        pnlBtn.add(btnReset);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlForm, BorderLayout.CENTER);
        pnlNorth.add(pnlBtn, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] cols = {"Mã món", "Tên món", "Số lượng"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addInput(JPanel p, GridBagConstraints gbc, int x, int y, String lbl, Component cmp) {
        gbc.gridx = x; gbc.gridy = y;
        JLabel l = new JLabel(lbl); l.setForeground(Color.WHITE);
        p.add(l, gbc);
        gbc.gridx = x+1;
        p.add(cmp, gbc);
    }

    // ===== METHODS =====
    public Kho getKhoInfo() {
        int sl = 0;
        try { sl = Integer.parseInt(txtSoLuong.getText()); } catch (Exception e) {}
        return new Kho(txtMaMon.getText(), txtTenMon.getText(), sl);
    }

    public void fillForm(Kho k) {
        txtMaMon.setText(k.getMaMon());
        txtTenMon.setText(k.getTenMon());
        txtSoLuong.setText(String.valueOf(k.getSoLuong()));
    }

    public void clearForm() {
        txtMaMon.setText(""); txtTenMon.setText(""); txtSoLuong.setText("");
    }
    
    public void addTableClickListener(java.awt.event.MouseListener ml) {
        table.addMouseListener(ml);
    }
    
    public void addRow(Kho k) { model.addRow(k.toObjectArray()); }
    public void clearTable() { model.setRowCount(0); }
    public int getSelectedRow() { return table.getSelectedRow(); }
    public JTable getTable() { return table; }

    // ===== LISTENERS =====
    public void addNhapKhoListener(ActionListener al) { btnNhapKho.addActionListener(al); }
    public void addResetListener(ActionListener al) { btnReset.addActionListener(al); }
}


