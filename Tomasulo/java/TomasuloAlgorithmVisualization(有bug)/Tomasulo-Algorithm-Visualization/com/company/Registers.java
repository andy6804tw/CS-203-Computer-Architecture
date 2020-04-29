package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by ylf951 on 16/3/26.
 */
public class Registers {
    private JTable table;
    private DefaultTableModel model;
    private Integer registerNumber = 16;
    public Registers(){
        model = new DefaultTableModel();
        table = new JTable(model);

        table.setRowHeight(25);
        table.setFont(new Font("Serif", Font.PLAIN, 20));

        model.addColumn("Register");
        model.addColumn("FU");

        for(int i = 0; i < registerNumber; i++) {
            model.insertRow(i, new Object[]{"F"+2*i, ""});
        }
    }
    public JTable getTable(){
        return table;
    }

    public void setFu(String register, String FU){
        for(int i = 0; i < registerNumber; i++){
            if(register.equals((String)model.getValueAt(i, 0))){
                model.setValueAt(FU, i, 1);
                return;
            }
        }
    }
    public String getFu(String register){
        for(int i = 0; i < registerNumber; i++){
            if(register.equals((String)model.getValueAt(i, 0))){
                return (String)model.getValueAt(i, 1);
            }
        }
        return "";
    }
    public String getRegister(String FU){
        for(int i = 0; i < registerNumber; i++)
            if(model.getValueAt(i, 1) == FU)
                return (String) model.getValueAt(i, 0);
        return "";
    }

    public void resetFu(String register){
        for(int i = 0; i < registerNumber; i++){
            if(register.equals((String)model.getValueAt(i, 0))){
                model.setValueAt("", i, 1);
                return;
            }
        }
    }


    public void reset(){
        for(int i = 0; i < registerNumber; i++){
            model.setValueAt("", i, 1);
        }
    }
}
