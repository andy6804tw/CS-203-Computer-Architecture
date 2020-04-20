import java.awt.*;
import java.awt.event.*;

import java.text.*;
import java.util.*;

public class DominoEffect extends java.applet.Applet implements ActionListener,ItemListener
{

private InputDc        swatch1;
private DistCheck      swatch2;
private TitleDc        swatchT;
private Introduction   swatchI;
private ErrorBoxS      swatchErr;

private ScrollPane scrollPaneo1;
private ScrollPane scrollPaneo2;

private int procTime,newIncrease,pauseTime;
private String   HataMesaj;

private boolean       myAnimationPanelFlag;
public  boolean       myFreezeFlag;
private boolean       inputconfirm;
 
private Color myOrange;
private Color myMagenta;

GridBagLayout gridbag           = new GridBagLayout();
GridBagConstraints constraints  = new GridBagConstraints();

public void init()
    {
 
   buildConstraints(constraints,0,0,1,1,0,10);
  swatchT = new TitleDc();
  swatchT.setBackground(myMagenta); 
  swatchT.resize(870,51);
  gridbag.setConstraints(swatchT,constraints);
   add(swatchT);
       
   
    myMagenta  = new Color(255,130,255);
    
    buildConstraints(constraints,0,1,1,1,0,10);
    constraints.fill    = GridBagConstraints.VERTICAL;
    constraints.anchor  = GridBagConstraints.CENTER;
    swatch1 = new InputDc();
    swatch1.setSize(870,51);
    gridbag.setConstraints(swatch1,constraints);
    add(swatch1); 
    

    swatch1.instructButton.addActionListener(this);
    swatch1.animateButton.addActionListener(this);
    swatch1.tMyTime.addItemListener(this);
    pauseTime = 1;


    
    buildConstraints(constraints,0,2,1,1,0,80);
    swatchI = new Introduction();
    scrollPaneo2 = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    scrollPaneo2.add(swatchI);
    scrollPaneo2.resize(870,408);
    gridbag.setConstraints(scrollPaneo2,constraints);
    add(scrollPaneo2); 
    myAnimationPanelFlag = false;

    }

public void actionPerformed(ActionEvent evt)
     {
     Object source = evt.getSource();
     if        (source == swatch1.instructButton)  loadMyInstruction(); 
     else if   (source == swatch1.animateButton)   loadMyAnimation(); 
     }
public void itemStateChanged(ItemEvent e) 
     {
     int tempPause = ((Integer)(e.getItem())).intValue();
     pauseTime = (tempPause*3); 
      
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
    
public void loadMyAnimation() 
    {
    getInputs();
    if( myAnimationPanelFlag == true) swatch2.stop();
    remove(scrollPaneo2);    
    scrollPaneo2 = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    
    if(inputconfirm == false) 
        {
        swatchErr = new ErrorBoxS(HataMesaj);
        scrollPaneo2.add(swatchErr);
        myAnimationPanelFlag = false;
        }
    else
        {
        swatch2 = new DistCheck(procTime,newIncrease,pauseTime);
        scrollPaneo2.add(swatch2);
        myAnimationPanelFlag = true;
        }
    scrollPaneo2.resize(870,408);
   buildConstraints(constraints,0,2,1,1,0,80);
    gridbag.setConstraints(scrollPaneo2,constraints);
    add(scrollPaneo2);

    validate();
    repaint();  
    }

public void loadMyInstruction()
    {
    if( myAnimationPanelFlag == true) swatch2.stop();
    remove(scrollPaneo2);    
   
    swatchI = new Introduction();
    scrollPaneo2 = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    scrollPaneo2.add(swatchI);
    scrollPaneo2.resize(870,408);
   
    buildConstraints(constraints,0,2,1,1,0,80);
    gridbag.setConstraints(scrollPaneo2,constraints);
    add(scrollPaneo2);

    validate();
    repaint();  
    myAnimationPanelFlag = false;
    }


    
public void getInputs()
    {
    inputconfirm = true;
    
    try 
        {
        procTime = Integer.parseInt(swatch1.tFaultProcessor.getText());  
        }catch (NumberFormatException e) 
        {
        inputconfirm = false;
        HataMesaj =  "Incorrect input, Fibonacci Level should be between 2 and 10";                
        }//end try      
    try 
        {
        newIncrease = Integer.parseInt(swatch1.tFaultNumber.getText());  
        }catch (NumberFormatException e) 
        {
        inputconfirm = false;
        HataMesaj =  "Incorrect input, Increase cannot be bigger than 20 or less than -20";                
        }//end try      
/*    try 
        {
        pauseTime = Integer.parseInt(swatch1.tMyTime.getText());  
        }catch (NumberFormatException e) 
        {
        inputconfirm = false;
        HataMesaj =  "Incorrect input, Timer can be betwwen 0 and 20";                
        }//end try     
*/    if(procTime >9 || procTime <2)
        {
        inputconfirm = false;
        HataMesaj =  "Fibonacci Level should be between 2 and 9";                
        }
    if(newIncrease >20 || newIncrease < -20 )
        {
        inputconfirm = false;
        HataMesaj =  "Increase cannot be bigger than 20 or less than -20 ";                
        }    
        
/*     if(pauseTime >100 || pauseTime <0)
        {
        inputconfirm = false;
        HataMesaj =  "Pause level should be between 1 and 100";                
        }         
*/            
    }
  


}




