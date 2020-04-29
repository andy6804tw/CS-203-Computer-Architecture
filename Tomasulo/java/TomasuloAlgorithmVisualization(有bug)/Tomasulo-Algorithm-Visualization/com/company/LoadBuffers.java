package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ylf951 on 16/3/26.
 */
public class LoadBuffers {
    private JTable table;
    private DefaultTableModel model;
    private int loadNumber = 2;
    private int []instructions;
    private int []countdown;
    public LoadBuffers(){
        model = new DefaultTableModel();
        table = new JTable(model);

        table.setRowHeight(25);
        table.setFont(new Font("Serif", Font.PLAIN, 20));

        model.addColumn("Name");
        model.addColumn("Busy");
        model.addColumn("Address");

        instructions = new int[loadNumber];
        countdown = new int[loadNumber];
        for(int i = 0; i < loadNumber; i++) {
            model.insertRow(i, new Object[]{"Load" + (i + 1), "No", ""});
            instructions[i] = -1;
            countdown[i] = -1;
        }


    }
    public JTable getTable(){
        return table;
    }
    public int getLoadNumber(){
        return loadNumber;
    }
    public void setLoadNumber(int number){
        if(number < loadNumber) {
            for(int i = 0; i < loadNumber-number; i++)
                model.removeRow(loadNumber-i-1);
            loadNumber = number;
        }
        else if(number > loadNumber){
            for(int i = 0; i < number - loadNumber; i++)
                model.insertRow(loadNumber+i, new Object[] {"Load"+(loadNumber+i+1), "No", ""});
            loadNumber = number;
        }
    }
    public boolean hasAvailable(){
        for(int i = 0; i < loadNumber; i++)
            if(model.getValueAt(i, 1) == "No")
                return true;
        return false;
    }
    public String issueLoad(int instr, String address){
        for(int i = 0; i < loadNumber; i++)
            if(model.getValueAt(i, 1) == "No"){
                model.setValueAt("Yes", i, 1);
                model.setValueAt(address, i, 2);
                instructions[i] = instr;
                countdown[i] = 2;
                return (String)model.getValueAt(i, 0);
            }
        return "";
    }
    public void setAvailable(String name){
        for(int i = 0; i < loadNumber; i++){
            if(name.equals((String)model.getValueAt(i, 0))){
                model.setValueAt("No", i, 1);
                model.setValueAt("", i, 2);
                instructions[i] = -1;
                countdown[i] = -1;
            }
        }
    }

    public List<DataBus> moveNextClock(){
        List<DataBus> result = new ArrayList<>();
        for(int i = 0; i < loadNumber; i++)
            if(model.getValueAt(i, 1) == "Yes"){
                if(countdown[i] > 0)
                    countdown[i]--;
                else if(countdown[i] == 0){
                    result.add(new DataBus(DataBus.CompletionType.ExcecCompletion, instructions[i], (String)model.getValueAt(i, 0), DataBus.ResourceType.LoadBuffer));

                }
            }
        return result;
    }

    public void reset(){
        for(int i = 0; i < loadNumber; i++){
            instructions[i] = -1;
            countdown[i] = -1;
            model.setValueAt("No", i, 1);
            model.setValueAt("", i, 2);
        }
    }

}
