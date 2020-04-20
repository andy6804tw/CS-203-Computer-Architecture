import java.awt.event.*;
import java.awt.*;

import java.text.*;
import java.util.*;

public class CheckPoint extends java.applet.Applet implements ActionListener

{
TitleC          swatchT;
Input1          swatch1;
Rates           swatch2;
Output          swatch3;

ErrorBoxS       swatchErr;
MyButton        swatchB;
Label           PageTitle;

ScrollPane scrollPaneo1;
ScrollPane scrollPaneo2;
ScrollPane scrollPaneo3;
ScrollPane scrollPaneoB;

ScrollPane scrollPaneoT;
ScrollPane scrollPaneoErr;

boolean inputconfirm;
String HataMesaj;
int availablePanel;

Color myBlue;
Color myYellow;
Color myPink;
Color myMagenta;
Color myMagenta2;
Color myOrange;
Color myOrange2;
Color myCyan;   
/* Input Values */

TRResults prb;

double  s;
int     M;
double  w;
int     L;
double  delta1;
double  delta2;

double Pcg;
double pfailed ;
double totalfrequnce ;

GridBagLayout gridbag           = new GridBagLayout();
GridBagConstraints constraints  = new GridBagConstraints();
public void init()
   {
   myBlue     = new Color(150,150,255);
   myYellow   = new Color(255,255,120);
   myPink     = new Color(255,200,200);
   myMagenta  = new Color(255,130,255);
   myMagenta2 = new Color(255,155,255); 
   myOrange   = new Color(255,200,120);
   myCyan     = new Color(64,255,255); 
   myOrange2  = new Color(255,180,100);
  
   setLayout(gridbag);
   
 // this.setBackground(myPink); 
 
  buildConstraints(constraints,0,0,1,1,0,10);
  swatchT = new TitleC();
 // scrollPaneoT = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
  // scrollPaneoT.add(swatchT);
   swatchT.resize(1100,80);
  gridbag.setConstraints(swatchT,constraints);
   add(swatchT);

 
   buildConstraints(constraints,0,1,1,1,0,18);
   swatch1 = new Input1();
   scrollPaneo1 = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
   scrollPaneo1.setBackground(myOrange2); 
   scrollPaneo1.add(swatch1);
   scrollPaneo1.resize(1100,142);
  gridbag.setConstraints(scrollPaneo1,constraints);
   add(scrollPaneo1); 
   
   buildConstraints(constraints,0,2,1,1,0,34);
   swatch2 = new Rates();
   scrollPaneo2 = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
   scrollPaneo2.setBackground(myOrange); 
   scrollPaneo2.add(swatch2);
   scrollPaneo2.resize(1100,272);
  gridbag.setConstraints(scrollPaneo2,constraints);
   add(scrollPaneo2);

  buildConstraints(constraints,0,3,1,1,0,13);
   swatchB = new MyButton();
   scrollPaneoB = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
   scrollPaneoB.setBackground(Color.lightGray); 
   scrollPaneoB.add(swatchB);
   scrollPaneoB.resize(1100,114);
   gridbag.setConstraints(scrollPaneoB,constraints);
   add(scrollPaneoB);
   swatchB.ResultButton.setBackground(Color.gray);
 swatchB.ResultButton.addActionListener(this);


   buildConstraints(constraints,0,4,1,1,0,25);
   swatch3 = new Output();
   scrollPaneo3 = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
   scrollPaneo3.setBackground(myMagenta); 
   scrollPaneo3.add(swatch3);
   scrollPaneo3.resize(1100,180);
  gridbag.setConstraints(scrollPaneo3,constraints);
   add(scrollPaneo3);
  availablePanel = 3;
  
 /* 
   swatch2.Instruction1.setBackground(Color.blue);
   swatch2.Instruction2.setBackground(Color.cyan);
   swatch2.Instruction3.setBackground(Color.gray);
   swatch2.Instruction4.setBackground(Color.green);
   swatch2.Instruction5.setBackground(Color.lightGray);
   swatch2.Instruction6.setBackground(Color.magenta);
   swatch2.Instruction7.setBackground(Color.orange);
   swatch2.Instruction8.setBackground(Color.pink);
   swatch2.Instruction9.setBackground(Color.red);
   swatch2.Instruction10.setBackground(Color.yellow);
  
   swatch2.FR1.setBackground(Color.blue);
   swatch2.FR2.setBackground(Color.cyan);
   swatch2.FR3.setBackground(Color.gray);
   swatch2.FR4.setBackground(Color.green);
   swatch2.FR5.setBackground(Color.lightGray);
   swatch2.FR6.setBackground(Color.magenta);
   swatch2.FR7.setBackground(Color.orange);
   swatch2.FR8.setBackground(Color.pink);
   swatch2.FR9.setBackground(Color.red);
   swatch2.FR10.setBackground(Color.yellow);
  
  swatch3.Pc.setBackground(myBlue);
  swatch3.PRb1.setBackground(myYellow);
  swatch3.PRb2.setBackground(myPink);
  swatch3.Pf.setBackground(myMagenta);
  swatch3.Tau1.setBackground(myOrange);
  swatch3.Tau2.setBackground(myCyan);
  
   swatch3.TPc.setBackground(myBlue);
  swatch3.TPRb1.setBackground(myYellow);
  swatch3.TPRb2.setBackground(myPink);
  swatch3.TPf.setBackground(myMagenta);
  swatch3.TTau1.setBackground(myOrange);
  swatch3.TTau2.setBackground(myCyan);
  
  swatch3.EmptyLabel1.setBackground(myOrangeDerivative);
*/  
  
   }// end of init

public void actionPerformed(ActionEvent evt)
{
     Object source = evt.getSource();
     if     (source == swatchB.ResultButton)  findResult();   
         
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

void findResult ()
{
boolean correctInput;
pfailed = 0.0 ;
Pcg = 0.0; 
Pcvalues prbc = new Pcvalues();
InputValues gir = fillInputRates(); // takes user input from user interface 
correctInput = CheckInputAll(gir);
if(correctInput == true && inputconfirm ==true)
     {
     swatch3 = new Output();
     prbc = findPc(gir,prbc); //find probability without any failure
     swatch3.TPc.setText(String.valueOf(Pcg)); // write result to textbox.
 
     prb  = new TRResults();
     findPRb1_2(gir,prbc); // find PRB1,PRB2
     swatch3.TPRb1.setText(String.valueOf(prb.RR1)); // write RR1 to screen 
     swatch3.TPRb2.setText(String.valueOf(prb.RR2)); // write RR1 to screen

     // failure probability
     pfailed = (1 - Pcg - prb.RR1 - prb.RR2);
     swatch3.TPf.setText(String.valueOf(pfailed));   

     //Tau calculations
     prb.TTau1 =  findmet(gir) + firstpart(gir) + secondpart(gir);
     prb.TTau2 =  findmet(gir) + firstpart(gir) + thirdpart(gir) + fourthpart(gir);
 
     swatch3.TTau1.setText(String.valueOf(prb.TTau1));
     swatch3.TTau2.setText(String.valueOf(prb.TTau2));
     fillswatch3();
     }// end if

else {swatchErr = new ErrorBoxS(HataMesaj); fillswatchErr();}
          
} // end of findResult

// -----------------<<<<<<<<<<<< PCoverage Methods Begin  Also finds additional values>>-----------------------
public Pcvalues findPc(InputValues inp,Pcvalues probc) //=============================================
{
M = 0;
w = 0.0;
Pcvalues probcx = probc;
if(inp.fv1  > 0.0) { probcx.tempPc1  = middlePcStep(inp.fr1 ,inp.tv1, inp.fv1 ); M+=1;w += (inp.fr1*inp.fv1);   }
if(inp.fv2  > 0.0) { probcx.tempPc2  = middlePcStep(inp.fr2 ,inp.tv2, inp.fv2 );M+=1;w += (inp.fr2*inp.fv2);   }
if(inp.fv3  > 0.0) { probcx.tempPc3  = middlePcStep(inp.fr3 ,inp.tv3, inp.fv3 );M+=1;w += (inp.fr3*inp.fv3);   }
if(inp.fv4  > 0.0) { probcx.tempPc4  = middlePcStep(inp.fr4 ,inp.tv4, inp.fv4 );M+=1;w += (inp.fr4*inp.fv4);   }
if(inp.fv5  > 0.0) { probcx.tempPc5  = middlePcStep(inp.fr5 ,inp.tv5, inp.fv5 );M+=1;w += (inp.fr5*inp.fv5);   }
if(inp.fv6  > 0.0) { probcx.tempPc6  = middlePcStep(inp.fr6 ,inp.tv6, inp.fv6 );M+=1;w += (inp.fr6*inp.fv6);   }
if(inp.fv7  > 0.0) { probcx.tempPc7  = middlePcStep(inp.fr7 ,inp.tv7, inp.fv7 );M+=1;w += (inp.fr7*inp.fv7);   }
if(inp.fv8  > 0.0) { probcx.tempPc8  = middlePcStep(inp.fr8 ,inp.tv8, inp.fv8 );M+=1;w += (inp.fr8*inp.fv8);   }
if(inp.fv9  > 0.0) { probcx.tempPc9  = middlePcStep(inp.fr9 ,inp.tv9, inp.fv9 );M+=1;w += (inp.fr9*inp.fv9);   }
if(inp.fv10 > 0.0) { probcx.tempPc10 = middlePcStep(inp.fr10,inp.tv10,inp.fv10);M+=1;w += (inp.fr10*inp.fv10); }

return probcx; 
}//end of findPc--------------------------------------------------------------------

public double middlePcStep(double frx,double tvx,double fvx)
{
double tempPcx = PO(frx, tvx);
Pcg  += ((tempPcx)*fvx);
return tempPcx;
}//end of middlePcStep --------------------------------------------------------------------


// -----------------<<<<<<<<<<<< PRB1_2 Methods Begin>>>>>>>>>>>>-----------------------
//==================================================================================
public void findPRb1_2(InputValues inp,Pcvalues probc)
{
if(inp.fv1  > 0.0)  middleRbStep(inp.fr1, inp.rr1, inp.tv1, inp.fv1, probc.tempPc1 );
if(inp.fv2  > 0.0)  middleRbStep(inp.fr2, inp.rr2, inp.tv2, inp.fv2, probc.tempPc2 );
if(inp.fv3  > 0.0)  middleRbStep(inp.fr3, inp.rr3, inp.tv3, inp.fv3, probc.tempPc3 );
if(inp.fv4  > 0.0)  middleRbStep(inp.fr4, inp.rr4, inp.tv4, inp.fv4, probc.tempPc4 );
if(inp.fv5  > 0.0)  middleRbStep(inp.fr5, inp.rr5, inp.tv5, inp.fv5, probc.tempPc5 );
if(inp.fv6  > 0.0)  middleRbStep(inp.fr6, inp.rr6, inp.tv6, inp.fv6, probc.tempPc6 );
if(inp.fv7  > 0.0)  middleRbStep(inp.fr7, inp.rr7, inp.tv7, inp.fv7, probc.tempPc7 );
if(inp.fv8  > 0.0)  middleRbStep(inp.fr8, inp.rr8, inp.tv8, inp.fv8, probc.tempPc8 );
if(inp.fv9  > 0.0)  middleRbStep(inp.fr9, inp.rr9, inp.tv9, inp.fv9, probc.tempPc9 );
if(inp.fv10 > 0.0)  middleRbStep(inp.fr10,inp.rr10,inp.tv10,inp.fv10,probc.tempPc10);


}//end of findPc--------------------------------------------


public void middleRbStep(double frx,double rrx,double tvx,double fvx,double probcx)
{
double temp,fri;

fri = (1 - s) * frx;
temp      = (PO_O(fri,rrx,tvx,tvx) * PO((s*frx),tvx) * (probcx /M) * ((1 - Math.pow(Pcg,M))/(1-Pcg)));      
//System.out.print("\nRR1 " + temp); 
prb.RR1  += (temp*fvx);
temp      = (temp * PO_O(fri,rrx,tvx,tvx) * PO((s*frx),tvx)  );
//System.out.print("\nRR2 " + temp); 
prb.RR2  += (temp * fvx);


}//end of middleRbStep --------------------------------------------------------------------


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public double PO_O(double lambda, double mu, double t1, double t)
{
return( POO(lambda,mu,t) - (Math.exp(-(lambda * t1)) * POO(lambda,mu,t-t1)));
}//end of PO_O--------------------------------------------

public double POO(double mlambda, double mmu, double mt)
{
return( mmu/ (mmu+mlambda) + (mlambda /(mmu+mlambda)) * Math.exp((mlambda+mmu)*(-mt))    );
}//end of POO--------------------------------------------

public double PO(double mmlambda, double mmt)
{
return( Math.exp(-(mmlambda * mmt) ) );
}//end of POO--------------------------------------------
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// -----------------<<<<<<<<<<<< Tau Cal. Methods Begin>>>>>>>>>>>>-----------------------
public double findmet(InputValues inp)//====Mean Execution Time of a fault free instruction====
{
double tempmet = 0.0;
tempmet += (inp.fv1 * inp.tv1);
tempmet += (inp.fv2 * inp.tv2);
tempmet += (inp.fv3 * inp.tv3);

return tempmet;
}//end of findmet--------------------------------------------------------------------


public double firstpart(InputValues inp)//====Tau Calculation part====
{
double part1 = prb.RR1 * (delta1 + ( ((M+1.0)/2.0) * findmet(inp) )     );
return part1;
}//end of firstpart--------------------------------------------------------------------

public double secondpart(InputValues inp)//====Tau Calculation part====
{
double part2 = pfailed * ( delta1+ ( ((M+1.0)/2.0) * findmet(inp) ) + delta2 + ( ((L+1)/2.0)*w) );
return part2;
}//end of secondpart--------------------------------------------------------------------

public double thirdpart(InputValues inp)//====Tau Calculation part====
{
double part3 = prb.RR2 * ( (2.0*delta1) + ( (M+1)*findmet(inp) )     );
return part3;
}//end of thirdpart--------------------------------------------------------------------

public double fourthpart(InputValues inp)//==========Tau Calculation part===============
{
double part4 = pfailed * ( (2.0*delta1)+ ( (M+1.0)*findmet(inp) ) + delta2 + ( ((L+1.0)/2.0)*w) );
return part4;
}//end of fourhpart--------------------------------------------------------------------



public void fillswatchErr()
{
if (availablePanel == 3 ) remove(scrollPaneo3  );
if (availablePanel == 99) remove(scrollPaneoErr);

scrollPaneoErr = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
scrollPaneoErr.setBackground(Color.pink); 
scrollPaneoErr.add(swatchErr);
scrollPaneoErr.resize(1100, 190); 
buildConstraints(constraints,0,4,1,1,0,25);
gridbag.setConstraints(scrollPaneoErr,constraints);

add(scrollPaneoErr);
validate();
repaint(); 
availablePanel = 99;
}


public void fillswatch3()
{
if (availablePanel == 3 ) remove(scrollPaneo3  );
if (availablePanel == 99) remove(scrollPaneoErr);

scrollPaneo3 = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
scrollPaneo3.setBackground(myMagenta); 
scrollPaneo3.add(swatch3);
scrollPaneo3.resize(1100,190); 
buildConstraints(constraints,0,4,1,1,0,25);
gridbag.setConstraints(scrollPaneo3,constraints);

add(scrollPaneo3);
validate();
repaint(); 
availablePanel = 3;
}


//===========================================================
InputValues fillInputRates()
{
inputconfirm = true;
HataMesaj = " "; 

InputValues girdi = new InputValues();


try {  girdi.fr1 = Double.valueOf(swatch2.FR1.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 1";                
       }//end try

try {  girdi.fr2 = Double.valueOf(swatch2.FR2.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 2";                
       }//end try
try {  girdi.fr3 = Double.valueOf(swatch2.FR3.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 3";                
       }//end try
try {  girdi.fr4 = Double.valueOf(swatch2.FR4.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 4";                
       }//end try
try {  girdi.fr5 = Double.valueOf(swatch2.FR5.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 5";                
       }//end try
try {  girdi.fr6 = Double.valueOf(swatch2.FR6.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 6";                
       }//end try
try {  girdi.fr7 = Double.valueOf(swatch2.FR7.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 7";                
       }//end try
try {  girdi.fr8 = Double.valueOf(swatch2.FR8.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 8";                
       }//end try
try {  girdi.fr9 = Double.valueOf(swatch2.FR9.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 9";                
       }//end try
try {  girdi.fr10 = Double.valueOf(swatch2.FR10.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Fault Rate 10";                
       }//end try
try {  girdi.rr1 = Double.valueOf(swatch2.RR1.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 1";                
       }//end try
try {  girdi.rr2 = Double.valueOf(swatch2.RR2.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 2";                
       }//end try
try {  girdi.rr3 = Double.valueOf(swatch2.RR3.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 3";                
       }//end try
try {  girdi.rr4 = Double.valueOf(swatch2.RR4.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 4";                
       }//end try
try {  girdi.rr5 = Double.valueOf(swatch2.RR5.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 5";                
       }//end try
try {  girdi.rr6 = Double.valueOf(swatch2.RR6.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 6";                
       }//end try
try {  girdi.rr7 = Double.valueOf(swatch2.RR7.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 7";                
       }//end try
try {  girdi.rr8 = Double.valueOf(swatch2.RR8.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 8";                
       }//end try
try {  girdi.rr9 = Double.valueOf(swatch2.RR9.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 9";                
       }//end try
try {  girdi.rr10 = Double.valueOf(swatch2.RR10.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Recover Rate 10";                
       }//end try
try {  girdi.tv1 = Double.valueOf(swatch2.TV1.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 1";                
       }//end try
try {  girdi.tv2 = Double.valueOf(swatch2.TV2.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 2";                
       }//end try
try {  girdi.tv3 = Double.valueOf(swatch2.TV3.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 3";                
       }//end try
try {  girdi.tv4 = Double.valueOf(swatch2.TV4.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 4";                
       }//end try
try {  girdi.tv5 = Double.valueOf(swatch2.TV5.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 5";                
       }//end try
try {  girdi.tv6 = Double.valueOf(swatch2.TV6.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 6";                
       }//end try
try {  girdi.tv7 = Double.valueOf(swatch2.TV7.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 7";                
       }//end try
try {  girdi.tv8 = Double.valueOf(swatch2.TV8.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 8";                
       }//end try
try {  girdi.tv9 = Double.valueOf(swatch2.TV9.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 9";                
       }//end try
try {  girdi.tv10 = Double.valueOf(swatch2.TV10.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Time Value 10";                
       }//end try
try {  girdi.fv1 = Double.valueOf(swatch2.FV1.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 1";                
       }//end try
try {  girdi.fv2 = Double.valueOf(swatch2.FV2.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 2";                
       }//end try
try {  girdi.fv3 = Double.valueOf(swatch2.FV3.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 3";                
       }//end try
try {  girdi.fv4 = Double.valueOf(swatch2.FV4.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 4";                
       }//end try
try {  girdi.fv5 = Double.valueOf(swatch2.FV5.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 5";                
       }//end try
try {  girdi.fv6 = Double.valueOf(swatch2.FV6.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 6";                
       }//end try
try {  girdi.fv7 = Double.valueOf(swatch2.FV7.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 7";                
       }//end try
try {  girdi.fv8 = Double.valueOf(swatch2.FV8.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 8";                
       }//end try
try {  girdi.fv9 = Double.valueOf(swatch2.FV9.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 9";                
       }//end try
try {  girdi.fv10 = Double.valueOf(swatch2.FV10.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Incorrect input in Frequnce Value 10";                
       }//end try
/**************--------------------**************************/

try {  s = Double.valueOf(swatch1.TPermanentFaults.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  " Incorrect input for Permanent Fault ratio<s>";                
       }//end try
/*
try {  M     = Integer.parseInt(swatch1.TInstBetTwoCheck.getText());
       }catch (NumberFormatException e) {inputconfirm = false;
        HataMesaj =  "Incorrect Input for Instrcution between two checkpoints<M>";        
       }//end try    
try {  w = Double.valueOf(swatch1.TMeanTimeSpentPerInstr.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  "Input error for Mean TimeSpent Per Instr<w>";                
       }//end try

*/
try {  L     = Integer.parseInt(swatch1.TLVal.getText());
       }catch (NumberFormatException e) {inputconfirm = false;
        HataMesaj =  "Incorrect Input for L value";        
       }//end try          
       
try {  delta1 = Double.valueOf(swatch1.TSetupTimeNeed.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  " Input error for Required setup time<delta1>";                
       }//end try
       
try {  delta2 = Double.valueOf(swatch1.TDiagRepairNeed.getText()).doubleValue();  
      }catch (NumberFormatException e) {inputconfirm = false;
       HataMesaj =  " Input error for Required diagnosis & repair time<delta2>";                
       }//end try
    
       
return girdi;
}//end of fillInputRates 
/*------------------------------------------------------------------*/
public boolean CheckInputAll( InputValues inpt)
{
InputValues tmpInp = inpt;


if (CheckInputsF(tmpInp) == false) {HataMesaj =  "Failure Rates should be between 0.0-1.0"; return false; }
if (CheckInputsR(tmpInp) == false) {HataMesaj =  "Recover Rates should be between 0.0-1.0"; return false; }
if (ComplexContr(tmpInp) == false) {HataMesaj =  "Failure and recover rates cannot be 0 at the same time for any instruction"; return false; }

totalfrequnce = (inpt.fv1+inpt.fv2+inpt.fv3+inpt.fv4+inpt.fv5+inpt.fv6+inpt.fv7+inpt.fv8+inpt.fv9+inpt.fv10);
if(!(totalfrequnce>=0.9999999 && totalfrequnce<=1.0000001)) {HataMesaj =  "Total Frequnce should be one. Yours is: "+totalfrequnce; return false; }


return true;
}// end of method CheckInputsAll
/*------------------------------------------------------------------*/
public boolean CheckInputsF(InputValues girdi)
{
return(CheckVal(girdi.fr1) && CheckVal(girdi.fr2) && CheckVal(girdi.fr3) && CheckVal(girdi.fr4) && CheckVal(girdi.fr5) &&
       CheckVal(girdi.fr6) && CheckVal(girdi.fr7) && CheckVal(girdi.fr8) && CheckVal(girdi.fr9) && CheckVal(girdi.fr10)  );
}// end of method CheckInputsF

/*------------------------------------------------------------------*/
public boolean CheckInputsR(InputValues girdi)
{
return(CheckVal(girdi.rr1) && CheckVal(girdi.rr2) && CheckVal(girdi.rr3) && CheckVal(girdi.rr4) && CheckVal(girdi.rr5) &&
       CheckVal(girdi.rr6) && CheckVal(girdi.rr7) && CheckVal(girdi.rr8) && CheckVal(girdi.rr9) && CheckVal(girdi.rr10)  );
}// end of method CheckInputsR
/*------------------------------------------------------------------*/
public boolean CheckVal(double sayi)
{
 return( 0.0<=sayi&& sayi<= 1.0 );
}// end of method CheckVal


/*------------------------------------------------------------------*/
public boolean ComplexContr(InputValues girdi)
{
return(   ComplexCheck(girdi.fr1, girdi.rr1, girdi.fv1)  &&
          ComplexCheck(girdi.fr2, girdi.rr2, girdi.fv2)  &&
          ComplexCheck(girdi.fr3, girdi.rr3, girdi.fv3)  &&
          ComplexCheck(girdi.fr4, girdi.rr4, girdi.fv4)  &&
          ComplexCheck(girdi.fr5, girdi.rr5, girdi.fv5)  &&
          ComplexCheck(girdi.fr6, girdi.rr6, girdi.fv6)  &&
          ComplexCheck(girdi.fr7, girdi.rr7, girdi.fv7)  &&
          ComplexCheck(girdi.fr8, girdi.rr8, girdi.fv8)  &&
          ComplexCheck(girdi.fr9, girdi.rr9, girdi.fv9)  &&
          ComplexCheck(girdi.fr10,girdi.rr10,girdi.fv10) 
          
      );
}// end of method ComplexContr

/*------------------------------------------------------------------*/
public boolean ComplexCheck(double frx,double rrx,double fvx)
{
if(frx ==0.0 && rrx == 0.0 && fvx != 0.0) return false;
return true; 
}// end of method ComplexCheck

/*------------------------------------------------------------------*/

} //end of class