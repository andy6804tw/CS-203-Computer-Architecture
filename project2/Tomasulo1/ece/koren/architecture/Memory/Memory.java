import java.awt.event.*;
import java.awt.*;

import java.text.*;
import java.util.*;

public class Memory extends java.applet.Applet implements ActionListener

{
Panel           errpan;
Input1          swatch0;
Out1            swatch1;
Help		swatch2;
Table2          swatch4;
ErrorBoxS        swatch99;
Buttons         buttons; 
Buttons1	buttons1;

ScrollPane scrollPanei;
ScrollPane scrollPaneo1;
ScrollPane scrollPaneo2;
ScrollPane scrollPaneo4;
ScrollPane scrollPaneo99;

short resultlayer;
Color renk10eylul;
String Str18eyl;


// Input values
String userseq;
int index;
int TotalMemory;
int NumOfBanksVal; 
double FreqOfMemReq;
int AccessTime;
int NumOfAccess;
int Sequence[];
int At[];
int St[];
int Bank[];
int StInBank[];
int NumRows;
int NumCols;
int NumInBank[];
int maxInAnyBank;
int maxSt;
int StrideSize;
int Mode = 1;
double rv;
Math   m;
GridBagLayout gridbag           = new GridBagLayout();
GridBagConstraints constraints  = new GridBagConstraints();
public void init()
{
   setLayout(gridbag);
   //this.setBackground(Color.gray); 

   buildConstraints(constraints,0,0,1,1,0,10);
   buttons = new Buttons();
   buttons.setSize(800,10);
   gridbag.setConstraints(buttons,constraints);
   add(buttons);
   buttons.button1.addActionListener(this);
   buttons.button2.addActionListener(this); 
   buttons.button3.addActionListener(this);

   buildConstraints(constraints,0,1,1,1,0,40);
   swatch0 = new Input1();
   scrollPanei = new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
   scrollPanei.setBackground(Color.white); 
   scrollPanei.add(swatch0);
   scrollPanei.setSize(800,200);
   gridbag.setConstraints(scrollPanei,constraints);
   add(scrollPanei);
   buildConstraints(constraints,0,2,1,1,0,10);
   buttons1 = new Buttons1();
   buttons1.setSize(800,10);
   gridbag.setConstraints(buttons1,constraints);
   add(buttons1);
   buttons1.button4.addActionListener(this);
   buttons1.button5.addActionListener(this);

   renk10eylul = new Color(100,100,200);
   buildConstraints(constraints,0,3,1,1,0,40);
   resultlayer = 1;
   swatch1 = new Out1();

   scrollPaneo1 = new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
 
   scrollPaneo1.setBackground(renk10eylul); 
   scrollPaneo1.add(swatch1);
   scrollPaneo1.setSize(800,300); 
   gridbag.setConstraints(scrollPaneo1,constraints);
   add(scrollPaneo1);

}// end of init

public void actionPerformed(ActionEvent evt)
{
     Object source = evt.getSource();
     if     (source == buttons.button1) update(1);
     else if(source == buttons.button2) update(2);
     else if(source == buttons.button3) update(3);
     else if(source == buttons1.button4)  update(4);
     else if(source == buttons1.button5)  update(5);
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


void update(int updno)
{
 if (updno == 1)
 {
   swatch0.TUserSequence.setEnabled(false);
   swatch0.TNumberOfRows.setEnabled(false);
   swatch0.TNumberOfCols.setEnabled(false);
   swatch0.TStride.setEnabled(false);
   swatch0.TNumberOfAccess.setEnabled(true);
   swatch0.UserSequence.setEnabled(false);
   swatch0.NumberOfRows.setEnabled(false);
   swatch0.NumberOfCols.setEnabled(false);
   swatch0.Stride.setEnabled(false);
   swatch0.NumberOfAccess.setEnabled(true);
   swatch0.TBandwidth.setEditable(false);
   swatch0.TBandwidth.setEnabled(false);
   swatch0.Bandwidth.setEnabled(false);
   swatch0.TBandwidth.setBackground(Color.white);
   swatch0.TBandwidth.setForeground(Color.black);
   swatch0.TBandwidth.setText(" ");
   swatch0.TTotalMemory.setEnabled(true);
   swatch0.TotalMemory.setEnabled(true);
   swatch0.TNumOfBanks.setBackground(Color.green);
   swatch0.TFrequencyOfMemoryAccess.setBackground(Color.green);
   swatch0.TAccessTime.setBackground(Color.green);

   Mode = 1;
 }
if(updno == 2)
{
   swatch0.TUserSequence.setEnabled(true);
   swatch0.TNumberOfRows.setEnabled(false);
   swatch0.TNumberOfCols.setEnabled(false);
   swatch0.TStride.setEnabled(false);
   swatch0.TNumberOfAccess.setEnabled(false);
   swatch0.UserSequence.setEnabled(true);
   swatch0.NumberOfRows.setEnabled(false);
   swatch0.NumberOfCols.setEnabled(false);
   swatch0.Stride.setEnabled(false);
   swatch0.NumberOfAccess.setEnabled(false);
   swatch0.TBandwidth.setEditable(false);
   swatch0.TBandwidth.setEnabled(false);
   swatch0.Bandwidth.setEnabled(false);
   swatch0.TBandwidth.setBackground(Color.white);
   swatch0.TBandwidth.setForeground(Color.black);
   swatch0.TBandwidth.setText(" ");
   swatch0.TTotalMemory.setEnabled(false);
   swatch0.TotalMemory.setEnabled(false);
   swatch0.TNumOfBanks.setBackground(Color.cyan);
   swatch0.TFrequencyOfMemoryAccess.setBackground(Color.cyan);
   swatch0.TAccessTime.setBackground(Color.cyan);

   Mode = 2;
}
if(updno == 3)
{
   swatch0.TUserSequence.setEnabled(false);
   swatch0.TNumberOfRows.setEnabled(true);
   swatch0.TNumberOfCols.setEnabled(true);
   swatch0.TStride.setEnabled(true);
   swatch0.TNumberOfAccess.setEnabled(false);
   swatch0.UserSequence.setEnabled(false);
   swatch0.NumberOfRows.setEnabled(true);
   swatch0.NumberOfCols.setEnabled(true);
   swatch0.Stride.setEnabled(true);
   swatch0.NumberOfAccess.setEnabled(false);
   swatch0.TBandwidth.setEditable(false);
   swatch0.TBandwidth.setEnabled(false);
   swatch0.Bandwidth.setEnabled(false);
   swatch0.TBandwidth.setBackground(Color.white);
   swatch0.TBandwidth.setForeground(Color.black);
   swatch0.TBandwidth.setText(" ");
   swatch0.TTotalMemory.setEnabled(false);
   swatch0.TotalMemory.setEnabled(false);
   swatch0.TNumOfBanks.setBackground(Color.magenta);
   swatch0.TFrequencyOfMemoryAccess.setBackground(Color.magenta);
   swatch0.TAccessTime.setBackground(Color.magenta);

   Mode = 3;
}
if(updno == 4)
{
   boolean getinput4eylul = getinputio();
   swatch0.TBandwidth.setEditable(false);
   swatch0.TBandwidth.setEnabled(true);
   swatch0.Bandwidth.setEnabled(true);
   
   if(getinput4eylul == true)
   {
      removing();
      if(Mode==2)
      {
        StringTokenizer StrTok = new StringTokenizer(userseq);
        NumOfAccess = StrTok.countTokens();
        Sequence = new int[NumOfAccess];
        index = 0;
        while(StrTok.hasMoreTokens())
        {
             Sequence[index] = Integer.parseInt(StrTok.nextToken());
             index++;
        }
	At	 = new int[NumOfAccess];
        St       = new int[NumOfAccess];
        Bank     = new int[NumOfAccess];
	NumInBank = new int[NumOfBanksVal];
	StInBank = new int[NumOfBanksVal];
	maxInAnyBank = 0;
        maxSt = 0;
	for(int i=0;i<NumOfAccess;i++)
        {
		Bank[i] = Sequence[i]%NumOfBanksVal;
		At[i]	= i*((int)(1.0/FreqOfMemReq));
		if(At[i]>StInBank[Bank[i]])
			StInBank[Bank[i]] = At[i]+AccessTime;
		else
			StInBank[Bank[i]]+=AccessTime;
		St[i] = StInBank[Bank[i]];
		NumInBank[Bank[i]]++;
		if(NumInBank[Bank[i]]>maxInAnyBank) 
			maxInAnyBank =  NumInBank[Bank[i]];
		if(maxSt<St[i]) maxSt = St[i];
        } 
        swatch4 = new Table2(NumOfBanksVal,NumOfBanksVal*(1+maxInAnyBank));
        resultlayer = 4;
        scrollPaneo4 = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        scrollPaneo4.setBackground(Color.white);
        scrollPaneo4.add(swatch4);
        scrollPaneo4.setSize(800,300);
        buildConstraints(constraints,0,3,1,1,0,40);
        gridbag.setConstraints(scrollPaneo4,constraints);
        add(scrollPaneo4);
        validate();
        repaint();

	for(int i=0;i<NumOfBanksVal;i++) NumInBank[i] = 0;
        for(int i=0;i<NumOfAccess;i++)
        {
	   NumInBank[Bank[i]]++;
           swatch4.word[NumOfBanksVal*NumInBank[Bank[i]]+Bank[i]].setText("< "+i+" , "+Sequence[i]+ " >    ["+At[i]+" , "+St[i]+" ]");
        }
	swatch0.TBandwidth.setBackground(Color.red);
	swatch0.TBandwidth.setForeground(Color.black);
	swatch0.TBandwidth.setText(" "+(float)(((float)NumOfAccess)/maxSt));
      }else if(Mode==1)
      {
        Sequence = new int[NumOfAccess];
        At       = new int[NumOfAccess];
        St       = new int[NumOfAccess];
        Bank     = new int[NumOfAccess];
        NumInBank = new int[NumOfBanksVal];
        StInBank = new int[NumOfBanksVal];
        maxInAnyBank = 0;
        maxSt = 0;
        for(int i=0;i<NumOfAccess;i++)
        {
                Sequence[i] = (int)(m.random()*TotalMemory);
                Bank[i] = Sequence[i]%NumOfBanksVal;
                At[i]   = i*((int)(1.0/FreqOfMemReq));
                if(At[i]>StInBank[Bank[i]])
                        StInBank[Bank[i]] = At[i]+AccessTime;
                else
                        StInBank[Bank[i]]+=AccessTime;
                St[i] = StInBank[Bank[i]];
                NumInBank[Bank[i]]++;
                if(NumInBank[Bank[i]]>maxInAnyBank)
                        maxInAnyBank =  NumInBank[Bank[i]];
                if(maxSt<St[i]) maxSt = St[i];
        }
        swatch4 = new Table2(NumOfBanksVal,NumOfBanksVal*(1+maxInAnyBank));
        resultlayer = 4;
        scrollPaneo4 = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        scrollPaneo4.setBackground(Color.white);
        scrollPaneo4.add(swatch4);
        scrollPaneo4.setSize(800,300);
        buildConstraints(constraints,0,3,1,1,0,40);
        gridbag.setConstraints(scrollPaneo4,constraints);
        add(scrollPaneo4);
        validate();
        repaint();

        for(int i=0;i<NumOfBanksVal;i++) NumInBank[i] = 0;
        for(int i=0;i<NumOfAccess;i++)
        {
           NumInBank[Bank[i]]++;
           swatch4.word[NumOfBanksVal*NumInBank[Bank[i]]+Bank[i]].setText("< "+i+" , "+Sequence[i]+ " >    ["+At[i]+" , "+St[i]+" ]");

        }
        swatch0.TBandwidth.setBackground(Color.red);
        swatch0.TBandwidth.setForeground(Color.black);
        swatch0.TBandwidth.setText(" "+(float)(4*((float)NumOfAccess)/maxSt));
      }else if(Mode==3)
      {
	NumOfAccess = (NumRows*NumCols/StrideSize);
        Sequence = new int[NumOfAccess];
        At       = new int[NumOfAccess];
        St       = new int[NumOfAccess];
        Bank     = new int[NumOfAccess];
        NumInBank = new int[NumOfBanksVal];
        StInBank = new int[NumOfBanksVal];
        maxInAnyBank = 0;
        maxSt = 0;
        for(int i=0;i<NumOfAccess;i++)
        {
                Sequence[i] = i*StrideSize;
                Bank[i] = Sequence[i]%NumOfBanksVal;
                At[i]   = i*((int)(1.0/FreqOfMemReq)); 
                if(At[i]>StInBank[Bank[i]])
                        StInBank[Bank[i]] = At[i]+AccessTime;
                else
                        StInBank[Bank[i]]+=AccessTime;
                St[i] = StInBank[Bank[i]];
                NumInBank[Bank[i]]++;
                if(NumInBank[Bank[i]]>maxInAnyBank)
                        maxInAnyBank =  NumInBank[Bank[i]];
                if(maxSt<St[i]) maxSt = St[i];
        }
        swatch4 = new Table2(NumOfBanksVal,NumOfBanksVal*(1+maxInAnyBank));
        resultlayer = 4;
        scrollPaneo4 = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        scrollPaneo4.setBackground(Color.white);
        scrollPaneo4.add(swatch4);
        scrollPaneo4.setSize(800,300);
        buildConstraints(constraints,0,3,1,1,0,40);
        gridbag.setConstraints(scrollPaneo4,constraints);
        add(scrollPaneo4);
        validate();
        repaint();

        for(int i=0;i<NumOfBanksVal;i++) NumInBank[i] = 0;
        for(int i=0;i<NumOfAccess;i++)
        {
           NumInBank[Bank[i]]++;
           swatch4.word[NumOfBanksVal*NumInBank[Bank[i]]+Bank[i]].setText("< "+i+" , "+Sequence[i]+ " >    ["+At[i]+" , "+St[i]+" ]");
        }
        swatch0.TBandwidth.setBackground(Color.red);
        swatch0.TBandwidth.setForeground(Color.black);
        swatch0.TBandwidth.setText(" "+(float)(4*((float)NumOfAccess)/maxSt));
      }else errorbox(); 
   
//      removing();
//      swatch4 = new Table2(NumOfBanksVal,TotalMemory);
//      resultlayer = 4;
//      scrollPaneo4 = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
//      scrollPaneo4.setBackground(Color.white);
//      scrollPaneo4.add(swatch4);
//      scrollPaneo4.setSize(800,300);
//      buildConstraints(constraints,0,3,1,1,0,40);
//      gridbag.setConstraints(scrollPaneo4,constraints);
//      add(scrollPaneo4);
//      validate();
//      repaint();
    }//end if getinpu4eylul
   else if(getinput4eylul == false)  errorbox();
}
if(updno==5)
{
      removing();
      resultlayer = 2;
      swatch2 = new Help();
      scrollPaneo2 = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
 
      scrollPaneo2.setBackground(Color.white);
      scrollPaneo2.setForeground(Color.black);
      scrollPaneo2.add(swatch2);
      scrollPaneo2.setSize(800,300);
      buildConstraints(constraints,0,3,1,1,0,40);
      gridbag.setConstraints(scrollPaneo2,constraints);
      add(scrollPaneo2);
      validate();
      repaint();
}
}// end update

/*---------------------------------------------------------------------------------------*/
public boolean getinputio()
{
boolean correctinput24a = true;
      Str18eyl = " "; 
  try { NumOfBanksVal  = Integer.parseInt(swatch0.TNumOfBanks.getText());
             }catch (NumberFormatException e) {correctinput24a = false;
        Str18eyl =  "Incorrect input in field :Number of Banks";    
        }//end try  
  try { TotalMemory  = Integer.parseInt(swatch0.TTotalMemory.getText());
             }catch (NumberFormatException e) {correctinput24a = false;
        Str18eyl =  "Incorrect input in field :Total Memory";
        }//end try  
  try { FreqOfMemReq  = Double.valueOf(swatch0.TFrequencyOfMemoryAccess.getText()).doubleValue();
             }catch (NumberFormatException e) {correctinput24a = false;
        Str18eyl =  "Incorrect input in field :Frequency of Memory Access";
        }//end try  
  try { AccessTime  = Integer.parseInt(swatch0.TAccessTime.getText());
             }catch (NumberFormatException e) {correctinput24a = false;
        Str18eyl =  "Incorrect input in field :Access Time";
        }//end try  
  try { NumOfAccess = Integer.parseInt(swatch0.TNumberOfAccess.getText());
             }catch (NumberFormatException e) {correctinput24a = false;
        Str18eyl =  "Incorrect input in field :Length of Sequence";
      }
  try { NumRows = Integer.parseInt(swatch0.TNumberOfRows.getText());
             }catch (NumberFormatException e) {correctinput24a = false;
        Str18eyl =  "Incorrect input in field :Number of Rows";
      }
  try { NumCols = Integer.parseInt(swatch0.TNumberOfCols.getText());
             }catch (NumberFormatException e) {correctinput24a = false;
        Str18eyl =  "Incorrect input in field :Number of Columns";
      }
  try { StrideSize = Integer.parseInt(swatch0.TStride.getText());
             }catch (NumberFormatException e) {correctinput24a = false;
        Str18eyl =  "Incorrect input in field :Stride Size";
      }
  userseq = new String();
  try { userseq = swatch0.TUserSequence.getText();
             }catch (NumberFormatException e) {correctinput24a = false;
        Str18eyl =  "Incorrect input in field :User Specified Sequence";
      }
return correctinput24a;
}//end of function getinput
/*---------------------------------------------------------------------------------------*/
public void errorbox()
{
      removing();
      resultlayer = 99;	
      swatch99 = new ErrorBoxS(Str18eyl);
      scrollPaneo99 = new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
 
      scrollPaneo99.setBackground(Color.red); 
      scrollPaneo99.add(swatch99);
      scrollPaneo99.setSize(800,150); 
      buildConstraints(constraints,0,3,1,1,0,40);
      gridbag.setConstraints(scrollPaneo99,constraints);
      add(scrollPaneo99);
      validate();
      repaint();  
}
/*---------------------------------------------------------------------------------------*/
public void removing()
{
if(resultlayer == 1 )     remove(scrollPaneo1);
if(resultlayer == 2 )     remove(scrollPaneo2);
else if(resultlayer == 4) remove(scrollPaneo4);  
else if(resultlayer == 99) remove(scrollPaneo99);
}
}
