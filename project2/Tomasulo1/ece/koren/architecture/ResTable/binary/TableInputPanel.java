import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TableInputPanel extends Panel
{
    Panel tablePanels;
    TablePanel tablePanelA;
    TablePanel tablePanelB;

    Panel inputPanel;
    Checkbox singleTaskMode;
    Checkbox twoTaskMode;
    CheckboxGroup group;

    Label resourceLabel;
    Label timeLabel;

    TextField resourceTextField;
    TextField timeTextField;

    Button build;

    ScrollPane scrollerA;
    ScrollPane scrollerB;

    RTA rta;

    public TableInputPanel(RTA rta)
    {
	this.rta = rta;
	
	setLayout(new BorderLayout());	

	createInputPanel();
	createTablePanels();
    }

    public void createTablePanels()
    {
	tablePanels = new Panel();
	tablePanels.setLayout(new GridLayout(1,2));
	
	scrollerA = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
	scrollerB = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
	
	tablePanels.add(scrollerA);
	tablePanels.add(scrollerB);

	add(tablePanels, "Center");
    }

    public void createInputPanel()
    {
	inputPanel = new Panel();
	inputPanel.setBackground(Color.white);

	group = new CheckboxGroup();
	singleTaskMode = new Checkbox("Single Task", true, group);
	singleTaskMode.setFont(new Font("Arial", Font.BOLD, 12));
	twoTaskMode = new Checkbox("Two Tasks", false, group);
	twoTaskMode.setFont(new Font("Arial", Font.BOLD, 12));
	inputPanel.add(singleTaskMode);
	inputPanel.add(twoTaskMode);

	resourceLabel = new Label("Number of Resources:");
	timeLabel = new Label("Number of Time slots:");
	build = new Button("Build");

	resourceTextField = new TextField(10);
	timeTextField = new TextField(10);

	resourceLabel.setFont(new Font("Arial", Font.BOLD, 12));
	resourceLabel.setForeground(Color.red);
	timeLabel.setFont(new Font("Arial", Font.BOLD, 12));
	timeLabel.setForeground(Color.red);
	
	build.setFont(new Font("Arial", Font.BOLD, 12));

	inputPanel.add(resourceLabel);
	inputPanel.add(resourceTextField);
	inputPanel.add(timeLabel);
	inputPanel.add(timeTextField);
	inputPanel.add(build);
	
	build.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		createTable(resourceTextField.getText(), 
			    timeTextField.getText());
	    }
	});

	add(inputPanel, "South");
    }

    public void createTable(String resources, String timeSlots)
    {
	Integer tableRows;
	Integer tableCols;

	// Get rid of white spaces
	resources = resources.trim();
	timeSlots = timeSlots.trim();

	// Get the integer value from the string inputs
	try {
	    tableRows = Integer.valueOf(resources);
	    tableCols = Integer.valueOf(timeSlots);
	} catch (Exception e) {
	    new ErrorBox(rta, "Error", "Numbers only");
	    return;
	}
	if(twoTaskMode.getState()) {
	    rta.tableA = new Table(tableRows.intValue(), tableCols.intValue());
	    rta.tableB = new Table(tableRows.intValue(), tableCols.intValue());

	    tablePanelA = new TablePanel(rta.tableA, "A", 
					 tableRows.intValue(), tableCols.intValue());
	    tablePanelB = new TablePanel(rta.tableB, "B", 
					 tableRows.intValue(), tableCols.intValue());

	    rta.setPatternEnabled(true);
	}
	else {
	    rta.tableA = new Table(tableRows.intValue(), tableCols.intValue());
 	    tablePanelA = new TablePanel(rta.tableA, "A", 
					 tableRows.intValue(), tableCols.intValue());
	    tablePanelB = new TablePanel(rta.tableB,"",0,0);
	    rta.setPatternEnabled(false);
	}

	scrollerA.add(tablePanelA);
	scrollerB.add(tablePanelB);
	
	scrollerA.getParent().validate();
	scrollerB.getParent().validate();
	scrollerA.repaint();
	scrollerB.repaint();
	tablePanelA.repaint();
	tablePanelB.repaint();
	tablePanels.repaint();
	repaint();
    }
}
    
