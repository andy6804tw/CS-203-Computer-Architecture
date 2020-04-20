import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.System;
import java.security.AccessControlException;
import java.util.NoSuchElementException;

public class MainCode extends JApplet
{
	public void init()
	{
		initComponents();
	}
	
	private JPanel inputPanel;
	private JPanel outputPanel;
	private JPanel buttonPanel;
	private JPanel headerPanel;
	private JPanel tablePanel;
	
	private Choice l1SizeList;
	private Choice l1AssoList;
	private Choice l2SizeList;
	private Choice l2AssoList;
	private Choice vSizeList;
	private Choice blkSizeList;
	private Choice cacheList;
	private Choice traceList;
	
	private JTextArea normalText;
	private JTextArea victimText;
	private JTextArea selvictimText;
	
	private JTextField l1MemCycle;
	private JTextField l2MemCycle;
	private JTextField vMemCycle;
	private JTextField mainMemCycle;
	
	private DefaultTableModel memRefTableModel;
	
	private JTable memRefTable;
	private JTable l1Table;
	private JTable l2Table;
	private JTable victimTable;
	
	private JScrollPane memRefScroll;
	private JScrollPane l1Scroll;
	private JScrollPane l2Scroll;
	private JScrollPane victimScroll;
	
	private int row=0; //indicates the row in memory reference table
	int traceSelect=0;
	int l2_size=0;
	int l2_map=1;
	int l1_size=0;
	int l1_map=1;
	int v_size=0;
	int blk_size=0;
	int cache_map=0;//0->view normal caching
	                //1->view victim caching
	                //2->view selective victim caching
	
	//Map values for l1 and l2 caches
	//1->direct mapped 
	//2->fully associative with LRU
	//3->2-way set associative with LRU
	//4->4-way set associative with LRU
	//5->8-way set associative with LRU
	
	//simulating variables	
	int blk_no; //all the caches have the same block size
		
    //parameters for calculating memory latency in clock cycles
	int t_v=1;
	int t_l1=5;
	int t_l2=10;
	int t_mm=100;
	
	//statistics
	int time=0; //Memory Access Time
	double avgmat=0; //Average Memory Access Time
	int l1Hits=0;
	int l1Misses=0;
	int l2Hits=0;
	int l2Misses=0;
	int vHits=0;
	int vMisses=0;
	int memRead=0;
	int memWrite=0;
	
	//creating objects
	l1_cache l1_cache_ob;
	l2_cache l2_cache_ob;
	v_cache  v_cache_ob;
	
	//initialize the components
	private void initComponents()
	{
		getContentPane().setLayout(null);
		setBackground(new Color(0,0,0));
		
        //^^HEADER PANEL^^//
		headerPanel = new JPanel();
		headerPanel.setLayout(null);
		headerPanel.setBackground(Color.darkGray);
		headerPanel.setBorder(new EtchedBorder());
		
		JLabel title = new JLabel("SELECTIVE VICTIM CACHING");
		title.setForeground(Color.orange);
		title.setFont(new Font("sansserif",Font.BOLD+Font.ITALIC,30));
		title.setBounds(450,20,500,30);
		
		headerPanel.add(title);
		
		getContentPane().add(headerPanel);
		headerPanel.setBounds(0,0,1275,70);
		
        //^^INPUT PANEL^^//
		inputPanel = new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setBackground(Color.lightGray);
		inputPanel.setBorder(new EtchedBorder());
		
		//labels in INPUT PANEL
		JLabel l1Size = new JLabel("L1 Cache Size:");
		JLabel l2Size = new JLabel("L2 Cache Size:");
		JLabel vSize = new JLabel("Victim Cache Size:");
		JLabel blkSize = new JLabel("Block Size:");
		JLabel l1cachepolicy = new JLabel("L1 Cache Policy:");
		JLabel l2cachepolicy = new JLabel("L2 Cache Policy:");
		JLabel cachetype = new JLabel("Type of Caching: ");
		JLabel traceselect = new JLabel("Memory Traces:");
		
		JLabel accesstime = new JLabel("MEMORY ACCESS TIME(in clock cycles):");
		JLabel l1access = new JLabel("L1-cache Access Time:");
		JLabel l2access = new JLabel("L2-cache Access Time:");
		JLabel vaccess = new JLabel("Victim-cache Access Time:");
		JLabel maccess = new JLabel("Main-memory Access Time:");
		
		//choice lists for INPUT PANEL
		l1SizeList = new Choice();
		l1AssoList = new Choice();
		l2SizeList = new Choice();
		l2AssoList = new Choice();
		vSizeList = new Choice();
		blkSizeList = new Choice();
		cacheList = new Choice();
		traceList = new Choice();
		
		//text fields for INPUT PANEL
		l1MemCycle = new JTextField(Integer.toString(5));
		l2MemCycle = new JTextField(Integer.toString(10));
		vMemCycle = new JTextField(Integer.toString(1));
		mainMemCycle = new JTextField(Integer.toString(100));
		
		//bulding choice lists
		
		l1SizeList.add("4 KB");
		l1SizeList.add("8 KB");
		l1SizeList.add("16 KB");
		l1SizeList.add("32 KB");
		l1SizeList.add("64 KB");
				
		l2SizeList.add("16 KB");
		l2SizeList.add("32 KB");
		l2SizeList.add("64 KB");
		l2SizeList.add("128 KB");
		l2SizeList.add("256 KB");
		l2SizeList.add("512 KB");
		

		vSizeList.add("1 KB");
		vSizeList.add("2 KB");
		vSizeList.add("4 KB");
		
		blkSizeList.add("32 Bytes");
		blkSizeList.add("64 Bytes");
		blkSizeList.add("128 Bytes");
		
		l1AssoList.add("Directly Mapped");
		l1AssoList.add("Fully Associative");
		l1AssoList.add("2-way Set Associative");
		l1AssoList.add("4-way Set Associative");
		l1AssoList.add("8-way Set Associative");

		l2AssoList.add("Directly Mapped");
		l2AssoList.add("Fully Associative");
		l2AssoList.add("2-way Set Associative");
		l2AssoList.add("4-way Set Associative");
		l2AssoList.add("8-way Set Associative");
		
		cacheList.add("Normal Caching");
		cacheList.add("Victim Caching");
		cacheList.add("Selective Victim Caching");
		
		traceList.add("T1(80K Ref)");
		traceList.add("T2(100K Ref)");
		traceList.add("T3(150K Ref)");
		
	    //Add all the components to the input panel
		
		//adding labels
		inputPanel.add(l1Size);
		l1Size.setBounds(10,20,105,20);
		inputPanel.add(l1cachepolicy);
		l1cachepolicy.setBounds(10,50,105,20);
		inputPanel.add(l2Size);
		l2Size.setBounds(10,80,105,20);
		inputPanel.add(l2cachepolicy);
		l2cachepolicy.setBounds(10,110,105,20);
		inputPanel.add(vSize);
		vSize.setBounds(10,140,105,20);
		inputPanel.add(blkSize);
		blkSize.setBounds(10,170,105,20);
		inputPanel.add(cachetype);
		cachetype.setBounds(10,200,105,20);
		inputPanel.add(traceselect);
		traceselect.setBounds(10,230,105,20);
		
		//adding memory access time
		inputPanel.add(accesstime);
		accesstime.setBounds(10,270,280,20);
		inputPanel.add(l1access);
		l1access.setBounds(10,300,160,20);
		inputPanel.add(l1MemCycle);
		l1MemCycle.setBounds(175,300,80,20);
		inputPanel.add(l2access);
		l2access.setBounds(10,330,160,20);
		inputPanel.add(l2MemCycle);
		l2MemCycle.setBounds(175,330,80,20);
		inputPanel.add(vaccess);
		vaccess.setBounds(10,360,160,20);
		inputPanel.add(vMemCycle);
		vMemCycle.setBounds(175,360,80,20);
		inputPanel.add(maccess);
		maccess.setBounds(10,390,160,20);
		inputPanel.add(mainMemCycle);
		mainMemCycle.setBounds(175,390,80,20);
				
        //adding choice lists
		inputPanel.add(l1SizeList);
		l1SizeList.setBounds(120,20,150,20);
		inputPanel.add(l1AssoList);
		l1AssoList.setBounds(120,50,150,20);
		inputPanel.add(l2SizeList);
		l2SizeList.setBounds(120,80,150,20);
		inputPanel.add(l2AssoList);
		l2AssoList.setBounds(120,110,150,20);
		inputPanel.add(vSizeList);
		vSizeList.setBounds(120,140,150,20);
		inputPanel.add(blkSizeList);
		blkSizeList.setBounds(120,170,150,20);
		inputPanel.add(cacheList);
		cacheList.setBounds(120,200,150,20);
		inputPanel.add(traceList);
		traceList.setBounds(120,230,150,20);
		
		getContentPane().add(inputPanel);
		inputPanel.setBounds(0,70,300,420);
		
        //^^BUTTON PANEL^^//
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(Color.lightGray);
		buttonPanel.setBorder(new EtchedBorder());
		
        //buttons in button panel
		JButton startButton = new JButton();
		startButton.setText("Initialize");
		buttonPanel.add(startButton);
		startButton.setBounds(40,40,80,30);
		
		startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae)
				{
					startButtonActionPerformed(ae);
				}});
		
		JButton runButton = new JButton();
		runButton.setText("Run");
		buttonPanel.add(runButton);
		runButton.setBounds(140,40,80,30);
		
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				runButtonActionPerformed(ae);
			}});
		
		JButton clearButton = new JButton();
		clearButton.setText("Clear");
		buttonPanel.add(clearButton);
		clearButton.setBounds(90,100,80,30);
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				clearButtonActionPerformed(ae);
			}});
		
		getContentPane().add(buttonPanel);
		buttonPanel.setBounds(0,490,300,200);
		
        //^^OUTPUT PANEL^^//
		outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBackground(Color.darkGray);
		outputPanel.setBorder(new EtchedBorder());
		
		normalText = new JTextArea();
		normalText.setBackground(Color.white);
		normalText.setEditable(false);
		normalText.setFont(new java.awt.Font("Terbuchet MS", 1, 12));
		JScrollPane normalPane = new JScrollPane(normalText);
		normalPane.setBounds(5,40,320,155);
		outputPanel.add(normalPane);
		
		victimText = new JTextArea();
		victimText.setBackground(Color.white);
		victimText.setEditable(false);
		victimText.setFont(new java.awt.Font("Terbuchet MS", 1, 12));
		JScrollPane victimPane = new JScrollPane(victimText);
		victimPane.setBounds(330,40,320,155);
		outputPanel.add(victimPane);
		
		selvictimText = new JTextArea();
		selvictimText.setBackground(Color.white);
		selvictimText.setEditable(false);
		selvictimText.setFont(new java.awt.Font("Terbuchet MS", 1, 12));
		JScrollPane selvictimPane = new JScrollPane(selvictimText);
		selvictimPane.setBounds(655,40,315,155);
		outputPanel.add(selvictimPane);
		
		JLabel outputheader = new JLabel("SIMULATION RESULTS");
		outputheader.setForeground(Color.orange);
		outputheader.setBounds(425,0,150,15);
		outputPanel.add(outputheader);
		
		JLabel normalheader = new JLabel("Normal Caching");
		normalheader.setForeground(Color.orange);
		normalheader.setHorizontalAlignment(JLabel.CENTER);
		normalheader.setBounds(5,20,320,15);
		outputPanel.add(normalheader);
		
		JLabel victimheader = new JLabel("Victim Caching");
		victimheader.setForeground(Color.orange);
		victimheader.setHorizontalAlignment(JLabel.CENTER);
		victimheader.setBounds(330,20,320,15);
		outputPanel.add(victimheader);
		
		JLabel selvictimheader = new JLabel("Selective Victim Caching");
		selvictimheader.setForeground(Color.orange);
		selvictimheader.setHorizontalAlignment(JLabel.CENTER);
		selvictimheader.setBounds(655,20,315,15);
		outputPanel.add(selvictimheader);
		
		getContentPane().add(outputPanel);
		outputPanel.setBounds(300,490,975,200);
	}
	
	private void createTables()
	{
		tablePanel = new JPanel();
		tablePanel.setLayout(null);
		tablePanel.setBackground(Color.darkGray);
		
		//Memory Reference Table
		memRefTable = new JTable();
		memRefTable.setBackground(Color.white);
		memRefTableModel = new DefaultTableModel();
		memRefTable = new JTable(memRefTableModel);
		memRefTable.setEnabled(false);
		memRefTableModel.addColumn("Memory Ref.");
		memRefTableModel.addColumn("R/W");
		memRefScroll = new JScrollPane(memRefTable);
		memRefScroll.setBounds(5,30,170,375);
		tablePanel.add(memRefScroll);
		
		JLabel memheader = new JLabel("Memory Reference Table");
		memheader.setForeground(Color.orange);
		memheader.setHorizontalAlignment(JLabel.CENTER);
		memheader.setBounds(5,0,145,30);
		tablePanel.add(memheader);
		
		// L1Cache
		l1Table = new JTable();
		l1Table.setBackground(Color.white);
		String[] l1TableHead = {"Index","Tag","Valid","LRU"};
		String[][] l1TableData = new String[l1_cache_ob.no_entries][4];
		for(int j=0;j<l1_cache_ob.no_entries;j++)
		{
			l1TableData[j][0]=null;
			l1TableData[j][1]=null;
			l1TableData[j][2]=null;
			l1TableData[j][3]=null;
		}
		
		l1Table = new JTable(l1TableData,l1TableHead);
		l1Table.setEnabled(false);
		l1Scroll = new JScrollPane(l1Table);
		l1Scroll.setBounds(180,30,260,375);
		tablePanel.add(l1Scroll);
		
		JLabel l1header = new JLabel("L1 Cache");
		l1header.setForeground(Color.orange);
		l1header.setHorizontalAlignment(JLabel.CENTER);
		l1header.setBounds(180,0,250,30);
		tablePanel.add(l1header);
		
		//L2 Cache 
		l2Table = new JTable();
		String[] l2TableHead = {"Index","Tag","Valid","LRU"};
		String[][] l2TableData = new String[l2_cache_ob.no_entries][4];
		for(int j=0;j<l2_cache_ob.no_entries;j++)
		{
			l2TableData[j][0]=null;
			l2TableData[j][1]=null;
			l2TableData[j][2]=null;
			l2TableData[j][3]=null;
		}
		
		l2Table = new JTable(l2TableData,l2TableHead);
		l2Table.setEnabled(false);
		l2Scroll = new JScrollPane(l2Table);
		l2Scroll.setBounds(445,30,260,375);
		tablePanel.add(l2Scroll);
		
		JLabel l2header = new JLabel("L2 Cache");
		l2header.setForeground(Color.orange);
		l2header.setHorizontalAlignment(JLabel.CENTER);
		l2header.setBounds(445,0,250,30);
		tablePanel.add(l2header);
		
		if ( cache_map !=0 )
		{
			//Victim cache
			victimTable = new JTable();
			String[] vTableHead = {"Index","Tag","Valid","LRU"};
			String[][] vTableData = new String[v_cache_ob.no_entries][4];
			for(int j=0;j<v_cache_ob.no_entries;j++)
			{
				vTableData[j][0]=null;
				vTableData[j][1]=null;
				vTableData[j][2]=null;
				vTableData[j][3]=null;
			}
		
			victimTable = new JTable(vTableData,vTableHead);
			victimTable.setEnabled(false);
			victimScroll = new JScrollPane(victimTable);
			victimScroll.setBounds(710,30,260,375);
			tablePanel.add(victimScroll);
		
			JLabel vheader = new JLabel("Victim Cache");
			vheader.setForeground(Color.orange);
			vheader.setHorizontalAlignment(JLabel.CENTER);
			vheader.setBounds(710,0,305,30);
			tablePanel.add(vheader);
		}
		
		tablePanel.setVisible(true);
		getContentPane().add(tablePanel);
		tablePanel.setBounds(302,72,973,413);
		
	}
	
	//initialize only memory reference table
	private void initTables()
	{
		URL url = null;
		String line,refType,memRef,type=null;
		int ct=0;	
		String urlValue=null;
		
		if(traceSelect==0)urlValue="http://www-unix.ecs.umass.edu/~asreedha/aparna/swim.txt";
		if(traceSelect==1)urlValue="http://www-unix.ecs.umass.edu/~asreedha/aparna/gcc.txt";
		if(traceSelect==2)urlValue="http://www-unix.ecs.umass.edu/~asreedha/aparna/new_gcc.txt";
		
		if(memRefTableModel.getRowCount()==0)
		{
			try
			{
				url = new URL(urlValue);
			}
			catch(MalformedURLException e)
			{
				urlValue="http://www-unix.ecs.umass.edu/~asreedha/aparna/sixpack.txt";
				try
				{
					url = new URL(urlValue);
				}
				catch(MalformedURLException e1)
				{
					e1.printStackTrace();
				}
			}
			try 
			{ 
				InputStream in=url.openStream(); 
				BufferedReader dis = 
					new BufferedReader(new InputStreamReader(in)); 
				while ((line = dis.readLine()) != null)
				{
					ct++;
					StringTokenizer st = new StringTokenizer(line);
					try
					{
						if(ct>1)
						{ 
							refType=st.nextToken(); //type of ref
							if (refType.compareTo("R") == 0) 
								type="Read";
							else
								type="Write";
							memRef=st.nextToken();
							memRefTableModel.addRow(new Object[] {memRef,type});
						}
					}
					catch(NoSuchElementException noe)
					{
						
					}
				}
				in.close(); 
			}
			catch (IOException e ) 
			{
				
			}
		}
	}
	
	//Actions performed when Button's are pressed
	
	private void startButtonActionPerformed(ActionEvent ae)
	{
		initData();
		createTables();
		initTables();
	}
	
	private void runButtonActionPerformed(ActionEvent ae)
	{
		time=0;
		int ct=0;
		double memRef=0;
		String refType;
		row=0;
		int numRows=memRefTable.getRowCount();
				
		while(row<numRows) // As long as memory references are available
		{
			ct++; 
			refType=memRefTable.getValueAt(row,1).toString();
			memRef=Double.parseDouble(memRefTable.getValueAt(row,0).toString());
			
			if(refType.compareTo("Read")==0)
				memRead++;
			else 
				memWrite++;
	
			blk_no = (int)memRef/blk_size;
						
			if(cache_map==0) //FOR NORMAL CACHING
			{
				if(l1_cache_ob.check_Blk(blk_no, l1_map)==1)  //L1 Cache hit  
				{                                                    
					time+=t_l1;
					l1Hits++;
				}
				else // L1 Cache miss and we need to check in L2 cache
				{
					l1Misses++;
					if(l2_cache_ob.check_Blk(blk_no, l2_map)==1) //L2 cache hit
				    {
				    	time+=t_l1+t_l2;
				    	l2Hits++;
				    	l1_cache_ob.add_Blk(blk_no,l1_map);
				    }
				    else//L2 Cache Miss; fetch the block from main memory and add to L1 and L2
				    {
				    	time+=t_l1+t_l2+t_mm;
				    	l2Misses++;
				    	l2_cache_ob.add_Blk(blk_no,l2_map); 
				    	l1_cache_ob.add_Blk(blk_no,l1_map); 
				    }
				}
			} //END OF NORMAL CACHING
			
			else if (cache_map==1) //FOR VICTIM CACHING
			{
				if(l1_cache_ob.check_Blk(blk_no, l1_map)==1) //L1 cache hit
				{
					l1Hits++;
					time += t_l1;
				}
				else //L1 cache miss
				{
					l1Misses++;
					if(v_cache_ob.check_Blk(blk_no)==1) // Victim cache hit
					{
						time += t_l1+t_v;
						vHits++;
						
						int temp=0;
						if(l1_map==1)temp=l1_cache_ob.direct_mapped_conflict(blk_no);
						else if(l1_map==2) temp=l1_cache_ob.fully_associative_conflict();
						else temp=l1_cache_ob.set_associative_conflict(blk_no,l1_map);
						
						if(temp != -1) // L1 cache is full and the block to be
							           //deleted shud first be placed in victim cache.
						{
							v_cache_ob.rem_Blk(blk_no);
							v_cache_ob.add_Blk(temp);							
							l1_cache_ob.add_Blk(blk_no,l1_map);
						}
						else 
						{
							v_cache_ob.rem_Blk(blk_no);
							l1_cache_ob.add_Blk(blk_no,l1_map);
						}
					}
					else // Victim cache miss
					{
						vMisses++;					
						if(l2_cache_ob.check_Blk(blk_no, l2_map)==1)//L2 cache hit
						{
							time += t_l1+t_v+t_l2;
							l2Hits++;
							int temp=0;
							if(l1_map==1) temp=l1_cache_ob.direct_mapped_conflict(blk_no);
							else if(l1_map==2) temp=l1_cache_ob.fully_associative_conflict();
							else temp=l1_cache_ob.set_associative_conflict(blk_no,l1_map);

							if (temp != -1) // L1 cache is full and the block to be
								            //deleted shud first be placed in victim cache.
							{
								v_cache_ob.add_Blk(temp);
								l1_cache_ob.add_Blk(blk_no,l1_map);
							}
							else l1_cache_ob.add_Blk(blk_no,l1_map);
						}
						else //L2 cache Miss
						{
							time+=t_l1+t_v+t_l2+t_mm;
							l2Misses++;
							l2_cache_ob.add_Blk(blk_no,l2_map);
							int temp;	
							if(l1_map==1)temp=l1_cache_ob.direct_mapped_conflict(blk_no);
							else if (l1_map==2)temp=l1_cache_ob.fully_associative_conflict();
							else temp=l1_cache_ob.set_associative_conflict(blk_no,l1_map);

							if(temp!=-1)//L1 is full and conflicting blk shud be added to
							{             // victim cache
								v_cache_ob.add_Blk(temp);								
								l1_cache_ob.add_Blk(blk_no,l1_map);								
							}
							else l1_cache_ob.add_Blk(blk_no,l1_map);
						}
					}
				}
			} // END OF VICTIM CACHING
			
			else //FOR SELECTIVE VICTIM CACHING
			{
				if(l1_cache_ob.check_Blk(blk_no,l1_map)==1)// Hit in L1
				{
					l1Hits++;
					time+=t_l1;
					l1_cache_ob.set_Hit(blk_no,l1_map,1);
					l1_cache_ob.set_Sticky(blk_no,l1_map,1);
				}
				else //L1 Miss
				{
					l1Misses++;
					if(v_cache_ob.check_Blk(blk_no)==1)// Miss in L1 and hit in victim
					{
						time+= t_l1+t_v;
						vHits++;
						int temp=0;
						if(l1_map==1)temp=l1_cache_ob.direct_mapped_conflict(blk_no);
						else if(l1_map==2)temp=l1_cache_ob.fully_associative_conflict();
						else temp=l1_cache_ob.set_associative_conflict(blk_no,l1_map);
						
						if(temp!=-1) //conflict exists in L1
						{
							int s=l1_cache_ob.get_Sticky(temp,l1_map);
							int h=v_cache_ob.get_Hit(blk_no,2);
							if(s==0)//sticky bit of L1 is 0
							{
								if(l2_cache_ob.just_check(temp,l2_map)==1)
								{
									l2_cache_ob.set_Hit(temp,l2_map,l1_cache_ob.get_Hit(temp,l1_map));
									v_cache_ob.set_Sticky(temp,l2_map,l1_cache_ob.get_Sticky(temp,l1_map));
								}
								v_cache_ob.rem_Blk(blk_no);
								v_cache_ob.add_Blk(temp);  
								v_cache_ob.set_Hit(temp,2,l1_cache_ob.get_Hit(temp,l1_map));
								v_cache_ob.set_Sticky(temp,2,l1_cache_ob.get_Sticky(temp,l1_map));
								l1_cache_ob.add_Blk(blk_no,l1_map);//interchange between l1 
								        						   //and victim cache
								l1_cache_ob.set_Sticky(blk_no,l1_map,1);
								l1_cache_ob.set_Hit(blk_no,l1_map,1);
							}
							else//sticky bit of L1 is 1
							{
								if(h==0)//hit bit of victim is 0
									l1_cache_ob.set_Sticky(temp,l1_map,0);//no interchage
								else//hit bit of victim is 1
								{
									if(l2_cache_ob.just_check(temp,l2_map)==1)
									{
										l2_cache_ob.set_Hit(temp,l2_map,l1_cache_ob.get_Hit(temp,l1_map));
										v_cache_ob.set_Sticky(temp,l2_map,l1_cache_ob.get_Sticky(temp,l1_map));
									}
									v_cache_ob.rem_Blk(blk_no);
									v_cache_ob.add_Blk(temp);
									v_cache_ob.set_Hit(temp,2,l1_cache_ob.get_Hit(temp,l1_map));
									v_cache_ob.set_Sticky(temp,2,l1_cache_ob.get_Sticky(temp,l1_map));
									l1_cache_ob.add_Blk(blk_no,l1_map);//interchange between
																	   //L1 and victim
									l1_cache_ob.set_Sticky(blk_no,l1_map,1);
									l1_cache_ob.set_Hit(blk_no,l1_map,0);
								}
							}
						}
						else //no conflict exists in L1 cache..move a copy to L1 cache 
						{
							v_cache_ob.rem_Blk(blk_no);
							l1_cache_ob.add_Blk(blk_no,l1_map);
							l1_cache_ob.set_Hit(blk_no,l1_map,0);
							l1_cache_ob.set_Sticky(blk_no,l1_map,1);
						}
					}
					else// Miss in L1 and victim
					{
						vMisses++;
						if(l2_cache_ob.check_Blk(blk_no,l2_map)==1)//hit in L2
						{							
							time+=t_l1+t_v+t_l2;
							l2Hits++;
							int temp=0;
							if(l1_map==1)temp=l1_cache_ob.direct_mapped_conflict(blk_no);
							else if(l1_map==2)temp=l1_cache_ob.fully_associative_conflict();
							else temp=l1_cache_ob.set_associative_conflict(blk_no,l1_map);
							
							if(temp!=-1) //conflict in L1
							{
								int s=l1_cache_ob.get_Sticky(temp,l1_map);
								int h=l2_cache_ob.get_Hit(blk_no,l2_map);
								
								if(s==0)
								{
									if(l2_cache_ob.just_check(temp,l2_map)==1)
									{
										l2_cache_ob.set_Hit(temp,l2_map,l1_cache_ob.get_Hit(temp,l1_map));
										v_cache_ob.set_Sticky(temp,l2_map,l1_cache_ob.get_Sticky(temp,l1_map));
									}
									v_cache_ob.add_Blk(temp);
									v_cache_ob.set_Hit(temp,2,l1_cache_ob.get_Hit(temp,l1_map));
									v_cache_ob.set_Sticky(temp,2,l1_cache_ob.get_Sticky(temp,l1_map));
									l1_cache_ob.add_Blk(blk_no,l1_map);
									l1_cache_ob.set_Sticky(blk_no,l1_map,1);
									l1_cache_ob.set_Hit(blk_no,l1_map,1);
								}
								else
								{
									if(h==0)
									{
										v_cache_ob.add_Blk(blk_no);
										l1_cache_ob.set_Sticky(temp,l1_map,0);
									}
									else
									{
										if(l2_cache_ob.just_check(temp,l2_map)==1)
										{
											l2_cache_ob.set_Hit(temp,l2_map,l1_cache_ob.get_Hit(temp,l1_map));
											v_cache_ob.set_Sticky(temp,l2_map,l1_cache_ob.get_Sticky(temp,l1_map));
										}
										v_cache_ob.add_Blk(temp);
										v_cache_ob.set_Hit(temp,2,l1_cache_ob.get_Hit(temp,l1_map));
										v_cache_ob.set_Sticky(temp,2,l1_cache_ob.get_Sticky(temp,l1_map));
										l1_cache_ob.add_Blk(blk_no,l1_map);
										l1_cache_ob.set_Hit(blk_no,l1_map,1);
										l1_cache_ob.set_Sticky(blk_no,l1_map,0);
									}
								}
							}
							else // no conflict in L1
							{
								l1_cache_ob.add_Blk(blk_no,l1_map);
								l1_cache_ob.set_Hit(blk_no,l1_map,0);
								l1_cache_ob.set_Sticky(blk_no,l1_map,1);
							}
						}
						else //Miss in L1,victim and L2.
						{
							time+=t_l1+t_v+t_l2+t_mm;
							l2Misses++;
							l2_cache_ob.add_Blk(blk_no,l2_map);
							l2_cache_ob.set_Hit(blk_no,l2_map,0);
							l2_cache_ob.set_Sticky(blk_no,l2_map,0);
							int temp=0;
							if(l1_map==1)temp=l1_cache_ob.direct_mapped_conflict(blk_no);
							else if(l1_map==2)temp=l1_cache_ob.fully_associative_conflict();
							else temp=l1_cache_ob.set_associative_conflict(blk_no,l1_map);
						
							if(temp!=-1)//l1 Conflict exists
							{
								int s=l1_cache_ob.get_Sticky(temp,l1_map);
																
								if(s==0)
								{
									if(l2_cache_ob.just_check(temp,l2_map)==1)
									{
										l2_cache_ob.set_Hit(temp,l2_map,l1_cache_ob.get_Hit(temp,l1_map));
										v_cache_ob.set_Sticky(temp,l2_map,l1_cache_ob.get_Sticky(temp,l1_map));
									}
									v_cache_ob.add_Blk(temp);
									v_cache_ob.set_Hit(temp,2,l1_cache_ob.get_Hit(temp,l1_map));
									v_cache_ob.set_Sticky(temp,2,l1_cache_ob.get_Sticky(temp,l1_map));
									l1_cache_ob.add_Blk(blk_no,l1_map);
									l1_cache_ob.set_Sticky(blk_no,l1_map,1);
									l1_cache_ob.set_Hit(blk_no,l1_map,1);
								}
								else
								{
									v_cache_ob.add_Blk(blk_no);
									l1_cache_ob.set_Sticky(temp,l1_map,0);
								}
							}
							else
							{
								l1_cache_ob.add_Blk(blk_no,l1_map);
								l1_cache_ob.set_Hit(blk_no,l1_map,0);
								l1_cache_ob.set_Sticky(blk_no,l1_map,1);
							}
						}
					}
				}
			} // END OF SELECTIVE VICTIM CACHING
			row++;
		}
		
		avgmat = time/ct;
		
		//display tables
		String temp;
		
		for(int i=0;i<l1_cache_ob.no_entries;i++)
		{
			temp=Integer.toString(i);
			l1Table.setValueAt(temp,i,0);
			temp=Integer.toString(l1_cache_ob.tag[i]);
			l1Table.setValueAt(temp,i,1);
			temp=Integer.toString(l1_cache_ob.valid[i]);
			l1Table.setValueAt(temp,i,2);
			temp=Integer.toString(l1_cache_ob.lru_ct[i]);
			l1Table.setValueAt(temp,i,3);
		}
		
		for(int i=0;i<l2_cache_ob.no_entries;i++)
		{
			temp=Integer.toString(i);
			l2Table.setValueAt(temp,i,0);
			temp=Integer.toString(l2_cache_ob.tag[i]);
			l2Table.setValueAt(temp,i,1);
			temp=Integer.toString(l2_cache_ob.valid[i]);
			l2Table.setValueAt(temp,i,2);
			temp=Integer.toString(l2_cache_ob.lru_ct[i]);
			l2Table.setValueAt(temp,i,3);
		}
		
		if(cache_map == 1)
		{
			for(int i=0;i<v_cache_ob.no_entries;i++)
			{
				temp=Integer.toString(i);
				victimTable.setValueAt(temp,i,0);
				temp=Integer.toString(v_cache_ob.tag[i]);
				victimTable.setValueAt(temp,i,1);
				temp=Integer.toString(v_cache_ob.valid[i]);
				victimTable.setValueAt(temp,i,2);
				temp=Integer.toString(v_cache_ob.lru_ct[i]);
				victimTable.setValueAt(temp,i,3);
			}
		}
		
		if(cache_map == 2)
		{
			for(int i=0;i<v_cache_ob.no_entries;i++)
			{
				temp=Integer.toString(i);
				victimTable.setValueAt(temp,i,0);
				temp=Integer.toString(v_cache_ob.tag[i]);
				victimTable.setValueAt(temp,i,1);
				temp=Integer.toString(v_cache_ob.valid[i]);
				victimTable.setValueAt(temp,i,2);
				temp=Integer.toString(v_cache_ob.lru_ct[i]);
				victimTable.setValueAt(temp,i,3);
			}
		}
		
		//display statistics
		printStats();
	}
	
	private void clearButtonActionPerformed(ActionEvent ae)
	{
		tablePanel.setBackground(Color.lightGray);
		tablePanel.removeAll();
		normalText.setText(""); //clear the previous outputs
		victimText.setText("");
		selvictimText.setText("");
	}
	
	private void initData()
	{
		row=0;
		time=0;//Memory Access Time
		avgmat=0;//Average memory access time
		l1Hits=0;
		l1Misses=0;
		l2Hits=0;
		l2Misses=0;
		vHits=0;
		vMisses=0;
		memRead=0;
		memWrite=0;
		traceSelect=0;
		//traceURL=null;
		l1_map=1;
		l2_map=1;
		
		String l2cachesize = l2SizeList.getSelectedItem();
		l2_size=Integer.parseInt(l2cachesize.substring(0,l2cachesize.indexOf("K")-1));
		l2_size *= 1024;
		
		String l1cachesize = l1SizeList.getSelectedItem();
		l1_size=Integer.parseInt(l1cachesize.substring(0,l1cachesize.indexOf("K")-1));
		l1_size *= 1024;
		
		String vcachesize = vSizeList.getSelectedItem();
		v_size = Integer.parseInt(vcachesize.substring(0,vcachesize.indexOf("K")-1));
		v_size *= 1024;
		
		String blocksize = blkSizeList.getSelectedItem();
		blk_size = Integer.parseInt(blocksize.substring(0,blocksize.indexOf("B")-1));
		
		int l1policy = l1AssoList.getSelectedIndex();
		l1_map = l1policy+1;
		
		int l2policy = l2AssoList.getSelectedIndex();
		l2_map = l2policy+1;
		
		cache_map = cacheList.getSelectedIndex();
		
		traceSelect = traceList.getSelectedIndex();
		
		String l1str = l1MemCycle.getText();
		t_l1 = Integer.parseInt(l1str);
		
		String l2str = l2MemCycle.getText();
		t_l2 = Integer.parseInt(l2str);
		
		String vstr = vMemCycle.getText();
		t_v = Integer.parseInt(vstr);
		
		String mmstr = mainMemCycle.getText();
		t_mm = Integer.parseInt(mmstr);
		
		//creating objects
		
		if(cache_map==0)
		{
			l1_cache_ob = new l1_cache(l1_size/blk_size);
			l2_cache_ob = new l2_cache(l2_size/blk_size);
		}
		else
		{
			l1_cache_ob = new l1_cache(l1_size/blk_size);
			l2_cache_ob = new l2_cache(l2_size/blk_size);
			v_cache_ob = new v_cache(v_size/blk_size);
		}
	}
	
	private void printStats()
	{
		String text=null;
		float temp=0;
		
		if(cache_map==0)
		{
			text ="   Total Memory References = ";
			text+=(row);
		
			text+="\n   Average Memory Access Time  = ";
			text+= avgmat;
			text+=" Clock Cycles";
			
			if((l1Hits+l1Misses)!=0)
			{
				text+="\n   L1 Cache Hit = ";
				temp=((float)l1Hits/(l1Hits+l1Misses));
				temp*=100;
				text+=temp;
				text+=" %";
			}
		
			if((l2Hits+l2Misses)!=0)
			{
				text+="\n   L2 Cache Hit  = ";
				temp=((float)l2Hits/(l2Hits+l2Misses));
				temp*=100;
				text+=temp;
				text+=" %";
			}
		}
		else
		{
			text ="   Total Memory References = ";
			text+=(row);
			
			text+="\n   Average Memory Access Time  = ";
			text+= avgmat;
			text+=" Clock Cycles";
			text+="\n   Number of Victim Hits = ";
			text+=vHits;
			text+="\n   Number of Victim Misses = ";
			text+=vMisses;
			if((l1Hits+l1Misses)!=0)
			{
				text+="\n   L1 Cache Hit Rate = ";
				temp=((float)l1Hits/(l1Hits+l1Misses));
				temp*=100;
				text+=temp;
				text+=" %";
			}
			
			if((l2Hits+l2Misses)!=0)
			{
				text+="\n   L2 Cache Hit Rate = ";
				temp=((float)l2Hits/(l2Hits+l2Misses));
				temp*=100;
				text+=temp;
				text+=" %";
			}
			
			if((vHits+vMisses)!=0)
			{
				text+="\n   Victim Cache Hit  = ";
				temp=((float)vHits/(vHits+vMisses));
				temp*=100;
				text+=temp;
				text+=" %";		
			}	
		}
		if(cache_map==0)normalText.setText(text);
		else if(cache_map==1)victimText.setText(text);
		else selvictimText.setText(text);
	}
}