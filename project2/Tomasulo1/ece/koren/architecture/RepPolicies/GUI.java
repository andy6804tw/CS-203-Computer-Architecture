/*
Nicholas  Merrill
12-28-2005
*/
import java.awt.*;         // make GUI compnent classes available
import java.awt.event.*;   // make GUI event classes available
import java.applet.Applet; // make the Applet class available
import java.lang.Object;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;




public class GUI extends Applet implements ItemListener, ActionListener
{
//
  private class RowHeaderRenderer extends JLabel implements TableCellRenderer
  {
    RowHeaderRenderer(JTable table) {
      JTableHeader header = table.getTableHeader();
      setOpaque(true);
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      setHorizontalAlignment(CENTER);
      super.setForeground(header.getForeground());
      super.setBackground(header.getBackground());
      super.setFont(header.getFont());
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
			  boolean isSelected, boolean hasFocus, int row, int column) {
      setText((value == null) ? "" : value.toString());
      return this;
    }
  }



    private Panel fnlr = new Panel ();
    private JScrollPane scroll;
    public int pcount; // keeps a count of how many check boxes are selected
    private int totalcount=0;  //number of times program has been called
    public long pgs; //number of page frames user chooses
    public int nseq; // number of sequences user inputs
    private long kval;  //value of K in LRU-k
    private boolean all=false;
    private pgReplacement pgrep;
    public boolean randombox=false;
    private Checkbox lruk = new Checkbox ();
    private Checkbox car = new Checkbox ();
    private Checkbox arc = new Checkbox ();
    private Checkbox opt = new Checkbox ();
    private Checkbox ilfu = new Checkbox ();
    private Checkbox ilru = new Checkbox ();
    private Checkbox fifo = new Checkbox ();
    private Checkbox random = new Checkbox ();
    public boolean fifobox=false;
    public boolean ilrubox=false;
    public boolean ilfubox=false;
    public boolean optbox=false;
    public boolean arcbox=false;
    public boolean carbox=false;
    public boolean lrukbox=false;
    protected Vector ref= new Vector();
    private Button step = new Button("Step");
    private Button fnl = new Button("Final");
    public TextField ts = new TextField("1 1 2 2 3 3 1 4 5 6 9 8 0 1 2 3");
    public TextField k = new TextField("2          ");
    public TextField t = new TextField("3                 ");
    private int nsteps=0;
    private JTable table;
    private String[][] fifov;
    private String[][] randomv;
    private String[][] ilruv;
    private String[][] ilfuv;
    private String[][] optv;
    private String[][] arcv;
    private String[][] carv;
    private String[][] lrukv;

//*********************init*****************************
//initial functiont that runs when applet starts
    public void init ()
    { // set up the applet

	pcount=0;
	setBackground (Color.white);
	setLayout (new BorderLayout ());

	Panel inputs = new Panel ();
	GridBagLayout grid = new GridBagLayout ();
	GridBagConstraints gc = new GridBagConstraints ();
	inputs.setLayout (grid);
	add (inputs, BorderLayout.WEST);

	Panel pol = new Panel ();
	pol.setLayout (new GridLayout (5, 2, 1, 1));


	//***********POLICY CHOICES********************************
	Panel title = new Panel ();
	title.add (new Label ("Check ALL the Policies to Simulate"));
	inputs.add (title);


	gc.fill = GridBagConstraints.BOTH;
	gc.gridwidth = GridBagConstraints.REMAINDER;
	gc.weightx = 0.0;                  //reset to the default
	grid.addLayoutComponent (title, gc);


	gc.gridwidth = GridBagConstraints.REMAINDER;
	grid.addLayoutComponent (pol, gc);
	inputs.add (pol);

	Panel line = new Panel ();
	line.add (new Label ("Random  "));
	random.addItemListener (this);
	line.add (random);
	pol.add (line);

	line = new Panel ();
	line.add (new Label ("FIFO          "));
	fifo.addItemListener (this);
	line.add (fifo);
	pol.add (line);

	line = new Panel ();
	line.add (new Label ("Ideal LRU"));
	ilru.addItemListener (this);
	line.add (ilru);
	pol.add (line);

	line = new Panel ();
	line.add (new Label ("Ideal LFU  "));
	ilfu.addItemListener (this);
	line.add (ilfu);
	pol.add (line);

	line = new Panel ();
	line.add (new Label ("Optimal   "));
	opt.addItemListener (this);
	line.add (opt);
	pol.add (line);

	line = new Panel ();
	line.add (new Label ("ARC           "));
	arc.addItemListener (this);
	line.add (arc);
	pol.add (line);

	line = new Panel ();
	line.add (new Label ("CAR          "));
	car.addItemListener (this);
	line.add (car);
	pol.add (line);

	line = new Panel ();
	line.add (new Label ("LRU-K       "));
	lruk.addItemListener (this);
	line.add (lruk);
	pol.add (line);

	line = new Panel ();
	line.add (new Label ("Enter Value for K"));
	pol.add (line);

	line = new Panel ();

	k.addActionListener(this);
	line.add (k);
	pol.add (line);


	title = new Panel ();
	title.add (new Label ("Number of Page Frames"));
	inputs.add (title);
	gc.gridwidth = GridBagConstraints.REMAINDER;
	grid.addLayoutComponent (title, gc);

	Panel num = new Panel();
	t.addActionListener(this);
	num.add(t);
	inputs.add(num);
	gc.weightx=0.0;
	gc.gridwidth = GridBagConstraints.REMAINDER;
	grid.addLayoutComponent(num,gc);

	title = new Panel ();
	title.add (new Label ("Enter Page Sequences (space between each)"));
	inputs.add (title);
	gc.weighty = 0.0;
	gc.gridwidth = GridBagConstraints.REMAINDER;
	grid.addLayoutComponent (title, gc);

	Panel seq = new Panel();
	ts.addActionListener(this);
	seq.add(ts);
	inputs.add(seq);
	gc.weighty=0.0;
	gc.gridwidth = GridBagConstraints.REMAINDER;
	grid.addLayoutComponent(seq,gc);

	title = new Panel ();
	title.add (new Label ("Choose Display Option"));
	inputs.add (title);
	gc.weighty = 0.0;
	gc.gridwidth = GridBagConstraints.REMAINDER;
	grid.addLayoutComponent (title, gc);


	Panel displ = new Panel();
	step.addActionListener(this);
	displ.add(step);
	inputs.add(displ);
	fnl.addActionListener(this);
	displ.add(fnl);
	inputs.add(displ);
	gc.gridwidth= GridBagConstraints.RELATIVE;
	grid.addLayoutComponent(displ,gc);

   }


//*************getInsets*************************
    public Insets getInsets ()
    { // Allow 7 pixels space around the edges of the applet
	return new Insets (7, 7, 7, 7);
    }
//**************DISPLAYRESULTS**************************************
    public void displayResults(String type)
    {


	if (nsteps==0)
	{
	if(totalcount>0)
	{
	remove(fnlr);
	fnlr=new Panel();
	remove(scroll);
	}

	pgrep= new pgReplacement((int)pgs,nseq,ref);

	if(fifobox==true)
	    fifov=pgrep.fifo();
	if(randombox==true)
	    randomv=pgrep.random();
	if(ilrubox==true)
	    ilruv=pgrep.iLRU();
	if(ilfubox==true)
	    ilfuv=pgrep.iLFU();
	if(optbox==true)
	    optv=pgrep.OPT();
	if(arcbox==true)
	      arcv=pgrep.ARC();
	if(carbox==true)
	      carv=pgrep.CAR();
	if(lrukbox==true)
	      lrukv=pgrep.LRUk((int)kval);
//

  Object[][] data = mkRowHead();
  Object[] column;

  MultiSpanCellTable fixedTable;
  int tsize;


    column = new Object[]{"",""};

    AttributiveCellTableModel fixedModel = new AttributiveCellTableModel(data, column);

    CellSpan cellAtt =(CellSpan)fixedModel.getCellAttribute();

    int tempnum=0;
    for(int j=0;j<pcount;j++)
      { int[] tempa= new int[(int)pgs+1+1];
	for(int i=0;i<=pgs+1;i++)
	    {tempa[i]=tempnum;
	     tempnum++;
	    }
	 cellAtt.combine(tempa  ,new int[] {0});
      }


    DefaultTableModel    model = new DefaultTableModel((int)((pgs+1)*pcount)+(pcount), nseq);

    fixedTable = new MultiSpanCellTable( fixedModel );
    table = new JTable( model );
    fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    fixedTable.setDefaultRenderer(Object.class, new RowHeaderRenderer(fixedTable));
    fixedTable.setGridColor(table.getTableHeader().getBackground());

    scroll = new JScrollPane( table );
    JViewport viewport = new JViewport();
    viewport.setView(fixedTable);
    viewport.setPreferredSize(fixedTable.getPreferredSize());
    scroll.setRowHeaderView(viewport);
    scroll.setWheelScrollingEnabled(true);
    table.setAutoResizeMode(0);
    table.getTableHeader().setReorderingAllowed(false);

    add(scroll, BorderLayout.CENTER);

    table.setRowHeight(table.getRowHeight()+1);


    //foillowing makes field inacceptable to user until final resutls are shown
    lruk.setEnabled(false);
	car.setEnabled(false);
	arc.setEnabled(false);
	opt.setEnabled(false);
	ilfu.setEnabled(false);
	ilru.setEnabled(false);
	fifo.setEnabled(false);
    random.setEnabled(false);

    ts.setEditable(false);
    t.setEditable(false);
    k.setEditable(false);


    validate();

  }
      if(type.equals("Step") || all==true)
      {   Object col[]=mkcol(nsteps);
	  table.getColumnModel().getColumn(nsteps).setHeaderValue((String)ref.elementAt(nsteps));
	  table.getTableHeader().resizeAndRepaint();
	  int n=0;
	  for(int i=0;i<=((pgs+1)*pcount)+(pcount)-1;i++)
	  {
	  table.setValueAt(col[i],i,nsteps);
    table.getColumnModel().getColumn(nsteps).setResizable(true);

	  }
	  table.validate();
      }

	nsteps++;

	if(all==true && nsteps!=nseq)
	   displayResults(type);

	if(nsteps>=nseq)
	  finalResults();

    }
//******************finalResults**************************
    private void finalResults()
    {

	fnlr.setLayout (new GridLayout (1, pcount));


	if(fifobox==true)
	   {
	    TextArea tran= new TextArea();
	    tran.setEditable(false);
	    tran.append ("FIFO \n");
	    tran.append ("Number of Page Frames="+pgs + "\n");
	    tran.append ("Number of References="+nseq+ "\n");
	    tran.append ("Number of Hits="+pgrep.fifos[0]+ "\n");
	    tran.append ("Number of Misses="+(nseq-pgrep.fifos[0])+" (Cold Start Misses= "+pgrep.fifos[1]+ ")\n");
	    //tran.append ("Hit Ratio = "+ (double)(pgrep.fifos[0])/nseq + "\n");
	    tran.append ("Hit Ratio = "+ (new java.math.BigDecimal ((double)(pgrep.fifos[0])/nseq)).setScale (4 , java.math.BigDecimal.ROUND_HALF_EVEN).doubleValue ()+"\n") ;
	    fnlr.add(tran);
	    }
	if(randombox==true)
	   {
	    TextArea tran= new TextArea();
	    tran.setEditable(false);
	    tran.append ("RANDOM \n");
	    tran.append ("Number of Page Frames="+pgs + "\n");
	    tran.append ("Number of References="+nseq+ "\n");
	    tran.append ("Number of Hits="+pgrep.randoms[0]+ "\n");
	    tran.append ("Number of Misses="+(nseq-pgrep.randoms[0])+" (Cold Start Misses= "+pgrep.randoms[1]+ ")\n");
	    //tran.append ("Hit Ratio = "+ (double)(pgrep.randoms[0])/nseq + "\n");
	    tran.append ("Hit Ratio = "+ (new java.math.BigDecimal ((double)(pgrep.randoms[0])/nseq)).setScale (4 , java.math.BigDecimal.ROUND_HALF_EVEN).doubleValue ()+"\n") ;
	    fnlr.add(tran);
	    }
	if(ilrubox==true)
	   {
	    TextArea tran= new TextArea();
	    tran.setEditable(false);
	    tran.append ("iLRU \n");
	    tran.append ("Number of Page Frames="+pgs + "\n");
	    tran.append ("Number of References="+nseq+ "\n");
	    tran.append ("Number of Hits="+pgrep.ilrus[0]+ "\n");
	    tran.append ("Number of Misses="+(nseq-pgrep.ilrus[0])+" (Cold Start Misses= "+pgrep.ilrus[1]+ ")\n");
	    //tran.append ("Hit Ratio = "+ (double)(pgrep.ilrus[0])/nseq + "\n");
	    tran.append ("Hit Ratio = "+ (new java.math.BigDecimal ((double)(pgrep.ilrus[0])/nseq)).setScale (4 , java.math.BigDecimal.ROUND_HALF_EVEN).doubleValue ()+"\n") ;
	    fnlr.add(tran);
	    }
	if(ilfubox==true)
	   {
	    TextArea tran= new TextArea();
	    tran.setEditable(false);
	    tran.append ("iLFU \n");
	    tran.append ("Number of Page Frames="+pgs + "\n");
	    tran.append ("Number of References="+nseq+ "\n");
	    tran.append ("Number of Hits="+pgrep.ilfus[0]+ "\n");
	    tran.append ("Number of Misses="+(nseq-pgrep.ilfus[0])+" (Cold Start Misses= "+pgrep.ilfus[1]+ ")\n");
	    //tran.append ("Hit Ratio = "+ (double)(pgrep.ilfus[0])/nseq + "\n");
	    tran.append ("Hit Ratio = "+ (new java.math.BigDecimal ((double)(pgrep.ilfus[0])/nseq)).setScale (4 , java.math.BigDecimal.ROUND_HALF_EVEN).doubleValue ()+"\n") ;
	    fnlr.add(tran);
	    }
	if(optbox==true)
	   {
	    TextArea tran= new TextArea();
	    tran.setEditable(false);
	    tran.append ("OPT \n");
	    tran.append ("Number of Page Frames="+pgs + "\n");
	    tran.append ("Number of References="+nseq+ "\n");
	    tran.append ("Number of Hits="+pgrep.opts[0]+ "\n");
	    tran.append ("Number of Misses="+(nseq-pgrep.opts[0])+" (Cold Start Misses= "+pgrep.opts[1]+ ")\n");
	    //tran.append ("Hit Ratio = "+ (double)(pgrep.opts[0])/nseq + "\n");
	    tran.append ("Hit Ratio = "+ (new java.math.BigDecimal ((double)(pgrep.opts[0])/nseq)).setScale (4 , java.math.BigDecimal.ROUND_HALF_EVEN).doubleValue ()+"\n") ;
	    fnlr.add(tran);
	    }
	if(arcbox==true)
	   {
	    TextArea tran= new TextArea();
	    tran.setEditable(false);
	    tran.append ("ARC \n");
	    tran.append ("Number of Page Frames="+pgs + "\n");
	    tran.append ("Number of References="+nseq+ "\n");
	    tran.append ("Number of Hits="+pgrep.arcs[0]+ "\n");
	    tran.append ("Number of Misses="+(nseq-pgrep.arcs[0])+" (Cold Start Misses= "+pgrep.arcs[1]+ ")\n");
	    //tran.append ("Hit Ratio = "+ (double)(pgrep.arcs[0])/nseq + "\n");
	    tran.append ("Hit Ratio = "+ (new java.math.BigDecimal ((double)(pgrep.arcs[0])/nseq)).setScale (4 , java.math.BigDecimal.ROUND_HALF_EVEN).doubleValue ()+"\n") ;
	    fnlr.add(tran);
	    }
	if(carbox==true)
	   {
	    TextArea tran= new TextArea();
	    tran.setEditable(false);
	    tran.append ("CAR \n");
	    tran.append ("Number of Page Frames="+pgs + "\n");
	    tran.append ("Number of References="+nseq+ "\n");
	    tran.append ("Number of Hits="+pgrep.cars[0]+ "\n");
	    tran.append ("Number of Misses="+(nseq-pgrep.cars[0])+" (Cold Start Misses= "+pgrep.cars[1]+ ")\n");
	    //tran.append ("Hit Ratio = "+ (double)(pgrep.cars[0])/nseq + "\n");
	        tran.append ("Hit Ratio = "+ (new java.math.BigDecimal ((double)(pgrep.cars[0])/nseq)).setScale (4 , java.math.BigDecimal.ROUND_HALF_EVEN).doubleValue ()+"\n") ;
	    fnlr.add(tran);
	    }
	if(lrukbox==true)
	   {
	    TextArea tran= new TextArea();
	    tran.setEditable(false);
	    tran.append ("LRU-K \n");
	    tran.append ("Number of Page Frames="+pgs + "\n");
	    tran.append ("Number of References="+nseq+ "\n");
	    tran.append ("Number of Hits="+pgrep.lruks[0]+ "\n");
	    tran.append ("Number of Misses="+(nseq-pgrep.lruks[0])+" (Cold Start Misses= "+pgrep.lruks[1]+ ")\n");
	    //tran.append ("Hit Ratio = "+ (double)(pgrep.lruks[0])/nseq + "\n");
	    tran.append ("Hit Ratio = "+ (new java.math.BigDecimal ((double)(pgrep.lruks[0])/nseq)).setScale (4 , java.math.BigDecimal.ROUND_HALF_EVEN).doubleValue ()+"\n") ;
	    fnlr.add(tran);
	    }



	    add(fnlr,BorderLayout.SOUTH);
	    validate();

	reset();

    }
//***************************RESET*************************
//resets all variable for next run
private void reset()
{
    all=false;
    nsteps=0;
    fifov=null;
    randomv=null;
    ilruv=null;
    ilfuv=null;
    optv=null;
    arcv=null;
    carv=null;
    lrukv=null;
    remove(table);
    totalcount++;
    lruk.setEnabled(true);
	car.setEnabled(true);
	arc.setEnabled(true);
	opt.setEnabled(true);
	ilfu.setEnabled(true);
	ilru.setEnabled(true);
	fifo.setEnabled(true);
    random.setEnabled(true);

    ts.setEditable(true);
    t.setEditable(true);
    k.setEditable(true);



}
//*********MkROWHEAD**************************************
    public Object[][] mkRowHead ()//, String[][] f)
    {
	Object[][] s= new Object[((int)((pgs+1)*pcount)+(pcount))][2];
	String space="";
	int n=0;

	if(fifobox==true)
	   {
	    s[n][0]="FIFO";
	    s[n][1]="0";
	    n++;
	    for(int i =1; i<=pgs-1;i++)
	      {
		s[n][1]=String.valueOf(i);
		n++;
	      }
	    s[n][1]="Hit/Miss";
	    n++;
	    s[n][1]="";
	    n++;
	  }
	if(randombox==true)
	   {
	    s[n][0]="RANDOM";
	    s[n][1]="0";
	    n++;
	    for(int i =1; i<=pgs-1;i++)
	      {
		s[n][1]=String.valueOf(i);
		n++;
	      }
	    s[n][1]="Hit/Miss";
	    n++;
	    s[n][1]="";
	    n++;
	   }
	if(ilrubox==true)
	   {
	    s[n][0]="iLRU";
	    s[n][1]="0";
	    n++;
	    for(int i =1; i<=pgs-1;i++)
	      {
		s[n][1]=String.valueOf(i);
		n++;
	      }
	    s[n][1]="Hit/Miss";
	    n++;
	    s[n][1]="";
	    n++;
	   }
	if(ilfubox==true)
	   {
	    s[n][0]="ILFU";
	    s[n][1]="0";
	    n++;
	    for(int i =1; i<=pgs-1;i++)
	      {
		s[n][1]=String.valueOf(i);
		n++;
	      }
	    s[n][1]="Hit/Miss";
	    n++;
	    s[n][1]="";
	    n++;
	   }
	if(optbox==true)
	   {
	    s[n][0]="OPT";
	    s[n][1]="0";
	    n++;
	    for(int i =1; i<=pgs-1;i++)
	      {
		s[n][1]=String.valueOf(i);
		n++;
	      }
	    s[n][1]="Hit/Miss";
	    n++;
	    s[n][1]="";
	    n++;
	   }
	if(arcbox==true)
	   {
	    s[n][0]="ARC";
	    s[n][1]="0";
	    n++;
	    for(int i =1; i<=pgs-1;i++)
	      {
		s[n][1]=String.valueOf(i);
		n++;
	      }
	    s[n][1]="Hit/Miss";
	    n++;
	    s[n][1]="";
	    n++;
	   }
	if(carbox==true)
	   {
	    s[n][0]="CAR";
	    s[n][1]="0";
	    n++;
	    for(int i =1; i<=pgs-1;i++)
	      {
		s[n][1]=String.valueOf(i);
		n++;
	      }
	    s[n][1]="Hit/Miss";
	    n++;
	    s[n][1]="";
	    n++;
	   }
	if(lrukbox==true)
	   {
	    s[n][0]="LRU-K";
	    s[n][1]="0";
	    n++;
	    for(int i =1; i<=pgs-1;i++)
	      {
		s[n][1]=String.valueOf(i);
		n++;
	      }
	    s[n][1]="Hit/Miss";
	    n++;
	    s[n][1]="";
	    n++;
	   }


	return s;

    }
//*********MKCOL*****************
// makes a column
// passes number of rows in column
    public Object[] mkcol (int n)
    {
	Vector s=new Vector();
	String space="";
	if(fifobox==true)
	   {
	    for(int i =0; i<=pgs;i++)
	      s.add(fifov[i][n]);
	    s.add(space);
	   }
	if(randombox==true)
	   {
	    for(int i =0; i<=pgs;i++)
	      s.add(randomv[i][n]);
	    s.add(space);
	   }
	if(ilrubox==true)
	   {
	    for(int i =0; i<=pgs;i++)
	      s.add(ilruv[i][n]);
	    s.add(space);
	   }
	if(ilfubox==true)
	   {
	    for(int i =0; i<=pgs;i++)
	      s.add(ilfuv[i][n]);
	    s.add(space);
	   }
	if(optbox==true)
	   {
	    for(int i =0; i<=pgs;i++)
	      s.add(optv[i][n]);
	    s.add(space);
	   }
	if(arcbox==true)
	   {
	    for(int i =0; i<=pgs;i++)
	      s.add(arcv[i][n]);
	    s.add(space);
	   }
	if(carbox==true)
	   {
	    for(int i =0; i<=pgs;i++)
	      s.add(carv[i][n]);
	    s.add(space);
	   }
	if(lrukbox==true)
	   {
	    for(int i =0; i<=pgs;i++)
	      s.add(lrukv[i][n]);
	    s.add(space);
	   }

	return s.toArray();

    }
//******ACTION PERFORMED********************
    public void actionPerformed (ActionEvent evt)
    {

	// respond to an action event; this method is part of the
	// ActionListener interface.
	Object target = evt.getSource (); // which component produced this event?
	if (target instanceof Button)
	{
		pgs=StringtoInt((t.getText()).trim());
		organize(ts.getText());
		nseq=ref.size(); //sets the number of sequences to global
		kval=StringtoInt(k.getText());


	    if (evt.getSource()== step){
	       Errors("Step");
	    }
	    else if(evt.getSource()==fnl)
	    {   all=true;
		    Errors("Final");
		}

	}
//below is used if want user to press enter to submit textfield values
/*	else if (target instanceof TextField)
	  {
	    if (evt.getSource()==t){
	       	   pgs=StringtoInt((evt.getActionCommand()).trim());
	    }
	    else if(evt.getSource()==ts){
		organize(evt.getActionCommand());
		nseq=ref.size(); //sets the number of sequences to global
	    }
	    else if(evt.getSource()==k)
	    {
	     kval=StringtoInt(evt.getActionCommand());
	    }
	   }
 */   }
//*********************ORGANIZE************************
  // takes in string value from user for references
  // then changes this string toa vector os sub string for each reference
 private void organize (String s)
    {
	int beg=0,end=0;
	boolean start=true;
	ref.removeAllElements(); //clears out vector
	for(int i=0; i<s.length();i++)
	{
	  if(start == true)
	  {
	    if(s.charAt(i)==' ')
	      {}
	    else if(i==s.length()-1) //if last char in string isnt space
	    {
	      beg=i;
	      end=i;
	      ref.addElement(s.substring(beg));
	    }
	    else{
	      beg=i;
	      start=false;
	      }
	  }
	  else{
	    if(s.charAt(i)==' ')   //finds end of sequence in string and makes substring in vecot
	    {
	      start=true;
	      end=i;
	      ref.addElement(s.substring(beg,end));
	    }
	    else if(i==s.length()-1)  //if last char in string is last in sequence
	    {
	      start=true;
	      end=i+1;
	      ref.addElement(s.substring(beg,end));
	    }
	  }
	}
    }
//************ERRORS********************
    //
    //if pages==0
    //if no check boxes
    //if no enter pushed on textarea
    //then do nothing dont allow action to be performed
    private void Errors(String t)
    {
       if(pcount==0 || pgs==0 || nseq==0)
           CallErrWin();
       else
          displayResults(t);

    }
//*********CallErrWin*******************
//creates a new window to display error
    private void CallErrWin()
    {

		ErrorWindow nw=new ErrorWindow();
        nw.init();
       }

//***************STRINGtoINT***********************
    //chaneg string value to integer value
    //cuts off integer value at space, if char is present returns 0
    private long StringtoInt(String x)
    {
	int len = 0;
	boolean valid= false;
	long number=0;
	int start=0;;
	for (int i=0; i <x.length(); i++)
	    {

	    if((int) x.charAt(i)>47 && (int) x.charAt(i)<58)
		{len++;
		 valid=true;
		}
	    else if((int) x.charAt(i)==32 && len > 0)
	      { valid = true;
		break;}
	    else if ((int) x.charAt(i)==32 && len == 0)
		 start++;
	    else
		break;
	    }

	if (len==x.length())
	    {valid=true;
	     len--;
	    }
	if (valid == true)
	  {  int temp =1;
	     for(int i = len; i>=start;i--)
	       {
		number+=((int)x.charAt(i) - 48)*temp;
		temp*=10;
	       }
	     return number;
	  }
	else
	  return 0;
    }

//*******************itemStateChanged***************************
// respond to an item event; this method is part of the
// ItemListener interface.
    public void itemStateChanged (ItemEvent evt)
    {
	int count=0;
	Object target = evt.getSource ();
	if (target instanceof Checkbox)
	{
	    if (evt.getStateChange () == ItemEvent.SELECTED)
		{
		 if(evt.getSource()==random)
		   randombox=true;
		 else if(evt.getSource()==fifo)
		    fifobox=true;
		 else if(evt.getSource()==ilru)
		    ilrubox=true;
		 else if(evt.getSource()==ilfu)
		    ilfubox=true;
		 else if(evt.getSource()==opt)
		    optbox=true;
		 else if(evt.getSource()==arc)
		    arcbox=true;
		 else if(evt.getSource()==car)
		    carbox=true;
		 else if(evt.getSource()==lruk)
		    {lrukbox=true;

		    }
		 pcount++;
		}
	    else{

		 if(evt.getSource()==random)
		   randombox=false;
		 else if(evt.getSource()==fifo)
		    fifobox=false;
		 else if(evt.getSource()==ilru)
		    ilrubox=false;
		 else if(evt.getSource()==ilfu)
		    ilfubox=false;
		 else if(evt.getSource()==opt)
		    optbox=false;
		 else if(evt.getSource()==arc)
		    arcbox=false;
		 else if(evt.getSource()==car)
		    carbox=false;
		 else if(evt.getSource()==lruk)
		    lrukbox=false;
		pcount--;
		}
	}
	else
	{  }
    }
/*
  **** Used to run as an application********
  static public void main (String argv[]) {
    final Applet applet = new GUI();
    System.runFinalizersOnExit(true);
    applet.resize(800,600);
    Frame frame = new Frame (
		 "Replacement Policy Simulator");
    frame.addWindowListener (
		  new WindowAdapter()
    {
      public void windowClosing (
		   WindowEvent event)
      {
	applet.stop();
	applet.destroy();
	System.exit(0);
      }
    });
    frame.add ("Center", applet);
//    applet.setStub (new MyAppletStub (
//         argv, applet));
    frame.show();
    applet.init();
    applet.start();
    frame.pack();
    frame.setSize(800,600);
    applet.resize(800,600);
  }

*/


} // end class

