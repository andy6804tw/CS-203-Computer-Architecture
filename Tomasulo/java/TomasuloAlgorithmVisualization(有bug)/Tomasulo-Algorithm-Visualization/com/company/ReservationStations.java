package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by ylf951 on 16/3/25.
 */
public class ReservationStations extends JComponent{
    private JTable table;
    private DefaultTableModel model;
    private int addNumber = 3;
    private int mulNumber = 2;
    String[] columnNames = {"Time",
            "Name",
            "Busy",
            "Op",
            "Vj",
            "Vk",
            "Qj",
            "Qk"};
    private int[] instructions;
    private int[] countdown;
    public ReservationStations(){
        model = new DefaultTableModel();
        table = new JTable(model);

        table.setRowHeight(25);
        table.setFont(new Font("Serif", Font.PLAIN, 20));

        for(int i = 0; i < columnNames.length; i++) {
            model.addColumn(columnNames[i]);
        }
        instructions = new int[addNumber+mulNumber];
        countdown = new int[addNumber+mulNumber];
        for(int i = 0; i < addNumber; i++){
            model.insertRow(i, new Object[] {"", "Add"+(i+1), "No", "", "", "", "", ""});
            instructions[i] = -1;
            countdown[i] = -1;
        }
        for(int i = 0; i <mulNumber; i++){
            model.insertRow(addNumber+i, new Object[] {"", "Mult"+(i+1), "No", "", "", "", "", ""});
            instructions[addNumber+i] = -1;
            countdown[addNumber+i] = -1;
        }
    }
    public JTable getTable(){
        return table;
    }

    public void setValueAt(String s, int row, int column){
        model.setValueAt(s, row, column);
    }

    public boolean hasAvailableAdd(){
        for(int i = 0; i < addNumber; i++)
            if(model.getValueAt(i, 2) == "No")
                return true;
        return false;
    }
    public boolean hasAvailableMul(){
        for(int i = addNumber; i < addNumber+mulNumber; i++)
            if(model.getValueAt(i, 2) == "No")
                return true;
        return false;
    }
    public String issueAdd(int instr_index, String Op, int j, String Fj, int k, String Fk){
        for(int i = 0; i < addNumber; i++)
            if(model.getValueAt(i, 2) == "No"){
                model.setValueAt("Yes", i, 2);
                model.setValueAt(Op, i, 3);
                if(j == 1)
                    model.setValueAt(Fj, i, 4);
                else
                    model.setValueAt(Fj, i, 6);
                if(k == 1)
                    model.setValueAt(Fk, i, 5);
                else
                    model.setValueAt(Fk, i, 7);
                instructions[i] = instr_index;



                return (String)model.getValueAt(i, 1);
            }
        return "";
    }
    public String issueMult(int instr_index, String Op, int j, String Fj, int k, String Fk){
        for(int i = addNumber; i < addNumber+mulNumber; i++)
            if(model.getValueAt(i, 2) == "No"){
                model.setValueAt("Yes", i, 2);
                model.setValueAt(Op, i, 3);
                if(j == 1)
                    model.setValueAt(Fj, i, 4);
                else
                    model.setValueAt(Fj, i, 6);
                if(k == 1)
                    model.setValueAt(Fk, i, 5);
                else
                    model.setValueAt(Fk, i, 7);
                instructions[i] = instr_index;

                return (String)model.getValueAt(i, 1);
            }
        return "";
    }
    public void becomeAvailable(String name, String register){
        for(int i = 0; i < addNumber+mulNumber; i++){
            if(model.getValueAt(i, 2) == "Yes" && countdown[i] == -1){//means this reservation station has not started computing, it's still waiting for some register(s) becoming available.
                if(model.getValueAt(i, 4) == "" && name.equals((String)model.getValueAt(i, 6))){
                    model.setValueAt("R("+register+")", i, 4);
                    model.setValueAt("", i, 6);
                }
                if(model.getValueAt(i, 5) == "" && name.equals((String)model.getValueAt(i, 7))){
                    model.setValueAt("R("+register+")", i, 5);
                    model.setValueAt("", i, 7);
                }

            }
        }
    }

    public void setAvailable(String name){
        for(int i = 0; i < addNumber+mulNumber; i++){
            if(name.equals((String)model.getValueAt(i, 1))){
                model.setValueAt("", i, 0);
                model.setValueAt("No", i, 2);
                model.setValueAt("", i, 3);
                model.setValueAt("", i, 4);
                model.setValueAt("", i, 5);
                model.setValueAt("", i, 6);
                model.setValueAt("", i, 7);
                instructions[i] = -1;
                countdown[i] = -1;
                return;
            }
        }
    }


    public List<DataBus> moveNextClock(){
        List<DataBus> result = new ArrayList<>();
        for(int i = 0; i < addNumber+mulNumber; i++)
            if(model.getValueAt(i, 2) == "Yes"){
                if(countdown[i] > 1) {
                    countdown[i]--;
                    model.setValueAt(countdown[i], i, 0);
                }
                else if(countdown[i] == 1){
                    countdown[i]--;
                    model.setValueAt(countdown[i], i, 0);
                    result.add(new DataBus(DataBus.CompletionType.ExcecCompletion, instructions[i], (String)model.getValueAt(i, 1), DataBus.ResourceType.ReservationsStaions));

                }
                if(countdown[i] == -1) { //countdown[i] == -1;
                    if (model.getValueAt(i, 6) == "" && model.getValueAt(i, 7) == "") { //which means this reservation station starts countdown
                        String op = (String) model.getValueAt(i, 3);
                        if (op.equals("ADD") || op.equals("SUB"))
                            countdown[i] = Util.ExecutionTime_Add;
                        else if (op.equals("MUL"))
                            countdown[i] = Util.ExecutionTime_Mult ;
                        else
                            countdown[i] = Util.ExecutionTime_Div;
                        model.setValueAt(countdown[i], i, 0);
                    }
                }

            }
        return result;
    }

    public void reset(){
        for(int i = 0; i < addNumber+mulNumber; i++){
            model.setValueAt("", i, 0);
            model.setValueAt("No", i, 2);
            model.setValueAt("", i, 3);
            model.setValueAt("", i, 4);
            model.setValueAt("", i, 5);
            model.setValueAt("", i, 6);
            model.setValueAt("", i, 7);
            instructions[i] = -1;
            countdown[i] = -1;
        }
    }
}
