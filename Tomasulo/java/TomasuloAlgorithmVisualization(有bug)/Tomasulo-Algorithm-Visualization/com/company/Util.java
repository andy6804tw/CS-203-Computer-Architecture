package com.company;

/**
 * Created by ylf951 on 16/3/27.
 */
public class Util {
    public enum IssueState{Available, LackLoadBuffer, LackStoreBuffer, LackAddUnit, LackMultUnit}

    static int ExecutionTime_Load = 2;
    static int ExecutionTime_Store = 1;
    static int ExecutionTime_Add = 2;
    static int ExecutionTime_Sub = 2;
    static int ExecutionTime_Mult = 10;
    static int ExecutionTime_Div = 40;

}
