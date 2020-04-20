import java.awt.*;
public class Input1 extends Panel
{

Label     PermanentFaults;
TextField TPermanentFaults;

//Label     InstBetTwoCheck;   /* M */
//TextField TInstBetTwoCheck;

//Label     MeanTimeSpentPerInstr; /* W */ 
//TextField TMeanTimeSpentPerInstr;

Label     LVal;  /* L */ 
TextField TLVal;

Label     SetupTimeNeed;   /* Delta 1 */
TextField TSetupTimeNeed;

Label     DiagRepairNeed;  /* Delta 2 */ 
TextField TDiagRepairNeed; 

Label     EmptyLabel1;

GridBagLayout gridbag           = new GridBagLayout();
GridBagConstraints constraints  = new GridBagConstraints();


public Input1()
   {
   setLayout(gridbag);
 //  this.setSize(1050,170);

   PermanentFaults            = new Label("Probability that fault is permanent <0-1.0>",Label.RIGHT);
   TPermanentFaults           = new TextField ("0.5",15);

 //  InstBetTwoCheck            = new Label("Number of Instructions between two checkpoints ",Label.RIGHT);
 //  TInstBetTwoCheck           = new TextField ("20",15);

 //  MeanTimeSpentPerInstr      = new Label("Mean Execution Time for Instruction",Label.RIGHT);
 //  TMeanTimeSpentPerInstr     = new TextField ("0.001",15);

  
   LVal                       = new Label("Average Number of Instructions per Program ",Label.RIGHT);
   TLVal                      = new TextField ("100",15);

   SetupTimeNeed              = new Label("Required Setup Time ",Label.RIGHT);
   TSetupTimeNeed             = new TextField ("0.002",15);

   DiagRepairNeed             = new Label("Required Diagnosis and Repair Time",Label.RIGHT);
   TDiagRepairNeed            = new TextField ("0.003",15);

   EmptyLabel1                = new Label(" ");
  

  
   buildConstraints(constraints,0,0,1,1,35,50);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(PermanentFaults,constraints);
   add(PermanentFaults);

   buildConstraints(constraints,1,0,1,1,15,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TPermanentFaults,constraints);
   add(TPermanentFaults);
/*  
   buildConstraints(constraints,2,0,1,1,35,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(InstBetTwoCheck,constraints);
   add(InstBetTwoCheck);
  
 
   buildConstraints(constraints,3,0,1,1,15,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TInstBetTwoCheck,constraints);
   add(TInstBetTwoCheck);
   
   buildConstraints(constraints,0,1,1,1,0,50);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(MeanTimeSpentPerInstr,constraints);
   add(MeanTimeSpentPerInstr);
  
   
   buildConstraints(constraints,1,1,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TMeanTimeSpentPerInstr,constraints);
   add(TMeanTimeSpentPerInstr);
  
 */  
   buildConstraints(constraints,2,0,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(LVal,constraints);
   add(LVal);
  
   
   buildConstraints(constraints,3,0,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TLVal,constraints);
   add(TLVal);
  
   
   buildConstraints(constraints,0,1,1,1,0,33);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(SetupTimeNeed,constraints);
   add(SetupTimeNeed);
  
   
   buildConstraints(constraints,1,1,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TSetupTimeNeed,constraints);
   add(TSetupTimeNeed);
  
   
   buildConstraints(constraints,2,1,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(DiagRepairNeed,constraints);
   add(DiagRepairNeed);
  
   
   buildConstraints(constraints,3,1,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TDiagRepairNeed,constraints);
   add(TDiagRepairNeed);

   buildConstraints(constraints,4,0,1,4,10,0);
   constraints.fill    = GridBagConstraints.BOTH;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(EmptyLabel1,constraints);
   add(EmptyLabel1);


   }

void buildConstraints(GridBagConstraints gbc,int gx,int gy,int gw,int gh,int wx,int wy)
    {
   gbc.gridx      = gx;
   gbc.gridy      = gy;
   gbc.gridwidth  = gw;
   gbc.gridheight = gh;
   gbc.weightx    = wx;
   gbc.weighty    = wy;
   }

}