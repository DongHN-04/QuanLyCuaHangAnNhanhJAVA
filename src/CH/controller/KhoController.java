/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CH.controller;

/**
 *
 * @author NGUYEN HOANG DONG
 */
import CH.view.KhoView;
import CH.dao.KhoDAO;
import CH.model.Kho;
import javax.swing.*;

public class KhoController {
    private KhoView view;
    private KhoDAO dao;

    public KhoController(KhoView view){
        this.view=view;
        this.dao=new KhoDAO();
        loadData();

        view.onThem(e->{
            Kho k = view.getInfo();
            if(k.getTenNL().isEmpty()){ JOptionPane.showMessageDialog(view,"Nhập tên!"); return;}
            k.setMaNL(dao.getNewID());
            if(dao.add(k)){ reload(); JOptionPane.showMessageDialog(view,"Thêm thành công!"); }
        });

        view.onSua(e->{
            if(view.getSelectedRow()<0) return;
            if(dao.update(view.getInfo())){ reload(); JOptionPane.showMessageDialog(view,"Sửa thành công!"); }
        });

        view.onXoa(e->{
            if(view.getSelectedRow()<0) return;
            String ma=view.getInfo().getMaNL();
            if(JOptionPane.showConfirmDialog(view,"Xóa "+ma+"?")==JOptionPane.YES_OPTION){
                if(dao.delete(ma)){ reload(); JOptionPane.showMessageDialog(view,"Xóa thành công!"); }
            }
        });

        view.onReset(e->view.clearForm());

        view.getTable().getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting() && view.getSelectedRow()>=0){
                int r=view.getSelectedRow();
                String ma=view.getTable().getValueAt(r,0).toString();
                String ten=view.getTable().getValueAt(r,1).toString();
                int sl=Integer.parseInt(view.getTable().getValueAt(r,2).toString());
                String dvt=view.getTable().getValueAt(r,3).toString();
                view.fillForm(new Kho(ma,ten,sl,dvt));
            }
        });
    }

    private void loadData(){
        view.clearTable();
        for(Kho k:dao.getAll()) view.addRow(k);
    }

    private void reload(){
        loadData(); view.clearForm();
    }
}

