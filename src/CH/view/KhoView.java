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
import java.awt.event.*;
import CH.model.Kho;

public class KhoView extends JPanel {
    private JTextField txtMa, txtTen, txtSL, txtDVT;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnReset;

    public KhoView() {
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(0,77,77));
        p.setBorder(new EmptyBorder(20,20,20,20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5); gbc.fill = GridBagConstraints.HORIZONTAL;

        addInput(p, gbc, 0,0,"Mã NL", txtMa=new JTextField(15)); txtMa.setEditable(false); txtMa.setText("Tự động");
        addInput(p, gbc, 0,1,"Tên nguyên liệu", txtTen=new JTextField(15));
        addInput(p, gbc, 0,2,"Số lượng", txtSL=new JTextField(15));
        addInput(p, gbc, 0,3,"Đơn vị tính", txtDVT=new JTextField(15));

        JPanel btnP = new JPanel(); btnP.setBackground(new Color(0,77,77));
        btnThem=new JButton("Thêm"); btnSua=new JButton("Sửa"); btnXoa=new JButton("Xóa"); btnReset=new JButton("Reset");
        btnP.add(btnThem); btnP.add(btnSua); btnP.add(btnXoa); btnP.add(btnReset);

        JPanel north = new JPanel(new BorderLayout());
        north.add(p,BorderLayout.CENTER); north.add(btnP,BorderLayout.SOUTH);
        add(north,BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"Mã NL","Tên NL","Số lượng","ĐVT"},0);
        table = new JTable(model); table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addInput(JPanel p, GridBagConstraints gbc,int x,int y,String name,Component c){
        gbc.gridx=x; gbc.gridy=y; JLabel lb=new JLabel(name); lb.setForeground(Color.WHITE); p.add(lb,gbc);
        gbc.gridx=x+1; p.add(c,gbc);
    }

    public Kho getInfo() {
        int sl=0; try{sl=Integer.parseInt(txtSL.getText());}catch(Exception e){}
        return new Kho(txtMa.getText(), txtTen.getText(), sl, txtDVT.getText());
    }

    public void fillForm(Kho k){
        txtMa.setText(k.getMaNL()); txtTen.setText(k.getTenNL());
        txtSL.setText(String.valueOf(k.getSoLuong())); txtDVT.setText(k.getDonViTinh());
    }

    public void clearForm(){
        txtMa.setText("Tự động"); txtTen.setText(""); txtSL.setText(""); txtDVT.setText("");
    }

    public void addRow(Kho k){ model.addRow(k.toObjectArray()); }
    public void clearTable(){ model.setRowCount(0); }
    public int getSelectedRow(){ return table.getSelectedRow(); }
    public JTable getTable(){ return table; }

    public void onThem(ActionListener al){ btnThem.addActionListener(al); }
    public void onSua(ActionListener al){ btnSua.addActionListener(al); }
    public void onXoa(ActionListener al){ btnXoa.addActionListener(al); }
    public void onReset(ActionListener al){ btnReset.addActionListener(al); }
}

