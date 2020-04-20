import java.awt.*;
public class InputDc extends Panel
{

Label       lFaultProcessor;
TextField 
 tFaultProcessor;

Label       lFaultNumber;
TextField   tFaultNumber; 

Label       lMyTime ;
List        tMyTime; 

Button       instructButton;
Button       animateButton;
Button       freezeButton;


private Color myMagenta;

GridBagLayout gridbag           = new GridBagLayout();
GridBagConstraints constraints  = new GridBagConstraints();

public InputDc ()
    {
    setLayout(gridbag);
  //  this.setSize(800,51);
    myMagenta  = new Color(255,130,255);
    
    lFaultProcessor  = new Label("Fibonacci Level, where error happens",Label.RIGHT);
    tFaultProcessor  = new TextField ("3",1);

    
    lFaultNumber     = new Label("Number that you want to add to error level",Label.RIGHT);
    
    tFaultNumber     = new TextField ("10",5);
    
    lMyTime          = new Label("Animation Speed",Label.RIGHT);
    //tMyTime          = new TextField ("10",2);
    tMyTime          = new List(1, false);
    
     
    tMyTime.add("100");
    tMyTime.add("95");
    tMyTime.add("90");
    tMyTime.add("85");
    tMyTime.add("80");
    tMyTime.add("75");
    tMyTime.add("70");
    tMyTime.add("65");
    tMyTime.add("60");
    tMyTime.add("55");   
    tMyTime.add("50");
    tMyTime.add("45");
    tMyTime.add("40");
    tMyTime.add("35");
    tMyTime.add("30");
    tMyTime.add("25");
    tMyTime.add("20");
    tMyTime.add("15");
    tMyTime.add("10");
    tMyTime.add("5");   
    
    lFaultProcessor.setBackground(myMagenta);
    lFaultNumber.setBackground(myMagenta);
    lMyTime.setBackground(myMagenta);
    
    instructButton = new Button("Instructions");
    animateButton  = new Button("Begin Animation");
    freezeButton   = new Button("Freeze");

 
 
    buildConstraints(constraints,0,0,1,1,25,50);
    constraints.fill    = GridBagConstraints.HORIZONTAL;
    constraints.anchor  = GridBagConstraints.EAST;
    gridbag.setConstraints(lFaultProcessor,constraints);
    add(lFaultProcessor);
    
    
    buildConstraints(constraints,1,0,1,1,20,0);
    constraints.fill    = GridBagConstraints.HORIZONTAL;
    constraints.anchor  = GridBagConstraints.WEST;
    gridbag.setConstraints(tFaultProcessor,constraints);
    add(tFaultProcessor);
    
   
    buildConstraints(constraints,2,0,1,1,25,0);
    constraints.fill    = GridBagConstraints.HORIZONTAL;
    constraints.anchor  = GridBagConstraints.EAST;
    gridbag.setConstraints(lFaultNumber,constraints);
    add(lFaultNumber);
    
   
    buildConstraints(constraints,3,0,1,1,20,0);
    constraints.fill    = GridBagConstraints.HORIZONTAL;
    constraints.anchor  = GridBagConstraints.WEST;
    gridbag.setConstraints(tFaultNumber,constraints);
    add(tFaultNumber);
    
    buildConstraints(constraints,0,1,1,1,0,50);
    constraints.fill    = GridBagConstraints.HORIZONTAL;
    constraints.anchor  = GridBagConstraints.EAST;
    gridbag.setConstraints(lMyTime,constraints);
    add(lMyTime);
    
     
    buildConstraints(constraints,1,1,1,1,0,0);
    constraints.fill    = GridBagConstraints.VERTICAL;
    constraints.anchor  = GridBagConstraints.WEST;
    gridbag.setConstraints(tMyTime,constraints);
    add(tMyTime);
    
    buildConstraints(constraints,2,1,1,1,0,0);
    constraints.fill    = GridBagConstraints.BOTH;
    constraints.anchor  = GridBagConstraints.CENTER;
    gridbag.setConstraints(animateButton,constraints);
    add(animateButton);

    buildConstraints(constraints,3,1,1,1,0,0);
    constraints.fill    = GridBagConstraints.BOTH;
    constraints.anchor  = GridBagConstraints.CENTER;
    gridbag.setConstraints(instructButton,constraints);
    add(instructButton);
 
    
       
    }
 
void buildConstraints(GridBagConstraints gbc,int gx,int gy,int gw,int gh,int wx,int wy)
    {
    gbc.gridx      = gx;
    gbc.gridy      = gy;
    gbc.gridwidth  = gw;
    gbc.gridheight = gh;
    gbc.weightx    = wx;
    gbc.weighty    = wy;
    gbc.ipadx      = 30;
    gbc.ipady      = 1;
    }
 
    
}