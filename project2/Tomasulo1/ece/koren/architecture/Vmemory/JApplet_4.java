/*
 * JApplet_4.java
 *
 * Created on December 18, 2002, 12:49 AM
 */


 /* <applet code="JApplet_4" width=800 height=600>
  </applet>

*/
public class JApplet_4 extends javax.swing.JApplet {
    
    /** Creates new form JApplet_4 */
    public JApplet_4() {
        initComponents();
    }
    
    
    private void initComponents() {
        hdrPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        outputPanel = new javax.swing.JPanel();
        outputText = new javax.swing.JTextArea();
        inputPanel = new javax.swing.JPanel();
        wordTrace = new javax.swing.JTextField();
        noofWrites = new javax.swing.JTextField();
        pageSize = new javax.swing.JTextField();
        mainMemsize = new javax.swing.JTextField();
        virtualMemsize = new javax.swing.JTextField();
        tlbHittime = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        faulttext= new javax.swing.JTextField();
        memref= new javax.swing.JTextField(); 
        amat= new javax.swing.JTextField();
	locality= new javax.swing.JTextField();

	jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
	fault= new javax.swing.JLabel();
    	memreflabel = new javax.swing.JLabel();
	amatlabel = new javax.swing.JLabel();
	localitylabel = new javax.swing.JLabel();

        buttonPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        
	allTablespanel = new javax.swing.JPanel();
        table = new javax.swing.JTable();
        tabletwo = new javax.swing.JTable();
        tablethree = new javax.swing.JTable();
        tablefour = new javax.swing.JTable();
	
	//my variables
	local_latency = 0;
	global_latency = 0.0;
	mainmemorysize = 12;
        page_faults =0;
	table_lookup_cycles = 1;
	memory_access_cycles=20;
	page_fault_time= 1000; //including time to restart and fetch page again
	pagetablehit=0;
	pagetablemiss=0;
	tlbhit=0;
	tlbmiss=0;
	no_writeback=0;
	checkbutton=0;
	Log= new String();
	localref=0;
        
 
        getContentPane().setLayout(null);
        //getContentPane().set Background(Color.black);        

        setBackground(new java.awt.Color(0, 0, 0));
        hdrPanel.setLayout(null);

        hdrPanel.setBackground(new java.awt.Color(204, 204, 255));
        hdrPanel.setBorder(new javax.swing.border.EtchedBorder());
        jLabel1.setText("  VIRTUAL MEMORY SIMULATOR ");
        hdrPanel.add(jLabel1);
        jLabel1.setBounds(100, 10, 300, 20);
	jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 15));
        getContentPane().add(hdrPanel);
        hdrPanel.setBounds(200, 0, 430, 30);

        outputPanel.setLayout(null);

        outputPanel.setBorder(new javax.swing.border.EtchedBorder());
        outputText.setBackground(new java.awt.Color(255, 255, 204));
        outputText.setEditable(false);
	jspfour = new javax.swing.JScrollPane(outputText);
	jspfour.setBounds(0, 0, 130, 440);
	outputText.setFont(new java.awt.Font("Arial", 1, 12));
        outputText.setText(" \n\n");
	outputPanel.add(jspfour);
        outputText.setToolTipText("OUTPUT AREA");
        getContentPane().add(outputPanel);
        outputPanel.setBounds(630, 0, 130, 440);

        inputPanel.setLayout(null);
	inputPanel.setBackground(java.awt.Color.blue);
        inputPanel.setBorder(new javax.swing.border.EtchedBorder());

        

        inputPanel.add(wordTrace);
        wordTrace.setBounds(160, 40, 60, 20);
         wordTrace.setBackground(java.awt.Color.red);
        wordTrace.setToolTipText("Enter the number of references to memory");
	wordTrace.setText(Integer.toString(60));

	inputPanel.add(noofWrites);
        noofWrites.setBounds(160, 70, 60, 20);
	noofWrites.setBackground(java.awt.Color.red);
	noofWrites.setToolTipText("% of these references as writes");					
        noofWrites.setText(Integer.toString(10));

        inputPanel.add(pageSize);
	pageSize.setBackground(java.awt.Color.red);        
	pageSize.setBounds(160, 100, 60, 20);
	pageSize.setToolTipText("Page Size in words");
	pageSize.setText(Integer.toString(32));

        
	inputPanel.add(mainMemsize);
        mainMemsize.setBounds(160, 130, 60, 20);
	mainMemsize.setBackground(java.awt.Color.red);
        mainMemsize.setToolTipText("Number of Page Frames in physical memory");
	mainMemsize.setText(Integer.toString(12));

        inputPanel.add(virtualMemsize);
        virtualMemsize.setBounds(160, 160, 60, 20);
	virtualMemsize.setBackground(java.awt.Color.red);
        virtualMemsize.setToolTipText("Number of Pages in virtual memory");
	virtualMemsize.setText(Integer.toString(32));

	inputPanel.add(tlbHittime);
        tlbHittime.setBounds(160, 190, 60, 20);
	tlbHittime.setBackground(java.awt.Color.red);
        tlbHittime.setToolTipText("Number of Clock Cycles to check TLB");
	tlbHittime.setText(Integer.toString(1));

	inputPanel.add(jTextField7);
        jTextField7.setBounds(160, 220, 60, 20);
	jTextField7.setBackground(java.awt.Color.red);
        jTextField7.setToolTipText("Number of entries in TLB");
	jTextField7.setText(Integer.toString(5));

        inputPanel.add(jTextField8);
        jTextField8.setBounds(160, 280, 60, 20);
	jTextField8.setBackground(java.awt.Color.red);
        jTextField8.setToolTipText("Number of Clock Cycles to access physical memory");
	jTextField8.setText(Integer.toString(20));

	inputPanel.add(faulttext);
        faulttext.setBounds(160, 310, 60, 20);
	faulttext.setBackground(java.awt.Color.red);
        faulttext.setToolTipText("Number of Clock Cycles to Handle a Page Fault");
	faulttext.setText(Integer.toString(1000));
	
	inputPanel.add(locality);
        locality.setBounds(160, 340, 60, 20);
	locality.setBackground(java.awt.Color.red);
        locality.setToolTipText(" Locality on Scale 0-10 : 0 is least local");
	locality.setText(Integer.toString(0));
	
	inputPanel.add(memref);
        memref.setBounds(160, 370, 60, 20);
	memref.setBackground(java.awt.Color.green);
        memref.setToolTipText(" Current Page being referred");
        memref.setEditable(false);
	memref.setText(null);

	inputPanel.add(amat);
        amat.setBounds(100, 410, 120, 20);
	amat.setBackground(java.awt.Color.green);
        amat.setToolTipText(" Average Memory Access Time in Clock Cycles");
	amat.setEditable(false);
	amat.setText(null);

	jLabel3.setText(" No. of Memory References");
        inputPanel.add(jLabel3);
        jLabel3.setBounds(41, 10, 180, 16);

        jLabel4.setText("% of Writes");
        inputPanel.add(jLabel4);
        jLabel4.setBounds(41, 70, 100, 16);

        jLabel5.setText("Page Size");
        inputPanel.add(jLabel5);
        jLabel5.setBounds(41, 100, 100, 16);

        jLabel6.setText("Main Mem Size");
        inputPanel.add(jLabel6);
        jLabel6.setBounds(40, 130, 100, 16);

        jLabel7.setText("Virtual Mem Size");
        inputPanel.add(jLabel7);
        jLabel7.setBounds(41, 160, 100, 16);

        jLabel8.setText("TLB Hit Time");
        inputPanel.add(jLabel8);
        jLabel8.setBounds(41, 190, 116, 16);

        jLabel9.setText("TLB Size");
        inputPanel.add(jLabel9); 
        jLabel9.setBounds(41, 220, 100, 16);

        jLabel10.setText("Memory Access Time");
        inputPanel.add(jLabel10);
        jLabel10.setBounds(40, 250, 150, 16);
         
	fault.setText("Page Fault Time");
        inputPanel.add(fault);
        fault.setBounds(40, 310, 150, 16);

	localitylabel.setText("Locality");
        inputPanel.add(localitylabel);
        localitylabel.setBounds(40, 340, 100, 16);

	memreflabel.setText("Current Page");
        inputPanel.add(memreflabel);
        memreflabel.setBounds(40, 370, 150, 16);
	amatlabel.setText("AMAT in clock cycles");
        inputPanel.add(amatlabel);
        amatlabel.setBounds(40, 390, 150, 16);
	memreflabel.setFont(new java.awt.Font("Comic Sans MS", 3, 12));
	amatlabel.setFont(new java.awt.Font("Comic Sans MS", 3, 12));
        
	getContentPane().add(inputPanel);
        inputPanel.setBounds(-30, 0, 230, 440);

        buttonPanel.setLayout(null);

        buttonPanel.setBackground(new java.awt.Color(0, 0, 0));
        buttonPanel.setBorder(new javax.swing.border.EtchedBorder());
        buttonPanel.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Start");
        buttonPanel.add(jButton1);
        jButton1.setBounds(0, 0, 70, 30);
	jButton1.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
	
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        buttonPanel.add(jButton2);
        jButton2.setBounds(70, 0, 70, 30);
	jButton2.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
	
        jButton3.setText("Step 1");
        buttonPanel.add(jButton3);
        jButton3.setBounds(140, 0, 70, 30);
	jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
	jButton3.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
	        

	jButton4.setText("Step 10");
        buttonPanel.add(jButton4);
        jButton4.setBounds(210, 0, 70, 30);
	jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
	jButton4.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
	
        jButton5.setText("Help");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
	jButton5.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
	
	/*jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });*/

        buttonPanel.add(jButton5);
        jButton5.setBounds(360, 0, 70, 30);


	jButton6.setText("Complete");
        buttonPanel.add(jButton6);
        jButton6.setBounds(280, 0, 80, 30);
	jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
	jButton6.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        
	getContentPane().add(buttonPanel);
        //buttonPanel.setBounds(200, 380, 260, 60);
	buttonPanel.setBounds(200, 410, 430, 30);
        allTablespanel.setLayout(null);

        allTablespanel.setBackground(new java.awt.Color(0, 0, 153));
        allTablespanel.setBorder(new javax.swing.border.EtchedBorder());
        table.setBackground(new java.awt.Color(204, 255, 204));
        table.setBorder(new javax.swing.border.EtchedBorder());
	

	String[] colHeads = { "Word", "Page", " W/R" };
       	String data[][] = new String[100][3] ;

       for (int j =0; j< 100 ; j++)
        {
	data[j][0]=null;
	data[j][1]=null;
	data[j][2]=null;
	

	}

       	table = new javax.swing.JTable(data, colHeads);
	table.setEnabled(false);
	jsp = new javax.swing.JScrollPane(table);
	jsp.setBounds(0, 0, 220, 190);
	allTablespanel.add(jsp);
	table.setToolTipText("Memory References");
	String[] colHeadstwo = { "VPN", "PPN", "V", "D" };
       	String datatwo[][] = new String[100][4] ;

       for (int j =0; j < 100 ; j++)
        {
	
	datatwo[j][0]=null;
	datatwo[j][1]=null;
	datatwo[j][2]=null;
	datatwo[j][3]=null;
	}

	tabletwo = new javax.swing.JTable(datatwo,colHeadstwo);
	tabletwo.setEnabled(false);
	
	jsptwo = new javax.swing.JScrollPane(tabletwo);
	jsptwo.setBounds(220, 0, 210, 190);
	allTablespanel.add(jsptwo);
        tabletwo.setToolTipText("TRANSLATION LOOK UP BUFFER: TLB");
    String[] colHeadsthree = { "index", "PPN", "V", "D", "LRU" };
    
    String datathree[][] = new String[100][5] ;

       for (int j =0; j < 100 ; j++)
        {
	datathree[j][0]=null;		
	datathree[j][1]=null;
	datathree[j][2]=null;
	datathree[j][3]=null;
	datathree[j][4]=null;
   	}

	tablethree = new javax.swing.JTable(datathree,colHeadsthree);
	tablethree.setEnabled(false);
	
	jspthree = new javax.swing.JScrollPane(tablethree);
	jspthree.setBounds(0, 190, 220, 190);
	allTablespanel.add(jspthree);
	tablethree.setToolTipText("PAGE TABLE");

	String[] colHeadsfour = { "index", "VPN"};
    
    	String datafour[][] = new String[100][2] ;

	for (int j =0; j < 100 ; j++)
        {
	
	datafour[j][0]=null;
	datafour[j][1]=null;
	
	}
 

	tablefour = new javax.swing.JTable(datafour,colHeadsfour);
	tablefour.setEnabled(false);
	
	jspfive = new javax.swing.JScrollPane(tablefour);
	jspfive.setBounds(220, 190, 210, 190);
	allTablespanel.add(jspfive);
	tablefour.setToolTipText("PHYSICAL MEMORY");

        
        tabletwo.setBackground(new java.awt.Color(255, 204, 204));
        tabletwo.setBorder(new javax.swing.border.EtchedBorder());
        
        tablethree.setBackground(new java.awt.Color(204, 255, 255));
        tablethree.setBorder(new javax.swing.border.EtchedBorder());
        
        tablefour.setBackground(new java.awt.Color(255, 255, 0));
        tablefour.setBorder(new javax.swing.border.EtchedBorder());
        tablefour.setForeground(new java.awt.Color(0, 0, 0));

        getContentPane().add(allTablespanel);
        allTablespanel.setBounds(200, 30, 430, 380);
	allTablespanel.setVisible(false);

    }

   
  // initialize action

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        
	outputText.setText("\n\n");
	outputText.append("Single Step Please");
	
		String text= wordTrace.getText();
	
		int block=Integer.parseInt(text);
 	
		text= noofWrites.getText();
		int write=Integer.parseInt(text);
 		text= pageSize.getText();
		int pagesize=Integer.parseInt(text);
		text= jTextField7.getText();
		int TLBsize=Integer.parseInt(text);
		 text= virtualMemsize.getText();
		int virtmemsize=Integer.parseInt(text);
 		text= mainMemsize.getText();
		int mainmemsize= Integer.parseInt(text);
		text= tlbHittime.getText();
		int tlbtime= Integer.parseInt(text);
		text= jTextField8.getText();
		int memtime= Integer.parseInt(text);
		text= faulttext.getText();
		int faulttime= Integer.parseInt(text);
		text= locality.getText();
		int local= Integer.parseInt(text);

        no_of_blocktrace = block;
	no_of_writes=(write * no_of_blocktrace)/100;
	Page_size=pagesize;
	mainmemorysize=mainmemsize;
	TLB_size=TLBsize;
        virtual_memory_size = virtmemsize * Page_size;
	pagetablesize = virtmemsize;        
	table_lookup_cycles = tlbtime;
	memory_access_cycles=memtime;
	page_fault_time= faulttime;
	localref=local;

	System.out.println(pagetablesize + virtual_memory_size);
	System.out.println(" references " + no_of_blocktrace);
	
	MemTrace = new MemRefGen(no_of_blocktrace, no_of_writes, Page_size,virtual_memory_size, localref);
	MemTrace.Generate();	
   
	System.out.println("block " + MemTrace.blocktrace[0] + " page " + 
MemTrace.pagetrace[0] + "perm " + MemTrace.read_write[0]); 
 	 checkbutton=0;    
        table_lookup_buffer = new Tlb(TLB_size);
        page_table = new PageTable(pagetablesize);
        main_memory = new MainMem(mainmemorysize);
	 String data[][] = new String[no_of_blocktrace][3] ;
        int j=0;
	for (j=0;j<no_of_blocktrace;j++)
	{Character a = new Character(MemTrace.read_write[j]);
	data[j][0]=Integer.toString(MemTrace.blocktrace[j]);
	data[j][1]=Integer.toString(MemTrace.pagetrace[j]);
	data[j][2]=a.toString();
	for (int k=0; k< 3; k++) {
	
			table.setValueAt(data[j][k],j,k);
   				}
    	}
    String datathree[][] = new String[pagetablesize][5] ;

       for ( j =0; j < pagetablesize ; j++)
        {
	datathree[j][0]=Integer.toString(page_table.PT_entry[j].virtual_page_number);
	datathree[j][1]=Integer.toString(page_table.PT_entry[j].physical_page_number);
	datathree[j][2]=Integer.toString(page_table.PT_entry[j].valid);
	datathree[j][3]=Integer.toString(page_table.PT_entry[j].dirty);	
	datathree[j][4]=Integer.toString(page_table.PT_entry[j].lru);	
         
		for (int k=0; k< 5; k++) {
	
			tablethree.setValueAt(datathree[j][k],j,k);
   					}	
			}
       
	
 	allTablespanel.setVisible(true);

		
}
   // clear action

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        
	outputText.setText("\n\nReady for another run? \n\n\nYou can either reset \nvalues or keep the same: \n\n\n Press start");
	local_latency = 0;
	global_latency = 0;
	page_faults =0;
	table_lookup_cycles = 1;
	memory_access_cycles=20;
	page_fault_time= 1000; //including time to restart and fetch page again
	pagetablehit=0;
	pagetablemiss=0;
	tlbhit=0;
	tlbmiss=0;
	no_writeback=0;
	checkbutton=0;
	Log = " " + "\n";
	String datathree[][] = new String[100][5] ;
	memref.setText(null);
	amat.setText(null);
       for (int j =0; j < 100 ; j++)
        {
	datathree[j][0]=null;		
	datathree[j][1]=null;
	datathree[j][2]=null;
	datathree[j][3]=null;
	datathree[j][4]=null;
	

	for (int k=0; k< 5; k++) {
	
			tablethree.setValueAt(datathree[j][k],j,k);
   					}
	}
		
	String[] colHeads = { "Word", "Page", " W/R" };
       	String data[][] = new String[100][3] ;

       for (int j =0; j< 100 ; j++)
        {
	data[j][0]=null;
	data[j][1]=null;
	data[j][2]=null;
	
		for (int k=0; k< 3; k++) {
	
			table.setValueAt(datathree[j][k],j,k);
   					}
	}
	
	String datatwo[][] = new String[100][4] ;

       for (int j =0; j < 100 ; j++)
        {
	
	datatwo[j][0]=null;
	datatwo[j][1]=null;
	datatwo[j][2]=null;
	datatwo[j][3]=null;

for (int k=0; k< 4; k++) {
	
			tabletwo.setValueAt(datatwo[j][k],j,k);
   					}

	}

	String datafour[][] = new String[100][2] ;

	for (int j =0; j < 100 ; j++)
        {
	
	datafour[j][0]=null;
	datafour[j][1]=null;
	for (int k=0; k< 2; k++) {
	
			tablefour.setValueAt(datafour[j][k],j,k);
   					}	
	}

		paint();
		// Add your handling code here:
    }

// single stepping action

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        outputText.setText("\n\n");
	int ppn=0;
	int index=0;
        int i=0;
	// main logic
        if (checkbutton< no_of_blocktrace)
	{ 
	  i=checkbutton;
	  Log = "\n";
 	  System.out.println("block " + MemTrace.blocktrace[i] + " page " + 
MemTrace.pagetrace[i] + "perm " + MemTrace.read_write[i]); 
 	

       
           local_latency = (memory_access_cycles) + (table_lookup_cycles);
	   page=MemTrace.pagetrace[i];
  
       	perm=MemTrace.read_write[i];
 	  
 	tlb_hit = table_lookup_buffer.Tlb_search(page, perm);
         
	if (tlb_hit) 
                 {System.out.println(" TLB hit");
		  Log= Log + " We have a TLB hit \n";
	          tlbhit++;
		  page_table.lruupdate(page);
		}
	// memory block trace generator, get back BlockTrace.memtrace and 
	
		if (!tlb_hit)  {
			System.out.println(" TLB miss \n");
			Log = Log + " TLB_miss \n";
			tlbmiss++;
			//int index=0;
 			if(TLB_size>0){
			index = table_lookup_buffer.toreplace();
			//System.out.println("TLB entry to replace" + index);
                        Log = Log + " Replacing TLB entry " + index + "\n";
                        page_table.modify(table_lookup_buffer.TLB_entry[index]);}
                        pagetable_hit = page_table.search(page,perm);
			local_latency += memory_access_cycles;
 
			if (pagetable_hit) {
				pagetablehit++;
				System.out.println(" PT hit");
				Log = Log + " PT Hit \n ";
			    // page_table.modify(page,perm,ppn);
                           if( perm=='w') page_table.PT_entry[page].dirty=1;
			   //if (perm='r') page_table.PT_entry[page].dirty=1;
if(TLB_size>0)	{	             table_lookup_buffer.modify(page_table.PT_entry[page], index);}
			     page_table.lruupdate(page);
				} // end of pagetable hit
		
			else  {
			pagetablemiss++;
			System.out.println(" PT miss");
			Log = Log + " PT Miss \n";
			local_latency += page_fault_time;
		 	page_faults ++;
			
if (page_faults > mainmemorysize)
		{	int vpn= page_table.toreplace(mainmemorysize-1);
			//page_table.lruupdate(page);
			ppn= page_table.PT_entry[vpn].physical_page_number;
			if (page_table.PT_entry[vpn].dirty==1) 
					{
					local_latency += page_fault_time;
					no_writeback++;
					}
			page_table.PT_entry[vpn].dirty=0;
		}                 
       if (page_faults <= mainmemorysize) ppn=page_faults-1;		
                        
main_memory.entry[ppn].indexed_by_physical_page_number=ppn;
  			main_memory.entry[ppn].correspondingto_virtual_page_number=page; 
			page_table.lruupdate(page); //imp: b4 modify
			page_table.modify(page,perm,ppn);
	if(TLB_size>0)	{	table_lookup_buffer.modify(page_table.PT_entry[page], index);}
			//page_table.lruupdate(page);
			} // end of page table miss
		} // end of tlb_miss
	global_latency += local_latency;
	double time = global_latency/(checkbutton+1);
	String avg = new String();
	avg = Double.toString(time);
	if (avg.length()>9) avg= avg.substring(0,8);
	amat.setText(avg);
//checkbutton.doubleValue();
	System.out.println("local " + local_latency + "faults" + page_faults +" "+ avg);

	String current = new String();
	Character a = new Character(MemTrace.read_write[checkbutton]);
	current = Integer.toString(MemTrace.blocktrace[checkbutton]) + " " + Integer.toString(MemTrace.pagetrace[checkbutton])
	+ " " + a.toString();
        memref.setText(current);
     	 } 

// }// end of if
    
// display : stats
     
 
       /*	 String data[][] = new String[no_of_blocktrace][3] ;

       for (int j =0; j< no_of_blocktrace ; j++)
        {Character a = new Character(MemTrace.read_write[j]);
	data[j][0]=Integer.toString(MemTrace.blocktrace[j]);
	data[j][1]=Integer.toString(MemTrace.pagetrace[j]);
	data[j][2]=a.toString();
	
	}

    	for (int k=0; k< 3; k++) {
	
			table.setValueAt(data[i][k],i,k);
   					}*/
	
        

    	String datatwo[][] = new String[TLB_size][4] ;

       for (int j =0; j < TLB_size ; j++)
        {
	datatwo[j][0]=Integer.toString(table_lookup_buffer.TLB_entry[j].virtual_page_number);
	datatwo[j][1]=Integer.toString(table_lookup_buffer.TLB_entry[j].physical_page_number);
	datatwo[j][2]=Integer.toString(table_lookup_buffer.TLB_entry[j].valid);
	datatwo[j][3]=Integer.toString(table_lookup_buffer.TLB_entry[j].dirty);
	
		
	}

    for (int k=0; k< 4; k++) {
	
			tabletwo.setValueAt(datatwo[index][k],index,k);

   					}

    String datathree[][] = new String[pagetablesize][5] ;

       for (int j =0; j < pagetablesize ; j++)
        {
	datathree[j][0]=Integer.toString(page_table.PT_entry[j].virtual_page_number);
	datathree[j][1]=Integer.toString(page_table.PT_entry[j].physical_page_number);
	datathree[j][2]=Integer.toString(page_table.PT_entry[j].valid);
	datathree[j][3]=Integer.toString(page_table.PT_entry[j].dirty);	
	datathree[j][4]=Integer.toString(page_table.PT_entry[j].lru);	

		for (int k=0; k< 5; k++) {
	
			tablethree.setValueAt(datathree[j][k],j,k);
   					}

	
}
       
    
    	String datafour[][] = new String[mainmemorysize][2] ;

	for (int j =0; j < mainmemorysize ; j++)
        {
	
	datafour[j][1]=Integer.toString(main_memory.entry[j].correspondingto_virtual_page_number);
datafour[j][0]=Integer.toString(main_memory.entry[j].indexed_by_physical_page_number);
	}	
		for (int k=0; k< 2; k++) {
	
			tablefour.setValueAt(datafour[ppn][k],ppn,k);
   					}
	
	
 
	
	Log = Log + " Local Latency " + local_latency + "\n Global Latency" + global_latency + "\n" + " Faults "  + page_faults + " \n" + " TLB Hits " + tlbhit + " \n" +
" Page Table Hits " + pagetablehit + "\n" + " No. of WriteBack " + no_writeback;
	outputText.setText(Log);
	checkbutton++;	


	// Add your handling code here:
    }

// stepping up 10


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
	outputText.setText("\n\n\n\n Sequence Over");
	
	int ppn=0;
	int index=0;
        int i=0;
	int loopiter=0;
	// main logic
	if ((checkbutton+10)<no_of_blocktrace) {loopiter=10;}
         else loopiter = no_of_blocktrace-checkbutton;
	for (int s= 0; s<loopiter; s++)
	{ 
	  i=checkbutton;
	  Log = "\n";
 	  System.out.println("block " + MemTrace.blocktrace[i] + " page " + 
MemTrace.pagetrace[i] + "perm " + MemTrace.read_write[i]); 
 	

       
           local_latency = (memory_access_cycles) + (table_lookup_cycles);
	   page=MemTrace.pagetrace[i];
  
       	perm=MemTrace.read_write[i];
 	  
 	tlb_hit = table_lookup_buffer.Tlb_search(page, perm);
         
	if (tlb_hit) 
                 {System.out.println(" TLB hit");
		  Log= Log + " We have a TLB hit \n";
	          tlbhit++;
		  page_table.lruupdate(page);
		}
	// memory block trace generator, get back BlockTrace.memtrace and 
	
		if (!tlb_hit)  {
			System.out.println(" TLB miss: Look into Page Table");
			Log = Log + " TLB_miss \n";
			tlbmiss++;
			//int index=0;
 			if(TLB_size>0){
			index = table_lookup_buffer.toreplace();
			//System.out.println("TLB entry to replace" + index);
                        Log = Log + " Replacing TLB entry " + index + "\n";
                        page_table.modify(table_lookup_buffer.TLB_entry[index]);}
                        pagetable_hit = page_table.search(page,perm);
			local_latency += memory_access_cycles;
 
			if (pagetable_hit) {
				pagetablehit++;
				System.out.println(" PT hit");
				Log = Log + " PT Hit \n ";
			    // page_table.modify(page,perm,ppn);
                           if( perm=='w') page_table.PT_entry[page].dirty=1;
			   //if (perm='r') page_table.PT_entry[page].dirty=1;
if(TLB_size>0)	{	             table_lookup_buffer.modify(page_table.PT_entry[page], index);}
			     page_table.lruupdate(page);
				} // end of pagetable hit
		
			else  {
			pagetablemiss++;
			System.out.println(" PT miss");
			Log = Log + " PT Miss \n";
			local_latency += page_fault_time;
		 	page_faults ++;
			
if (page_faults > mainmemorysize)
		{	int vpn= page_table.toreplace(mainmemorysize-1);
			//page_table.lruupdate(page);
			ppn= page_table.PT_entry[vpn].physical_page_number;
			if (page_table.PT_entry[vpn].dirty==1) 
					{
					local_latency += page_fault_time;
					no_writeback++;
					}
			page_table.PT_entry[vpn].dirty=0;
		}                 
       if (page_faults <= mainmemorysize) ppn=page_faults-1;		
                        
main_memory.entry[ppn].indexed_by_physical_page_number=ppn;
  			main_memory.entry[ppn].correspondingto_virtual_page_number=page; 
			page_table.lruupdate(page); //imp: b4 modify
			page_table.modify(page,perm,ppn);
	if(TLB_size>0)	{	table_lookup_buffer.modify(page_table.PT_entry[page], index);}
			//page_table.lruupdate(page);
			} // end of page table miss
		} // end of tlb_miss
	global_latency += local_latency;
	System.out.println("local " + local_latency + "faults" + page_faults);
	String current = new String();
	Character a = new Character(MemTrace.read_write[checkbutton]);
	current = Integer.toString(MemTrace.blocktrace[checkbutton]) + " " + Integer.toString(MemTrace.pagetrace[checkbutton])
	+ " " + a.toString();
        memref.setText(current);
	double time = global_latency/(checkbutton+1);
	String avg = new String();
	avg = Double.toString(time);
	if (avg.length()>9) avg= avg.substring(0,8);
	amat.setText(avg);
	checkbutton++;
     	 } 

// }// end of if
    
// display : stats
     
 
       	 String data[][] = new String[no_of_blocktrace][3] ;

       for (int j =0; j< checkbutton ; j++)
        {Character a = new Character(MemTrace.read_write[j]);
	data[j][0]=Integer.toString(MemTrace.blocktrace[j]);
	data[j][1]=Integer.toString(MemTrace.pagetrace[j]);
	data[j][2]=a.toString();
	
	

    	/*for (int k=0; k< 3; k++) {
	
			table.setValueAt(data[j][k],j,k);
   					}*/
		}
    	String datatwo[][] = new String[TLB_size][4] ;

       for (int j =0; j < TLB_size ; j++)
        {
	datatwo[j][0]=Integer.toString(table_lookup_buffer.TLB_entry[j].virtual_page_number);
	datatwo[j][1]=Integer.toString(table_lookup_buffer.TLB_entry[j].physical_page_number);
	datatwo[j][2]=Integer.toString(table_lookup_buffer.TLB_entry[j].valid);
	datatwo[j][3]=Integer.toString(table_lookup_buffer.TLB_entry[j].dirty);
	
		
//	}

    for (int k=0; k< 4; k++) {
	
			tabletwo.setValueAt(datatwo[j][k],j,k);

   					}
}
    String datathree[][] = new String[pagetablesize][5] ;

       for (int j =0; j < pagetablesize ; j++)
        {
	datathree[j][0]=Integer.toString(page_table.PT_entry[j].virtual_page_number);
	datathree[j][1]=Integer.toString(page_table.PT_entry[j].physical_page_number);
	datathree[j][2]=Integer.toString(page_table.PT_entry[j].valid);
	datathree[j][3]=Integer.toString(page_table.PT_entry[j].dirty);	
	datathree[j][4]=Integer.toString(page_table.PT_entry[j].lru);	

		for (int k=0; k< 5; k++) {
	
			tablethree.setValueAt(datathree[j][k],j,k);
   					}

	
}
       
    
    	String datafour[][] = new String[mainmemorysize][2] ;

	for (int j =0; j < mainmemorysize ; j++)
        {
	
	datafour[j][1]=Integer.toString(main_memory.entry[j].correspondingto_virtual_page_number);
datafour[j][0]=Integer.toString(main_memory.entry[j].indexed_by_physical_page_number);
		
		for (int k=0; k< 2; k++) {
	
			tablefour.setValueAt(datafour[j][k],j,k);
   					}
	
	}
 
	
	Log = Log + " Local Latency " + local_latency + "\n Global Latency" + global_latency + "\n" + " Faults "  + page_faults + " \n" + " TLB Hits " + tlbhit + " \n" +
" Page Table Hits " + pagetablehit + "\n" + " No. of WriteBack " + no_writeback;
	outputText.setText(Log);
	
        // Add your handling code here:
    }


// completetion


    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
	outputText.setText("\n\n\n\n Sequence Over");
	
	int ppn=0;
	int index=0;
        int i=0;
	int loopiter=0;
	// main logic
	loopiter = no_of_blocktrace-checkbutton;
	for (int s= 0; s<loopiter; s++)
	{ 
	  i=checkbutton;
	  Log = "\n";
 	  System.out.println("block " + MemTrace.blocktrace[i] + " page " + 
MemTrace.pagetrace[i] + "perm " + MemTrace.read_write[i]); 
 	

       
           local_latency = (memory_access_cycles) + (table_lookup_cycles);
	   page=MemTrace.pagetrace[i];
  
       	perm=MemTrace.read_write[i];
 	  
 	tlb_hit = table_lookup_buffer.Tlb_search(page, perm);
         
	if (tlb_hit) 
                 {System.out.println(" TLB hit");
		  Log= Log + " We have a TLB hit \n";
	          tlbhit++;
		  page_table.lruupdate(page);
		}
	// memory block trace generator, get back BlockTrace.memtrace and 
	
		if (!tlb_hit)  {
			System.out.println(" TLB miss: Look into Page Table");
			Log = Log + " TLB_miss \n";
			tlbmiss++;
			//int index=0;
 			if(TLB_size>0){
			index = table_lookup_buffer.toreplace();
			//System.out.println("TLB entry to replace" + index);
                        Log = Log + " Replacing TLB entry " + index + "\n";
                        page_table.modify(table_lookup_buffer.TLB_entry[index]);}
                        pagetable_hit = page_table.search(page,perm);
			local_latency += memory_access_cycles;
 
			if (pagetable_hit) {
				pagetablehit++;
				System.out.println(" PT hit");
				Log = Log + " PT Hit \n ";
			    // page_table.modify(page,perm,ppn);
                           if( perm=='w') page_table.PT_entry[page].dirty=1;
			   //if (perm='r') page_table.PT_entry[page].dirty=1;
if(TLB_size>0)	{	             table_lookup_buffer.modify(page_table.PT_entry[page], index);}
			     page_table.lruupdate(page);
				} // end of pagetable hit
		
			else  {
			pagetablemiss++;
			System.out.println(" PT miss");
			Log = Log + " PT Miss \n";
			local_latency += page_fault_time;
		 	page_faults ++;
			
if (page_faults > mainmemorysize)
		{	int vpn= page_table.toreplace(mainmemorysize-1);
			//page_table.lruupdate(page);
			ppn= page_table.PT_entry[vpn].physical_page_number;
			if (page_table.PT_entry[vpn].dirty==1) 
					{
					local_latency += page_fault_time;
					no_writeback++;
					}
			page_table.PT_entry[vpn].dirty=0;
		}                 
       if (page_faults <= mainmemorysize) ppn=page_faults-1;		
                        
main_memory.entry[ppn].indexed_by_physical_page_number=ppn;
  			main_memory.entry[ppn].correspondingto_virtual_page_number=page; 
			page_table.lruupdate(page); //imp: b4 modify
			page_table.modify(page,perm,ppn);
	if(TLB_size>0)	{	table_lookup_buffer.modify(page_table.PT_entry[page], index);}
			//page_table.lruupdate(page);
			} // end of page table miss
		} // end of tlb_miss
	global_latency += local_latency;
	System.out.println("local " + local_latency + "faults" + page_faults);
	String current = new String();
	Character a = new Character(MemTrace.read_write[checkbutton]);
	current = Integer.toString(MemTrace.blocktrace[checkbutton]) + " " + Integer.toString(MemTrace.pagetrace[checkbutton])
	+ " " + a.toString();
        memref.setText(current);
	double time = global_latency/(checkbutton+1);
	String avg = new String();
	avg = Double.toString(time);
	if (avg.length()>9) avg= avg.substring(0,8);
	amat.setText(avg);
	checkbutton++;
     	 } 

// }// end of if
    
// display : stats
     
 
       	 String data[][] = new String[no_of_blocktrace][3] ;

       for (int j =0; j< checkbutton ; j++)
        {Character a = new Character(MemTrace.read_write[j]);
	data[j][0]=Integer.toString(MemTrace.blocktrace[j]);
	data[j][1]=Integer.toString(MemTrace.pagetrace[j]);
	data[j][2]=a.toString();
	
	

    	/*for (int k=0; k< 3; k++) {
	
			table.setValueAt(data[j][k],j,k);
   					}*/
		}
    	String datatwo[][] = new String[TLB_size][4] ;

       for (int j =0; j < TLB_size ; j++)
        {
	datatwo[j][0]=Integer.toString(table_lookup_buffer.TLB_entry[j].virtual_page_number);
	datatwo[j][1]=Integer.toString(table_lookup_buffer.TLB_entry[j].physical_page_number);
	datatwo[j][2]=Integer.toString(table_lookup_buffer.TLB_entry[j].valid);
	datatwo[j][3]=Integer.toString(table_lookup_buffer.TLB_entry[j].dirty);
	
		
//	}

    for (int k=0; k< 4; k++) {
	
			tabletwo.setValueAt(datatwo[j][k],j,k);

   					}
}
    String datathree[][] = new String[pagetablesize][5] ;

       for (int j =0; j < pagetablesize ; j++)
        {
	datathree[j][0]=Integer.toString(page_table.PT_entry[j].virtual_page_number);
	datathree[j][1]=Integer.toString(page_table.PT_entry[j].physical_page_number);
	datathree[j][2]=Integer.toString(page_table.PT_entry[j].valid);
	datathree[j][3]=Integer.toString(page_table.PT_entry[j].dirty);	
	datathree[j][4]=Integer.toString(page_table.PT_entry[j].lru);	

		for (int k=0; k< 5; k++) {
	
			tablethree.setValueAt(datathree[j][k],j,k);
   					}

	
}
       
    
    	String datafour[][] = new String[mainmemorysize][2] ;

	for (int j =0; j < mainmemorysize ; j++)
        {
	
	datafour[j][1]=Integer.toString(main_memory.entry[j].correspondingto_virtual_page_number);
datafour[j][0]=Integer.toString(main_memory.entry[j].indexed_by_physical_page_number);
		
		for (int k=0; k< 2; k++) {
	
			tablefour.setValueAt(datafour[j][k],j,k);
   					}
	
	}
 
	
	Log = Log + " Local Latency " + local_latency + "\n Global Latency" + global_latency + "\n" + " Faults "  + page_faults + " \n" + " TLB Hits " + tlbhit + " \n" +
" Page Table Hits " + pagetablehit + "\n" + " No. of WriteBack " + no_writeback;
	outputText.setText(Log);
	
        // Add your handling code here:
    }


// help  
   private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        outputText.setText("\n\n\n\n  \nask me \nfor help \ncoming soon");
	

 String help = new String();
help = "Inputs: \n 1. Number of memory \n references\n 2. % of write: This % of Number\n of memory \n references will be \n write and rest of \n them will be read \n 3.Virtual Memory Size \n 4.Main Memory Size \n 5.Page size \n 6.TLB size:\n Number of entries \n in TLB \n 7.TLB access time \n 8.Main Memory access \n time \n 9.Miss Penalty";

	outputText.setText(help);
	//outputText.append(help);
// Add your handling code here:
    }

    


	public void paint() {

    
 inputPanel.setVisible(false);
	
	table_lookup_buffer.Display();
     	page_table.display();
//  	main_memory.display();
	System.out.println(" references " + no_of_blocktrace);
	System.out.println(" tlb hit " + tlbhit);
	System.out.println("tlb miss " + tlbmiss);
			System.out.println("pt hit " + pagetablehit);		
	System.out.println("pt miss" + pagetablemiss);
	System.out.println("global time " + global_latency);
	System.out.println("faults " + page_faults);
	
        System.out.println("writeback " + no_writeback);
        //System.out.println("writeback " + check);
	inputPanel.setVisible(true);

	}


    
    
    // Variables declaration - do not modify
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jButton2;
    private javax.swing.JTable jTable13;
    private javax.swing.JTextField wordTrace;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTable virtualMemoryTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JButton jButton3;
    private javax.swing.JTextField virtualMemsize;
    private javax.swing.JTextField pageSize;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTable pageTraceTable;
    private javax.swing.JTextField tlbHittime;
    private javax.swing.JTable jTable12;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JTextArea outputText;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField mainMemsize;
    private javax.swing.JPanel allTablespanel;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JPanel hdrPanel;
    private javax.swing.JTextField noofWrites;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTable table;
    private javax.swing.JTable tabletwo;
    private javax.swing.JTable tablethree;
    private javax.swing.JTable tablefour;
    private javax.swing.JScrollPane jsp;
    private javax.swing.JScrollPane jsptwo;
    private javax.swing.JScrollPane jspthree;
    private javax.swing.JScrollPane jspfour;	
    private javax.swing.JScrollPane jspfive;
    private javax.swing.JLabel fault;
    private javax.swing.JTextField faulttext;
    
    private javax.swing.JLabel memreflabel;
    private javax.swing.JTextField memref;
    private javax.swing.JLabel amatlabel;
    private javax.swing.JTextField amat;	
    private javax.swing.JLabel localitylabel;
    private javax.swing.JTextField locality;
    private javax.swing.JButton jButton6;

	// variables 

        int page; 
        char perm; 
        int no_of_blocktrace;
	int no_of_writes;
        int Page_size;
        int TLB_size;
	int virtual_memory_size;
	int pagetablesize; // virtual pages 0-31;
	int local_latency;
	double global_latency;
	int mainmemorysize;
        int page_faults;
	int table_lookup_cycles;
	int memory_access_cycles;
	int page_fault_time; //including time to restart and fetch page again
	int pagetablehit;
	int pagetablemiss;
	int tlbhit;
	int tlbmiss;
	int no_writeback;
	boolean tlb_hit;
	boolean pagetable_hit;
        int checkbutton;
        MemRefGen MemTrace;
	String ColHeads;
        Tlb table_lookup_buffer;
        PageTable page_table;
        MainMem main_memory;	
	String Log;
	int localref;
    // End of variables declaration
    // End of variables declaration
    
}


// dont touch below this
// generation of Page Trace
class MemRefGen {
   public int blocktrace[];
   public char read_write[];
   public int pagetrace[];
   private int write_no;
   private int max;
   private int page_size;
   private int virtsize;
   private int locality;
	
   MemRefGen(int size, int no_of_writes, int page, int virt, int localref) { 
             blocktrace = new int[size];
		 pagetrace = new int[size];
		 read_write = new char[size];
		 write_no= no_of_writes;
		 virtsize=virt;
             max= size;
             page_size= page;
	     locality=localref;
 		 //System.out.println("No of writes: " + write_no);
		 //System.out.println("size: " + max);
             //System.out.println("size: " + page_size);
 } // end of constructor 
 
  void Generate() { 
          for (int i =0; i < max; i++)
{
	       int  face = 1 + ( int ) ( Math.random() * (virtsize-2)); // 1024 : VM blocks
                   blocktrace[i] = face;
               	  int  test = 1 + ( int ) ( Math.random() * 10); // 1024 : locality
        	if ((i>0) && (test< (locality+1))){
				blocktrace[i]=((pagetrace[i-1])*(page_size))+(int)((test*page_size)/11);					
System.out.println("test " + test + " " + blocktrace[i]);
				}// end if for locality      
	
	
	           pagetrace[i] =  (int)((blocktrace[i])/(page_size));
                   read_write[i]='r';
 }
 
	  for ( int j = 0; j <  write_no ; j++)
{	
	       int  rw = 1 + ( int ) ( Math.random() * max);
                   while ( read_write[rw-1]=='w' ) 
                           { 
			rw = 1 + ( int ) ( Math.random() * max);
		   }
		//System.out.println("No of writes: " + j + " " + rw);
	       read_write[rw-1]='w';
} // end for 
} // end generate
} // end MemRefGen class
// end of mem trace generator
// TLB details class
class Tlb_entry
{
  public int virtual_page_number;
  public int physical_page_number;
  public int access_bit;
  public int valid;
  public int dirty;
 
 Tlb_entry() {
 virtual_page_number=0;
 physical_page_number=0;
 access_bit=0;
 valid=0;
 dirty=0;
}
} // end of Tlb entry 
// fully -associative TLB
class Tlb {
 
     public Tlb_entry TLB_entry[];
     public PageTable_entry modify;
     private int size_of_tlb; 
     int page;
     char perm;
     private int tlb_replace = -1;
		
	// Tlb_entry is the format class
      // TLB_entry_no is array of entries in TLB.
	  Tlb (int size) { 
 
        size_of_tlb = size; 
        TLB_entry = new Tlb_entry[size_of_tlb];
           for (int i = 0; i < size; i++) TLB_entry[i] = new 
Tlb_entry();  // initialization of array of objects!! imp!! 
   
      for (int i=0; i < size_of_tlb; i++  )
		{
                  TLB_entry[i].virtual_page_number = 0; // for debug
		  TLB_entry[i].physical_page_number = 0; 
		  TLB_entry[i].dirty = 0; 
		  TLB_entry[i].valid = 0; 
		  TLB_entry[i].access_bit = 7; 
		//	System.out.println("No of writes: " + size_of_tlb + " " + TLB_entry[i].virtual_page_number);
		}
 } // end of constructor
    void Display() {
 		for ( int i=0; i < size_of_tlb; i++ )
        { System.out.println(" TLB entry " + "V " + i + " " + 
TLB_entry[i].virtual_page_number);
System.out.println(" TLB entry " + "P " + i + " " + 
TLB_entry[i].physical_page_number);
System.out.println(" TLB entry " + "V " + i + " " + 
TLB_entry[i].valid);
System.out.println(" TLB entry " + "D " + i + " " + 
TLB_entry[i].dirty);
System.out.println(" TLB entry " + "A " + i + " " + 
TLB_entry[i].access_bit);
} // end of for
} // end of Display
    boolean Tlb_search(int page, char perm) { 
	this.page=page;
	this.perm=perm;
	
	//System.out.println(page);
	for (int i=0; i<size_of_tlb;i++ )
	{
	if ((TLB_entry[i].virtual_page_number==page) && 
(TLB_entry[i].valid==1)) 
			{
                if (perm=='w') TLB_entry[i].dirty=1; //update on hit and write
              return true; 
		}
        //return false;
	}
   return false;
} 
    int toreplace() {
	// dummy replace tlb 
	//int i=0;
        tlb_replace ++;
	if ( tlb_replace >= size_of_tlb ) tlb_replace= 0;        
	return tlb_replace; // search algo
} // end of toreplace
	
	
void modify( PageTable_entry modif, int numb) {
          modify = modif;
          int index = numb;
	  TLB_entry[index].dirty=modif.dirty;
	  TLB_entry[index].valid=modif.valid;
	  TLB_entry[index].access_bit=modif.access_bit;
	  TLB_entry[index].physical_page_number=modif.physical_page_number;
	  TLB_entry[index].virtual_page_number=modif.virtual_page_number;		
}	// tlb_toreplace will give an index
} // end of tlb
// Page Table Details
class PageTable_entry
{
  public int physical_page_number;
  public int access_bit;
  public int valid;
  public int dirty;
  public int virtual_page_number; 
  public int lru; //least recently used will have highest number
 PageTable_entry() 
{
 physical_page_number=0;
 access_bit=7;
 valid=0;
 dirty=0;
 virtual_page_number = 0;
 lru =0;
}
}
class PageTable {
 
     public PageTable_entry PT_entry[];
     public int size_of_PT; 
     private Tlb_entry modify = new Tlb_entry();
     private PageTable_entry toreplace = new PageTable_entry();
     int page;
     char perm;
	
        PageTable(int size) { 
 
        size_of_PT = size; 
        PT_entry = new PageTable_entry[size_of_PT];
           for (int i = 0; i < size; i++) 
        { 
  		PT_entry[i] = new PageTable_entry();  // initialization of array of objects!! imp!! 
   		PT_entry[i].virtual_page_number = i;
		
        }
        PT_entry[1].access_bit = 5;
 // for debug
         // PT_entry[10].dirty=0;	
	  //PT_entry[10].valid= 1;
	  //PT_entry[10].access_bit= 5;
	  //PT_entry[10].physical_page_number= 15;
 } // end of constructor
    
    void modify( Tlb_entry modif) {
          
	  modify = modif;
          if (modify.valid==1)	{ // dont need to modify PT when TLB  entry itself invalid  
          int index = modify.virtual_page_number;
	  PT_entry[index].dirty=modify.dirty;	
	  PT_entry[index].valid= modify.valid;
	  PT_entry[index].access_bit= modify.access_bit;
	  PT_entry[index].physical_page_number=modify.physical_page_number;			  		
}         
} // end modify
 
    void modify( int pag, char per, int ppn ) {
          
	  int page=pag;
	  char perm=per;	
          
	  PT_entry[page].dirty=0;
	  if (perm=='w') PT_entry[page].dirty=1;		
	
//	  if (perm=='r') PT_entry[page].dirty=0;
	  PT_entry[page].valid= 1;
	  PT_entry[page].access_bit= 7;
	  PT_entry[page].physical_page_number=ppn;			           
} // end modify
    void display() {
	for (int i=0; i < size_of_PT; i++)
		{
		
		System.out.println(" PT entry index " + " " + i + "V " + 
PT_entry[i].virtual_page_number + "P " + PT_entry[i].physical_page_number + "A " + 
PT_entry[i].access_bit + " V" + PT_entry[i].valid + "D " + 
PT_entry[i].dirty + " lru " + PT_entry[i].lru );
			
		}
} //end of display	
    boolean search(int page, char perm) {   // this will return index of Pagetable to replace
	this.page=page;
	this.perm=perm;
	//System.out.println(page);
	if (PT_entry[page].valid==1) return true; 
	else return false;
	} 
   int toreplace(int maxsize) {
         // lru search
 	    int index = 0;
	    for (int i=0; i < size_of_PT; i++)
		 {if (PT_entry[i].lru==maxsize) index = i;}	
            update(index);// corresponds to virtual page number or PT entry
            return index;
	}// end of toreplace
 
   void update(int numb) { 
	int index = numb;
        PT_entry[index].valid=0;
	PT_entry[index].lru=0;
        if (PT_entry[index].dirty==1) {
 System.out.println(" Need to put into Write page");
	}// put into write
	//PT_entry[index].dirty=0;
	} // end update	
   void lruupdate(int page) { 
		
		//int change=size_of_PT;
		if (PT_entry[page].valid==1) 
                        {
				int change=PT_entry[page].lru;
				for (int i=0; i < size_of_PT; i++) 
						{
			 if ((PT_entry[i].valid==1)&&(PT_entry[i].lru<change)) 
(PT_entry[i].lru)++;
				   		         
						}
			}
                 else {
			for (int i=0; i < size_of_PT; i++) 
			if (PT_entry[i].valid==1) (PT_entry[i].lru)++;
                   }
			 PT_entry[page].lru=0;
	} //endlruupdate
} // end of pagetable
class mainmem_entry
{
  public int indexed_by_physical_page_number;
  public int correspondingto_virtual_page_number; 
  public String data;
 
	mainmem_entry() 
	{
 		indexed_by_physical_page_number=0;
 		data = "Page data ";
 		correspondingto_virtual_page_number = 0;
	}
}
class MainMem {
      private int size;
      public mainmem_entry entry[];
      MainMem(int size) {
             
	this.size = size; 
        entry = new mainmem_entry[size];
           for (int i = 0; i < size; i++) 
        { 
  		entry[i] = new mainmem_entry();  // initialization of array of objects!! imp!! 
   		entry[i].indexed_by_physical_page_number = i;
        }
} //end of constructor
void display() {
	for (int i=0; i < size; i++)
		{
		
		System.out.println(" MM entry index " + " " + i + "V " + 
entry[i].correspondingto_virtual_page_number + "P " + 
entry[i].indexed_by_physical_page_number + "D" + entry[i].data);
			
		}
	} //end of display	
} // end of MainMem
// Main Memory Details
class Write_Buffer {
	  public PageTable_entry replace= new PageTable_entry();
	  public PageTable write = new PageTable(32);	
        
  Write_Buffer(PageTable change) {
		//public PageTable write = new PageTable(32);
		write = change;
		//write.dirty = 1;
		for (int i = 0; i < write.size_of_PT; i++) 
        { 
  	
   		write.PT_entry[i].dirty = 1;
        }
		change = write;
			
} // end of constructor
} // end Write_Buffer

