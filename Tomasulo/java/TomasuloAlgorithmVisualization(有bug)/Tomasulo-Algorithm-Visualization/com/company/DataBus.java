package com.company;


/**
 * Created by ylf951 on 16/3/27.
 */
public class DataBus {
    public enum CompletionType {ExcecCompletion, WriteResult}
    public enum ResourceType{ReservationsStaions, LoadBuffer, StoreBuffer}

    private CompletionType type;
    private int instr_index;
    private String name;
    private ResourceType resource;



    public DataBus(CompletionType t, int index, String n, ResourceType r){
        type = t;
        instr_index = index;
        name = n;
        resource = r;
    }

    public CompletionType getType(){
        return type;
    }
    public int getInstrIndex(){
        return instr_index;
    }
    public String getName(){
        return name;
    }
    public ResourceType getResourceType(){ return resource;}
}
