package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ylf951 on 16/3/26.
 */

public class StoreBuffers {
    private JTable table;
    private DefaultTableModel model;
    private int storeNumber = 2;
    private int []instructions;
    private int []countdown;

    public StoreBuffers(){
        model = new DefaultTableModel();
        table = new JTable(model);

        table.setRowHeight(25);
        table.setFont(new Font("Serif", Font.PLAIN, 20));

        model.addColumn("Name");
        model.addColumn("Busy");
        model.addColumn("Address");
        model.addColumn("Qj");


        instructions = new int[storeNumber];
        countdown = new int[storeNumber];

        for(int i = 0; i < storeNumber; i++) {
            model.insertRow(i, new Object[]{"Store" + (i + 1), "No", "", ""});
            instructions[i] = -1;
            countdown[i] = -1;
        }
    }
    public JTable getTable(){
        return table;
    }
    public int getStoreNumber(){
        return storeNumber;
    }


    public boolean hasAvailable(){
        for(int i = 0; i < storeNumber; i++)
            if(model.getValueAt(i, 1) == "No")
                return true;
        return false;
    }


    public void setAvailable(String name){
        for(int i = 0; i < storeNumber; i++){
            if(name.equals((String)model.getValueAt(i, 0))){
                model.setValueAt("No", i, 1);
                model.setValueAt("", i, 2);
                instructions[i] = -1;
                countdown[i] = -1;
            }
        }
    }



    public void issueStore(int instr, String address, String FU){
        for(int i = 0; i < storeNumber; i++){
            if(model.getValueAt(i, 1) == "No"){
                model.setValueAt("Yes", i, 1);
                model.setValueAt(address, i, 2);
                if(FU != ""){
                    model.setValueAt(FU, i, 3);
                }
                instructions[i] = instr;
                return;
            }
        }
    }

    public List<DataBus> moveNextClock(){
        List<DataBus> result = new ArrayList<>();
        for(int i = 0; i < storeNumber; i++){
            if(model.getValueAt(i, 1) == "Yes"){
                if(countdown[i] > 1){
                    countdown[i]--;
                }
                else if(countdown[i] == 1){
                    result.add(new DataBus(DataBus.CompletionType.ExcecCompletion, instructions[i], (String)model.getValueAt(i, 0), DataBus.ResourceType.StoreBuffer));
                }
                else if(countdown[i] == -1){
                    if(model.getValueAt(i, 3) == "")
                        countdown[i] = Util.ExecutionTime_Store;
                }
            }
        }
        return result;
    }

    public void becomeAvailable(String name){
        for(int i = 0; i < storeNumber; i++){
            if(model.getValueAt(i, 1) == "Yes" && name.equals((String)model.getValueAt(i,3))){
                model.setValueAt("", i, 3);
            }
        }
    }




    public void reset(){
        for(int i = 0; i < storeNumber; i++){
            instructions[i] = -1;
            countdown[i] = -1;
            model.setValueAt("No", i, 1);
            model.setValueAt("", i, 2);
            model.setValueAt("", i, 3);
        }
    }
}
