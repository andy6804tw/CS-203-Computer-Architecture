import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.applet.Applet;

public class RTA extends Frame
		 implements ActionListener
{
    public Table tableA;
    public Table tableB;

    public boolean twoTasks;
    
    Panel titlePanel;
    TableInputPanel tip;

    Panel mainPanel;
    CardLayout mainLayout;

    public Panel northPanel;
    Label patternLabel;
    Label currPatternLabel;
    TextField patternTextField;
    Button patternButton;
    String pattern;

    Panel southPanel;
    Button iButton;
    Button sdButton;
    Button qButton;
    Button hButton;
    Button stButton;

    Vector states;
    Vector greedyPath;
    TextPanel statsPanel;
    TextPanel helpPanel;
    boolean ps;

    public RTA()
    {
	// Set title
	super("Reservation Table Analyzer");
	
	// Does not allow user to resize window
	setResizable(false);
	
	// Set the Frame the screen size 
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	System.out.println(screen.width);
	System.out.println(screen.height);
	//if(screen.width < 800 || screen.height < 600)
	  setSize(screen);
	//else
	//  setSize(new Dimension(1050,600));
	setLayout(new BorderLayout());
	setBackground(Color.black);

	// Create North Panel (Pattern panel)
	createNorthPanel();

	// Create south Panel buttons
	createSouthPanel();

	// Create title screen
	createTitlePanel();

	// Create main panel (Center panel)
	createMainPanel();

	// Statistics panel
	statsPanel = new TextPanel();
	
	// Help panel
	createHelpPanel();

	// Show frame
	show();

	// Close window and exit program
	addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent event) {
		dispose();
		System.exit(0);
	    }
	});

	ps = false;
    }

    public void createHelpPanel()
    {  
	helpPanel = new TextPanel();
	mainPanel.add(helpPanel.scrollBar, "Help");

	helpPanel.append("Help:", Color.magenta);
	helpPanel.appendNewLine("", Color.black);
	helpPanel.appendNewLine("About Reservation Table Analyzer:", Color.green);
	helpPanel.appendNewLine("This software is used to analyze reservation tables with one or two tasks", Color.white);
	helpPanel.appendNewLine("running on a pipeline.", Color.white);
	helpPanel.appendNewLine("", Color.black);
	helpPanel.appendNewLine("Building a Reservation Table:", Color.green);
	helpPanel.appendNewLine("1. Click the Reservation Table Input Button.", Color.white);
	helpPanel.appendNewLine("2. Choose between single task mode and two task mode using the check boxes.", Color.white);
	helpPanel.appendNewLine("3. Specify the number of resources and time slots in the reservation table", Color.white);
	helpPanel.appendNewLine("    using the text fields found at the bottom of the screen.", Color.white);
	helpPanel.appendNewLine("4. Click the Build Button and an empty reservation table will be displayed.", Color.white);
	helpPanel.appendNewLine("5. Fill in the reservation table by clicking on the squares that you want to", Color.white);
	helpPanel.appendNewLine("    mark with an X. To unmark a square, simply click on the square you", Color.white);
	helpPanel.appendNewLine("    want to unmark.", Color.white);
	helpPanel.appendNewLine(" ", Color.black);
	helpPanel.appendNewLine("State Diagram:", Color.green);
	helpPanel.appendNewLine("Clicking on the State Diagram Button will bring up the state diagram for the", Color.white);
	helpPanel.appendNewLine("current reservation table. On the left hand side, a list of all the possible states", Color.white); 
	helpPanel.appendNewLine("are listed. On the right hand side, the edges of the state diagram are listed.", Color.white);
	helpPanel.appendNewLine(" ", Color.black);
	helpPanel.appendNewLine("Pipeline Statistics: ", Color.green);
	helpPanel.appendNewLine("Clicking on the Pipeline Statistics button will display the following information", Color.white); 
	helpPanel.appendNewLine("for the current reservation table:", Color.white);
	helpPanel.appendNewLine("1. Forbidden Latency", Color.white);
	helpPanel.appendNewLine("2. Pipeline Collision Vector", Color.white);
	helpPanel.appendNewLine("3. Greedy Cycle", Color.white);
	helpPanel.appendNewLine("4. Minimum Average Latency", Color.white);
	helpPanel.appendNewLine("5. Throughput", Color.white);
	helpPanel.appendNewLine("In two task mode, the greedy cycle, minimum average latency, and throughput",Color.white);
	helpPanel.appendNewLine("can change by changing the Task Pattern. The Task Pattern can be specified",Color.white); 
	helpPanel.appendNewLine("by entering a sequence of A's and B's in the text field found at the top", Color.white);
	helpPanel.appendNewLine("of the program.",Color.white);
	helpPanel.appendNewLine(" ", Color.black);
    }

    public void setPatternEnabled(boolean value)
    {
	if(!value) {
	    patternLabel.setEnabled(false);
	    patternTextField.setEnabled(false);
	    currPatternLabel.setEnabled(false);
	    patternButton.setEnabled(false);	
	    //patternLabel.repaint();
	    //currPatternLabel.repaint();
	    twoTasks = false;
	}
	else {
	    patternLabel.setEnabled(true);
	    patternTextField.setEnabled(true);
	    currPatternLabel.setEnabled(true);
	    patternButton.setEnabled(true);	
	    //patternLabel.repaint();
	    //currPatternLabel.repaint();
	    twoTasks = true;
	}
    }

    public void createNorthPanel()
    {
	northPanel = new Panel();
	northPanel.setBackground(Color.white);

	patternLabel = new Label("Enter Task Pattern:");
	patternLabel.setFont(new Font("Arial", Font.BOLD, 12));
	northPanel.add(patternLabel);
	patternTextField = new TextField(20);
	patternTextField.addActionListener(this);
	northPanel.add(patternTextField);
	patternButton = new Button("Enter");
	patternButton.setFont(new Font("Arial", Font.BOLD, 12));
	patternButton.addActionListener(this);
	northPanel.add(patternButton);
	pattern = new String("A");
	currPatternLabel = new Label("Current Pattern: ("+pattern+
				     ")*                        ");
	currPatternLabel.setForeground(Color.blue);
	currPatternLabel.setFont(new Font("Arial", Font.BOLD, 12));
	northPanel.add(currPatternLabel);

	setPatternEnabled(false);

	add(northPanel, "North");
    }
	
    public void createTitlePanel()
    {
	titlePanel = new Panel();
	titlePanel.setLayout(new BorderLayout());
	titlePanel.setBackground(Color.black);
	
	Label titleLabel = new Label("Reservation Table Analyzer", Label.CENTER);
	titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
	titleLabel.setBackground(Color.black);
	titleLabel.setForeground(Color.green);
	titlePanel.add(titleLabel, "Center");
	Label nameLabel = new Label("University of Massachusetts", Label.CENTER);
	nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
	nameLabel.setBackground(Color.black);
	nameLabel.setForeground(Color.red);
	titlePanel.add(nameLabel, "South");
    }

    public void createMainPanel()
    {
	mainPanel = new Panel();
	mainPanel.setLayout(new BorderLayout());
	mainPanel.setBackground(Color.black);
	
	mainLayout = new CardLayout();
	mainPanel.setLayout(mainLayout);

	mainPanel.add("titlePanel", titlePanel);
	mainLayout.show(mainPanel, "titlePanel");

	tip = new TableInputPanel(this);
	mainPanel.add("TableInputPanel", tip);

	add(mainPanel, "Center");
    }

    public void createSouthPanel()
    {
	southPanel = new Panel();
	southPanel.setLayout(new GridLayout(1,5));
	
	iButton = new Button("Reservation Table Input");
	sdButton = new Button("State Diagram");
	stButton = new Button("Pipeline Statistics");
	hButton = new Button("Help");
	qButton = new Button("Quit");

	iButton.setForeground(Color.blue);
	iButton.setBackground(Color.lightGray);
	iButton.setFont(new Font("Arial", Font.BOLD, 12));
	sdButton.setForeground(Color.blue);
	sdButton.setBackground(Color.lightGray);
	sdButton.setFont(new Font("Arial", Font.BOLD, 12));
	stButton.setForeground(Color.blue);
	stButton.setBackground(Color.lightGray);
	stButton.setFont(new Font("Arial", Font.BOLD, 12));
	hButton.setForeground(Color.blue);
	hButton.setBackground(Color.lightGray);
	hButton.setFont(new Font("Arial", Font.BOLD, 12));
	qButton.setForeground(Color.blue);
	qButton.setBackground(Color.lightGray);
	qButton.setFont(new Font("Arial", Font.BOLD, 12));

	iButton.addActionListener(this);
	hButton.addActionListener(this);
	sdButton.addActionListener(this);
	stButton.addActionListener(this);
	qButton.addActionListener(this);

	southPanel.add(iButton);
	southPanel.add(sdButton);
	southPanel.add(stButton);
	southPanel.add(hButton);
	southPanel.add(qButton);

	add(southPanel, "South");
    }

    public void actionPerformed(ActionEvent e) 
    {
        Object src = e.getSource();

	if(src == iButton) {
	    mainLayout.show(mainPanel, "TableInputPanel");
	    ps = false;
	}

	if(src == sdButton) {
	    if(twoTasks && tableA.isEqual(tableB)) {
		setPatternEnabled(false);
	    }
	    if(findStates()) {
		printStates();
		mainPanel.add("StateDiagramPanel", new StateDiagramPanel(states));
		mainLayout.show(mainPanel, "StateDiagramPanel");
	    }
	    ps = false;
	}

	if(src == stButton) {
	    if(twoTasks && tableA.isEqual(tableB)) {
		setPatternEnabled(false);
	    }
	    if(findStates()) {
		if(printStats()) {
		    mainPanel.add("Statistics", statsPanel);
		    mainLayout.show(mainPanel, "Statistics");
		}
	    }
	    ps = true;
	}

	if(src == hButton) {
	    mainLayout.show(mainPanel, "Help");
	    System.out.println("help");
	    ps = false;
	}
	    
	if (src == qButton) {
	    dispose();
	    System.exit(0);
	}

	if (src == patternButton || src == patternTextField) {
	    updatePattern(patternTextField.getText());
	    patternTextField.setText("");
	}
    }

    // Process the pattern entered by the user
    // Check for invalid patterns
    public void updatePattern(String input)
    {
	if(input.length()==0)
	    return;

	input = input.toUpperCase();
	input = input.trim();
	char[] inArray = input.toCharArray();

	// Check for invalid inputs
	for(int i=0; i<inArray.length; i++)
	    if(inArray[i]!='A' && inArray[i]!='B') {
		new ErrorBox(this, "Error", "Invalid Pattern Error: A and B only");
		return;
	    }
	pattern = input;
	currPatternLabel.setText("Current Pattern: ("+pattern+")*");
	greedyPath = null;
	
	if(ps) {
	    printStats();
	    mainPanel.add(statsPanel, "Statistics");
	    mainLayout.show(mainPanel, "Statistics");
	}
    }

    // Adds a state to the current state list
    public State addState(Matrix m)
    {
	// Check if the state is already in the list
	for(int i=0; i<states.size(); i++) {
	    Matrix tmp = ((State)states.elementAt(i)).m;
	    if(tmp.isEqual(m))
		return (State)states.elementAt(i);
	}
	states.addElement(new State(states.size()+1,m));
	return (State)states.elementAt(states.size()-1);
    }

    // Find all the possible states for a specific set of 
    // Reservation Tables
    public boolean findStates()
    {
	Matrix matrixA=null;
	Matrix matrixB=null;

	if(tableA==null) {
	    new ErrorBox(this,"Error", "No reservation table data available");
	    return false;
	}

	// Find starting states
	states = new Vector(10);

	if(twoTasks) {
	    matrixA = new Matrix(2, tableA.getCols());
	    matrixA.matrix[0] = tableA.after(tableA.getTable());
	    matrixA.matrix[1] = tableB.after(tableA.getTable());
	    matrixB = new Matrix(2, tableB.getCols());
	    matrixB.matrix[0] = tableA.after(tableB.getTable());
	    matrixB.matrix[1] = tableB.after(tableB.getTable());

	    addState(matrixA);
	    addState(matrixB);
	}
	else {
	    matrixA = new Matrix(1, tableA.getCols());
	    matrixA.matrix[0] = tableA.getPCV();
	    addState(matrixA);
	}

	State nextState = null;
	// Find states
	// Also find the edges of the state transition graph
	if(twoTasks) {
	    for(int i=0; i<states.size(); i++) {
		State currState = (State)states.elementAt(i);
		Matrix currMatrix = currState.m;
		for(int j=0; j<currMatrix.getCols(); j++) {
		    // Every time there is a '0' in the state there is a chance
		    // that this '0' may lead to a new state
		    if(currMatrix.matrix[0][j]==0) {
			// Shift the current matrix left by j amount and OR it with Matrix A to
			// get the new state
			nextState = addState(Matrix.orMatrix(currMatrix.shiftLeft(j), 
							     matrixA));
			// Update the path list for the current state
			currState.edgeA.addElement(new Edge('A', j, nextState));
		    }
			
		    if(currMatrix.matrix[1][j]==0) {
			// Shift the current matrix left by j amount and OR it with 
			// Matrix B to get the new state
			nextState = addState(Matrix.orMatrix(currMatrix.shiftLeft(j), 
								 matrixB));
			// Update the path list for the current state
			currState.edgeB.addElement(new Edge('B', j, nextState));
		    }
		}
	    }

	    State firstState = (State)states.elementAt(0);
	    State secondState = (State)states.elementAt(1);
	    for(int i=0; i<states.size(); i++) {
		State s = (State)states.elementAt(i);
		s.edgeA.addElement(new Edge('A',matrixA.getCols(),firstState));
		s.edgeB.addElement(new Edge('B',matrixA.getCols(),secondState));
	    }
	}  	       
	else {
	    for(int i=0; i<states.size(); i++) {
		State currState = (State)states.elementAt(i);
		Matrix currMatrix = currState.m;
		for(int j=0; j<currMatrix.getCols(); j++) {
		    // Every time there is a '0' in the state there is a chance
		    // that this '0' may lead to a new state
		    if(currMatrix.matrix[0][j]==0) {
			// Shift the current matrix left by j amount and OR it with Matrix A to
			// get the new state
			nextState = addState(Matrix.orMatrix(currMatrix.shiftLeft(j), 
							     matrixA));
			// Update the path list for the current state
			currState.edgeA.addElement(new Edge(' ', j, nextState));
		    }
		}
	    }
	    State firstState = (State)states.elementAt(0);
	    for(int i=0; i<states.size(); i++) {
		State s = (State)states.elementAt(i);
		s.edgeA.addElement(new Edge(' ',matrixA.getCols(),firstState));
	    }
	}

	return true;
    }

    public void printStates()
    {
	if(states==null) {
	    if(!findStates())
		return;
	}

	for(int i=0; i<states.size(); i++) {
	    State s = (State)states.elementAt(i);
	    s.print();
	}
    }

    public boolean findGreedyCycle()
    {
	boolean found = false;
	Vector startStates = new Vector();
	int offset = -1;
	char[] patternArray = {'A'};

	if(twoTasks)
	    // Convert the pattern string into an array
	    patternArray = pattern.toCharArray();

	if(tableA==null) {
	    new ErrorBox(this,"Error", "No reservation table data available");
	    return false;
	}
	
	// If there are no states go and calculate them first before
	// continuing on
	if(states==null)
	    findStates();
	if(states.size()==0)
	    findStates();

	// Allocate space fore greedy cycle
	greedyPath = new Vector();

	// Starting state
	State startState = (State)states.elementAt(0);
	startStates.addElement(startState);
	greedyPath.addElement(new Edge('A',0,startState));
	// Current state
	State currState = startState;

	// Start at state #1 and perform the tasks in the task pattern and keep track
	// of which paths were taken and which states were visited. After all the tasks
	// have been completed compare the current state with the starting states.
	// If the current state is found in the list of starting states then then a greedy 
	// cycle has been found. If they are not equal
	// then add the current state to the starting state list and perform the tasks
	// again. Keep on doing this until the greedy cycle has been found. 
	while(!found) {

	    for(int i=0; i<patternArray.length; i++) {
		if(patternArray[i]=='A') {
		    greedyPath.addElement((Edge)currState.edgeA.elementAt(0));
		    currState = ((Edge)(currState.edgeA.elementAt(0))).nextState;
		}
		else {
		    greedyPath.addElement((Edge)currState.edgeB.elementAt(0));
		    currState = ((Edge)(currState.edgeB.elementAt(0))).nextState;
		}
	    }    
	    
	    // Check if a greedy cycle has been found or not		
	    if(startStates.lastIndexOf(currState)!=-1) {
		found = true;
		// Locate the index of the starting state in the greedy path vector
		if(startStates.size()!=1) {
		    for(int j=greedyPath.size()-(patternArray.length+1); j>=0; j--) {
			Edge p = (Edge)greedyPath.elementAt(j);
			if(currState == (State)p.nextState) {
			    offset = j;
			    break;
			}
		    }
		}
		else
		    offset = 0;
	    }
	    else {
		// Update startStates list with the current state
		startStates.addElement(currState);
	    }
	}

	// Remove all states before the starting state in the greedy path vector
	for(int i=0; i<offset+1; i++)
	    greedyPath.removeElementAt(0);

	return true;
    }

    public void printGreedyCycle()
    {
	statsPanel.appendNewLine("Greedy Cycle:", Color.green);
	String result = "";
	for(int i=0; i<greedyPath.size(); i++) {
	    Edge e = (Edge)greedyPath.elementAt(i);
	    if(i!=greedyPath.size()-1)
		result += (String.valueOf(e.task) + String.valueOf(e.cycles) + ",");
	    else
		result +=(String.valueOf(e.task) + String.valueOf(e.cycles));
	}
	statsPanel.append("("+result+")*", Color.white);
	statsPanel.appendNewLine("", Color.black);
    }

    public void printPCV()
    {
	statsPanel.appendNewLine("Pipeline Collision Vector:", Color.green);
	if(twoTasks) {
	    statsPanel.appendNewLine("Table A:", Color.cyan);
	    statsPanel.append(tableA.getPCVstring(), Color.white);
	    statsPanel.appendNewLine("Table B:", Color.cyan);
	    statsPanel.append(tableB.getPCVstring(), Color.white);
	}
	else {
	    statsPanel.append(tableA.getPCVstring(), Color.white);
	}
	statsPanel.appendNewLine("", Color.black);
    }

    public void printForbiddenLatency()
    {
	statsPanel.appendNewLine("Forbidden Latencies:", Color.green);
	if(twoTasks) {
	    statsPanel.appendNewLine("Table A:", Color.cyan);
	    statsPanel.append(tableA.getForbiddenLatencies(), Color.white);
	    statsPanel.appendNewLine("Table B:", Color.cyan);
	    statsPanel.append(tableB.getForbiddenLatencies(), Color.white);
	}
	else {
	    statsPanel.append(tableA.getForbiddenLatencies(), Color.white);
	}
	statsPanel.appendNewLine("", Color.black);
    }

    public void printMALT()
    {
	int distance=0;
	double mal=0;

	for(int i=0; i<greedyPath.size(); i++) {
	    Edge p = (Edge)greedyPath.elementAt(i);
	    distance += p.cycles;
	}

	if(greedyPath.size()!=0)
	    mal = (double)distance / (double)greedyPath.size();

	statsPanel.appendNewLine("Minimum Average Latency:", Color.green);
	statsPanel.append(""+distance+"/"+greedyPath.size()+" or "+mal, Color.white);
	statsPanel.appendNewLine("", Color.black);
	statsPanel.appendNewLine("Throughput:", Color.green);
	statsPanel.append(""+1/mal, Color.white);
    }
	
    public boolean printStats()
    {
	if(!findGreedyCycle())
	    return false;

	statsPanel = new TextPanel();

	statsPanel.append("Pipeline Statistics:", Color.magenta);
	statsPanel.appendNewLine(" ", Color.black);

	printForbiddenLatency();
	printPCV();
	printGreedyCycle();
	printMALT();

	return true;
    }

    public static void main(String args[])
    {
	RTA r = new RTA();
    }
}

