import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class StateDiagramPanel extends Panel
{
    TextPanel statesPanel;
    TextPanel edgesPanel;
    Vector states;

    public StateDiagramPanel(Vector states)
    {
	this.states = states;
	setLayout(new BorderLayout());
	statesPanel = new TextPanel();
	add(statesPanel.scrollBar,"West");
	edgesPanel = new TextPanel();
	add(edgesPanel.scrollBar, "Center");

	printStates();
	printEdges();
    }

    public void printStates()
    {
	statesPanel.append("Possible States: ", Color.magenta);
	statesPanel.appendNewLine(" ", Color.black);
	for(int i=0; i<states.size(); i++) {
	    State s = (State)states.elementAt(i);
	    s.print(statesPanel);
	    statesPanel.appendNewLine(" ", Color.black);
	}
    }

    public void printEdges()
    {
	edgesPanel.append("Graph Edges: ", Color.magenta);
	edgesPanel.appendNewLine(" ", Color.black);
	for(int i=0; i<states.size(); i++) {
	    State s = (State)states.elementAt(i);
	    s.printEdges(edgesPanel);
	    edgesPanel.appendNewLine(" ", Color.black);
	}
    }
}

