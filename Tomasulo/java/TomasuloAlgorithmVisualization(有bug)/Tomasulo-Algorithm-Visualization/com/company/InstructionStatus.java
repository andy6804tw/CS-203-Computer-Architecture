package com.company;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by ylf951 on 16/3/25.
 */
public class InstructionStatus extends JComponent {
    private JTable table;
    private DefaultTableModel model;
    String[] columnNames = {"Instruction",
            "Dest",
            "j",
            "k",
            "Issue",
            "Exec Completion",
            "Write \nResult"};
    List<List<String>> instructions = new ArrayList<>();

    private boolean[] isIssued;
    private boolean[] isCompleted;
    public InstructionStatus(List<List<String>> instrs){
        model = new DefaultTableModel();
        table = new JTable(model);

        table.setRowHeight(25);
        table.setFont(new Font("Serif", Font.PLAIN, 20));

        for(int i = 0; i < columnNames.length; i++) {
            model.addColumn(columnNames[i]);
        }
        instructions = instrs;


        for(int i = 0; i < instructions.size(); i++){
            List<String>temp = instructions.get(i);
            model.insertRow(i, new Object[] {temp.get(0), temp.get(1), temp.get(2), temp.get(3), "", "", ""});
        }

        isIssued = new boolean[instructions.size()];
        isCompleted = new boolean[instructions.size()];


    }

    public void loadNewInstructions(List<List<String>> instrs){
        for(int i = 0; i < isIssued.length; i++)  //delete original table
            model.removeRow(0);


        instructions = instrs; // load new set of instructions
        for(int i = 0; i < instructions.size(); i++){
            List<String>temp = instructions.get(i);
            model.insertRow(i, new Object[] {temp.get(0), temp.get(1), temp.get(2), temp.get(3), "", "", ""});
        }

        isIssued = new boolean[instructions.size()];
        isCompleted = new boolean[instructions.size()];
    }


    public JTable getTable(){
        return table;
    }



    public boolean isAllIssued(){
        for(int i = 0; i < isIssued.length; i++)
            if(!isIssued[i])
                return false;
        return true;
    }
    public boolean isAllCompleted(){
        for(int i = 0; i < isCompleted.length; i++)
            if(!isCompleted[i])
                return false;
        return true;
    }
    public int getFirstUnIssued(){
        for(int i = 0; i < isIssued.length; i++)
            if(!isIssued[i]){
                return i;
            }
        return -1;
    }
    public void setIssueTime(int instr_index, int clock){
        model.setValueAt(clock, instr_index, 4);
        isIssued[instr_index] = true;
    }

    public void setExecComplTime(int instr_index, int clock){
        model.setValueAt(clock, instr_index, 5);
    }
    public void setWriteResultTime(int instr_index, int clock){
        model.setValueAt(clock, instr_index, 6);
        isCompleted[instr_index] = true;
    }

    public void reset(){
        for(int i = 0; i < isCompleted.length; i++){
            isCompleted[i] = false;
            isIssued[i] = false;
            model.setValueAt("", i, 4);
            model.setValueAt("", i, 5);
            model.setValueAt("", i, 6);
        }
    }

}
