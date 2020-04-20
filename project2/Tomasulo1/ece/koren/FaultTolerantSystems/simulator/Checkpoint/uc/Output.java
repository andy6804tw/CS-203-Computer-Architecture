import java.awt.*;
public class Output extends Panel
{
Label     Pc;
TextField TPc;

Label     PRb1;
TextField TPRb1;

Label     PRb2;
TextField TPRb2;

Label     Pf; 
TextField TPf; 

Label     Tau1; 
TextField TTau1;

Label     Tau2; 
TextField TTau2;


Label     EmptyLabel1;

GridBagLayout gridbag           = new GridBagLayout();
GridBagConstraints constraints  = new GridBagConstraints();


public Output()
   {
   setLayout(gridbag);
   this.setSize(1050,210);

   Pc               = new Label(" Probability of succesfully execution without any failure",Label.RIGHT);
   TPc              = new TextField("0.0",20);

   PRb1             = new Label("Probability of execution after one rollback",Label.RIGHT);
   TPRb1            = new TextField ("0.0",20);

   PRb2             = new Label("Probabilty of execution after two rollbacks",Label.RIGHT);
   TPRb2            = new TextField ("0.0",20);

   Pf               = new Label("Failure probability",Label.RIGHT);
   TPf              = new TextField ("0.0",20);

   Tau1             = new Label("Required Time for one rollback run ",Label.RIGHT);
   TTau1            = new TextField ("0.0",20);

   Tau2             = new Label("Required Time  for two rollback run",Label.RIGHT);
   TTau2            = new TextField ("0.0",20);

   EmptyLabel1                = new Label(" ");
  

  
   buildConstraints(constraints,0,0,1,1,20,34);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(Pc,constraints);
   add(Pc);

   buildConstraints(constraints,1,0,1,1,27,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TPc,constraints);
   add(TPc);
  
   buildConstraints(constraints,2,0,1,1,20,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(PRb1,constraints);
   add(PRb1);
  
 
   buildConstraints(constraints,3,0,1,1,27,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TPRb1,constraints);
   add(TPRb1);
   
   buildConstraints(constraints,0,1,1,1,0,33);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(PRb2,constraints);
   add(PRb2);
  
   
   buildConstraints(constraints,1,1,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TPRb2,constraints);
   add(TPRb2);
  
   
   buildConstraints(constraints,2,1,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(Pf,constraints);
   add(Pf);
  
   
   buildConstraints(constraints,3,1,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TPf,constraints);
   add(TPf);
  
   
   buildConstraints(constraints,0,2,1,1,0,33);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(Tau1,constraints);
   add(Tau1);
  
   
   buildConstraints(constraints,1,2,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TTau1,constraints);
   add(TTau1);
  
   
   buildConstraints(constraints,2,2,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.WEST;
   gridbag.setConstraints(Tau2,constraints);
   add(Tau2);
  
   
   buildConstraints(constraints,3,2,1,1,0,0);
   constraints.fill    = GridBagConstraints.HORIZONTAL;
   constraints.anchor  = GridBagConstraints.EAST;
   gridbag.setConstraints(TTau2,constraints);
   add(TTau2);

   buildConstraints(constraints,4,0,1,4,6,0);
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