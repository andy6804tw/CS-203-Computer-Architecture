import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;


/**
 * @author Rakesh Kothari
 * @author Siddhartha Bunga
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/* <applet code="MainWindow" width=800 height=600>
 </applet>
 
 */
public class MainWindow extends JApplet {
	
	
	public MainWindow() {
		initComponents();
	}
	
	private JTextField tlbSizeTxt;
	private Choice mmSizeList;
	private Choice pageSizeList;
	private Choice cacheLevelList;
	private Choice l2CacheSizeList;
	private Choice l2BlockSizeList;
	private Choice l2PolicyList;
	private Choice l1CacheSizeList;
	private Choice l1BlockSizeList;
	private Choice l1PolicyList;
	private Choice mmPolicyList;
	private Choice brkPointList;
	private Choice traceSelectList;
	private Choice l2ReplacementPolicyList;
	private Choice l1ReplacementPolicyList;
	
	private JTable memRefTbl;	
	private JTable tlbTbl;
	private JTable pgTbl;
	private JTable l1iTbl;
	private JTable l1dTbl;
	private JTable l2Tbl;
	private JTable mmTbl;
	private JPanel inputPanel;
	private JPanel outputPanel;
	private JPanel hdrPanel;
	private JPanel tblPanel;
	private JPanel buttonPanel;
	private JTextArea outputText;
	private JTextArea defaultText;
	private JTextField urlTxt;
	
	private JTextField label1Txt;
	private JTextField label2Txt;
	private JTextField label3Txt;
	private JTextField label4Txt;
	private JTextField label5Txt;
	private JTextField label6Txt;
	
	
	private DefaultTableModel memRefTabModel;
	private DefaultTableModel pgTabModel;
	private ColorRenderer colorRenderer;
	
	private JScrollPane tlbJsp;
	private JScrollPane ptJsp;
	private JScrollPane l1iJsp;
	private JScrollPane l1dJsp;
	private JScrollPane mmJsp;
	private JScrollPane l2Jsp;
	
	private boolean clrBtnPressed=false;
	private boolean partBtnPressed=false;
	//last break (or check)point (row in the memory reference table)
	private int row=0;
	
	int traceSelect=0;
	String traceUrl=null;
	double vm_sz=4294967296.0;			// for 32 bit addressing
	int pg_sz=0; 
	int mm_sz=0;
	int tlb_sz=0;
	int cache_level=0;
	int l2_sz=0;
	int l2_blk_sz=0;
	int l2_map=2;		
	
	int l1_sz=0;
	int l1_blk_sz=0;
	int l1_map=2;		
	
	//	Map values for caches
	//	1=> direct mapped. No page replacement policy for direct mapped
	//  2=> fully associative with FIFO replacement
	//	3=> fully associative with LRU replacement
	// 	4=> set associative with FIFO replacement
	//	5=> set associative with LRU replacement
	
	
	//	Replacement algorithms for MM
	// 	1=> FIFO replacement 	2=> LRU replacement 
	int mm_replacement=2;
	
	int brkpt=0;		// break point event
	
	
	//simulating variables
	int pg_no,frame_no,pg_off;
	int blk_no_l1,blk_off_l1;
	int blk_no_l2,blk_off_l2;
	
	int l1_val=0;	//1=>instruction	2=>data
	
	int cachelevelch=0;		// 0=>L1 & L2	1=>L1 Unified	2=>L1 I&D
	
	// parameters for calculating memory latency in clock cycles
	int t_tlb=1;
	int t_pt=5;
	int t_l1=5;
	int t_l2=10;
	int t_mm=100;
	int t_vm=1000000;	// 1000 K clock cycles
	
	
	// statistics
	int time=0;		// Memory Access Time
	int avgmat=0;		// Average Memory Access Time
	int tat=0;		// total access time
	int pgHits=0;
	int pgFaults=0;
	int tlbHits=0;
	int tlbMiss=0;
	int l1iHits=0;
	int l1dHits=0;
	int l1iMiss=0;
	int l1dMiss=0;
	int l2Hits=0;
	int l2Miss=0;
	int instCount=0;
	int dataCount=0;
	
	// simulation 
	int tot_pages=0;
	int no_frames=0;
	
	
	// creating objects
	tlb tlb_ob;
	pg_tbl pg_tbl_ob;
	main_mem main_mem_ob;
	cache_l1_i cache_l1_i_ob;
	cache_l1_d cache_l1_d_ob;
	cache_l2 cache_l2_ob;
	
	//intialize the components	
	
	private void initComponents() {
		
		getContentPane().setLayout(null);
		setBackground(new Color(0, 0, 0));
		
		/*Input Panel. Position=Left*/
		inputPanel= new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setBackground(Color.lightGray);
		//inputPanel.setBackground(new Color(13,77,115));
		inputPanel.setBorder(new EtchedBorder());
		
		
		//labels of the input panel
		JLabel pageSize= new JLabel("Page Size (KB)");
		JLabel mmSize= new JLabel("Main Memory Size (MB)");
		JLabel tlbSize= new JLabel("TLB Size");
		JLabel cacheLevels= new JLabel("Cache Levels");
		JLabel l2CacheSize= new JLabel("L2-Cache Size");
		JLabel l2BlockSize= new JLabel("L2-Block Size");
		JLabel l2CachePolicy= new JLabel("L2-Cache Policy");
		JLabel l2ReplacementPolicy= new JLabel("L2-Replacement Policy");
		JLabel l1CacheSize= new JLabel("L1-Cache Size");
		JLabel l1BlockSize= new JLabel("L1-Block Size");
		JLabel l1CachePolicy= new JLabel("L1-Cache Policy");
		JLabel l1ReplacementPolicy= new JLabel("L1-Replacement Policy");
		JLabel mmMapping= new JLabel("Page Replacement Policy");
		JLabel brkPoint=new JLabel("Break Points");
		JLabel traceSelect=new JLabel("Memory Trace Samples");
		JLabel url=new JLabel("Custom Trace (from Url) ");
		
		JLabel lab1=new JLabel("FA :   Fully Associative");
		JLabel lab2=new JLabel("SA :   Set Associative");
		JLabel lab3=new JLabel("DM :   Direct Mapped");
		
		lab1.setForeground(Color.blue);
		lab2.setForeground(Color.blue);
		lab3.setForeground(Color.blue);
		
		//Input Fields of the input panel
		pageSizeList= new Choice();   //2KB default
		mmSizeList=new Choice();     //32 MB default
		tlbSizeTxt= new JTextField(Integer.toString(10));  //128 entries default
		cacheLevelList=new Choice();
		l2CacheSizeList= new Choice();  //64 KB default
		l2BlockSizeList= new Choice();
		l2PolicyList= new Choice();
		l2ReplacementPolicyList= new Choice();
		l1CacheSizeList= new Choice();  //64 KB default
		l1BlockSizeList= new Choice();
		l1PolicyList= new Choice();
		l1ReplacementPolicyList= new Choice();
		mmPolicyList= new Choice();
		brkPointList=new Choice();
		traceSelectList=new Choice();
		urlTxt= new JTextField();
		
		//Build Choice Lists
		//1. Page Size
		pageSizeList.add("2 KB");
		pageSizeList.add("4 KB");
		pageSizeList.add("8 KB");
		
		
		//2. Main Memory Size
		mmSizeList.add("16 MB");
		mmSizeList.add("32 MB");
		mmSizeList.add("64 MB");
		mmSizeList.add("128 MB");
		
		//3. L2 Cache Size
		l2CacheSizeList.add("32 KB");
		l2CacheSizeList.add("64 KB");
		l2CacheSizeList.add("128 KB");
		l2CacheSizeList.add("256 KB");
		l2CacheSizeList.add("512 KB");
		
		//4. L2 Block Size
		l2BlockSizeList.add("512 Bytes");
		l2BlockSizeList.add("256 Bytes");
		l2BlockSizeList.add("128 Bytes");
		
		//5. L2 Cache Policy List
		l2PolicyList.add("DM");
		l2PolicyList.add("FA");
		l2PolicyList.add("SA 2 way");
		l2PolicyList.select("FA");
		
		// L2 replacement Policy List
		l2ReplacementPolicyList.add("FIFO");
		l2ReplacementPolicyList.add("LRU");
		
		//6. L1 Cache Size
		l1CacheSizeList.add("32 KB");
		l1CacheSizeList.add("64 KB");
		l1CacheSizeList.add("128 KB");
		l1CacheSizeList.add("256 KB");
		l1CacheSizeList.add("512 KB");
		
		//7. L1 Block Size
		l1BlockSizeList.add("128 Bytes");
		l1BlockSizeList.add("64 Bytes");
		l1BlockSizeList.add("32 Bytes");
		
		//8. L1 Policy List
		l1PolicyList.add("DM");
		l1PolicyList.add("FA");
		l1PolicyList.add("SA 2 way");
		l1PolicyList.select("FA");
		
		// L1 replacement Policy List
		l1ReplacementPolicyList.add("FIFO");
		l1ReplacementPolicyList.add("LRU");
		
		//9. L1 Policy List
		mmPolicyList.add("FIFO");
		mmPolicyList.add("LRU");
		
		//10. Break Point Events
		brkPointList.add("TLB Hit");
		brkPointList.add("TLB Miss");
		brkPointList.add("PT Hit");		
		brkPointList.add("Page Fault");
		brkPointList.add("L1 Hit");
		brkPointList.add("L1 Miss");
		brkPointList.add("L2 Hit");
		brkPointList.add("L2 Miss");
		brkPointList.add("Step");
		brkPointList.select("TLB Miss");
		
		
		//11. Cache Levels
		cacheLevelList.add("L1 & L2");
		cacheLevelList.add("L1 Unified");
		cacheLevelList.add("L1 I/D");
		
		
		traceSelectList.add("T1(500 Ref.)");
		traceSelectList.add("T2 (5K Ref.)");
		traceSelectList.add("T3 (7K Ref.)");
		traceSelectList.add("T4 (10K Ref.)");
		traceSelectList.select("T2 (5K Ref.)");
		
		
		//Add components to input panel
		
		//Add all the Labels
		inputPanel.add(pageSize);
		pageSize.setBounds(40,10,100,30);
		inputPanel.add(mmSize);
		mmSize.setBounds(40,40,140,30);
		inputPanel.add(tlbSize);
		tlbSize.setBounds(40,70,100,30);
		inputPanel.add(l2CacheSize);
		l2CacheSize.setBounds(40,100,100,30);
		inputPanel.add(l2BlockSize);
		l2BlockSize.setBounds(40,130,100,30);
		inputPanel.add(l2CachePolicy);
		l2CachePolicy.setBounds(40,160,120,30);
		inputPanel.add(l2ReplacementPolicy);
		l2ReplacementPolicy.setBounds(40,190,150,30);
		inputPanel.add(l1CacheSize);
		l1CacheSize.setBounds(40,220,100,30);
		inputPanel.add(l1BlockSize);
		l1BlockSize.setBounds(40,250,100,30);
		inputPanel.add(l1CachePolicy);
		l1CachePolicy.setBounds(40,280,140,30);
		inputPanel.add(mmMapping);
		inputPanel.add(l1ReplacementPolicy);
		l1ReplacementPolicy.setBounds(40,310,150,30);
		mmMapping.setBounds(40,340,150,30);
		inputPanel.add(brkPoint);
		brkPoint.setBounds(40,370,100,30);
		inputPanel.add(cacheLevels);
		cacheLevels.setBounds(40,400,100,30);
		inputPanel.add(traceSelect);
		traceSelect.setBounds(40,430,135,30);
		inputPanel.add(url);
		url.setBounds(40,465,160,20);
		inputPanel.add(lab1);
		lab1.setBounds(40,500,200,20);
		inputPanel.add(lab2);
		lab2.setBounds(40,520,200,20);
		inputPanel.add(lab3);
		lab3.setBounds(40,540,200,20);
		
		
		//Add all the input fields to input Panel
		inputPanel.add(pageSizeList);
		pageSizeList.setBounds(200,10,80,30);
		inputPanel.add(mmSizeList);
		mmSizeList.setBounds(200,40,80,30);
		inputPanel.add(tlbSizeTxt);
		tlbSizeTxt.setBounds(200,70,80,20);
		inputPanel.add(l2CacheSizeList);
		l2CacheSizeList.setBounds(200,100,80,30);
		inputPanel.add(l2BlockSizeList);
		l2BlockSizeList.setBounds(200,130,80,30);
		inputPanel.add(l2PolicyList);
		l2PolicyList.setBounds(200,160,80,30);
		inputPanel.add(l2ReplacementPolicyList);
		l2ReplacementPolicyList.setBounds(200,190,80,30);
		inputPanel.add(l1CacheSizeList);
		l1CacheSizeList.setBounds(200,220,80,30);
		inputPanel.add(l1BlockSizeList);
		l1BlockSizeList.setBounds(200,250,80,30);
		inputPanel.add(l1PolicyList);
		l1PolicyList.setBounds(200,280,80,30);
		inputPanel.add(l1ReplacementPolicyList);
		l1ReplacementPolicyList.setBounds(200,310,80,30);		
		inputPanel.add(mmPolicyList);
		mmPolicyList.setBounds(200,340,80,30);
		inputPanel.add(brkPointList);
		brkPointList.setBounds(200,370,80,30);
		inputPanel.add(cacheLevelList);
		cacheLevelList.setBounds(200,400,80,30);
		inputPanel.add(traceSelectList);
		traceSelectList.setBounds(200,435,80,30);
		inputPanel.add(urlTxt);
		urlTxt.setBounds(200,465,80,20);
		
		
		getContentPane().add(inputPanel);
		inputPanel.setBounds(-30, 40, 300, 570);
		
		
		//Header Panel Layout
		hdrPanel=new JPanel();
		hdrPanel.setLayout(null);
		hdrPanel.setBackground(new Color(13,77,115));
		hdrPanel.setBorder(new javax.swing.border.EtchedBorder());
		JLabel hdrLabel=new JLabel("Real Time Memory Simulator");
		//hdrLabel.setBounds(80, 10, 300, 20);
		hdrLabel.setBounds(500, 10, 300, 20);
		hdrLabel.setFont(new Font("Terbuchet MS", 1, 15));
		//hdrLabel.setForeground(new java.awt.Color(204, 204, 255));
		hdrLabel.setForeground(Color.orange);
		hdrPanel.add(hdrLabel);
		//hdrPanel.setBounds(270, 0, 330, 40);
		hdrPanel.setBounds(0, 0, 1150, 40);
		getContentPane().add(hdrPanel);
		
		
		// Button Panel
		buttonPanel= new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(new Color(0, 0, 0));
		buttonPanel.setBorder(new EtchedBorder());
		buttonPanel.setForeground(new Color(0, 0, 0));
		
		JButton startButton = new JButton();
		startButton.setText("Initialize");
		buttonPanel.add(startButton);
		startButton.setBounds(0, 0, 135, 40);
		startButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) 
			{
				startButtonActionPerformed(evt);
			}
		}
		);
		
		JButton runButton = new JButton();
		runButton.setText("Complete Run");
		buttonPanel.add(runButton);
		runButton.setBounds(135, 0, 135, 40);
		runButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) 
			{
				fullRunButtonActionPerformed(evt);
			}
		}
		);
		
		
		JButton clearButton = new JButton();
		clearButton.setText("Clear");
		buttonPanel.add(clearButton);
		clearButton.setBounds(0, 40, 135, 40);
		clearButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) 
			{
				clearButtonActionPerformed(evt);
			}
		}
		);
		
		JButton partRunButton = new JButton();
		partRunButton.setText("Run upto Break Point");
		buttonPanel.add(partRunButton);
		partRunButton.setBounds(135, 40, 135, 40);
		partRunButton.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		partRunButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) 
			{
				partRunButtonActionPerformed(evt);
			}
		}
		);
		
		getContentPane().add(buttonPanel);
		buttonPanel.setBounds(0, 610,270, 80);
		
		
		// Output Panel
		outputPanel= new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBorder(new EtchedBorder());
		outputPanel.setBackground(new Color(134,166,141));
		
		JLabel lbOutTxt=new JLabel("Simulation Results");
		lbOutTxt.setHorizontalAlignment(JLabel.CENTER);
		lbOutTxt.setForeground(Color.black);
		lbOutTxt.setBackground(new Color(13,77,115));
		lbOutTxt.setBorder(new EtchedBorder());
		lbOutTxt.setBounds(0,0,440,30);
		outputPanel.add(lbOutTxt);
		
		JLabel label1=new JLabel("TLB Access Time ");
		JLabel label2=new JLabel("Page Table Access Time ");
		JLabel label3=new JLabel("L1 Access Time ");
		JLabel label4=new JLabel("L2 Access Time ");
		JLabel label5=new JLabel("Main Memory Access Time ");
		JLabel label6=new JLabel("Virtual Memory Access Time(in K)");
		JLabel label7=new JLabel("All values are in Clock Cycle units");
		
		label1Txt= new JTextField(Integer.toString(1)); 
		label2Txt= new JTextField(Integer.toString(5)); 
		label3Txt= new JTextField(Integer.toString(5)); 
		label4Txt= new JTextField(Integer.toString(10)); 
		label5Txt= new JTextField(Integer.toString(100)); 
		label6Txt= new JTextField(Integer.toString(1000)); 
		
	
		outputText = new JTextArea();
		outputText.setBackground(new Color(255, 255, 204));
		outputText.setEditable(false);
		JScrollPane outputPane = new JScrollPane(outputText);
		outputPane.setBounds(0, 30, 440, 160);
		outputPane.setBorder(new EtchedBorder());
		outputText.setFont(new java.awt.Font("Terbuchet MS", 1, 12));
		
		JLabel lbDefTxt=new JLabel("Simulation Constants");
		lbDefTxt.setHorizontalAlignment(JLabel.CENTER);
		lbDefTxt.setForeground(Color.black);
		lbDefTxt.setBackground(new Color(13,77,115));
		lbDefTxt.setBorder(new EtchedBorder());
		lbDefTxt.setBounds(440,0,440,30);
		outputPanel.add(lbDefTxt);
		
		outputPanel.add(label1);
		label1.setBounds(450,40,150,15);
		outputPanel.add(label2);
		label2.setBounds(450,60,150,15);
		outputPanel.add(label3);
		label3.setBounds(450,80,150,15);
		outputPanel.add(label4);
		label4.setBounds(450,100,150,15);
		outputPanel.add(label5);
		label5.setBounds(450,120,200,15);
		outputPanel.add(label6);
		label6.setBounds(450,140,200,15);
		outputPanel.add(label7);
		label7.setBounds(450,170,200,15);
		
		outputPanel.add(label1Txt);
		label1Txt.setBounds(650,40,50,15);
		outputPanel.add(label2Txt);
		label2Txt.setBounds(650,60,50,15);
		outputPanel.add(label3Txt);
		label3Txt.setBounds(650,80,50,15);
		outputPanel.add(label4Txt);
		label4Txt.setBounds(650,100,50,15);
		outputPanel.add(label5Txt);
		label5Txt.setBounds(650,120,50,15);
		outputPanel.add(label6Txt);
		label6Txt.setBounds(650,140,50,15);
		
		defaultText = new JTextArea();
		defaultText.setBackground(new Color(255, 255, 204));
		defaultText.setEditable(false);
		JScrollPane defaultPane = new JScrollPane(defaultText);
		defaultPane.setBounds(440, 30, 440, 160);
		defaultPane.setBorder(new EtchedBorder());
		defaultText.setFont(new java.awt.Font("Terbuchet MS", 1, 12));
//		defaultText.setText(getConstants());
		
		outputPanel.add(outputPane);
		outputPanel.add(defaultPane);
		outputText.setToolTipText("OUTPUT AREA");
		getContentPane().add(outputPanel);
		outputPanel.setBounds(270, 500, 880, 190);
		
		
	}

	
	//create all the tables	
	
	private void createTables()
	{
		//		Panel for Tables
		tblPanel= new JPanel();
		memRefTbl = new JTable();			
		tblPanel.setLayout(null);
		//tblPanel.setBackground(new Color(0, 0, 153));
		tblPanel.setBackground(new Color(134,166,141));
		tblPanel.setBorder(new EtchedBorder());
		
		//values
		
		/** Memory Reference Table **/
		memRefTbl.setBackground(new Color(204, 255, 204));
		memRefTbl.setBorder(new EtchedBorder());			
		String[] memRefColHeads = { "Word", "Page", " I/D" };
		memRefTabModel=new DefaultTableModel();
		memRefTbl=new JTable(memRefTabModel);
		memRefTbl.setEnabled(false);
		memRefTabModel.addColumn("Mem Ref");
		memRefTabModel.addColumn("Page");
		memRefTabModel.addColumn("I/D");
		colorRenderer = new ColorRenderer(memRefTbl);
		memRefTbl.setDefaultRenderer(Object.class, colorRenderer);
		JScrollPane memRefJsp = new JScrollPane(memRefTbl);
		memRefJsp.setBounds(0, 30, 220, 430);
		tblPanel.add(memRefJsp);
		//memRefTbl.setToolTipText("Memory References");
		
		JLabel lbMemRefTab=new JLabel("Memory Reference Table");
		lbMemRefTab.setHorizontalAlignment(JLabel.CENTER);
		lbMemRefTab.setForeground(Color.black);
		lbMemRefTab.setBackground(new Color(13,77,115));
		lbMemRefTab.setBorder(new EtchedBorder());
		lbMemRefTab.setBounds(0,0,220,30);
		tblPanel.add(lbMemRefTab);
		
		/** TLB Table**/ 
		tlbTbl = new JTable();
		tlbTbl.setBackground(new Color(204, 255, 204));
		tlbTbl.setBorder(new EtchedBorder());
		String[] tlbColHeads = {"Index", "VPN" , "PPN"};
		String tlbData[][] = new String[tlb_ob.no_entries][3] ;
		for (int j =0; j< tlb_ob.no_entries ; j++)
		{
			tlbData[j][0]=null;
			tlbData[j][1]=null;	
			tlbData[j][2]=null;	
		}		
		tlbTbl = new JTable(tlbData, tlbColHeads);
		tlbTbl.setEnabled(false);
		tlbJsp = new JScrollPane(tlbTbl);
		tlbJsp.setBounds(220, 30, 220, 200);
		tblPanel.add(tlbJsp);
		//tlbTbl.setToolTipText("Translation Look Aside Buffer");
		
		JLabel lbTlbTab=new JLabel("TLB");
		lbTlbTab.setHorizontalAlignment(JLabel.CENTER);
		lbTlbTab.setBackground(new Color(13,77,115));
		lbTlbTab.setBorder(new EtchedBorder());
		lbTlbTab.setForeground(Color.black);
		lbTlbTab.setBounds(220,0,220,30);
		tblPanel.add(lbTlbTab);

	
		/** Page Table **/
		pgTabModel = new DefaultTableModel();
		pgTbl = new JTable(pgTabModel);
		pgTbl.setBackground(new Color(204, 255, 204));
		pgTbl.setBorder(new EtchedBorder());
		pgTabModel.addColumn("Index");
		pgTabModel.addColumn("VPN");
		pgTabModel.addColumn("PPN");
		pgTbl.setEnabled(false);
		ptJsp = new JScrollPane(pgTbl);
		ptJsp.setBounds(220, 260, 220, 200);
		tblPanel.add(ptJsp);
		//pgTbl.setToolTipText("Page Table");
		
		JLabel lbPgTab=new JLabel("Page Table");
		lbPgTab.setHorizontalAlignment(JLabel.CENTER);
		lbPgTab.setBackground(new Color(13,77,115));
		lbPgTab.setBorder(new EtchedBorder());
		lbPgTab.setForeground(Color.black);
		lbPgTab.setBounds(220,230,220,30);
		tblPanel.add(lbPgTab);
		
		/** L1 Instruction Cache Table **/
		l1iTbl = new JTable();
		l1iTbl.setBackground(new Color(204, 255, 204));
		l1iTbl.setBorder(new EtchedBorder());
		String[] l1iColHeads = {"Index", "Tag", "Valid","LRU Ct"};
		String l1iData[][] = new String[cache_l1_i_ob.no_entries][4] ;
		
		for (int j =0; j< cache_l1_i_ob.no_entries ; j++)
		{
			l1iData[j][0]=null;
			l1iData[j][1]=null;	
			l1iData[j][2]=null;
			l1iData[j][3]=null;
		}
		
		l1iTbl = new JTable(l1iData, l1iColHeads);
		l1iTbl.setEnabled(false);
		l1iJsp = new JScrollPane(l1iTbl);
		l1iJsp.setBounds(440, 30, 220, 200);
		tblPanel.add(l1iJsp);
		//l1iTbl.setToolTipText("L1 Instruction Cache");
		
		JLabel lbL1ICacheTab=new JLabel("L1 Instruction Cache");
		lbL1ICacheTab.setHorizontalAlignment(JLabel.CENTER);
		lbL1ICacheTab.setBackground(new Color(13,77,115));
		lbL1ICacheTab.setBorder(new EtchedBorder());
		lbL1ICacheTab.setForeground(Color.black);
		lbL1ICacheTab.setBounds(440,0,220,30);
		tblPanel.add(lbL1ICacheTab);
		
		/** L1 Data Cache Table **/
		l1dTbl = new JTable();
		l1dTbl.setBackground(new Color(204, 255, 204));
		l1dTbl.setBorder(new EtchedBorder());
		String[] l1dColHeads = {"Index", "Tag","Valid","LRU Ct"};
		String l1dData[][] = new String[cache_l1_d_ob.no_entries][4] ;
		
		for (int j =0; j< cache_l1_d_ob.no_entries ; j++)
		{
			l1dData[j][0]=null;
			l1dData[j][1]=null;	
			l1dData[j][2]=null;
			l1dData[j][3]=null;
		}
		
		l1dTbl = new JTable(l1dData, l1dColHeads);
		l1dTbl.setEnabled(false);
		l1dJsp = new JScrollPane(l1dTbl);
		l1dJsp.setBounds(440, 260, 220, 200);
		tblPanel.add(l1dJsp);
		//l1dTbl.setToolTipText("L1 Data Cache");
		
		JLabel lbL1DCacheTab=new JLabel("L1 Data Cache");
		lbL1DCacheTab.setHorizontalAlignment(JLabel.CENTER);
		lbL1DCacheTab.setBackground(new Color(13,77,115));
		lbL1DCacheTab.setBorder(new EtchedBorder());
		lbL1DCacheTab.setForeground(Color.black);
		lbL1DCacheTab.setBounds(440,230,220,30);
		tblPanel.add(lbL1DCacheTab);
		
		/** L2 Cache Table **/
		l2Tbl = new JTable();
		l2Tbl.setBackground(new Color(204, 255, 204));
		l2Tbl.setBorder(new EtchedBorder());
		String[] l2ColHeads = {"Index", "Tag","Valid","LRU Ct"};
		String l2Data[][] = new String[cache_l2_ob.no_entries][4] ;
		
		for (int j =0; j< cache_l2_ob.no_entries ; j++)
		{
			l2Data[j][0]=null;
			l2Data[j][1]=null;	
			l2Data[j][2]=null;
			l2Data[j][3]=null;
		}
		
		l2Tbl = new JTable(l2Data, l2ColHeads);
		l2Tbl.setEnabled(false);
		l2Jsp = new JScrollPane(l2Tbl);
		l2Jsp.setBounds(660, 30, 220, 200);
		tblPanel.add(l2Jsp);
		//l2Tbl.setToolTipText("L2 Cache");
		
		JLabel lbL2CacheTab=new JLabel("L2 Cache");
		lbL2CacheTab.setHorizontalAlignment(JLabel.CENTER);
		lbL2CacheTab.setBackground(new Color(13,77,115));
		lbL2CacheTab.setBorder(new EtchedBorder());
		lbL2CacheTab.setForeground(Color.black);
		lbL2CacheTab.setBounds(660,0,220,30);
		tblPanel.add(lbL2CacheTab);
		
		/** Main Memory Table **/
		mmTbl = new JTable();
		mmTbl.setBackground(new Color(204, 255, 204));
		mmTbl.setBorder(new EtchedBorder());
		String[] mmColHeads = {"Frame", "Page","Valid","LRU Ct"};
		String mmData[][] = new String[main_mem_ob.no_frames][4] ;
		
		for (int j =0; j< main_mem_ob.no_frames ; j++)
		{
			mmData[j][0]=null;
			mmData[j][1]=null;	
			mmData[j][2]=null;
			mmData[j][3]=null;
		}
		
		mmTbl = new JTable(mmData, mmColHeads);
		mmTbl.setEnabled(false);
		mmJsp = new JScrollPane(mmTbl);
		mmJsp.setBounds(660, 260, 220, 200);
		tblPanel.add(mmJsp);
		//mmTbl.setToolTipText("Main Memory");
		
		JLabel lbMMTab=new JLabel("Main Memory");
		lbMMTab.setHorizontalAlignment(JLabel.CENTER);
		lbMMTab.setBackground(new Color(13,77,115));
		lbMMTab.setBorder(new EtchedBorder());
		lbMMTab.setForeground(Color.black);
		lbMMTab.setBounds(660,230,220,30);
		tblPanel.add(lbMMTab);
		
		getContentPane().add(tblPanel);
		tblPanel.setBounds(270, 40, 880, 460);
		tblPanel.setVisible(true);	
	}
	
	
	
	//initialize only memory reference table
	
	
	private void initTables()
	{
		URL url=null;
		String line,refType,memRef,type=null;
		int ct=0,valRefType,d;
		
		String urlValue=null;
		
		if(traceUrl.length() > 4)
		{
			urlValue=traceUrl;
		}
		else
		{
			if(traceSelect==0)urlValue="http://www.ecs.umass.edu/ece/koren/architecture/CacheTLB/trace1.txt";
			if(traceSelect==1)urlValue="http://www.ecs.umass.edu/ece/koren/architecture/CacheTLB/trace2.txt";
			if(traceSelect==2)urlValue="http://www.ecs.umass.edu/ece/koren/architecture/CacheTLB/trace3.txt";
			if(traceSelect==3)urlValue="http://www.ecs.umass.edu/ece/koren/architecture/CacheTLB/trace4.txt";
			
		}
		
		
		if(memRefTabModel.getRowCount()==0)
		{
			try { 
				
				    
					url=new URL(urlValue);
					System.out.println("URL: "+urlValue);
					//System.out.println(getCodeBase());
					//url = new URL (getCodeBase(), FileToRead ); 
					
				}
			 
			catch (MalformedURLException  e ) { 
				outputText.setText("Malformed URL: "+urlValue+"\nTaking Trace 1 Instead"); 
				urlValue="http://www.ecs.umass.edu/ece/koren/architecture/CacheTLB/trace2.txt";
				try {
					url=new URL(urlValue);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//stop(); 
			} 
			
			try { 
				InputStream in=url.openStream(); 
				BufferedReader dis = 
					new BufferedReader(new InputStreamReader(in)); 
				int count=1;
				while ((line = dis.readLine()) != null){ 
					ct++;
					StringTokenizer st = new StringTokenizer(line);
					if(ct>1)
					{
						refType=st.nextToken(); //type of ref
						valRefType=Integer.parseInt(refType);
						memRef=st.nextToken(); //memory address
						if(valRefType==3)type=" IF";
						if(valRefType==2)type=" DW";
						if(valRefType==1)type=" DR";
						/* memRefTbl.setValueAt(Integer.toString(count++),ct-2,0);
						 memRefTbl.setValueAt(b,ct-2,1);
						 memRefTbl.setValueAt(type,ct-2,2);	*/
						memRefTabModel.addRow(new Object[] {memRef,Integer.toString(Integer.parseInt(memRef)/pg_sz),type});
						//	          System.out.println("Rows: "+memRefTbl.getRowCount());
					}
				}
				in.close(); 
			} 
			catch (IOException e ) {} 
		}
	}
	
	
	// Actions performed by buttons
	
	
	private void startButtonActionPerformed(ActionEvent evt) {
		
		
		initData();
		createTables();
		initTables();
		
		
	}
	
	
	// complete execution
	
	private void fullRunButtonActionPerformed(ActionEvent evt)
	{
		time=0;
		String line,a,b,type=null;
		int ct=0;
		int memRef=0;
		String refType;
		row=0;
		int numRows=memRefTbl.getRowCount();
		
		while (row<numRows)		// memory reference left
		{		
			ct++;
			time=0;
			refType=memRefTbl.getValueAt(row,2).toString();
			memRef=Integer.parseInt(memRefTbl.getValueAt(row,0).toString());
			
			if(refType==" IF"){l1_val=1;instCount++;}
			if(refType==" DR"){l1_val=2;dataCount++;}
			if(refType==" DW"){l1_val=2;dataCount++;}
			
			
			System.out.println(memRef);
			
			// simulating memory with the generated memory reference d
			
			//				VM - MM interface
			
			
			pg_no=(int)(memRef/pg_sz);
			pg_off=(int)(memRef%pg_sz);
			
			
			
			System.out.println("\n\nStatistics for memory ref " + pg_no +"  " + pg_off);
			
			
			// check whether the pg_no is there in TLB
			frame_no= tlb_ob.check_entry(pg_no);
			
			if(frame_no !=-1 ){ System.out.println("TLB hit");time+=t_tlb;tlbHits++;}
			else
			{
				time+=t_pt;		// page table access delay. tlb access and page table access are done in parallel
				System.out.println("TLB miss");tlbMiss++;
				frame_no= pg_tbl_ob.check_entry(pg_no);		//check page table for pg_no
				if(frame_no !=-1 ) {System.out.println("Page Table hit");pgHits++;}
				else{
					System.out.println("Page Table Miss. Page Fault.");		// Page Fault
					time+=t_vm;
					pgFaults++;
					// Replacement algorithms for MM
					int temp=0;
					if(mm_replacement==1)		// FIFO replacement scheme
					{
						temp=main_mem_ob.isfull();		
					}
					
					if(mm_replacement==2)		// LRU replacement scheme
					{
						temp=main_mem_ob.isfull_lru();
					}
					
					if(temp!=-1)			// MM is full and a page has to be replaced acc to algo
					{
						// have to replace page
						pg_tbl_ob.rem_entry(temp);			// removing the prev page's page tbl entry
						tlb_ob.rem_entry(temp);				// removing the prev page's tlb entry
						cache_l1_i_ob.update(temp);			// update the caches so that they do not 
						cache_l1_d_ob.update(temp);			// contain references to the replaced page
						cache_l2_ob.update(temp);
						frame_no=main_mem_ob.add_entry(pg_no,mm_replacement);
						pg_tbl_ob.add_entry(pg_no,frame_no);
						tlb_ob.add_entry(pg_no,frame_no);
						
					}
					
					else					// MM is not full
					{
						// no need to replace. just add entries in page table and tlb
						frame_no=main_mem_ob.add_entry(pg_no,mm_replacement);
						pg_tbl_ob.add_entry(pg_no,frame_no);
						tlb_ob.add_entry(pg_no,frame_no);
					}
				}
			}
			
			System.out.println("Page "+ pg_no + " is in MM frame no.:"+ frame_no);
			
			
			
			//		MM - Caches interface
			
			
			int pa=frame_no*pg_sz+pg_off;			// calculating physical address
			int temp_l1=0;							// takes value depending on whether it is I or D
			
			System.out.println("PA  " +pa);
			// block no. and block offset corresponding to L1 in MM
			blk_no_l1=pa/l1_blk_sz;
			blk_off_l1=pa%l1_blk_sz;
			
			// block no. and block offset corresponging to L2 in MM
			blk_no_l2=pa/l2_blk_sz;
			blk_off_l2=pa%l2_blk_sz;
			
			
			// for 2 level caching
			if(cachelevelch==0)
			{
				if(l1_val==1)
					temp_l1=cache_l1_i_ob.check_entry(blk_no_l1);
				else
					temp_l1=cache_l1_d_ob.check_entry(blk_no_l1);
				
				
				
				if(temp_l1==1)	// check L1 cahce
				{
					time+=t_l1;
					if(l1_val==1)
					{System.out.println("L1_I Cache Hit");l1iHits++;}
					else
					{System.out.println("L1-D Cache Hit");l1dHits++;}
					
				}
				else
				{
					if(l1_val==1)
					{System.out.println("L1_I Cache Miss");l1iMiss++;}
					else
					{System.out.println("L1-D Cache Miss");l1dMiss++;}
					
					if ( cache_l2_ob.check_entry(blk_no_l2) ==1 )	// Check L2 cahce
					{
						System.out.println("L2 Cache Hit");
						time+=t_l1+t_l2;
						l2Hits++;
						if(l1_val==1)
							cache_l1_i_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);  // add in L1
						else
							cache_l1_d_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);  // add in L1
						System.out.println("Tag added  to L1" + blk_no_l1);
						
						
					}
					
					else 
					{
						System.out.println("L2 Cache Miss");		// not avlb in L1 and L2. get from MM
						time+=t_l1+t_l2+t_mm;
						l2Miss++;
						if(mm_replacement ==2)
							main_mem_ob.inc_count(frame_no);		// increment lru count for MM
						
						int temp=0;				// takes value depending on the mapping of L2
						if(l2_map==1)temp=cache_l2_ob.isconflict(blk_no_l2);	// direct mapped
						else if(l2_map==2)temp=cache_l2_ob.isfull();			// fifo
						else if(l2_map==3)temp=cache_l2_ob.isfull_lru();		//lru	
						
						
						if(temp!=-1 )	// l2 is full.A block in l2 will be replaced acc to algo
						{
							//update l1 so that it does not contain any references to the replaced block of 1l
							if(l1_val==1)
								cache_l1_i_ob.update_l1(temp);
							else
								cache_l1_d_ob.update_l1(temp);
							
							
							cache_l2_ob.add_entry(blk_no_l2,l2_map,pg_no);
							System.out.println("Tag added  to L2" + blk_no_l2);
							
							if(l1_val==1)
								cache_l1_i_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
							else
								cache_l1_d_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
							System.out.println("Tag added  to L1" + blk_no_l1);
							
						}
						
						
						else			//no replacement.just adding entry
						{
							cache_l2_ob.add_entry(blk_no_l2,l2_map,pg_no);
							System.out.println("Tag added  to L2" + blk_no_l2);
							if(l1_val==1)
								cache_l1_i_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
							else
								cache_l1_d_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
							System.out.println("Tag added  to L1" + blk_no_l1);
							
						}
					}
				}
				
				
				
			}		// end of 2 level caching
			
			
			
			// for one level unified caching
			if(cachelevelch==1)
			{
				
				
				if ( cache_l2_ob.check_entry(blk_no_l2) ==1 )	// Check L1 Unified cahce
				{
					System.out.println("L2 Cache Hit");
					time+=t_l2;
					l2Hits++;
				}
				
				else 
				{
					System.out.println("L2 Cache Miss");		// not avlb in L1 . get from MM
					time+=t_l2+t_mm;
					l2Miss++;
					if(mm_replacement ==2)
						main_mem_ob.inc_count(frame_no);		// increment lru count for MM
					
					int temp=0;				// takes value depending on the mapping of L1 unified
					if(l2_map==1)temp=cache_l2_ob.isconflict(blk_no_l2);	// direct mapped
					else if(l2_map==2)temp=cache_l2_ob.isfull();			// fifo
					else if(l2_map==3)temp=cache_l2_ob.isfull_lru();		//lru	
					
					
					if(temp!=-1 )	// l2 is full.A block in l2 will be replaced acc to algo
					{
						
						
						cache_l2_ob.add_entry(blk_no_l2,l2_map,pg_no);
						System.out.println("Tag added  to L1 Unified" + blk_no_l2);
					}
					
					
					else			//no replacement.just adding entry
					{
						cache_l2_ob.add_entry(blk_no_l2,l2_map,pg_no);
						System.out.println("Tag added  to L2" + blk_no_l2);
						
					}
				}
				
			}
			
			
			//  one level seperate caches
			if(cachelevelch==2)
			{
				
				
				if(l1_val==1)
					temp_l1=cache_l1_i_ob.check_entry(blk_no_l1);
				else
					temp_l1=cache_l1_d_ob.check_entry(blk_no_l1);
				
				
				
				if(temp_l1==1)	// check L1 cahce
				{
					time+=t_l1;
					if(l1_val==1)
					{System.out.println("L1_I Cache Hit");l1iHits++;}
					else
					{System.out.println("L1-D Cache Hit");l1dHits++;}
					
				}
				else
				{
					if(l1_val==1)
					{System.out.println("L1_I Cache Miss");l1iMiss++;}
					else
					{System.out.println("L1-D Cache Miss");l1dMiss++;}
					
					// not available in cache . get from MM
					
					time+=t_l1+t_mm;
					if(mm_replacement ==2)
						main_mem_ob.inc_count(frame_no);		// increment lru count for MM
					
					if(l1_val==1)
						cache_l1_i_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
					else
						cache_l1_d_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
					System.out.println("Tag added  to L1" + blk_no_l1);
				}
			}//	end of one level seperate caching
			
			
			System.out.println("Time Taken : " + time);
			tat+=time;
			avgmat=tat/ct;
			System.out.println("AMAT in clock cycle time: " + avgmat );
			row++;
			
		} 
		
		// display tables
		
		String temp;
		for(int i=0;i<tlb_ob.no_entries;i++)
		{
			temp=Integer.toString(i);
			tlbTbl.setValueAt(temp,i,0);
			temp=Integer.toString(tlb_ob.pg_no[i]);
			tlbTbl.setValueAt(temp,i,1);
			temp=Integer.toString(tlb_ob.frame_no[i]);
			tlbTbl.setValueAt(temp,i,2);
		}
		
		for(int i=0;i<cache_l2_ob.no_entries;i++)
		{
			temp=Integer.toString(i);
			l2Tbl.setValueAt(temp,i,0);
			temp=Integer.toString(cache_l2_ob.tag[i]);
			l2Tbl.setValueAt(temp,i,1);
			temp=Integer.toString(cache_l2_ob.valid[i]);
			l2Tbl.setValueAt(temp,i,2);
			temp=Integer.toString(cache_l2_ob.lru_ct[i]);
			l2Tbl.setValueAt(temp,i,3);
		}
		
		for(int i=0;i<cache_l1_i_ob.no_entries;i++)
		{
			temp=Integer.toString(i);
			l1iTbl.setValueAt(temp,i,0);
			temp=Integer.toString(cache_l1_i_ob.tag[i]);
			l1iTbl.setValueAt(temp,i,1);
			temp=Integer.toString(cache_l1_i_ob.valid[i]);
			l1iTbl.setValueAt(temp,i,2);
			temp=Integer.toString(cache_l1_i_ob.lru_ct[i]);
			l1iTbl.setValueAt(temp,i,3);
		}
		
		for(int i=0;i<cache_l1_d_ob.no_entries;i++)
		{
			temp=Integer.toString(i);
			l1dTbl.setValueAt(temp,i,0);
			temp=Integer.toString(cache_l1_d_ob.tag[i]);
			l1dTbl.setValueAt(temp,i,1);
			temp=Integer.toString(cache_l1_d_ob.valid[i]);
			l1dTbl.setValueAt(temp,i,2);
			temp=Integer.toString(cache_l1_d_ob.lru_ct[i]);
			l1dTbl.setValueAt(temp,i,3);
		}
		
		for(int i=0;i<main_mem_ob.no_frames;i++)
		{
			temp=Integer.toString(i);
			mmTbl.setValueAt(temp,i,0);
			temp=Integer.toString(main_mem_ob.pg_stored[i]);
			mmTbl.setValueAt(temp,i,1);
			temp=Integer.toString(main_mem_ob.valid[i]);
			mmTbl.setValueAt(temp,i,2);
			temp=Integer.toString(main_mem_ob.lru_ct[i]);
			mmTbl.setValueAt(temp,i,3);
		}
		
		Object [] keys=pg_tbl_ob.hm.keySet().toArray();
		for (int i=0;i< keys.length;i++)
		{
			int val=pg_tbl_ob.check_entry(Integer.parseInt(keys[i].toString()));
			pgTabModel.addRow(new Object[]{Integer.toString(i),keys[i].toString(),Integer.toString(val)});
		}
		
		// display output
		printStats();
		
	}
	
	
	//	run till one of the following break points
	
	// define events
	//	0=>	TLB Hit
	//	1=> TLB Miss
	//	2=> Page Table Hit
	//	3=> Page Fault
	//	4=> L1 Hit
	// 	5=>	L1 Miss
	//	6=>	L2 Hit
	//	7=>	L2 Miss
	//	8=> Step
	
	
	private void partRunButtonActionPerformed(ActionEvent evt)
	{
		int event[]=new int [9];
		for(int i=0;i<9;i++)event[i]=0;
		time=0;
		String type=null;
		int memRef=0;
		String refType;
		int numRows=memRefTbl.getRowCount();
		
		partBtnPressed=true;
		while (row<numRows && event[brkpt]!=1)		// memory reference left
		{		
			if(row!=0)
			{
				colorRenderer.setRowColor(row-1, Color.white);
				colorRenderer.stopBlinking();
			}
			//for(int i=0;i<8;i++)event[i]=0;
			time=0;
			refType=memRefTbl.getValueAt(row,2).toString();
			memRef=Integer.parseInt(memRefTbl.getValueAt(row,0).toString());
			
			if(refType==" IF"){l1_val=1;instCount++;}
			if(refType==" DR"){l1_val=2;dataCount++;}
			if(refType==" DW"){l1_val=2;dataCount++;}

			System.out.println(memRef);
			
			// simulating memory with the generated memory reference d
			
			//				VM - MM interface
			
			
			pg_no=(int)(memRef/pg_sz);
			pg_off=(int)(memRef%pg_sz);
			
			
			System.out.println("mem ref " + l1_val );
			System.out.println("Statistics for memory ref " + pg_no +"  " + pg_off);
			
			
			// check whether the pg_no is there in TLB
			frame_no= tlb_ob.check_entry(pg_no);
			
			if(frame_no !=-1 ){ System.out.println("TLB hit");time+=t_tlb;event[0]=1;tlbHits++;}
			else
			{
				time+=t_pt;
				System.out.println("TLB miss");event[1]=1;tlbMiss++;
				frame_no= pg_tbl_ob.check_entry(pg_no);		//check page table for pg_no
				if(frame_no !=-1 ) {System.out.println("Page Table hit");event[2]=1;pgHits++;}
				else{
					System.out.println("Page Table Miss. Page Fault.");		// Page Fault
					time+=t_vm;
					event[3]=1;
					pgFaults++;
					// Replacement algorithms for MM
					int temp=0;
					if(mm_replacement==1)		// FIFO replacement scheme
					{
						temp=main_mem_ob.isfull();		
					}
					
					if(mm_replacement==2)		// LRU replacement scheme
					{
						temp=main_mem_ob.isfull_lru();
					}
					
					if(temp!=-1)			// MM is full and a page has to be replaced acc to algo
					{
						// have to replace page
						pg_tbl_ob.rem_entry(temp);			// removing the prev page's page tbl entry
						tlb_ob.rem_entry(temp);				// removing the prev page's tlb entry
						cache_l1_i_ob.update(temp);			// update the caches so that they do not 
						cache_l1_d_ob.update(temp);			// contain references to the replaced page
						cache_l2_ob.update(temp);
						frame_no=main_mem_ob.add_entry(pg_no,mm_replacement);
						pg_tbl_ob.add_entry(pg_no,frame_no);
						tlb_ob.add_entry(pg_no,frame_no);
						
					}
					
					else					// MM is not full
					{
						// no need to replace. just add entries in page table and tlb
						frame_no=main_mem_ob.add_entry(pg_no,mm_replacement);
						pg_tbl_ob.add_entry(pg_no,frame_no);
						tlb_ob.add_entry(pg_no,frame_no);
					}
				}
			}
			
			System.out.println("Page "+ pg_no + " is in MM frame no.:"+ frame_no);
			
			
			
			//		MM - Caches interface
			
			
			int pa=frame_no*pg_sz+pg_off;			// calculating physical address
			int temp_l1=0;							// takes value depending on whether it is I or D
			
			System.out.println("PA  " +pa);
			// block no. and block offset corresponding to L1 in MM
			blk_no_l1=pa/l1_blk_sz;
			blk_off_l1=pa%l1_blk_sz;
			
			
			// block no. and block offset corresponging to L2 in MM
			blk_no_l2=pa/l2_blk_sz;
			blk_off_l2=pa%l2_blk_sz;
			
			
			
			// for 2 level caching
			if(cachelevelch==0)
			{
				if(l1_val==1)
					temp_l1=cache_l1_i_ob.check_entry(blk_no_l1);
				else
					temp_l1=cache_l1_d_ob.check_entry(blk_no_l1);
				
				
				
				if(temp_l1==1)	// check L1 cahce
				{
					time+=t_l1;
					if(l1_val==1)
					{System.out.println("L1_I Cache Hit");l1iHits++;event[4]=1;}
					else
					{System.out.println("L1-D Cache Hit");l1dHits++;event[4]=1;}
					
				}
				else
				{
					if(l1_val==1)
					{System.out.println("L1_I Cache Miss");l1iMiss++;event[5]=1;}
					else
					{System.out.println("L1-D Cache Miss");l1dMiss++;event[5]=1;}
					
					
					
					
					
					if ( cache_l2_ob.check_entry(blk_no_l2) ==1 )	// Check L2 cahce
					{
						System.out.println("L2 Cache Hit");
						time+=t_l1+t_l2;
						l2Hits++;
						event[6]=1;
						if(l1_val==1)
							cache_l1_i_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);  // add in L1
						else
							cache_l1_d_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);  // add in L1
						System.out.println("Tag added  to L1" + blk_no_l1);
						
						
					}
					
					else 
					{
						System.out.println("L2 Cache Miss");		// not avlb in L1 and L2. get from MM
						time+=t_l1+t_l2+t_mm;
						l2Miss++;
						event[7]=1;
						if(mm_replacement ==2)
							main_mem_ob.inc_count(frame_no);		// increment lru count for MM
						
						int temp=0;				// takes value depending on the mapping of L2
						if(l2_map==1)temp=cache_l2_ob.isconflict(blk_no_l2);	// direct mapped
						else if(l2_map==2)temp=cache_l2_ob.isfull();			// fifo
						else if(l2_map==3)temp=cache_l2_ob.isfull_lru();		//lru	
						
						
						if(temp!=-1 )	// l2 is full.A block in l2 will be replaced acc to algo
						{
							//update l1 so that it does not contain any references to the replaced block of 1l
							if(l1_val==1)
								cache_l1_i_ob.update_l1(temp);
							else
								cache_l1_d_ob.update_l1(temp);
							
							
							cache_l2_ob.add_entry(blk_no_l2,l2_map,pg_no);
							System.out.println("Tag added  to L2" + blk_no_l2);
							
							if(l1_val==1)
								cache_l1_i_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
							else
								cache_l1_d_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
							System.out.println("Tag added  to L1" + blk_no_l1);
							
						}
						
						
						else			//no replacement.just adding entry
						{
							cache_l2_ob.add_entry(blk_no_l2,l2_map,pg_no);
							System.out.println("Tag added  to L2" + blk_no_l2);
							if(l1_val==1)
								cache_l1_i_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
							else
								cache_l1_d_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
							System.out.println("Tag added  to L1" + blk_no_l1);
							
						}
					}
				}
				
				
				
			}		// end of 2 level caching
			
			
			
			// for one level unified caching
			if(cachelevelch==1)
			{
				
				if ( cache_l2_ob.check_entry(blk_no_l2) ==1 )	// Check L1 Unified cahce
				{
					System.out.println("L2 Cache Hit");
					time+=t_l2;
					l2Hits++;
					event[6]=1;
				}
				
				else 
				{
					System.out.println("L2 Cache Miss");		// not avlb in L1 . get from MM
					time+=t_l2+t_mm;
					l2Miss++;
					event[7]=1;
					if(mm_replacement ==2)
						main_mem_ob.inc_count(frame_no);		// increment lru count for MM
					
					int temp=0;				// takes value depending on the mapping of L1 unified
					if(l2_map==1)temp=cache_l2_ob.isconflict(blk_no_l2);	// direct mapped
					else if(l2_map==2)temp=cache_l2_ob.isfull();			// fifo
					else if(l2_map==3)temp=cache_l2_ob.isfull_lru();		//lru	
					
					
					if(temp!=-1 )	// l2 is full.A block in l2 will be replaced acc to algo
					{
						
						
						cache_l2_ob.add_entry(blk_no_l2,l2_map,pg_no);
						System.out.println("Tag added  to L1 Unified" + blk_no_l2);
					}
					
					
					else			//no replacement.just adding entry
					{
						cache_l2_ob.add_entry(blk_no_l2,l2_map,pg_no);
						System.out.println("Tag added  to L2" + blk_no_l2);
						
					}
				}
				
			}
			
			
			//  one level seperate caches
			if(cachelevelch==2)
			{
				
				
				if(l1_val==1)
					temp_l1=cache_l1_i_ob.check_entry(blk_no_l1);
				else
					temp_l1=cache_l1_d_ob.check_entry(blk_no_l1);
				
				
				
				if(temp_l1==1)	// check L1 cahce
				{
					time+=t_l1;
					if(l1_val==1)
					{System.out.println("L1_I Cache Hit");l1iHits++;event[4]=1;}
					else
					{System.out.println("L1-D Cache Hit");l1dHits++;event[4]=1;}
					
				}
				else
				{
					if(l1_val==1)
					{System.out.println("L1_I Cache Miss");l1iMiss++;event[5]=1;}
					else
					{System.out.println("L1-D Cache Miss");l1dMiss++;event[5]=1;}
					
					// not available in cache . get from MM
					
					time+=t_l1+t_mm;
					if(mm_replacement ==2)
						main_mem_ob.inc_count(frame_no);		// increment lru count for MM
					
					if(l1_val==1)
						cache_l1_i_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
					else
						cache_l1_d_ob.add_entry(blk_no_l1,l1_map,pg_no,blk_no_l2);
					System.out.println("Tag added  to L1" + blk_no_l1);
				}
			}//	end of one level seperate caching
			
			
			
			//update and display tables
			colorRenderer.setRowColor(row, Color.red);
			colorRenderer.startBlinking(700);
			String temp;
			for(int i=0;i<tlb_ob.no_entries;i++)
			{
				temp=Integer.toString(i);
				tlbTbl.setValueAt(temp,i,0);
				temp=Integer.toString(tlb_ob.pg_no[i]);
				tlbTbl.setValueAt(temp,i,1);
				temp=Integer.toString(tlb_ob.frame_no[i]);
				tlbTbl.setValueAt(temp,i,2);
			}
			
			for(int i=0;i<cache_l2_ob.no_entries;i++)
			{
				temp=Integer.toString(i);
				l2Tbl.setValueAt(temp,i,0);
				temp=Integer.toString(cache_l2_ob.tag[i]);
				l2Tbl.setValueAt(temp,i,1);
				temp=Integer.toString(cache_l2_ob.valid[i]);
				l2Tbl.setValueAt(temp,i,2);
				temp=Integer.toString(cache_l2_ob.lru_ct[i]);
				l2Tbl.setValueAt(temp,i,3);
				
			}
			
			for(int i=0;i<cache_l1_i_ob.no_entries;i++)
			{
				temp=Integer.toString(i);
				l1iTbl.setValueAt(temp,i,0);
				temp=Integer.toString(cache_l1_i_ob.tag[i]);
				l1iTbl.setValueAt(temp,i,1);
				temp=Integer.toString(cache_l1_i_ob.valid[i]);
				l1iTbl.setValueAt(temp,i,2);
				temp=Integer.toString(cache_l1_i_ob.lru_ct[i]);
				l1iTbl.setValueAt(temp,i,3);
				
			}
			
			for(int i=0;i<cache_l1_d_ob.no_entries;i++)
			{
				temp=Integer.toString(i);
				l1dTbl.setValueAt(temp,i,0);
				temp=Integer.toString(cache_l1_d_ob.tag[i]);
				l1dTbl.setValueAt(temp,i,1);
				temp=Integer.toString(cache_l1_d_ob.valid[i]);
				l1dTbl.setValueAt(temp,i,2);
				temp=Integer.toString(cache_l1_d_ob.lru_ct[i]);
				l1dTbl.setValueAt(temp,i,3);
				
			}
			
			for(int i=0;i<main_mem_ob.no_frames;i++)
			{
				temp=Integer.toString(i);
				mmTbl.setValueAt(temp,i,0);
				temp=Integer.toString(main_mem_ob.pg_stored[i]);
				mmTbl.setValueAt(temp,i,1);
				temp=Integer.toString(main_mem_ob.valid[i]);
				mmTbl.setValueAt(temp,i,2);
				temp=Integer.toString(main_mem_ob.lru_ct[i]);
				mmTbl.setValueAt(temp,i,3);
				
			}
			
			Object [] keys=pg_tbl_ob.hm.keySet().toArray();
			int rowCnt=pgTabModel.getRowCount();
			for(int i=0;i<rowCnt;i++)
				pgTabModel.removeRow(0);
			
			for (int i=0;i< keys.length;i++)
			{
				int val=pg_tbl_ob.check_entry(Integer.parseInt(keys[i].toString()));
				pgTabModel.addRow(new Object[]{Integer.toString(i),keys[i].toString(),Integer.toString(val)});
			}
			//repaint();
			
			System.out.println("Time Taken : " + time);
			tat+=time;
			avgmat=tat/(row+1);
			System.out.println("AMAT in clock cycle time: " + avgmat );
			row++;
			event[8]=1;
		} 
		printStats();
	}

	
	// clear for next simulaiton
	
	
	private void clearButtonActionPerformed(ActionEvent evt){
		//initData();
		//remove all the tables
		tblPanel.remove(tlbJsp);
		tblPanel.remove(ptJsp);
		tblPanel.remove(l1iJsp);
		tblPanel.remove(l1dJsp);
		tblPanel.remove(mmJsp);
		tblPanel.remove(l2Jsp);
		outputText.setText("");
		clrBtnPressed=true;
		if(colorRenderer!=null)
		{
			colorRenderer.stopBlinking();
			colorRenderer.setRowColor(row-1, Color.white);
		}
		repaint();
	}
	
	
	// take values from the input panel
	
	
	
	private void initData(){
		row=0; 		 //check point
		time=0;		// Memory Access Time
		avgmat=0;		// Average Memory Access Time
		tat=0;		// total access time
		tlbHits=0;
		tlbMiss=0;
		pgHits=0;
		pgFaults=0;
		l1iHits=0;
		l1dHits=0;
		l1iMiss=0;
		l1dMiss=0;
		l2Hits=0;
		l2Miss=0;
		instCount=0;
		dataCount=0;
		traceSelect=0;
		traceUrl=null;
		l1_map=0;
		l2_map=0;

		
		String pgszch=pageSizeList.getSelectedItem();
		pg_sz= Integer.parseInt(pgszch.substring(0,pgszch.indexOf("K")-1));
		System.out.println("SIZE: "+pg_sz);
		pg_sz*=1024;
		
		String mmszch=mmSizeList.getSelectedItem();
		mm_sz= Integer.parseInt(mmszch.substring(0,mmszch.indexOf("M")-1));
		mm_sz*=1024*1024;
		
		String tlbsize=tlbSizeTxt.getText();
		tlb_sz=Integer.parseInt(tlbsize);
		
		String l2cacheszch=l2CacheSizeList.getSelectedItem();
		l2_sz= Integer.parseInt(l2cacheszch.substring(0,l2cacheszch.indexOf("K")-1));
		l2_sz*=1024;
		
		String l2blkszch=l2BlockSizeList.getSelectedItem();
		l2_blk_sz= Integer.parseInt(l2blkszch.substring(0,l2blkszch.indexOf("B")-1));
		
		
		int l2polch=l2PolicyList.getSelectedIndex();
		l2_map= l2polch+1;
		
		int l2Reppolch=l2ReplacementPolicyList.getSelectedIndex();

			if(l2_map==2 && l2Reppolch==1)l2_map=3;
			if(l2_map==3 && l2Reppolch==0)l2_map=4;
			if(l2_map==3 && l2Reppolch==1)l2_map=5;
			
		
		String l1cacheszch=l1CacheSizeList.getSelectedItem();
		l1_sz= Integer.parseInt(l1cacheszch.substring(0,l1cacheszch.indexOf("K")-1));
		l1_sz*=1024;
		
		String l1blkszch=l1BlockSizeList.getSelectedItem();
		l1_blk_sz= Integer.parseInt(l1blkszch.substring(0,l1blkszch.indexOf("B")-1));
		
		
		int l1polch=l1PolicyList.getSelectedIndex();
		l1_map= l1polch+1;
		
		
		int l1Reppolch=l1ReplacementPolicyList.getSelectedIndex();

			if(l1_map==2 && l1Reppolch==1)l1_map=3;
			if(l1_map==3 && l1Reppolch==0)l1_map=4;
			if(l1_map==3 && l1Reppolch==1)l1_map=4;
		
		
		int mmpolch=mmPolicyList.getSelectedIndex();
		mm_replacement=mmpolch+1;
		
		int brkch=brkPointList.getSelectedIndex();
		brkpt=brkch;
		
		cachelevelch=cacheLevelList.getSelectedIndex();
		
		int tracech=traceSelectList.getSelectedIndex();
		traceSelect=tracech;
		
		traceUrl=urlTxt.getText();
		
		// simulation constants
		
		String t_tlbstr=label1Txt.getText();
		t_tlb=Integer.parseInt(t_tlbstr);
		String t_ptstr=label2Txt.getText();
		t_pt=Integer.parseInt(t_ptstr);
		String t_l1str=label3Txt.getText();
		t_l1=Integer.parseInt(t_l1str);
		String t_l2str=label4Txt.getText();
		t_l2=Integer.parseInt(t_l2str);
		String t_mmstr=label5Txt.getText();
		t_mm=Integer.parseInt(t_mmstr);
		String t_vmstr=label6Txt.getText();
		t_vm=Integer.parseInt(t_vmstr);
		
		// simulation 
		tot_pages=(int)(vm_sz/pg_sz);
		no_frames=mm_sz/pg_sz;
		
		
		// creating objects
		tlb_ob= new tlb(tlb_sz);
		pg_tbl_ob= new pg_tbl();
		main_mem_ob= new main_mem(no_frames);
		cache_l1_i_ob= new cache_l1_i(l1_sz/l1_blk_sz);
		cache_l1_d_ob= new cache_l1_d(l1_sz/l1_blk_sz);
		cache_l2_ob= new cache_l2(l2_sz/l2_blk_sz);
		
		
	}
	
	
	
	// print statistics
		
	private void printStats()
	{
		String text=null;
		float temp=0;
		
		text ="   Total Memory References = ";
		text+=(row +1);
		
		if(row!=0){
			text+="\n   Instruction References = ";
			temp=(float)instCount/row;
			temp*=100;
			text+=temp;
			text+=" %";
		}
		if(row!=0){
			text+="\n   Data References  = ";
			temp=(float)dataCount/row;
			temp*=100;
			text+=temp;
			text+=" %";
		}
		
		text+="\n   Average Memory Access Time (AMAT)  = ";
		text+= avgmat;
		text+=" Clock Cycles";

		
		if((tlbHits+tlbMiss)!=0){
			text+="\n   TLB Hits  = ";	
			temp=((float)tlbHits/(float)(tlbHits+tlbMiss));
			temp*=100;
			text+=temp;
			text+=" %";
		}
		
		if((pgHits+pgFaults)!=0){
			text+="\n   Page Faults = ";
			temp=((float)pgHits/(pgHits+pgFaults));
			temp*=100;
			temp=100-temp;
			text+=temp;
			text+=" %";
		}
		if((l1iHits+l1iMiss)!=0){
			text+="\n   L1-I Cache Hit = ";
			temp=((float)l1iHits/(l1iHits+l1iMiss));
			temp*=100;
			text+=temp;
			text+=" %";
		}
		
		if((l1dHits+l1dMiss)!=0){
			text+="\n   L1-D Cache Hit = ";
			temp=((float)l1dHits/(l1dHits+l1dMiss));
			temp*=100;
			text+=temp;
			text+=" %";
		}

		if((l2Hits+l2Miss)!=0){
			text+="\n   L2 Cache Hit  = ";
			temp=((float)l2Hits/(l2Hits+l2Miss));
			temp*=100;
			text+=temp;
			text+=" %";
		}
		outputText.setText(text);
	}
	
	String getConstants(){
		String ret=null;
		ret="\n   TLB Access Time = ";
//		ret+=t_tlb;
		ret+="\n   Page Table Access Time = ";
//		ret+=t_pt;
		ret+="\n   L1 Cache Access Time = ";
//		ret+=t_l1;
		ret+="\n   L2 Cache Access Time = ";
//		ret+=t_l2;
		ret+="\n   Main Memory Access Time = ";
//		ret+=t_mm;
		ret+="\n   Virtual Memory Access Time = ";
//		ret+=(t_vm/1000);
		ret+=" K";
		ret+="\n\n\n All values are in Clock Cycle units";
		return(ret);
	}
	
	
}
