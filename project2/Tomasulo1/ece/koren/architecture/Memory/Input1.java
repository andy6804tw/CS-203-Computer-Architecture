import java.awt.*;
public class Input1 extends Panel
{

Label     TotalMemory;
TextField TTotalMemory;

Label     NumOfBanks;
TextField TNumOfBanks;

Label     FrequencyOfMemoryAccess;
TextField TFrequencyOfMemoryAccess;

Label     AccessTime;
TextField TAccessTime;

Label     NumberOfAccess;
TextField TNumberOfAccess;

Label     UserSequence;
TextField TUserSequence;

Label     NumberOfRows;
TextField TNumberOfRows;

Label     NumberOfCols;
TextField TNumberOfCols;

Label     Stride;
TextField TStride;

Label     Bandwidth;
TextField TBandwidth;

public Input1()
{
   GridBagLayout gridbag = new GridBagLayout();
   GridBagConstraints c = new GridBagConstraints();
   Font f = new Font("TimesRoman",Font.PLAIN,16);

   setLayout(gridbag);
   this.setSize(900,240);
   this.setFont(f);

   TotalMemory         = new Label("                      Total Memory (MB)",Label.RIGHT);
   TTotalMemory        = new TextField("32",10);
   TTotalMemory.setBackground(Color.green);
   TTotalMemory.setColumns(10);
   TTotalMemory.setFont(f);
   NumOfBanks          = new Label("                          Number of Banks",Label.RIGHT);
   TNumOfBanks         = new TextField("4",10);
   TNumOfBanks.setBackground(Color.yellow);

   FrequencyOfMemoryAccess = new Label("Frequency (memory request/cpu cycle)",Label.RIGHT);
   TFrequencyOfMemoryAccess = new TextField("1",10);
   TFrequencyOfMemoryAccess.setBackground(Color.yellow);

   AccessTime		= new Label("                 Access Time (CPU Cycle)",Label.RIGHT); 
   TAccessTime           = new TextField("24",10);
   TAccessTime.setBackground(Color.yellow);

   NumberOfAccess      = new Label("            Length Of Random Sequence",Label.RIGHT); 
   TNumberOfAccess      = new TextField("8",10);
   
   UserSequence   = new Label("                User Specified Sequence",Label.RIGHT);
   TUserSequence  = new TextField("1 7 4 2 12 3 15 4",10);

   NumberOfRows  = new Label("                  Number of Array Rows",Label.RIGHT);
   TNumberOfRows = new TextField("4",10);

   NumberOfCols  = new Label("              Number of Array Columns",Label.RIGHT);
   TNumberOfCols = new TextField("2",10);

   Stride	 = new Label("                     Array Access Stride ",Label.RIGHT);
   TStride	 = new TextField("1",10);

   Bandwidth     = new Label("  Memory Bandwidth (words/cpu cycle)", Label.RIGHT);
   TBandwidth    = new TextField(" ",10);

   TUserSequence.setEnabled(false);
   TNumberOfRows.setEnabled(false);
   TNumberOfCols.setEnabled(false);
   TStride.setEnabled(false);
   UserSequence.setEnabled(false);
   NumberOfRows.setEnabled(false);
   NumberOfCols.setEnabled(false);
   Stride.setEnabled(false);

   TNumberOfAccess.setBackground(Color.green);
   TUserSequence.setBackground(Color.cyan);
   TNumberOfRows.setBackground(Color.magenta);
   TNumberOfCols.setBackground(Color.magenta);
   TStride.setBackground(Color.magenta);

   add(NumOfBanks);
   c.gridx = 0; c.gridy = 0;
   c.gridwidth=3;
   gridbag.setConstraints(NumOfBanks,c);
   add(TNumOfBanks);
   c.gridx = 3; c.gridy = 0;
   c.gridwidth=2;
   gridbag.setConstraints(TNumOfBanks,c);
 
   add(FrequencyOfMemoryAccess);
   c.gridx = 5; c.gridy = 0;
   c.gridwidth=3;
   gridbag.setConstraints(FrequencyOfMemoryAccess,c);
   add(TFrequencyOfMemoryAccess);
   c.gridx = 8; c.gridy = 0;
   c.gridwidth=2;
   gridbag.setConstraints(TFrequencyOfMemoryAccess,c);

   add(AccessTime);
   c.gridx = 0; c.gridy = 1;
   c.gridwidth=3;
   gridbag.setConstraints(AccessTime,c);
   add(TAccessTime);
   c.gridx = 3; c.gridy = 1;
   c.gridwidth=2;
   gridbag.setConstraints(TAccessTime,c);

   add(TotalMemory);
   c.gridx = 5; c.gridy = 1;
   c.gridwidth=3;
   gridbag.setConstraints(TotalMemory,c);
   add(TTotalMemory);
   c.gridx = 8; c.gridy = 1;
   c.gridwidth=2;
   gridbag.setConstraints(TTotalMemory,c);

   add(NumberOfAccess);
   c.gridx = 0; c.gridy = 2;
   c.gridwidth=3;
   gridbag.setConstraints(NumberOfAccess,c);
   add(TNumberOfAccess);
   c.gridx = 3; c.gridy = 2;
   c.gridwidth=2;
   gridbag.setConstraints(TNumberOfAccess,c);

   add(UserSequence);
   c.gridx = 5; c.gridy = 2;
   c.gridwidth=3;
   gridbag.setConstraints(UserSequence,c);
   add(TUserSequence);
   c.gridx = 8; c.gridy = 2;
   c.gridwidth=2;
   gridbag.setConstraints(TUserSequence,c);

   add(NumberOfRows);
   c.gridx = 0; c.gridy = 3;
   c.gridwidth=3;
   gridbag.setConstraints(NumberOfRows,c);
   add(TNumberOfRows);
   c.gridx = 3; c.gridy = 3;
   c.gridwidth=2;
   gridbag.setConstraints(TNumberOfRows,c);

   add(NumberOfCols);
   c.gridx = 5; c.gridy = 3;
   c.gridwidth=3;
   gridbag.setConstraints(NumberOfCols,c);
   add(TNumberOfCols);
   c.gridx = 8; c.gridy = 3;
   c.gridwidth=2;
   gridbag.setConstraints(TNumberOfCols,c);

   add(Stride);
   c.gridx = 0; c.gridy = 4;
   c.gridwidth=3;
   gridbag.setConstraints(Stride,c);
   add(TStride);
   c.gridx = 3; c.gridy = 4;
   c.gridwidth=2;
   gridbag.setConstraints(TStride,c);
   
   add(Bandwidth);
   c.gridx = 5; c.gridy = 4;
   c.gridwidth=3;
   gridbag.setConstraints(Bandwidth,c);
   add(TBandwidth); 
   c.gridx = 8; c.gridy = 4; 
   c.gridwidth=2;
   gridbag.setConstraints(TBandwidth,c);
   }
}
